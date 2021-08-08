package com.baemin.server.ceo.board.controller;

import com.baemin.server.ceo.board.dto.CommentDto;
import com.baemin.server.ceo.board.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/comment")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger( CommentController.class );

    @Autowired
    private CommentService commentService;

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity put( final CommentDto.addReq req ) {

        logger.info( "method: POST, api: /board/comment" );

        return commentService.add(req);
    }

    @RequestMapping( method = RequestMethod.PUT, value = "/{id}" )
    public ResponseEntity update( @PathVariable( "id" ) long id, final CommentDto.updateReq req ) {

        logger.info( "method: PUT, api: /board/comment/{id} - userId: {}, contents: {}", req.getUserId(), req.getContents() );

        return commentService.update( id, req);
    }

}
