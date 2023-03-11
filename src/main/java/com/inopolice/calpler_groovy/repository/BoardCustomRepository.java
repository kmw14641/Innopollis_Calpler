package com.inopolice.calpler_groovy.repository;

import com.inopolice.calpler_groovy.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardCustomRepository {
    Page<Board> findAllWithTag(Pageable pageable, List<String> tags, String university);
}
