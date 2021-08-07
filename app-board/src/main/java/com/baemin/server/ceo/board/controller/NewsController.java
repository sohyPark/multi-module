package com.baemin.server.ceo.board.controller;

import com.baemin.server.ceo.board.service.NewsdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/news")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger( NewsController.class );

    @Autowired
    private NewsdService newsdService;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity getNews() {

        logger.info( "method: POST, api: /board/news" );

        return newsdService.getNews();
    }
}
