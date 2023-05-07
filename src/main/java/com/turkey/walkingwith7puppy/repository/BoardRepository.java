package com.turkey.walkingwith7puppy.repository;

import com.turkey.walkingwith7puppy.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
