package com.baemin.server.ceo.board.service;

import com.baemin.server.ceo.core.entity.Post;
import com.baemin.server.ceo.core.entity.User;
import com.baemin.server.ceo.core.repository.PostRepository;
import com.baemin.server.ceo.core.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void put() {

        User user = User.builder()
                .email( "admin@gmail.com" )
                .password( "admin" )
                .build();
        userRepository.save( user );

        Post post = Post.builder()
                .title( "제목" )
                .contents( "내용" )
                .boardId( 1 )
                .user( user )
                .build();

        Post newPost = postRepository.save( post );

        Assertions.assertNotNull( newPost );
    }

    @Test
    void findAll() {
        Assertions.assertNotNull( postRepository.findAll() );
    }

    @Test
    void update() {
        long postId = 1;
        long userId = 1;

        Optional<Post> findPost = postRepository.findPostByIdAndUser_Id( postId, userId );
        Assertions.assertNotNull( findPost.get() );

    }
}