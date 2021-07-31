package com.baemin.server.ceo.board.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class ContentsService {

    private static final List<String> FORBIDDEN_URLS = Arrays.asList("naver.com", "daum.net");
    private static final List<String> FORBIDDEN_WORDS = Arrays.asList( "쿠팡이츠", "요기요", "싫어", "ㅠㅠㅠㅠㅠ","ㅋㅋㅋㅋㅋㅋ" );

    public boolean isContainsUrl(String contents) {
        if ( ObjectUtils.isEmpty( contents ) ) {
            return true;
        }

        for ( String url : FORBIDDEN_URLS ) {
            if ( contents.contains( url )) {
                return false;
            }
        }

        return true;
    }

    public boolean isContainsWord(String contents) {
        if ( ObjectUtils.isEmpty( contents ) ) {
            return true;
        }

        for ( String word : FORBIDDEN_WORDS ) {
            if ( contents.contains( word )) {
                return false;
            }
        }

        return true;
    }
}
