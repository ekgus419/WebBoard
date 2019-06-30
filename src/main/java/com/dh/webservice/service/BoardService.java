package com.dh.webservice.service;

import com.dh.webservice.domain.Board;
import org.springframework.data.domain.Page;

public interface BoardService {
    // 게시판 리스트 보기
    public Page<Board> findAll(Integer pageNum, Integer pageSize);

}
