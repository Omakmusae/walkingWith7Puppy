package com.turkey.walkingwith7puppy.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.turkey.walkingwith7puppy.dto.request.BoardRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String address;

    @Column
    private String img;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    private Board(String title, String content, String address, String img , Member member) {
        this.title = title;
        this.content = content;
        this.address = address;
        this.img = img;
        this.member = member;
    }

    public static Board of(String title, String content, String address, String img ,Member member) {
        return new Board(title, content, address, img ,member);
    }

    public void updateBoard(BoardRequest boardRequest){
        this.title = boardRequest.getTitle();
        this.content = boardRequest.getContent();
        this.address = boardRequest.getAddress();
        this.img = boardRequest.getImg();
    }
}
