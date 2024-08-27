package kr.co.ict.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict.finalproject.service.PaymentService;
import kr.co.ict.finalproject.vo.PaymentVO;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    // 개발자 : 이승희
    @Autowired
    public PaymentService paymentService;

    @PostMapping("add")
    public void paymentadd(@ModelAttribute PaymentVO vo) {
        paymentService.add(vo);
    }

    @PostMapping("list")
    public List<PaymentVO> paymentlist(@RequestParam("membernum") Long membernum) {
        return paymentService.list(membernum);
    }

    @PostMapping("detail")
    public PaymentVO paymentdetail(@RequestParam("paymentnum") Long paymentnum) {
        return paymentService.detail(paymentnum);
    }

    @PostMapping("update")
    public void paymentupdate(@ModelAttribute PaymentVO vo) {
        paymentService.update(vo);
    }

    @PostMapping("delete")
    public void paymentdelete(@RequestParam("num") Long num) {
        paymentService.delete(num);
    }
}
