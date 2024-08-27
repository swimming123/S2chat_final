package kr.co.ict.finalproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict.finalproject.service.CartService;
import kr.co.ict.finalproject.service.ProductService;
import kr.co.ict.finalproject.vo.CartVO;
import kr.co.ict.finalproject.vo.OrderVO;
import kr.co.ict.finalproject.vo.ProductVO;

@RestController
@RequestMapping("/cart")
public class CartController {
    // 개발자 : 이승희
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> cartadd(@ModelAttribute CartVO vo) {
        cartService.cartadd(vo);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/list")
    public ResponseEntity<List<CartVO>> cartlist(@RequestParam("membernum") Long membernum) {
        List<CartVO> cartlist = cartService.cartlist(membernum);
        return ResponseEntity.ok(cartlist);
    }

    @PostMapping("/orderlist")
    public ResponseEntity<List<OrderVO>> cartorderlist(@RequestParam("membernum") Long membernum,
            @RequestParam("productnum") List<String> productnumlist) {
        List<OrderVO> orderlist = new ArrayList<>();
        for (String productnum : productnumlist) {
            CartVO cartvo = cartService.cartorderlist(membernum, productnum);
            if (cartvo.getProductnum().equals(productnum)) {
                OrderVO ordervo = new OrderVO();
                ordervo.setProductnum(productnum);
                ProductVO pvo = productService.detail(Integer.parseInt(productnum));
                ordervo.setName(pvo.getProduct_name());
                ordervo.setPrice(pvo.getProduct_price());
                ordervo.setCount(cartvo.getCount());
                ordervo.setImg(pvo.getProduct_image());
                orderlist.add(ordervo);
            }
        }
        return ResponseEntity.ok(orderlist);
    }

    @PostMapping("/update")
    public ResponseEntity<?> cartupdate(@RequestParam("membernum") Long membernum,
            @RequestParam("productnum") String productnum, @RequestParam("count") int count) {
        if (count == 0) {
            cartService.cartdelete(membernum, productnum);
        }
        cartService.cartupdate(membernum, productnum, count);
        CartVO vo = cartService.cartorderlist(membernum, productnum);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> cartdelete(@RequestParam("membernum") Long membernum,
            @RequestParam("productnum") String productnum) {
        cartService.cartdelete(membernum, productnum);
        return ResponseEntity.ok().build();
    }

}
