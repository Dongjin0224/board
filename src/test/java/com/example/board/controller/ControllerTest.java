package com.example.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class ControllerTest {

//    가짜 MVC
//    마치 브라우저에서 URL을 요청한 것처럼 환경을 만들어준다.
    private MockMvc mockMvc;

//    요청을 처리해주는 WebApplicationContext를 불러온다.
    @Autowired
    private WebApplicationContext webApplicationContext;

//    하위의 모든 테스타가 실행 전에 실행되도록 한다.
    @BeforeEach // 먼저 실행되게 해주는 어노테이션
    public void setUp(){
//        가짜 MVC에 WebApplicationContext를 전달한 후 환경을 생성한다.
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testList() throws Exception{
        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))//.get은 가져오는게 아니고 방식을 말하는 것
                .andReturn().getModelAndView().getModelMap().toString());
    }

    @Test
    public void testRegister() throws Exception{
        String bno = mockMvc.perform(
                MockMvcRequestBuilders.post("/board/register")
                .param("title", "테스트 새 글 제목")
                .param("content", "테스트 새 글 내용")
                .param("writer", "kdj1234")
        ).andReturn().getFlashMap().toString();

        log.info(bno);
    }

    @Test
    public void testRead() throws Exception{
        String board = mockMvc.perform(
                MockMvcRequestBuilders.get("/board/read")
               .param("bno" , "8")
        ).andReturn().getModelAndView().getViewName();/*getModelMap().toString();*/

        log.info(board);
    }

    @Test
    public void testModify() throws Exception{
        String result = mockMvc.perform(
                    MockMvcRequestBuilders.post("/board/modify")
                            .param("bno", "8")
                            .param("title", "수정된 새 제목!!!")
                            .param("content", "수정된 새 내용!!!")
            ).andReturn().getModelAndView().getModelMap().toString();

        log.info(result);
    }

    @Test
    public void testRemove() throws Exception{
        String result = mockMvc.perform(
                MockMvcRequestBuilders.post("/board/remove")
                    .param("bno", "10")
        ).andReturn().getFlashMap().toString();

        log.info(result);
    }

    @Test
    public void testGoRegister() throws Exception{
        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/register"))
        .andReturn().getModelAndView().getViewName());
    }

    @Test
    public void testGoModify() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/board/modify").param("bno","8"))
                .andReturn().getModelAndView().getModelMap().toString();
    }
}
