package com.turkey.walkingwith7puppy.repository;

import java.util.List;

import com.turkey.walkingwith7puppy.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {

	@Query("select distinct B from Board B left join fetch B.comments")
	List<Board> findAllJoinFetch();
}
