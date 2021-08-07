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

        logger.info( "method: POST, api: /admin/users, auth: {}, name: {}, email: {}", req.getAuth(), req.getName(), req.getEmail() );

        return userService.signIn( req );
    }

    @RequestMapping( method = RequestMethod.PUT, value = "/{id}" )
    public ResponseEntity update( @PathVariable( "id" ) long id, @RequestBody final UserDto.updateReq req ) {

        logger.info( "method: PUT, api: /admin/users/{id}, auth: {}, name: {}", req.getAuth(), req.getName() );

        return userService.update( id, req );
    }
}
