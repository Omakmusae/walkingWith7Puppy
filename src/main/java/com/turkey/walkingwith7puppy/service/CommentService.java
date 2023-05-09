package com.turkey.walkingwith7puppy.service;

import com.turkey.walkingwith7puppy.dto.CommentDto;
import com.turkey.walkingwith7puppy.entity.Board;
import com.turkey.walkingwith7puppy.entity.Comment;
import com.turkey.walkingwith7puppy.entity.Member;
import com.turkey.walkingwith7puppy.exception.CommonErrorCode;
import com.turkey.walkingwith7puppy.exception.MemberErrorCode;
import com.turkey.walkingwith7puppy.exception.RestApiException;
import com.turkey.walkingwith7puppy.repository.BoardRepository;
import com.turkey.walkingwith7puppy.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void createComment(final Long boardId, final CommentDto commentDto, final Member member) {

        Board board = findBoardByIdOrElseThrow(boardId);

        commentDto.setBoard(board);
        commentDto.setMember(member);

        Comment comment = commentRepository.saveAndFlush(CommentDto.toEntity(commentDto));
    }

    @Transactional
    public void updateComment(final Long boardId, final Long commentId, final CommentDto commentDto, final Member member) {

        Board board = findBoardByIdOrElseThrow(boardId);
        Comment comment = findCommentByIdOrElseThrow(commentId);

        throwIfNotOwner(comment, member.getUsername());

        comment.updateContent(commentDto.getContent());
    }

    @Transactional
    public void deleteComment(final Long boardId, final Long commentId, final Member member) {

        Board board = findBoardByIdOrElseThrow(boardId);
        Comment comment = findCommentByIdOrElseThrow(commentId);

        throwIfNotOwner(comment, member.getUsername());

        commentRepository.delete(comment);
    }

    private Board findBoardByIdOrElseThrow(Long boardId) {

        return boardRepository.findById(boardId).orElseThrow(
            () -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND)
        );
    }

    private Comment findCommentByIdOrElseThrow(Long commentId) {

        return commentRepository.findById(commentId).orElseThrow(
            () -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND)
        );
    }

    private void throwIfNotOwner(Comment comment, String loginUsername) {

        if (!comment.getMember().getUsername().equals(loginUsername))
            throw new RestApiException(MemberErrorCode.INACTIVE_MEMBER);
    }
}
