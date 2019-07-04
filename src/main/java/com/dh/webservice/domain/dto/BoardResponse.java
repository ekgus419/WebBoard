package com.dh.webservice.domain.dto;

import com.dh.webservice.domain.Board;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class BoardResponse {
    private Page<Board> boardList;
    private Long listCount;
    private int totalPages;
    private int beginIndex;
    private int currentPage;
    private String userName;

//    total: data.page.totalPages,
//    page: data.page.currentPage,
//    maxVisible: data.page.pageSize
}
