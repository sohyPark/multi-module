package com.baemin.server.ceo.board.controller;

import com.baemin.server.ceo.board.dto.CommentDto;
import com.baemin.server.ceo.board.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger( CommentController.class );

    @Autowired
    private CommentService commentService;

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity put( @RequestHeader HttpHeaders headers, final CommentDto.addReq req ) {

        logger.info( "method: POST, api: /post" );

        return commentService.add(req);
    }
}
