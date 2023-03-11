package com.inopolice.calpler_groovy.repository;

import com.inopolice.calpler_groovy.entity.Board;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static com.inopolice.calpler_groovy.entity.QBoard.board;

public class BoardCustomRepositoryImpl implements BoardCustomRepository {
    private JPAQueryFactory jpaQueryFactory;
    public BoardCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Board> findAllWithTag(Pageable pageable, List<String> tags, String university) {
        List<Board> content = jpaQueryFactory
                .selectFrom(board)
                .where(containsTags(tags), board.university.eq(university))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.id.desc())
                .fetch();
        Long count = jpaQueryFactory
                .select(board.count())
                .from(board)
                .where(containsTags(tags), board.university.eq(university))
                .fetchOne();
        return new PageImpl<>(content, pageable, count);
    }

    private BooleanBuilder containsTags(List<String> tags) {
        if(tags.get(0).equals("")) return null;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String tag: tags) {
            booleanBuilder.and(board.tag.contains(tag));
        }
        return booleanBuilder;
    }
}
