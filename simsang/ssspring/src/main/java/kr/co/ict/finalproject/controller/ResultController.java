package kr.co.ict.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict.finalproject.service.ResultService;
import kr.co.ict.finalproject.vo.ResultVO;

@RestController
@RequestMapping("/result")
public class ResultController {
    // 개발자 : 박성호
    @Autowired
    private ResultService ResultService;

    @RequestMapping("/list")
    public List<ResultVO> list() {
        return ResultService.listResult();
    }

    @GetMapping("/weekcount")
    public List<ResultVO> weekCount(@RequestParam("membernum") int membernum) {
        return ResultService.weekCount(membernum);
    }
}
