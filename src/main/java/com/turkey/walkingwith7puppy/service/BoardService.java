package com.turkey.walkingwith7puppy.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkey.walkingwith7puppy.dto.request.BoardRequest;
import com.turkey.walkingwith7puppy.dto.response.BoardResponse;
import com.turkey.walkingwith7puppy.entity.Board;
import com.turkey.walkingwith7puppy.entity.Member;
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

		return boardRepository.findById(boardId)
			.map(BoardResponse::from)
			.orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - boardId: " + boardId));
	}

	@Transactional
	public void createBoard(final Member member, final BoardRequest boardRequest) {

		boardRequest.setMember(member);
		Board board = boardRepository.saveAndFlush(BoardRequest.toEntity(boardRequest));
	}

	@Transactional
	public void updateBoard(final Member member, final Long boardId, final BoardRequest boardRequest) {

		Board board = boardRepository.findById(boardId).orElseThrow(
			() -> new IllegalArgumentException("게시물이 없습니다")
		);

		if (board.getMember().getUsername().equals(member.getUsername())) {
			board.updateBoard(boardRequest);
		}
		throw new IllegalArgumentException("권한이 없습니다");
	}

	@Transactional
	public void deleteBoard(final Member member, final Long boardId) {

		Board board = boardRepository.findById(boardId).orElseThrow(
			() -> new IllegalArgumentException("게시물이 없습니다")
		);

		if (board.getMember().getUsername().equals(member.getUsername())) {
			boardRepository.deleteById(boardId);
		}
		throw new IllegalArgumentException("권한이 없습니다");
	}
}
