package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("requestvo")
public class RequestVO {
    // 개발자 : 이승희
    private Long membernum; // 멤버번호 forign연결
    private Long requestnum; // 주문번호 백단
    private String address; // 배송지
    private String detailaddress; // 상세주소
    private String addrequest; // 주문요청사항
    private String way; // 결제방식
    private String waycorp; // 결제방식 회사
    private String waynum; // 결제방식 번호
    private String rdate; // 주문일 자동

}
