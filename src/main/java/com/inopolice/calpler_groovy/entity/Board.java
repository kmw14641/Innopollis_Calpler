package com.inopolice.calpler_groovy.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 45)
    @NotNull
    private String name;
    @Column(length = 150)
    @NotNull
    private String department;
    @Column(length = 150)
    @NotNull
    private String job;
    @Column(length = 150)
    private String experience;
    @Column(columnDefinition = "TEXT")
    private String text;
    @Column(length = 150)
    private String link;
    @Column(length = 150)
    private String filename;
    @Column(length = 300)
    private String filepath;
    @Column(length = 45)
    private String author;
    @Column(length = 45)
    private String tag;
    @Column(length = 45)
    private String university;
}
