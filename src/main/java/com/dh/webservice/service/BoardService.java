/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebBoard)
 *
 */
package com.dh.webservice.service;

import com.dh.webservice.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * @title Repository에 설정되어있는 기본 Query를 사용하지 않고 재정의하여 사용한다.
 * @author cdh
 * @FileName : BoardService
 *
 */
public interface BoardService {
    // 게시판 리스트 보기
    public Page<Board> getfindAll(Integer pageNo, Integer pageSize);

    public Board getfindOne(int bNo);

}
