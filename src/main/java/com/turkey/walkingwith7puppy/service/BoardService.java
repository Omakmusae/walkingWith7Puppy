package com.turkey.walkingwith7puppy.service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.turkey.walkingwith7puppy.dto.request.BoardRequest;
import com.turkey.walkingwith7puppy.dto.response.BoardResponse;
import com.turkey.walkingwith7puppy.entity.Board;
import com.turkey.walkingwith7puppy.entity.Member;
import com.turkey.walkingwith7puppy.exception.CommonErrorCode;
import com.turkey.walkingwith7puppy.exception.MemberErrorCode;
import com.turkey.walkingwith7puppy.exception.RestApiException;
import com.turkey.walkingwith7puppy.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

	private final BoardRepository boardRepository;

	private String S3Bucket = "walkingpuppy7"; // Bucket 이름
	@Autowired
	AmazonS3Client amazonS3Client;

	public List<BoardResponse> searchBoards() {

		return boardRepository.findAllJoinFetch()
				.stream()
				.map(BoardResponse::from)
				// .sorted(Comparator.comparing(BoardResponse::getCreatedAt).reversed())
				.collect(Collectors.toList());
	}

	public BoardResponse searchBoard(final Long boardId) {

		Board board = findBoardByIdOrElseThrow(boardId);

		return BoardResponse.from(board);
	}

	public List<BoardResponse> searchBoards(String address) {

		return boardRepository.findAllJoinFetchANDAddress(address)
				.stream()
				.map(BoardResponse::from)
				// .sorted(Comparator.comparing(BoardResponse::getCreatedAt).reversed())
				.collect(Collectors.toList());
	}

	@Transactional
	public void createBoard(final Member member, final BoardRequest boardRequest, MultipartFile file) throws IOException {
		String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
		saveimg(fileName, file);
		String imagePath = amazonS3Client.getUrl(S3Bucket, fileName).toString();
		boardRequest.setMember(member);
		boardRequest.setImg(imagePath);

		Board board = boardRepository.saveAndFlush(BoardRequest.toEntity(boardRequest));
	}


	@Transactional
	public void updateBoard(final Member member, final Long boardId, final BoardRequest boardRequest, MultipartFile file) throws IOException {

		Board board = findBoardByIdOrElseThrow(boardId);
		String[] imgId = board.getImg().split("/");
		amazonS3Client.deleteObject(S3Bucket, imgId[imgId.length - 1]);
		String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
		saveimg(fileName, file);
		String imagePath = amazonS3Client.getUrl(S3Bucket, fileName).toString();
		boardRequest.setMember(member);
		boardRequest.setImg(imagePath);

		throwIfNotOwner(board, member.getUsername());

		board.updateBoard(boardRequest);
	}

	@Transactional
	public void deleteBoard(final Member member, final Long boardId) {

		Board board = findBoardByIdOrElseThrow(boardId);
		String[] imgId = board.getImg().split("/");
		amazonS3Client.deleteObject(S3Bucket, imgId[imgId.length - 1]);
		throwIfNotOwner(board, member.getUsername());

		boardRepository.delete(board);
	}

	void saveimg (String filename, MultipartFile file) throws IOException {
		long size = file.getSize();
		ObjectMetadata objectMetaData = new ObjectMetadata();
		objectMetaData.setContentType(file.getContentType());
		objectMetaData.setContentLength(size);

		amazonS3Client.putObject(
			new PutObjectRequest(S3Bucket, filename, file.getInputStream(), objectMetaData)
				.withCannedAcl(CannedAccessControlList.PublicRead)
		);
	}

	private Board findBoardByIdOrElseThrow(Long boardId) {

		return boardRepository.findById(boardId).orElseThrow(
			() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND)
		);
	}

	private void throwIfNotOwner(Board board, String loginUsername) {

		if (!board.getMember().getUsername().equals(loginUsername))
			throw new RestApiException(MemberErrorCode.INACTIVE_MEMBER);
	}
}
