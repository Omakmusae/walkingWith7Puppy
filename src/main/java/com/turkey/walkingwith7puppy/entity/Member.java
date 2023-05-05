package com.turkey.walkingwith7puppy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String password;
  @Column(nullable = false, unique = true)
  private String nickname;
  @Column(nullable = false, unique = true)
  private String email;

}
