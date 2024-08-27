package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("payvo")
public class PaymentVO {
    // 개발자 : 이승희
    private Long num; // 결제수단 넘버
    private Long membernum; // 멤버번호 forign연결
    private String paynick; // 결제별명
    private String payway; // 결제방식
    private String paycorp; // 결제회사
    private Long paynum; // 결제수단번호
    private String payday; // 년월
    private int paycvc; // cvc
    private int cdate; // 등록일
}
