package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("ordervo")
public class OrderVO {
    // 개발자 : 이승희
    private Long ordernum; // 상품주문번호
    private Long requestnum; // 주문번호 request랑 forgin연결
    private Long membernum; // 상품번호
    private String productnum; // 상품번호
    private String name; // 상품이름
    private int count; // 상품수량
    private double price; // 상품가격
    private String odate; // 장바구니 추가 날짜
    private String img; // 이미지
    private String delivery; // 배송상태

}
