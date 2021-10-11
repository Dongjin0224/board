package com.example.board.mappers;

import com.example.board.beans.vo.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class BoardMapperTest {

    @Autowired
    private BoardMapper mapper;

    @Test
    public void testGetList(){
        mapper.getList().forEach(board -> log.info(board.toString()));
    }

    @Test
    public void testInsert(){
        BoardVO vo = new BoardVO();
        vo.setTitle("여기가 제목");
        vo.setContent("여기가 내용");
        vo.setWriter("user00");
        mapper.insert(vo);
    }

    @Test
    public void testInsertSelectKey_bno(){
        BoardVO vo = new BoardVO();
        vo.setTitle("여기가 제목2");
        vo.setContent("여기가 내용2");
        vo.setWriter("user01");
        mapper.insertSelectKey_bno(vo);
    }

    @Test
    public void testRead(){
        log.info(mapper.read(5L).toString());
    }

    @Test
    public void testUpdate(){
        if(mapper.read(7L) == null){
            log.info("*****************NO SUCH BOARD****************");
        }else {
            BoardVO vo = new BoardVO();
            vo.setTitle("수정 잘 됩니까");
            vo.setContent("잘 됩니다!!");
            vo.setBno((long) 7);
            log.info("UPDATE COUNT : " + mapper.update(vo));
        }
    }

    @Test
    public void testDelete(){
        if(mapper.read(7L) == null){
            log.info("*****************NO SUCH BOARD****************");
        }else {
            log.info("DELETE COUNT : " + mapper.delete((long) 7L));
        }
    }
}
