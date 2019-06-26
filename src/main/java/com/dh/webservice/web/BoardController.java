package com.dh.webservice.web;

import com.dh.webservice.domain.Board;
import com.dh.webservice.domain.User;
import com.dh.webservice.repository.BoardRepository;
import com.dh.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/board/")
@Transactional
public class BoardController {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/write")
    public String write() {
        return "/board/write";
    }

    @PostMapping("/write")
    @ResponseBody
    public Board write(@RequestBody Board board, Principal principal) {
        String writer = principal.getName();
        board.setWriter(writer);
        return boardRepository.save(board);
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boardList = boardRepository.findAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authName + " + auth.getName());
        User user = userService.findUserByUserEmail(auth.getName());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("boardList", boardList);
        return "/board/list";

    }

    @GetMapping("/detail/{bNo}")
    public String detail(@PathVariable int bNo, Model model) {

        // 기존 게시글
        Board board = boardRepository.findOne(bNo);

        // 존재하지 않는 게시글 (삭제 게시글 포함) 접근하는 경우
        if(board == null) {
            return "/board/404";
        }
        // 조회수 증가
        board.setHit(board.getHit() + 1);
        Board newBoard = boardRepository.save(board);

        model.addAttribute("board", newBoard);

        return "/board/detail";
    }

    @GetMapping("/update/{bNo}")
    public String modify(@PathVariable int bNo, Model model) {

        model.addAttribute("board", boardRepository.findOne(bNo));

        return "/board/update";
    }

    @PutMapping("/update/{bNo}")
    @ResponseBody
    public Board update(@PathVariable int bNo, @RequestBody Board board, Principal principal) {

        Board updateBoard = boardRepository.findOne(bNo);

        // 수정 시 제목, 내용 이외 기존 사항 반영
        updateBoard.setBno(bNo);
        updateBoard.setTitle(board.getTitle());
        updateBoard.setContent(board.getContent());

        return boardRepository.save(updateBoard);

    }

    @DeleteMapping("/delete/{bNo}")
    public String delete(@PathVariable int bNo) {
        boardRepository.delete(bNo);
        return "/board/list";
    }

}
