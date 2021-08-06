package com.baemin.server.ceo.core.repository;

import com.baemin.server.ceo.core.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    Page<Post> findAll( Pageable pageable );

    long countPostByCreatedDateBetween( LocalDateTime start, LocalDateTime end );

    Optional<Post> findPostByIdAndUser_Id( @Param( value = "postId" ) long postId, @Param( value = "userId" ) long userId );

}
