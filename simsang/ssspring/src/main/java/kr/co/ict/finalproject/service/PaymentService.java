package kr.co.ict.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict.finalproject.dao.PaymentDAO;
import kr.co.ict.finalproject.vo.PaymentVO;

@Service
public class PaymentService {
    // 개발자 : 이승희
    @Autowired
    private PaymentDAO paymentDAO;

    public void add(PaymentVO vo) {
        paymentDAO.payadd(vo);
    }

    public List<PaymentVO> list(Long membernum) {
        return paymentDAO.paylist(membernum);
    }

    public PaymentVO detail(Long paymentnum) {
        return paymentDAO.paydetail(paymentnum);
    }

    public void update(PaymentVO vo) {
        paymentDAO.payupdate(vo);
    }

    public void delete(Long num) {
        paymentDAO.paydelete(num);
    }

}