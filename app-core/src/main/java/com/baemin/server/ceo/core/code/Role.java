package com.baemin.server.ceo.core.code;

public enum Role {

    ADMIN(1),
    USER(2);

    Role( int role ) {
        this.role = role;
    }

    private final int role;

    public int getRole() {
        return role;
    }

    public static boolean isContains(int role) {
        for ( Role enumRole : Role.values()) {
           if (role == enumRole.getRole()) {
               return true;
           }
        }
        return false;
    }

}
