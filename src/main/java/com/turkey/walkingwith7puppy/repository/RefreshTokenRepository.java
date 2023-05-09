package com.turkey.walkingwith7puppy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkey.walkingwith7puppy.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByUsername(String username);
}
