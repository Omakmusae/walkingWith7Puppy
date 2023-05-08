package com.turkey.walkingwith7puppy.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.turkey.walkingwith7puppy.dto.request.BoardRequest;
import com.turkey.walkingwith7puppy.dto.response.BoardResponse;
import com.turkey.walkingwith7puppy.security.UserDetailsImpl;
import com.turkey.walkingwith7puppy.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardController {

	private final BoardService boardService;

	@GetMapping("/boards")
	public ResponseEntity<List<BoardResponse>> getBoardList() {

		return ResponseEntity.status(HttpStatus.OK)
				.body(boardService.searchBoards());
	}

	@GetMapping("/boards/{boardId}")
	public ResponseEntity<BoardResponse> getBoard(@PathVariable final Long boardId) {

		return ResponseEntity.status(HttpStatus.OK)
				.body(boardService.searchBoard(boardId));
	}

	@PostMapping("/boards")
	public ResponseEntity<Void> createBoard(
			@AuthenticationPrincipal final UserDetailsImpl userDetails,
			@RequestBody @Valid final BoardRequest boardRequest) {

		boardService.createBoard(userDetails.getMember(), boardRequest);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@PutMapping("/boards/{boardId}")
	public ResponseEntity<Void> updateBoard(
			@AuthenticationPrincipal final UserDetailsImpl userDetails,
			@PathVariable final Long boardId,
			@RequestBody @Valid final BoardRequest boardRequest) {

		boardService.updateBoard(userDetails.getMember(), boardId, boardRequest);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@DeleteMapping("/boards/{boardId}")
	public ResponseEntity<Void> deleteArticle(
			@AuthenticationPrincipal final UserDetailsImpl userDetails,
			@PathVariable final Long boardId) {

		boardService.deleteBoard(userDetails.getMember(), boardId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/boards")
	public ResponseEntity<List<BoardResponse>> searchBoardList(@RequestParam String address) {
		System.out.println(address);
		return ResponseEntity.status(HttpStatus.OK)
				.body(boardService.searchBoards(address));
	}

}
