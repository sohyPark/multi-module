package com.baemin.server.ceo.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContentsServiceTest {


    @Value("${contents.url}")
    String[] contents;

    @Test
    void isContainsUrl() {
        String content = contents[0];
        System.out.println(content);
    }
}