package com.baemin.server.ceo.admin.dto;

import lombok.Getter;
import lombok.Setter;

public class BoardDto {

    @Getter
    @Setter
    public static class addReq {
        private String name;
        private String description;
    }

    @Getter
    @Setter
    public static class updateReq {
        private String name;
        private String description;
        private int active;
    }
}
