package com.inopolice.calpler_groovy.repository;

import com.inopolice.calpler_groovy.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBoardIdOrderByGroupNumAscIdAsc(Integer id);
    List<Comment> findByGroupNum(Integer id);
}
