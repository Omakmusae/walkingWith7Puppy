package com.turkey.walkingwith7puppy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String area;

    @ManyToOne
    @JoinColumn(name="memberId", nullable = false)
    private Member member;
}
