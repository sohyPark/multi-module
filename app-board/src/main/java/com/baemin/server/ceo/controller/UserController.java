package com.baemin.server.ceo.controller;


import com.baemin.server.ceo.service.UserService;
import entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping( "/board/user" )
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger( UserController.class );

    @Autowired
    private UserService userService;

    @RequestMapping( method = RequestMethod.POST, value = "/login" )
    public ResponseEntity<UserEntity> login(@RequestBody final UserEntity userEntity, HttpServletResponse response ) {

        logger.info( "method: POST, api: /login, id: {}", userEntity.getId() );

        return userService.get(userEntity, response );
    }
}
