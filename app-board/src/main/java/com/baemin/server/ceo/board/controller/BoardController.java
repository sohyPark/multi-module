package com.baemin.server.ceo.board.controller;

import com.baemin.server.ceo.board.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger( BoardController.class );

    @Autowired
    private BoardService boardService;

    @RequestMapping( method = RequestMethod.GET, value = "/news" )
    public ResponseEntity getNews() {

        logger.info( "method: GET, api: /board/news" );

        return boardService.getNews();
    }
}
