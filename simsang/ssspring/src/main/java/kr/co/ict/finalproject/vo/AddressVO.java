package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("addressvo")
public class AddressVO {
    // 개발자 : 이승희
    private Long num; // 배송비 번호
    private Long membernum; // 멤버번호 forign연결
    private String addressnick; // 주소별명
    private String address; // 주소
    private String detailaddress; // 상세주소
    private String addrequest; // 배송시 요청사항
}
