package com.baemin.server.ceo.board.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HackerNews {
    private String by;
    private long descendants;
    private long id;
    private long[] kids;
    private int score;
    private long time;
    private String title;
    private String type;
    private String url;
}
