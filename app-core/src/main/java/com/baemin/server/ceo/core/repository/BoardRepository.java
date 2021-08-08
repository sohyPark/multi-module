package com.baemin.server.ceo.core.repository;

import com.baemin.server.ceo.core.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findBoardsByActive(int active);
}
