package com.baemin.server.ceo.admin.controller;

import com.baemin.server.ceo.admin.dto.BoardDto;
import com.baemin.server.ceo.admin.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/admin/boards" )
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger( BoardController.class );

    @Autowired
    private BoardService boardService;

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity add( @RequestBody final BoardDto.addReq req ) {

        logger.info( "method: POST, api: /admin/boards, name: {}, description: {}", req.getName(), req.getDescription() );

        return boardService.add( req );
    }

    @RequestMapping( method = RequestMethod.PUT, value = "/{id}" )
    public ResponseEntity update( @PathVariable( "id" ) long id, @RequestBody final BoardDto.updateReq req ) {

        logger.info( "method: PUT, api: /admin/boards/{id}, id: {}, name: {}, description: {}, active: {}", id, req.getName(), req.getDescription(), req.getActive() );

        return boardService.update( id, req );
    }

    @RequestMapping( method = RequestMethod.DELETE, value = "/{id}" )
    public ResponseEntity hide( @PathVariable( "id" ) long id ) {

        logger.info( "method: DELETE, api: /admin/boards/{id}, id: {}", id );

        return boardService.hide( id );
    }

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity getBoards() {

        logger.info( "method: GET, api: /admin/boards" );

        return boardService.getAll();
    }
}
