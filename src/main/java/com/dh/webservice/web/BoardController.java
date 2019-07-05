package com.dh.webservice.web;

import com.dh.webservice.domain.Board;
import com.dh.webservice.domain.dto.BoardResponse;
import com.dh.webservice.repository.BoardRepository;
import com.dh.webservice.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;


@Controller
@RequestMapping("/board/")
@Transactional
public class BoardController {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    //    @ResponseBody // 리스펀스바디 -> 응답시, 리퀘스트 -> 요청시
    @GetMapping("/list")
    public ModelAndView list(Optional<Integer> pageNo, Optional<Integer> pageSize, Principal principal){
        String writer = principal.getName();
        int evalPageSize = pageSize.orElse(10);
        int evalPage = (pageNo.orElse(0) < 1) ? 0 : pageNo.get() - 1;

        // ajax Data
        Page<Board> page = boardService.getfindAll(evalPage, evalPageSize);
        ModelAndView model = new ModelAndView("/board/list");
        // 현재 페이지
        int currentPage = page.getNumber()+1;
        // 전체 페이지
        int totalPages = page.getTotalPages();
        // 전체 데이터수
        long listCount = page.getTotalElements();

        BoardResponse boardResponse = new BoardResponse();

        boardResponse.setBoardList(page);
        boardResponse.setCurrentPage(currentPage);
        boardResponse.setTotalPages(totalPages);
        boardResponse.setListCount(listCount);
        boardResponse.setUserName(writer);
        model.addObject("currentPage", boardResponse.getCurrentPage());
        model.addObject("boardList", boardResponse.getBoardList());
        model.addObject("totalPages", boardResponse.getTotalPages());
        model.addObject("userName", boardResponse.getUserName());
        model.addObject("listCount", boardResponse.getListCount());

        return model;
    }



    @GetMapping("/write")
    public String write() {
        return "/board/write";
    }


    @PostMapping("/write")
    @ResponseBody
    public Board write(@RequestBody Board board, Principal principal) {

        System.out.println(board.toString());

        String writer = principal.getName();
        // 작성자인지 확인
        if(!writer.equals("") &&  writer.trim().length() > 0) {
            board.setWriter(writer);
            // 게시글 저장
            Board saveBoard = boardRepository.save(board);
            // 생성된 bNo로 groupNo 설정
            saveBoard.setGroupNo(saveBoard.getBNo());
            return boardRepository.save(saveBoard);
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

    @PutMapping("/update/{bNo}")
    @ResponseBody
    public Board update(@PathVariable int bNo, @RequestBody Board board, Principal principal) {
        String writer = principal.getName();

        // 작성자인지 확인
        if(board.getWriter().equals(writer)) {
            Board updateBoard = boardRepository.findOne(bNo);

            // 수정 시 제목, 내용 이외 기존 사항 반영
            updateBoard.setBNo(bNo);
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


    @GetMapping("/write/{bNo}")
    public String writeReply(@PathVariable int bNo, Model model) {
        // 답글의 답글인 경우
        // groupNo가 있는지 확인
        System.out.println(bNo);
//        int groupNo = boardService.getGroupNoBybNo(bNo);
        int groupNo = boardService.getfindOne(bNo).getGroupNo();
        System.out.println("groupNo : " + groupNo);

        model.addAttribute("groupNo", groupNo);
        model.addAttribute("parentNo", bNo);

        return "/board/reply";
    }

    @PostMapping("/writeReply")
    @ResponseBody
    public Board writeReply(@RequestBody Board board, Principal principal) {

        System.out.println("================= writeReply(); ====================== ");
        System.out.println("board.toStrint();  : " + board.toString() );

        String writer = principal.getName();
        // 작성자인지 확인
        if(!writer.equals("") &&  writer.trim().length() > 0) {

            int groupNo = board.getGroupNo();
            int parentNo = board.getParentNo();
            // 원글의 답글인 경우
            if(parentNo == 0) {
                parentNo = groupNo;
            }

            int groupSeq = boardRepository.findMaxGroupSeqByGroupNo(groupNo);
            int depth = boardRepository.findMaxDepthByParentNo(parentNo);

            board.setParentNo(parentNo);
            board.setGroupSeq(groupSeq+1);
            board.setDepth(depth+1);
            board.setWriter(writer);

            return boardRepository.save(board);

        }else{
            return new Board();
        }

    }

}
