package com.baemin.server.ceo.board.controller;

import com.baemin.server.ceo.board.dto.PostDto;
import com.baemin.server.ceo.board.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "/board/posts" )
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger( PostController.class );

    @Autowired
    private PostService postService;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity getAll( final PostDto.getAllReq req ) {

        logger.info( "method: GET, api: /board/posts - page: {}, size: {}", req.getPage(), req.getSize() );

        return postService.getAll( req );
    }

    @RequestMapping( method = RequestMethod.GET, value = "/{id}" )
    public ResponseEntity get( @PathVariable( "id" ) long id ) {

        logger.info( "method: GET, api: /board/posts/{id} - id: {}", id );

        return postService.get( id );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity put( final PostDto.putReq req ) {

        logger.info( "method: POST, api: /board/posts" );

        return postService.put( req );
    }

    @RequestMapping( method = RequestMethod.PUT )
    public ResponseEntity update( final PostDto.putReq req ) {

        logger.info( "method: PUT, api: /post" );

        return postService.put( req );
    }
}
