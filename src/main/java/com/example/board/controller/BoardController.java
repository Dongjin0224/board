package com.example.board.controller;


import com.example.board.beans.vo.BoardVO;
import com.example.board.services.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

//  프레젠테이션 계층의 구현

//  Task        URL              Method      Parameter       Form        URL이동
//  전체 목록   /board/list         GET         없음           없음         없음
//  등록 처리   /board/register     POST        모든 항목       필요         이동
//  조회 처리   /board/read         GET         bno           필요         없음
//  삭제 처리   /board/remove       POST        bno           필요         이동
//  수정 처리   /board/modify       POST        모든 항목       필요         이동

@Controller
@Slf4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("list")
    public String list(Model model){
        log.info("-----------------------------------");
        log.info("list");
        log.info("-----------------------------------");
        model.addAttribute("list", boardService.getList());
        return "board/list";
    }

    @PostMapping("register")
    public RedirectView register(BoardVO boardVO, RedirectAttributes rttr){
        log.info("-----------------------------------");
        log.info("register + " + boardVO.toString());
        log.info("-----------------------------------");

        boardService.register(boardVO);
//        쿼리 스트링으로 전달(GET방식)
//        rttr.addAttribute("bno", boardVO.getBno());

//        세션의 flash영역을 이용하여 전달
        rttr.addFlashAttribute("bno", boardVO.getBno()); //

//        RedirectView를 사용하면 redirect방식으로 전송이 가능하다.
        return new RedirectView("list");
    }

//        여러 요청을 하나의 메소드로 받을 때에는 {}를 사용하여 콤마로 구분한다.
    @GetMapping({"read", "modify"})
    public void read(@RequestParam("bno") Long bno, String result, Model model, HttpServletRequest request){
        String reqURI = request.getRequestURI();
        //read 요청 시 read 출력
        //modify 요청 시 modify 출력
        String reqType = reqURI.substring(reqURI.indexOf(request.getContextPath()) + 7);
//        String URI = reqURI.split("/")[2];
        log.info("-----------------------------------");
        log.info(reqType + " : " + bno);
        log.info("-----------------------------------");

        model.addAttribute("board", boardService.get(bno));
    }

//    /modify 요청을 처리할 수 있는 비지니스 로직 작성
//    수정 성공시 result에 "success"를 담아서 전달한다.
//    단위 테스트로 View에 전달할 파라미터를 조회한다.

    @PostMapping("modify")
    public RedirectView modify(BoardVO boardVO, RedirectAttributes rttr){
        log.info("-----------------------------------");
        log.info("modify + " + boardVO.toString());
        log.info("-----------------------------------");

        if(boardService.modify(boardVO)){
            rttr.addAttribute("result", "success");
            rttr.addAttribute("bno", boardVO.getBno());
        }else{
            rttr.addAttribute("result", "fail");
        }

        return new RedirectView("read");
    }

//    /remove 요청을 처리할 수 있는 비지니스 로직 작성
//    삭제 성공 시 result에 "success"를 flash에 담아서 전달한다.
//    삭제 실패 시 result에 "fail"을 flash에 담아서 전달한다.
//    단위 테스트로 전달할 파라미터를 조회한다.

    @PostMapping("remove")
    public RedirectView remove(@RequestParam("bno") Long bno, RedirectAttributes rttr){
        log.info("-----------------------------------");
        log.info("remove + " + bno);
        log.info("-----------------------------------");

        if(boardService.remove(bno)){
            rttr.addFlashAttribute("result", "success");
        }else{
            rttr.addFlashAttribute("result","fail");
        }

        return new RedirectView("list");
    }


    @GetMapping("register")
    public void register(){}
}
