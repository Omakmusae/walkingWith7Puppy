package com.turkey.walkingwith7puppy.service;

import com.turkey.walkingwith7puppy.dto.request.CommentRequest;
import com.turkey.walkingwith7puppy.entity.Board;
import com.turkey.walkingwith7puppy.entity.Comment;
import com.turkey.walkingwith7puppy.entity.Member;
import com.turkey.walkingwith7puppy.repository.BoardRepository;
import com.turkey.walkingwith7puppy.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void createComment(final Long boardId, final CommentRequest commentRequest, final Member member) {

        Board board = boardRepository.findById(boardId).orElseThrow(
            () -> new IllegalArgumentException("게시물이 없습니다")
        );

        commentRequest.setBoard(board);
        commentRequest.setMember(member);
        Comment comment = commentRepository.saveAndFlush(CommentRequest.toEntity(commentRequest));
    }

    @Transactional
    public void updateComment(final Long boardId, final Long commentId, final CommentRequest commentRequest, final Member member) {

        Board board = boardRepository.findById(boardId).orElseThrow(
            () -> new IllegalArgumentException("게시물이 없습니다")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
            () -> new IllegalArgumentException("댓글이 없습니다")
        );

        if (comment.getMember().getUsername().equals(member.getUsername())) {
            comment.updateContent(commentRequest);
        } else {
            throw new IllegalArgumentException("권한이 없습니다");
        }

    }

    @Transactional
    public void deleteComment(final Long boardId, final Long commentId, final Member member) {

        Board board = boardRepository.findById(boardId).orElseThrow(
            () -> new IllegalArgumentException("게시물이 없습니다")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
            () -> new IllegalArgumentException("댓글이 없습니다")
        );

        if (comment.getMember().getUsername().equals(member.getUsername())) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("권한이 없습니다");
        }

    }
}
