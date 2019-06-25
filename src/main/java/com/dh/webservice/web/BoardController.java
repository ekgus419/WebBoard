package com.dh.webservice.web;

import com.dh.webservice.domain.Board;
import com.dh.webservice.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/")
@Transactional
public class BoardController {

    @Autowired
    BoardRepository boardRepository;

    @GetMapping("/write")
    public String write(){
        return "/board/register";
    }

    @PostMapping("/write")
    public void savePosts(@RequestBody Board board){
        boardRepository.save(board);
    }

}
