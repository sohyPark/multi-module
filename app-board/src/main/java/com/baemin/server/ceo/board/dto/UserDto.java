package com.baemin.server.ceo.board.dto;

import lombok.Getter;
import lombok.Setter;

public class UserDto {

    @Getter
    @Setter
    public static class addReq {
        private int auth;
        private String name;
        private String email;
        private String password;
    }
}
