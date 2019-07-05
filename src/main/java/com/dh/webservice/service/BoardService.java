package com.dh.webservice.service;

import com.dh.webservice.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardService {
    // 게시판 리스트 보기
    public Page<Board> getfindAll(Integer pageNo, Integer pageSize);

    public Board getfindOne(int bNo);
    // findMaxGroupSeqByGroupNo

}
