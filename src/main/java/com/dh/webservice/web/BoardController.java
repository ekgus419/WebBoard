/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebBoard)
 *
 */

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

/**
 * @title Board 컨트롤러 파일
 * @author cdh
 * @FileName : BoardController
 *
 */
@Controller
@RequestMapping("/board/")
@Transactional
public class BoardController {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    /**
     * 전체 게시글 목록
     * @param pageNo
     * @param pageSize
     * @param principal
     * @return 전체 게시글 목록 List
     */
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

    /**
     * 게시글 등록
     * @return 게시글 등록 뷰 페이지
     */
    @GetMapping("/write")
    public String write() {
        return "/board/write";
    }

    /**
     * 게시글 등록
     * @param board
     * @return 등록된 게시글 Entity
     */
    @PostMapping("/write")
    @ResponseBody
    public Board write(@RequestBody Board board, Principal principal) {

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

    /**
     * 게시글 상세 페이지
     * @param bNo
     * @param model
     * @param principal
     * @return 게시글 상세 뷰 페이지
     */
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

    /**
     * 게시글 수정 페이지
     * @param bNo
     * @param model
     * @return 게시글 수정 뷰 페이지
     */
    @GetMapping("/update/{bNo}")
    public String update(@PathVariable int bNo, Model model) {

        model.addAttribute("board", boardRepository.findOne(bNo));

        return "/board/update";
    }

    /**
     * 게시글 수정
     * @param bNo
     * @param board
     * @return 수정된 게시글 Entity
     */
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

    /**
     * 게시글 삭제
     * @param bNo
     * @return boolean
     */
    @DeleteMapping("/delete/{bNo}")
    @ResponseBody
    public boolean delete(@PathVariable int bNo, Principal principal) {
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> master
=======
>>>>>>> ac31268f1a1e6f182faa7aaef9a966768effbaed
>>>>>>> b8bfa1dd6ea6538834f3dca36fe8a07b0ecbf735
=======
>>>>>>> develop

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

    /**
     * 답글 등록 페이지
     * @param bNo
     * @param model
     * @return 답글 등록 뷰 페이지
     */
    @GetMapping("/write/{bNo}")
    public String writeReply(@PathVariable int bNo, Model model) {
        // 답글의 답글인 경우
        // groupNo가 있는지 확인
        int groupNo = boardService.getfindOne(bNo).getGroupNo();
        model.addAttribute("groupNo", groupNo);
        model.addAttribute("parentNo", bNo);

        return "/board/reply";
    }

    /**
     * 답글 등록
     * @param board
     * @param principal
     * @return 등록된 답글 Entity
     */
    @PostMapping("/writeReply")
    @ResponseBody
    public Board writeReply(@RequestBody Board board, Principal principal) {

        String writer = principal.getName();
        // 작성자인지 확인
        if(!writer.equals("") &&  writer.trim().length() > 0) {

            int groupNo = board.getGroupNo();
            int parentNo = board.getParentNo();
            // 원글의 답글인 경우
            if(parentNo == 0) {
                parentNo = groupNo;
            }

            // parentNo로 부모글 컬럼 가져오기
            Board parentBoard = boardRepository.findBoardByBNo(parentNo);

            // 부모글 groupSeq
            int parentGroupSeq = parentBoard.getGroupSeq();

            // 현재 넣으려는 글의 seq
            int nextGroupSeq = parentGroupSeq + 1;

            // 부모글 depth
            int parentDepth = parentBoard.getDepth();

            // 작성한 글의 depth
            int depth = parentDepth + 1;

            // max Seq
            int maxGroupSeq = boardRepository.findMaxGroupSeqByGroupNo(groupNo);

            if(nextGroupSeq <= maxGroupSeq){
                // 답글이 존재함(이미 존재하는 group_seq인지 확인)
                Board findBoard = boardRepository.findBoardByGroupNoAndGroupSeq(groupNo,nextGroupSeq);
                if(findBoard.getParentNo() != parentNo) {
                    board.setGroupSeq(nextGroupSeq);
                    boardRepository.updateAllGroupSeq(findBoard.getGroupNo(), findBoard.getGroupSeq());
                }
            }
            if(board.getGroupSeq() == 0) {
                board.setGroupSeq(maxGroupSeq + 1);
            }
            board.setParentNo(parentNo);
            board.setDepth(depth);
            board.setWriter(writer);

            return boardRepository.save(board);
        }else{
            return new Board();
        }

    }


// ver2
//            // parentNo로 부모글 컬럼 가져오기
//            Board parentBoard = boardRepository.findOneByBno(parentNo);
//
//            // 부모글 groupSeq
//            int parentGroupSeq = parentBoard.getGroupSeq();
//            int maxParentGroupSeq = parentGroupSeq + 1;
//
//            // 부모글 depth
//            int parentDepth = parentBoard.getDepth();
//
//            // 답글의 groupSeq
//            int groupSeq = boardRepository.findMaxGroupSeqByGroupNo(groupNo);
//
//            // Max Seq
//            int maxGroupSeq = groupSeq+1;
//
//            // 작성한 글의 depth
//            int depth = parentDepth + 1;
//
//            // 이미 존재하는 group_seq인지 확인
//            Board findBoard = boardRepository.findOneByGroupNoAndGroupSeq(groupNo,parentGroupSeq);
//            if(findBoard != null) {
//                // 이미 존재하는 group_seq
//                // 이 글부터 그룹 안에 있는 모든 group_seq 를 1 증가
//                board.setGroupSeq(maxParentGroupSeq);
//                boardRepository.updateAllGroupSeq(findBoard.getGroupNo(), findBoard.getGroupSeq(), parentGroupSeq);
//            }else{
//                board.setGroupSeq(maxGroupSeq);
//            }
//            board.setParentNo(parentNo);
//            board.setDepth(depth);
//            board.setWriter(writer);
//
//            return boardRepository.save(board);


// ver1
//    public Board writeReply(@RequestBody Board board, Principal principal) {
//
//        System.out.println("================= writeReply(); ====================== ");
//        System.out.println("board.toStrint();  : " + board.toString() );
//
//        String writer = principal.getName();
//        // 작성자인지 확인
//        if(!writer.equals("") &&  writer.trim().length() > 0) {
//
//            int groupNo = board.getGroupNo();
//            int parentNo = board.getParentNo();
//            // 원글의 답글인 경우
//            if(parentNo == 0) {
//                parentNo = groupNo;
//            }
//
//            // Max Seq
//            int groupSeq = boardRepository.findMaxGroupSeqByGroupNo(groupNo);
//
//            // 부모글 Seq
//            int parentGroupSeq = boardRepository.findGroupSeqByParentNo(parentNo)+1;
//            int depth = boardRepository.findDepthByParentNo(parentNo);
//
//            // 부모글 Seq 값이 있는지
//            int isValue = boardRepository.findGroupSeqByGroupNoAndGroupSeq(groupNo,parentGroupSeq);
//
//            if(isValue > 0 ){
//                boardRepository.updateAllGroupSeq(groupNo,groupSeq);
//                board.setGroupSeq(parentGroupSeq);
//            }else{
//                board.setGroupSeq(groupSeq+1);
//            }
//            board.setParentNo(parentNo);
//            board.setDepth(depth+1);
//            board.setWriter(writer);
//
//            return boardRepository.save(board);
//
//        }else{
//            return new Board();
//        }
//
//    }
}
