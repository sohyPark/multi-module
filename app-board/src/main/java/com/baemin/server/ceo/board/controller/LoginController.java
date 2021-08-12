package com.baemin.server.ceo.board.controller;

import com.baemin.server.ceo.board.service.LoginService;
import com.baemin.server.ceo.core.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/board")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger( LoginController.class );

    @Autowired
    private LoginService userService;

    @RequestMapping( method = RequestMethod.POST, value = "/login" )
    public ResponseEntity login( HttpServletResponse response, @RequestBody final User user ) {

        logger.info( "method: POST, api: /board/login, email: {}", user.getEmail() );

        return userService.login(user, response );
    }

    @RequestMapping( method = RequestMethod.POST, value = "/logout" )
    public ResponseEntity logout() {

        logger.info( "method: POST, api: /board/logout" );

        return userService.logout();
    }
}
