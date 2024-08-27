package kr.co.ict.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict.finalproject.service.CommService;
import kr.co.ict.finalproject.vo.CommVO;

@RestController
@RequestMapping("/comm")
public class CommController {
    // 개발자 : 정준영
    @Autowired
    private CommService commService;

    @PostMapping("/add")
    public ResponseEntity<?> commadd(@RequestBody CommVO vo) {
        commService.add(vo);
        return ResponseEntity.ok().body(vo);
    }

    @GetMapping("/commlist")
    public List<CommVO> commlist() {
        return commService.list();
    }

}
