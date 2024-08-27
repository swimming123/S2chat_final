package kr.co.ict.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict.finalproject.dao.CartDAO;
import kr.co.ict.finalproject.vo.CartVO;

@Service
public class CartService {
    // 개발자 : 이승희
    @Autowired
    private CartDAO cartDAO;

    public void cartadd(CartVO vo) {
        cartDAO.cartadd(vo);
    }

    public List<CartVO> cartlist(Long membernum) {
        return cartDAO.cartlist(membernum);
    }

    public CartVO cartorderlist(Long membernum, String productnum) {
        return cartDAO.cartorderlist(membernum, productnum);
    }

    public void cartupdate(Long membernum, String productnum, int count) {
        cartDAO.cartupdate(membernum, productnum, count);
    }

    public void cartdelete(Long membernum, String productnum) {
        cartDAO.cartdelete(membernum, productnum);
    }

}
