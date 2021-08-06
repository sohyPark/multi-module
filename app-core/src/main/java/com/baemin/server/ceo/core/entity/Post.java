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
public class Post extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    private String title;

    @Setter
    private String contents;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;

    private long boardId;

    private int count;

    @Setter
    @ColumnDefault( "1" )
    private int active; // 0: inactive, 1: active

    @Builder
    public Post( long id, String title, String contents, User user, long boardId, int count ) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.user = user;
        this.boardId = boardId;
        this.count = count;
    }
}
