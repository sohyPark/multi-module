package com.baemin.server.ceo.board.service;

import com.baemin.server.ceo.board.dto.CommentDto;
import com.baemin.server.ceo.board.enumtype.ActiveStatus;
import com.baemin.server.ceo.board.util.RestResponse;
import com.baemin.server.ceo.core.entity.Comment;
import com.baemin.server.ceo.core.entity.User;
import com.baemin.server.ceo.core.repository.CommentRepository;
import com.baemin.server.ceo.core.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger( CommentService.class );

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity add( CommentDto.addReq req ) {

        if ( req.getPostId() < 1 ) {
            logger.error( "postId is empty" );
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "댓글을 달 게시글을 선택해주세요." );
        }

        if ( ObjectUtils.isEmpty( req.getContents() ) ) {
            logger.error( "contents is empty" );
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "댓글 내용을 입력해주세요." );
        }

        Optional<User> user = userRepository.findById( req.getUserId() );
        if ( !user.isPresent() ) {
           logger.error( "user is empty - userId: {}", req.getUserId() );
           return RestResponse.fail( HttpStatus.BAD_REQUEST, "계정 정보가 올바르지 않습니다." );
        }

        long postId = req.getPostId();
        String contents = req.getContents();
        User findUser = user.get();

        Comment comment = Comment.builder()
            .user( findUser )
            .postId( postId )
            .contents( contents )
            .build();

        Comment newComment = commentRepository.save( comment );
        if ( newComment.getId() < 1 ) {
            logger.error( "comment save failed - email: {}, postId: {}, contents: {}", findUser.getEmail(), postId, comment );
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "댓글 등록이 실패하였습니다." );
        }

        return RestResponse.success();
    }

    public ResponseEntity update( long id, CommentDto.updateReq req ) {

        Optional<Comment> findComment = commentRepository.findById( id );
        if ( !findComment.isPresent() ) {
            logger.error( "comment is not found by id - id: {}", id );
            return new ResponseEntity<>( "수정할 댓글을 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        long userId = req.getUserId();

        Comment comment = findComment.get();
        long commentUserId = comment.getUser().getId();
        if (userId != commentUserId) {
            logger.error( "the commenter and the requested userId do not match - commentUserId: {}, userId: {}", commentUserId, userId );
            return new ResponseEntity<>( "본인이 작성한 댓글만 수정할 수 있습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }


        String contents = req.getContents();
        comment.setContents( contents );

        Comment updatedComment = commentRepository.save( comment );
        if (updatedComment.getId() < 1) {
            logger.error( "comment update is failed - id: {}, contents: {}", id, contents );
            return new ResponseEntity<>( "댓글 수정이 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        return RestResponse.success();
    }

    public ResponseEntity hide( long id, CommentDto.hideReq req ) {

        Optional<Comment> findComment = commentRepository.findById( id );
        if ( !findComment.isPresent() ) {
            logger.error( "comment is not found - id: {}", id );
            return RestResponse.fail( HttpStatus.INTERNAL_SERVER_ERROR, "숨김처리 할 댓글을 찾을 수 없습니다." );
        }

        Comment comment = findComment.get();
        long userId = req.getUserId();
        long commentUserId = comment.getUser().getId();
        if (commentUserId != userId) {
            logger.error( "not your own comment - id: {}, comment's userId: {}, req userId: {}", id, commentUserId, userId );
            return RestResponse.fail( HttpStatus.INTERNAL_SERVER_ERROR, "본인이 작성한 댓글만 숨길 수 있습니다." );
        }

        comment.setActive( ActiveStatus.IN_ACTIVE.ordinal() );

        Comment updatedComment = commentRepository.save( comment );
        if (updatedComment.getId() < 1) {
            logger.error( "comment save is failed - id: {}", id);
            return RestResponse.fail( HttpStatus.INTERNAL_SERVER_ERROR, "숨김처리가 실패하였습니다." );
        }

        return RestResponse.success();

    }
}
