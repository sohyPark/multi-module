package com.baemin.server.ceo.board.enumtype;

public enum AuthCode {

    ADMIN(1),  // 내가 생성한 문서 중 결재 진행 중인 문서
    USER(2);   // 내가 결재를 해야 할 문서

    AuthCode( int auth ) {
        this.auth = auth;
    }

    private final int auth;

    public int getAuth() {
        return auth;
    }

    public static boolean isContains(int auth) {
        for ( AuthCode authCode : AuthCode.values()) {
           if (auth == authCode.getAuth()) {
               return true;
           }
        }
        return false;
    }

}
