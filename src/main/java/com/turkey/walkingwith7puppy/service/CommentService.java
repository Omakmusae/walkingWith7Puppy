package com.turkey.walkingwith7puppy.service;

import com.turkey.walkingwith7puppy.dto.request.CommentCreateRequest;
import com.turkey.walkingwith7puppy.entity.Board;
import com.turkey.walkingwith7puppy.entity.Comment;
import com.turkey.walkingwith7puppy.entity.Member;
import com.turkey.walkingwith7puppy.jwt.JwtUtil;
import com.turkey.walkingwith7puppy.repository.BoardRepository;
import com.turkey.walkingwith7puppy.repository.CommentRepository;
import com.turkey.walkingwith7puppy.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    private final BoardRepository boardRepository;

    @Transactional
    public void createComment(Long boardId, CommentCreateRequest commentCreateRequest, Member member){

        if(member == null){
            throw new IllegalArgumentException("로그인이 필요합니다");
        }

        Board board = boardRepository.findById(boardId).orElseThrow(
                ()  -> new IllegalArgumentException("게시물이 없습니다")
        );

        Comment comment = commentRepository.saveAndFlush(new Comment(commentCreateRequest,board,member));






    }
    @Transactional

    public void updateComment(Long commentId, CommentCreateRequest commentCreateRequest, UserDetailsImpl userDetails){

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 없습니다")
        );

        if(comment.getMember().getUsername().equals(userDetails.getUsername())){
            comment.update(commentCreateRequest);
        }
        else{
            throw new IllegalArgumentException("권한이 없습니다");
        }

    }

    @Transactional
    public void deleteComment(Long commentId,UserDetailsImpl userDetails){

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 없습니다")
        );

        if(comment.getMember().getUsername().equals(userDetails.getUsername())){
            commentRepository.delete(comment);
        }
        else{
            throw new IllegalArgumentException("권한이 없습니다");
        }

    }







}
