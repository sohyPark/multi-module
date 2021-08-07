package com.baemin.server.ceo.board.controller;

import com.baemin.server.ceo.board.service.NewsdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger( BoardController.class );

    @Autowired
    private NewsdService newsdService;


}
