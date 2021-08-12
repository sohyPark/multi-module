package com.baemin.server.ceo.admin.service;

import com.baemin.server.ceo.admin.dto.BoardDto;
import com.baemin.server.ceo.admin.enumtype.ActiveStatus;
import com.baemin.server.ceo.core.entity.Board;
import com.baemin.server.ceo.core.repository.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private static final Logger logger = LoggerFactory.getLogger( BoardService.class );

    @Autowired
    private BoardRepository boardRepository;

    public ResponseEntity add( BoardDto.addReq req ) {
        if ( ObjectUtils.isEmpty( req.getName() ) ) {
            logger.error( "name is required" );
            return new ResponseEntity<>( "게시판 이름을 입력해주세요.", HttpStatus.BAD_REQUEST );
        }

        if ( ObjectUtils.isEmpty( req.getDescription() ) ) {
            logger.error( "description is required" );
            return new ResponseEntity<>( "게시판 설명을 입력해주세요.", HttpStatus.BAD_REQUEST );
        }

        String name = req.getName();
        String description = req.getDescription();

        Board board = Board.builder()
                .name( name )
                .description( description )
                .build();

        Board newBoard = null;
        try {
            newBoard = boardRepository.save( board );
        } catch ( DataIntegrityViolationException e ) {
            logger.error( "board already exist - name: {}, error: {}", name, e.getMessage() );
            return new ResponseEntity<>( "이미 존재하는 게시판입니다.", HttpStatus.BAD_REQUEST );
        }

        if ( newBoard.getId() < 1 ) {
            logger.error( "board save is failed - name: {}, description: {}", name, description );
            return new ResponseEntity<>( "게시판 추가가 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        return new ResponseEntity<>( HttpStatus.OK );
    }

    public ResponseEntity update( long id, BoardDto.updateReq req ) {

        Optional<Board> findBoard = boardRepository.findById( id );
        if ( !findBoard.isPresent() ) {
            logger.error( "not found board by id - id: {}", id );
            return new ResponseEntity<>( "게시판을 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        String name = req.getName();
        String description = req.getDescription();

        Board board = findBoard.get();
        board.setName( name );
        board.setDescription( description );

        Board updatedBoard = boardRepository.save( board );
        if ( updatedBoard.getId() < 1 ) {
            logger.error( "board update is failed - name: {}, description: {}", name, description );
            return new ResponseEntity<>( "게시판 수정이 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        return new ResponseEntity<>( HttpStatus.OK );
    }

    public ResponseEntity hide( long id ) {

        Optional<Board> findBoard = boardRepository.findById( id );
        if ( !findBoard.isPresent() ) {
            logger.error( "not found board by id - id: {}", id );
            return new ResponseEntity<>( "게시판을 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        Board board = findBoard.get();
        board.setActive( ActiveStatus.IN_ACTIVE.ordinal() );

        Board updatedBoard = boardRepository.save( board );
        if ( updatedBoard.getId() < 1 ) {
            logger.error( "board hide is failed - : id: {}", id );
            return new ResponseEntity<>( "게시판 숨김이 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        return new ResponseEntity<>( HttpStatus.OK );
    }

    public ResponseEntity getAll() {
        List<Board> boardList = boardRepository.findAll();
        return new ResponseEntity<>( boardList, HttpStatus.OK );
    }

    public ResponseEntity getActiveBoards() {
        List<Board> boardList = boardRepository.findBoardsByActive(ActiveStatus.ACTIVE.ordinal() );
        return new ResponseEntity<>( boardList, HttpStatus.OK );
    }
}
