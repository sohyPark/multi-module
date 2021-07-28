package com.baemin.server.ceo.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    private String id;

    private String name;

    private String password;

    private int count;

    @Builder
    public User( long seq, String id, String name, String password, int count ) {
        this.seq = seq;
        this.id = id;
        this.name = name;
        this.password = password;
        this.count = count;
    }
}