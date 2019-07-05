package com.dh.webservice.service;

import com.dh.webservice.domain.Board;
import com.dh.webservice.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Override
    public Page<Board> getfindAll(Integer pageNo, Integer pageSize) {
        PageRequest pr = new PageRequest(pageNo, pageSize,
                new Sort(
                        new Order(Direction.DESC,"groupNo"),
                        new Order(Direction.ASC,"groupSeq"),
                        new Order(Direction.ASC,"depth")
                )
        );

        return boardRepository.findAll(pr);
    }

    @Override
    public Board getfindOne(int bNo) {
        Board board = boardRepository.findOne(bNo);
        return board;
    }


}
