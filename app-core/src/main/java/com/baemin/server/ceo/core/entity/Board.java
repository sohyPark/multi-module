package com.baemin.server.ceo.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(unique = true)
    private String name;

    @Setter
    private String description;

    @ColumnDefault( "1" )
    @Setter
    private int active; // 0: inactive, 1: active

    @Builder
    public Board( long id, String name, String description, int active ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
    }
}
