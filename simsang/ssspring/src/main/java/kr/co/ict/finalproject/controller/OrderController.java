package kr.co.ict.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict.finalproject.service.OrderService;
import kr.co.ict.finalproject.vo.OrderVO;

@RestController
@RequestMapping("/order")
public class OrderController {
    // 개발자 : 이승희
    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<?> orderadd(@ModelAttribute OrderVO vo) {
        orderService.orderadd(vo);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/list")
    public ResponseEntity<List<OrderVO>> orderlist(@RequestParam("requestnum") Long requestnum) {
        List<OrderVO> orderlist = orderService.orderlist(requestnum);
        return ResponseEntity.ok(orderlist);
    }

    @PostMapping("/cancellist/{membernum}")
    public ResponseEntity<List<OrderVO>> memlist(@PathVariable Long membernum) {
        List<OrderVO> orderlist = orderService.memlist(membernum);
        return ResponseEntity.ok(orderlist);
    }

    @PostMapping("/canceldetail/{ordernum}")
    public ResponseEntity<?> cancellist(@PathVariable Long ordernum) {
        OrderVO order = orderService.canceldetail(ordernum);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/update")
    public ResponseEntity<?> orderupdate(@RequestParam("membernum") Long membernum,
            @RequestParam("requestnum") String productnum, @RequestParam("count") int count) {
        orderService.orderupdate(membernum, productnum, count);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delivery")
    public ResponseEntity<?> ordercancel(@RequestParam("membernum") Long membernum,
            @RequestParam("requestnum") String requestnum, @RequestParam("delivery") String delivery) {
        orderService.ordercancel(membernum, requestnum, delivery);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> orderdelete(@RequestParam("membernum") Long membernum,
            @RequestParam("productnum") String productnum) {
        orderService.orderdelete(membernum, productnum);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public ResponseEntity<?> ordersearch(@RequestParam("membernum") Long membernum,
            @RequestParam("content") String content) {
        List<OrderVO> vo = orderService.ordersearch(membernum, content);
        return ResponseEntity.ok(vo);
    }

}