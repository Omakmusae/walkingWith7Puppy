package com.turkey.walkingwith7puppy.repository;

import java.util.List;

import com.turkey.walkingwith7puppy.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

	@Query("select distinct B from Board B left join fetch B.comments")
	List<Board> findAllJoinFetch();

	@Query("select distinct B from Board B left join fetch B.comments where B.address like %:address%")
	List<Board> findAllJoinFetchANDAddress(@Param("address") String address);

}
