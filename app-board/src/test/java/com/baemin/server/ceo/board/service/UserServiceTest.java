package com.baemin.server.ceo.board.service;

import com.baemin.server.ceo.core.entity.User;
import com.baemin.server.ceo.core.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback(value = false)
    void save() {
        User user = User.builder()
                .email( "admin@gamil.com" )
                .password( "admin" )
                .build();

        userRepository.save( user );
    }

    @Test
    void find() {
        Assertions.assertNotNull(userRepository.findById( 1L ));
    }
}