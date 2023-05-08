package com.turkey.walkingwith7puppy.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
		@RequestPart(value = "data") @Valid final BoardRequest boardRequest,
		@RequestPart(value = "ImgUrl") MultipartFile file) throws
		IOException {

		boardService.createBoard(userDetails.getMember(), boardRequest, file);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@PutMapping("/boards/{boardId}")
	public ResponseEntity<Void> updateBoard(
		@AuthenticationPrincipal final UserDetailsImpl userDetails,
		@PathVariable final Long boardId,
		@RequestPart(value = "data") @Valid final BoardRequest boardRequest,
		@RequestPart(value = "ImgUrl") MultipartFile file) throws IOException {
		boardService.updateBoard(userDetails.getMember(), boardId, boardRequest, file);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@DeleteMapping("/boards/{boardId}")
	public ResponseEntity<Void> deleteArticle(
		@AuthenticationPrincipal final UserDetailsImpl userDetails,
		@PathVariable final Long boardId) {

		boardService.deleteBoard(userDetails.getMember(), boardId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}
