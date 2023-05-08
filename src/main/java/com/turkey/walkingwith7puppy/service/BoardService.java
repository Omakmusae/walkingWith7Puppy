package com.turkey.walkingwith7puppy.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

		return boardRepository.findByAddressJoinFetch(address)
			.stream()
			.map(BoardResponse::from)
			// .sorted(Comparator.comparing(BoardResponse::getCreatedAt).reversed())
			.collect(Collectors.toList());
	}

	@Transactional
	public void createBoard(final Member member, final BoardRequest boardRequest) {

		boardRequest.setMember(member);

		Board board = boardRepository.saveAndFlush(BoardRequest.toEntity(boardRequest));
	}

	@Transactional
	public void updateBoard(final Member member, final Long boardId, final BoardRequest boardRequest) {

		Board board = findBoardByIdOrElseThrow(boardId);

		throwIfNotOwner(board, member.getUsername());

		board.updateBoard(boardRequest);
	}

	@Transactional
	public void deleteBoard(final Member member, final Long boardId) {

		Board board = findBoardByIdOrElseThrow(boardId);

		throwIfNotOwner(board, member.getUsername());

		boardRepository.delete(board);
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
