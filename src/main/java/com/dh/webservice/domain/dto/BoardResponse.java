/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebBoard)
 *
 */
package com.dh.webservice.domain.dto;

import com.dh.webservice.domain.Board;
import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * @title Board Entity List 출력시 필요한 데이터를 정의한다.
 * @author cdh
 * @FileName BoardResponse
 *
 */
@Data
public class BoardResponse {
    private Page<Board> boardList;
    private Long listCount;
    private int totalPages;
    private int beginIndex;
    private int currentPage;
    private String userName;

}
