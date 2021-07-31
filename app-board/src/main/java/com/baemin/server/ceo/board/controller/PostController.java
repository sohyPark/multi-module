package com.baemin.server.ceo.board.controller;

import com.baemin.server.ceo.board.dto.PostDto;
import com.baemin.server.ceo.board.service.PostService;
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
@RequestMapping(value = "/post")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger( PostController.class );

    @Autowired
    private PostService postService;

    @RequestMapping( method = RequestMethod.GET, value = "/all" )
    public ResponseEntity getAll( @RequestHeader HttpHeaders headers ) {

        logger.info( "method: GET, api: /post/all" );

        return postService.getAll();
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity put( @RequestHeader HttpHeaders headers, final PostDto.putReq req ) {

        logger.info( "method: POST, api: /post" );

        return postService.put(req);
    }

    @RequestMapping( method = RequestMethod.PUT )
    public ResponseEntity update( @RequestHeader HttpHeaders headers, final PostDto.putReq req ) {

        logger.info( "method: PUT, api: /post" );

        return postService.put(req);
    }
}
