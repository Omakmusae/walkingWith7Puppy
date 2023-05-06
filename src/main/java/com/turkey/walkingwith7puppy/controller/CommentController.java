package com.turkey.walkingwith7puppy.controller;


import com.turkey.walkingwith7puppy.dto.request.CommentCreateRequest;
import com.turkey.walkingwith7puppy.security.UserDetailsImpl;
import com.turkey.walkingwith7puppy.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor

public class CommentController {

    private final CommentService commentService;


    @PostMapping("/boards/{boardId}/comments")
    public void createComment(@PathVariable Long boardId, @RequestBody CommentCreateRequest commentCreateRequest, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.createComment(boardId,commentCreateRequest,userDetails.getMember());
    }

    @PutMapping("/boards/comments/{commentId}")
    public void updateComment(@PathVariable Long commentId,@RequestBody CommentCreateRequest commentCreateRequest,@AuthenticationPrincipal UserDetailsImpl userDetails){
        updateComment(commentId,commentCreateRequest,userDetails);

    }

    @DeleteMapping("/boards/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        deleteComment(commentId,userDetails);
    }
}
