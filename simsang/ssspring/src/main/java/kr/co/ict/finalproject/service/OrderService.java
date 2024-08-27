package kr.co.ict.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict.finalproject.dao.OrderDAO;
import kr.co.ict.finalproject.vo.OrderVO;

@Service
public class OrderService {
    // 개발자 : 이승희
    @Autowired
    private OrderDAO orderDAO;

    public void orderadd(OrderVO vo) {
        orderDAO.orderadd(vo);
    }

    public List<OrderVO> orderlist(Long requestnum) {
        return orderDAO.orderlist(requestnum);
    }

    public List<OrderVO> memlist(Long membernum) {
        return orderDAO.memlist(membernum);
    }

    public void orderupdate(Long membernum, String productnum, int count) {
        orderDAO.orderupdate(membernum, productnum, count);
    }

    public void ordercancel(Long membernum, String requestnum, String delivery) {
        orderDAO.ordercancel(membernum, requestnum, delivery);
    }

    public OrderVO canceldetail(Long ordernum) {
        return orderDAO.orderdetail(ordernum);
    }

    public void orderdelete(Long membernum, String productnum) {
        orderDAO.orderdelete(membernum, productnum);
    }

    public List<OrderVO> ordersearch(Long membernum, String content) {
        return orderDAO.ordersearch(membernum, content);
    }

}