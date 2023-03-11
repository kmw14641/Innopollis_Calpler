package com.inopolice.calpler_groovy.repository;

import com.inopolice.calpler_groovy.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer>, BoardCustomRepository {
    Page<Board> findAll(Pageable pageable);
}
