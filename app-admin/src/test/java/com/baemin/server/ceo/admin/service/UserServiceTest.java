package com.baemin.server.ceo.admin.service;

import com.baemin.server.ceo.core.entity.User;
import com.baemin.server.ceo.core.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void find() {
        String id = "admin";
        String password = "admin";

        User userEntity = User.builder()
                .id( id )
                .password( password )
                .name( "관리자" )
                .build();

        userRepository.save( userEntity );

        Optional<User> user = userRepository.findUserByIdAndPassword( id, password );
        Assertions.assertNotNull(user);
    }

}