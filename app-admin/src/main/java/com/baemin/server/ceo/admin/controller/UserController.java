package com.baemin.server.ceo.admin.controller;


import com.baemin.server.ceo.admin.dto.UserDto;
import com.baemin.server.ceo.admin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/admin/users" )
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger( UserController.class );

    @Autowired
    private UserService userService;

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity signIn( @RequestBody final UserDto.addReq req ) {

        logger.info( "method: POST, api: /admin/users, role: {}, name: {}, email: {}", req.getRole(), req.getName(), req.getEmail() );

        return userService.signIn( req );
    }

    @RequestMapping( method = RequestMethod.PUT, value = "/{id}" )
    public ResponseEntity update( @PathVariable( "id" ) long id, @RequestBody final UserDto.updateReq req ) {

        logger.info( "method: PUT, api: /admin/users/{id}, id: {}, role: {}, name: {}", id, req.getRole(), req.getName() );

        return userService.update( id, req );
    }

    @RequestMapping( method = RequestMethod.DELETE, value = "/{id}" )
    public ResponseEntity hide( @PathVariable( "id" ) long id ) {

        logger.info( "method: DELETE, api: /admin/users/{id}, id: {}", id );

        return userService.hide( id );
    }


    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity get() {

        logger.info( "method: GET, api: /admin/users" );

        return userService.get();
    }
}
