package com.baemin.server.ceo.board.service;

import com.baemin.server.ceo.board.enumtype.ActiveStatus;
import com.baemin.server.ceo.core.entity.Board;
import com.baemin.server.ceo.core.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public ResponseEntity getAll() {
        List<Board> boardList = boardRepository.findBoardsByActive( ActiveStatus.ACTIVE.ordinal() );
        return new ResponseEntity<>( boardList, HttpStatus.OK );
    }
}
