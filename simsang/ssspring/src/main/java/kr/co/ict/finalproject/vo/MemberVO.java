package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("membervo")
public class MemberVO {
    // 개발자 : 이승희
    private int num;
    private String id;
    private String pwd;
    private String nick;
    private String email;
    private String tel;
    private String address;
    private String detailaddress;
    private String mdate;

    // null허용
    private String name;
    private String age;
    private String gender;
    private String imgn;

}