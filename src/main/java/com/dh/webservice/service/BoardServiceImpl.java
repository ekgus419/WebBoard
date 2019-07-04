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
    public Page<Board> findAll(Integer pageNo, Integer pageSize) {
        /* 게시판 리스트 보기  curPage : 요청하는 페이지 , 첫페이지는 0부터 시작  */

        PageRequest pr = new PageRequest(pageNo, pageSize,
                new Sort(
                        new Order(Direction.DESC,"createdDate")
                )
        );

        return boardRepository.findAll(pr);
    }





}
