package com.turkey.walkingwith7puppy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(force = true)
public class RefreshToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String refreshToken;

	@NotNull
	private String username;

	public RefreshToken(String refreshToken, String username) {
		this.refreshToken = refreshToken;
		this.username = username;
	}

	public RefreshToken updateToken(String refreshToken) {
		this.refreshToken = refreshToken;
		return this;
	}
}
