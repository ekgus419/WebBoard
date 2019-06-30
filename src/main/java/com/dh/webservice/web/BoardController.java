package com.dh.webservice.web;

import com.dh.webservice.domain.Board;
import com.dh.webservice.repository.BoardRepository;
import com.dh.webservice.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/board/")
@Transactional
public class BoardController {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

//    @GetMapping("/list")
//    public String list(Model model, Principal principal) {
//        String writer = principal.getName();
//        List<Board> boardList = boardRepository.findAll();
//        model.addAttribute("userName", writer);
//        model.addAttribute("boardList", boardList);
//        return "/board/list";
//
//    }

    @GetMapping("/list/{pageNum}")
    public String list(Model model, Pageable pageable, @PathVariable Integer pageNum, Integer pageSize, Principal principal){

        String writer = principal.getName();
        if(pageSize == null){
            pageSize = 10;
        }

        Page<Board> page = boardService.findAll(pageNum, pageSize);

        int current = page.getNumber()+1;
        int begin  =  Math.max(1, current - 5);
        int end  =  Math.min(begin + 10, page.getTotalPages());

//        model.addAttribute("page", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("userName", writer);
        model.addAttribute("boardList", page);

        return "/board/list";
    }

    @GetMapping("/write")
    public String write() {
        return "/board/write";
    }


    @PostMapping("/write")
    @ResponseBody
    public Board write(@RequestBody Board board, Principal principal) {
        String writer = principal.getName();
        // 작성자인지 확인
        if(!writer.equals("") &&  writer.trim().length() > 0) {
            board.setWriter(writer);
            return boardRepository.save(board);
        }else{
            return new Board();
        }

    }


    @GetMapping("/detail/{bNo}")
    public String detail(@PathVariable int bNo, Model model, Principal principal) {

        String writer = principal.getName();

        // 기존 게시글
        Board board = boardRepository.findOne(bNo);

        // 존재하지 않는 게시글 (삭제 게시글 포함) 접근하는 경우
        if(board == null) {
            return "/board/404";
        }

        // 작성자인지 확인, 자기 자신이 쓴 글이 아닐때만 조회수 증가
        if(!board.getWriter().equals(writer)){
            // 조회수 증가
            board.setHit(board.getHit() + 1);
        }

        Board newBoard = boardRepository.save(board);
        model.addAttribute("board", newBoard);

        return "/board/detail";

    }

    @GetMapping("/update/{bNo}")
    public String update(@PathVariable int bNo, Model model) {

        model.addAttribute("board", boardRepository.findOne(bNo));

        return "/board/update";
    }

//    @PutMapping("/update/{bNo}")
//    @ResponseBody
//    public Board update(@PathVariable int bNo, @RequestBody Board board, Principal principal) {
//        String writer = principal.getName();
//
//        // 작성자인지 확인
//        if(board.getWriter().equals(writer)) {
//            Board updateBoard = boardRepository.findOne(bNo);
//
//            // 수정 시 제목, 내용 이외 기존 사항 반영
//            updateBoard.setBno(bNo);
//            updateBoard.setTitle(board.getTitle());
//            updateBoard.setContent(board.getContent());
//
//            return boardRepository.save(updateBoard);
//        }else{
//            return new Board();
//        }
//
//    }

    @PutMapping("/update/{bNo}")
    @ResponseBody
    public Board update(@PathVariable int bNo, @RequestBody Board board, Principal principal) {
        String writer = principal.getName();

        // 작성자인지 확인
        if(board.getWriter().equals(writer)) {
            Board updateBoard = boardRepository.findOne(bNo);

            // 수정 시 제목, 내용 이외 기존 사항 반영
            updateBoard.setBno(bNo);
            updateBoard.setTitle(board.getTitle());
            updateBoard.setContent(board.getContent());

            return boardRepository.save(updateBoard);
        }else{
            return new Board();
        }

    }


    @DeleteMapping("/delete/{bNo}")
    @ResponseBody
    public boolean delete(@PathVariable int bNo, Principal principal) {

        String writer = principal.getName();

        // 기존 게시글
        Board board = boardRepository.findOne(bNo);

        // 존재하지 않는 게시글 (삭제 게시글 포함) 접근하는 경우
        if(board == null) {
            return false;
        }

        // 작성자인지 확인
        if(board.getWriter().equals(writer)) {
            boardRepository.delete(bNo);
            return true;
        }else{
            return false;
        }

    }


}
