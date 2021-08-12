package com.baemin.server.ceo.board.service;

import com.baemin.server.ceo.board.util.RestResponse;
import com.baemin.server.ceo.core.entity.User;
import com.baemin.server.ceo.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger( UserService.class );

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity getUsers() {

        List<User> userList = userRepository.findAll();

        return RestResponse.success( userList );
    }

}
