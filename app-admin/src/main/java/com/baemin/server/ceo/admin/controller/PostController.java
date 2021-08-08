package com.baemin.server.ceo.admin.controller;

import com.baemin.server.ceo.admin.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/admin/posts" )
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger( PostController.class );

    @Autowired
    private PostService postService;

    @RequestMapping( method = RequestMethod.DELETE, value = "/{id}" )
    public ResponseEntity hide( @PathVariable( "id" ) long id ) {

        logger.info( "method: DELETE, api: /admin/posts/{id}, id: {}", id );

        return postService.hide( id );
    }
}
