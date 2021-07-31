package com.baemin.server.ceo.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@RequiredArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    @ColumnDefault( "1" )
    private int active; // 0: inactive, 1: active

    @Builder
    public Board( long id, String name, String description ) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
