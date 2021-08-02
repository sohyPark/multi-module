package com.baemin.server.ceo.board.dto;

import lombok.Getter;
import lombok.Setter;

public class CommentDto {

    @Getter
    @Setter
    public static class addReq {
        private long userId;
        private long postId;
        private String contents;
    }

    @Getter
    @Setter
    public static class updateReq {
        private long userId;
        private long commentId;
        private String contents;
    }

    @Getter
    @Setter
    public static class hideReq {
        private long userId;
        private long commentId;
    }
}
