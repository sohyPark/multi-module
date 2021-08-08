package com.baemin.server.ceo.admin.service;

import com.baemin.server.ceo.admin.enumtype.ActiveStatus;
import com.baemin.server.ceo.core.entity.Post;
import com.baemin.server.ceo.core.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger( PostService.class );

    @Autowired
    private PostRepository postRepository;

    public ResponseEntity hide( long id ) {

        Optional<Post> findPost = postRepository.findById( id );
        if ( !findPost.isPresent() ) {
            logger.error( "not found post by id - id: {}", id );
            return new ResponseEntity<>( "게시물을 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        Post post = findPost.get();
        post.setActive( ActiveStatus.IN_ACTIVE.ordinal() );

        Post updatedPost = postRepository.save( post );
        if ( updatedPost.getId() < 1 ) {
            logger.error( "user post is failed - : id: {}", id );
            return new ResponseEntity<>( "게시물 숨김이 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        return new ResponseEntity<>( HttpStatus.OK );
    }
}
