package com.baemin.server.ceo.board.service;

import com.baemin.server.ceo.board.dto.PostDto;
import com.baemin.server.ceo.board.enumtype.ActiveStatus;
import com.baemin.server.ceo.board.util.RestResponse;
import com.baemin.server.ceo.core.entity.Board;
import com.baemin.server.ceo.core.entity.Post;
import com.baemin.server.ceo.core.entity.User;
import com.baemin.server.ceo.core.repository.BoardRepository;
import com.baemin.server.ceo.core.repository.PostRepository;
import com.baemin.server.ceo.core.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger( PostService.class );

    private static final int maxPostCount = 5;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ContentsService contentsService;

    public ResponseEntity getAll() {

        List<Board> boardList = boardRepository.findAll();

        return RestResponse.success( boardList );

    }

    @Transactional
    public ResponseEntity put( PostDto.putReq req ) {

        long boardId = req.getBoardId();
        if ( boardId < 1 ) {
            logger.error( "boardId is empty" );
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "게시판을 선택해주세요." );
        }

        String title = req.getTitle();
        if ( ObjectUtils.isEmpty( title ) ) {
            logger.error( "title is empty" );
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "제목을 입력해주세요." );
        }

        String contents = req.getContents();
        if ( ObjectUtils.isEmpty( contents ) ) {
            logger.error( "contents is empty" );
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "내을 입력해주세요." );
        }

        if ( contentsService.isContainsUrl( contents ) ) {
            logger.error( "contents is empty" );
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "게시글 내용에 naver나 daum으로 이동하는 링크가 포함되어선 안됩니다.." );
        }

        if ( contentsService.isContainsWord( contents ) ) {
            logger.error( "contents is empty" );
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "게시글 내용에 금칙어가 포함되어 있습니다." );
        }

        long userId = req.getUserId();
        Optional<User> user = userRepository.findById( req.getUserId() );
        if ( !user.isPresent() ) {
            logger.error( "[findById] - userId: {}", userId );
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "내을 입력해주세요." );
        }

        LocalDate today = LocalDate.now();
        LocalTime startTime = LocalTime.of( 00, 00, 00 );
        LocalTime endTime = LocalTime.of( 23, 59, 59 );
        LocalDateTime startLdt = LocalDateTime.of( today, startTime );
        LocalDateTime endLdt = LocalDateTime.of( today, endTime );

        long todayPostCount = postRepository.countPostByCreatedDateBetween( startLdt, endLdt );
        if ( todayPostCount > maxPostCount ) {
            logger.error( "[countPostByCreatedDateBetween] - startLdt: {}, endLdtL: {}]", startLdt, endLdt );
            return RestResponse.fail( HttpStatus.INTERNAL_SERVER_ERROR, "하루에 게시글은5개 까지만 작성할 수 있습니다." );
        }

        Post post = Post.builder()
                .title( title )
                .contents( contents )
                .user( user.get() )
                .boardId( boardId )
                .build();
        postRepository.save( post );

        return RestResponse.success( post.getId() );

    }

    public ResponseEntity update( PostDto.updateReq req ) {

        long postId = req.getPostId();
        if ( postId < 1 ) {
            logger.error( "postId is empty" );
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "수정할 게시을 선택해주세요." );
        }

        String title = req.getTitle();
        if ( ObjectUtils.isEmpty( title ) ) {
            logger.error( "title is empty" );
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "제목을 입력해주세요." );
        }

        String contents = req.getContents();
        if ( ObjectUtils.isEmpty( contents ) ) {
            logger.error( "contents is empty" );
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "내을 입력해주세요." );
        }

        Optional<Post> findPost = postRepository.findPostByIdAndUser_Id( postId, req.getUserId() );
        if ( !findPost.isPresent() ) {
            logger.error( "not found post by postId and userIdt - postId: {}, userId: {}", postId, req.getUserId() );
            return RestResponse.fail( HttpStatus.INTERNAL_SERVER_ERROR, "수정할 게시글을 찾을 수 없습니다." );
        }

        Post post = findPost.get();
        if ( post.getUser().getId() != req.getUserId() ) {
            logger.error( "not found post by postId and userIdt - postId: {}, userId: {}", postId, req.getUserId() );
            return RestResponse.fail( HttpStatus.INTERNAL_SERVER_ERROR, "본인이 작성한 게시글만 수정할 수 있습니다." );
        }

        post.setContents( contents );
        Post newPost = postRepository.save( post );

        return RestResponse.success( newPost.getId() );

    }

    public ResponseEntity hide( PostDto.hideReq req ) {
        long postId = req.getPostId();
        Optional<Post> findPost = postRepository.findById( postId );
        if ( !findPost.isPresent() ) {
            logger.error( "post is not found - commentId: {}", postId );
            return RestResponse.fail( HttpStatus.INTERNAL_SERVER_ERROR, "숨김처리 할 게시을 찾을 수 없습니다." );
        }

        Post post = findPost.get();
        long userId = req.getUserId();
        long postUserId = post.getUser().getId();
        if (postUserId != userId) {
            logger.error( "not your own post - postId: {}, post's userId: {}, req userId: {}", postId, postUserId, userId );
            return RestResponse.fail( HttpStatus.INTERNAL_SERVER_ERROR, "본인이 작성한 댓글만 숨길 수 있습니다." );
        }

        post.setActive( ActiveStatus.IN_ACTIVE.ordinal() );

        Post updatePost = postRepository.save( post );
        if (updatePost.getId() < 1) {
            logger.error( "post save is failed - commentId: {}", postId);
            return RestResponse.fail( HttpStatus.INTERNAL_SERVER_ERROR, "숨김처리가 실패하였습니다." );
        }

        return RestResponse.success();

    }
}
