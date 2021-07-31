package com.baemin.server.ceo.board.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.net.URLConnection;

@Service
public class BoardService {

    private static final String HACKER_NEWS_URL = "https://hacker-news.firebaseio.com/v0/newstories.json?print=pretty";

    public ResponseEntity getNews() throws Exception {

        URL url = new URL(HACKER_NEWS_URL);
        URLConnection conn = url.openConnection();




        return null;
    }
}
