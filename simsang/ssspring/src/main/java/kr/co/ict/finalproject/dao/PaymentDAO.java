package kr.co.ict.finalproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.ict.finalproject.vo.PaymentVO;

@Mapper
public interface PaymentDAO {
        // 개발자 : 이승희
        public void payadd(PaymentVO vo);

        public List<PaymentVO> paylist(@Param("membernum") Long membernum);

        public PaymentVO paydetail(@Param("paymentnum") Long paymentnum);

        public void payupdate(PaymentVO vo);

        public void paydelete(@Param("num") Long num);

}