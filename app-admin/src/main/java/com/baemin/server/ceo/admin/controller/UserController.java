package com.baemin.server.ceo.admin.controller;


import com.baemin.server.ceo.admin.service.UserService;
import com.baemin.server.ceo.core.entity.User;
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
    public ResponseEntity<User> login( @RequestBody final User user, HttpServletResponse response ) {

        logger.info( "method: POST, api: /login, id: {}", user.getId() );

        return userService.get(user, response );
    }
}