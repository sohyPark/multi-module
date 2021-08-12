package com.baemin.server.ceo.admin.dto;

import lombok.Getter;
import lombok.Setter;

public class UserDto {

    @Getter
    @Setter
    public static class addReq {
        private int role;
        private String name;
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class updateReq {
        private int role;
        private String name;
        private String password;
    }
}
