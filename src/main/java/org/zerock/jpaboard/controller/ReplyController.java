package org.zerock.jpaboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.jpaboard.dto.ReplyDTO;
import org.zerock.jpaboard.service.ReplyService;

import java.util.List;

@RestController
@RequestMapping("/replies")
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService; // 자동주입을 위해 final

    //댓글 출력
    @GetMapping(value =  "/board/{bno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno) {

        log.info("bno: " + bno);

        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);

    }

    // 댓글 등록
    @PostMapping("")
    public ResponseEntity<Long> register (@RequestBody ReplyDTO replyDTO) {

        log.info("{}",replyDTO);

        Long rno = replyService.register(replyDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);

    }

    // 댓글 삭제
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {

        log.info("RNO : " + rno);

        replyService.remove(rno);

        return new ResponseEntity<>("success" , HttpStatus.OK);

    }

    // 댓글 수정
    @PutMapping("/{rno}")
    public ResponseEntity<String> modify (@RequestBody ReplyDTO replyDTO) {
        log.info("{}",replyDTO);

        replyService.modify(replyDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
