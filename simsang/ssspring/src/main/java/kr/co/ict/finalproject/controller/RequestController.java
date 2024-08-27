package kr.co.ict.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict.finalproject.service.CartService;
import kr.co.ict.finalproject.service.OrderService;
import kr.co.ict.finalproject.service.ProductService;
import kr.co.ict.finalproject.service.RequestService;
import kr.co.ict.finalproject.vo.CartVO;
import kr.co.ict.finalproject.vo.OrderVO;
import kr.co.ict.finalproject.vo.ProductVO;
import kr.co.ict.finalproject.vo.RequestVO;

@RestController
@RequestMapping("/request")
public class RequestController {
    // 개발자 : 이승희
    @Autowired
    private RequestService requestService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> requestadd(@ModelAttribute RequestVO requestvo,
            @RequestParam("productnum") List<String> productnumlist) {
        Long requestnum = requestService.generateOrderNumber();
        requestvo.setRequestnum(requestnum);
        requestService.requestadd(requestvo);
        List<CartVO> cartlist = cartService.cartlist(requestvo.getMembernum());
        try {
            for (String productnum : productnumlist) {
                for (CartVO cartvo : cartlist) {
                    if (cartvo.getProductnum().equals(productnum)) {
                        OrderVO ordervo = new OrderVO();
                        ordervo.setMembernum(requestvo.getMembernum());
                        ordervo.setRequestnum(requestnum);
                        ordervo.setProductnum(productnum);
                        ProductVO pvo = productService.detail(Integer.parseInt(productnum));
                        ordervo.setName(pvo.getProduct_name());
                        ordervo.setPrice(pvo.getProduct_price());
                        ordervo.setImg(pvo.getProduct_image());
                        ordervo.setCount(cartvo.getCount());
                        ordervo.setDelivery("배송준비중");
                        orderService.orderadd(ordervo);
                        cartService.cartdelete(cartvo.getMembernum(), productnum);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            requestService.requestdelete(requestnum);
        }
        return ResponseEntity.ok(requestnum);
    }

    @PostMapping("/list")
    public ResponseEntity<?> requestlist(@RequestParam("membernum") Long membernum) {
        List<RequestVO> requestlist = requestService.requestlist(membernum);
        if (requestlist == null || requestlist.isEmpty()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(requestlist);
    }

    @PostMapping("/detail")
    public ResponseEntity<?> requestdetail(@RequestParam("membernum") Long membernum,
            @RequestParam("requestnum") Long requestnum) {
        List<RequestVO> requestlist = requestService.requestlist(membernum);
        for (RequestVO requestvo : requestlist) {
            if (requestvo.getRequestnum().equals(requestnum)) {
                return ResponseEntity.ok(requestvo);
            }
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> requestcancel(@RequestParam("membernum") Long membernum,
            @RequestParam("requestnum") Long requestnum) {
        List<RequestVO> requestlist = requestService.requestlist(membernum);
        for (RequestVO requestvo : requestlist) {
            if (requestvo.getRequestnum() == requestnum) {
                requestService.requestdelete(requestnum);
            }
        }
        return ResponseEntity.ok().build();
    }

}