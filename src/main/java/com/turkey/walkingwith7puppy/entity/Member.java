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
  private String email;

  private Member(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public static Member of(String username, String password, String email) {
    return new Member(username, password, email);
  }

}
