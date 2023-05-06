package com.turkey.walkingwith7puppy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.turkey.walkingwith7puppy.dto.request.CommentCreateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;


    public Comment(CommentCreateRequest commentCreateRequest,Board board,Member member){
        this.content = commentCreateRequest.getContent();
        this.board = getBoard();
        this.member = getMember();

    }
    public void update(CommentCreateRequest commentCreateRequest){
        this.content = commentCreateRequest.getContent();
    }


}
