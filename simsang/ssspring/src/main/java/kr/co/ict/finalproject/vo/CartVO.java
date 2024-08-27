package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("cartvo")
public class CartVO {
    // 개발자 : 박성호
    private Long membernum; // member num forign 연결
    private String productnum; // 상품번호
    private int count; // 상품수량
    private String cdate; // 장바구니 추가 날짜

}
