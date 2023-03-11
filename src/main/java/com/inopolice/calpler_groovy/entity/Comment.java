package com.inopolice.calpler_groovy.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String text;
    @NotNull
    private Integer groupNum;
    @Column(length = 45)
    private String author;
    @NotNull
    private Integer boardId;
}
