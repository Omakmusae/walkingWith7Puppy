package com.turkey.walkingwith7puppy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.turkey.walkingwith7puppy.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
