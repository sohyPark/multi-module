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
public class Comment extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    private String contents;

    @ManyToOne
    private User user;

    private long postId;

    @Setter
    @ColumnDefault( "1" )
    private int active; // 0: inactive, 1: active

    @Builder
    public Comment( long id, String contents, User user, long postId ) {
        this.id = id;
        this.contents = contents;
        this.user = user;
        this.postId = postId;
    }
}
