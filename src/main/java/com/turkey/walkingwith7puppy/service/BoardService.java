package com.turkey.walkingwith7puppy.service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.turkey.walkingwith7puppy.dto.BoardDto;
import com.turkey.walkingwith7puppy.dto.response.BoardResponse;
import com.turkey.walkingwith7puppy.entity.Board;
import com.turkey.walkingwith7puppy.entity.Member;
import com.turkey.walkingwith7puppy.exception.CommonErrorCode;
import com.turkey.walkingwith7puppy.exception.MemberErrorCode;
import com.turkey.walkingwith7puppy.exception.RestApiException;
import com.turkey.walkingwith7puppy.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;

	private String S3Bucket = "walkingpuppy7"; // Bucket 이름
	private final AmazonS3Client amazonS3Client;

	@Transactional(readOnly = true)
	public List<BoardResponse> searchBoards() {

		return boardRepository.findAllJoinFetch()
			.stream()
			.map(BoardResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public BoardResponse searchBoard(final Long boardId) {

		Board board = findBoardByIdOrElseThrow(boardId);

		return BoardResponse.from(board);
	}

	@Transactional(readOnly = true)
	public List<BoardResponse> searchBoards(String address) {

		return boardRepository.findByAddressJoinFetch(address)
			.stream()
			.map(BoardResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public void createBoard(final Member member, final BoardDto boardDto, final MultipartFile file) {

		String imagePath = saveImg(file);

		boardDto.setMember(member);
		boardDto.setImg(imagePath);

		Board board = boardRepository.saveAndFlush(BoardDto.toEntity(boardDto));
	}

	@Transactional
	public void updateBoard(final Member member, final Long boardId, final BoardDto boardDto, final MultipartFile file) {

		Board board = findBoardByIdOrElseThrow(boardId);

		throwIfNotOwner(board, member.getUsername());

		deleteImg(board);

		String imagePath = saveImg(file);

		board.updateBoard(boardDto.getTitle(), boardDto.getContent(), boardDto.getAddress(), imagePath);
	}

	@Transactional
	public void deleteBoard(final Member member, final Long boardId) {

		Board board = findBoardByIdOrElseThrow(boardId);

		throwIfNotOwner(board, member.getUsername());

		deleteImg(board);

		boardRepository.delete(board);
	}

	private String saveImg (final MultipartFile file) {

		String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
		long size = file.getSize();
		ObjectMetadata objectMetaData = new ObjectMetadata();
		objectMetaData.setContentType(file.getContentType());
		objectMetaData.setContentLength(size);

		try {
			amazonS3Client.putObject(
				new PutObjectRequest(S3Bucket, fileName, file.getInputStream(), objectMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
		} catch (IOException e) {
			throw new RestApiException(CommonErrorCode.IO_EXCEPTION);
		}

		return amazonS3Client.getUrl(S3Bucket, fileName).toString();
	}

	private void deleteImg(final Board board) {

		String[] imgId = board.getImg().split("/");
		amazonS3Client.deleteObject(S3Bucket, imgId[imgId.length - 1]);
	}

	private Board findBoardByIdOrElseThrow(final Long boardId) {

		return boardRepository.findById(boardId).orElseThrow(
			() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND)
		);
	}

	private void throwIfNotOwner(final Board board, final String loginUsername) {

		if (!board.getMember().getUsername().equals(loginUsername))
			throw new RestApiException(MemberErrorCode.INACTIVE_MEMBER);
	}
}
