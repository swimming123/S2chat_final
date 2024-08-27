package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("qna")
public class QnaVO {
    // 개발자 : 정준영
    private int num;
    private int membernum;
    private String name;
    private String qnaquestiondate;
    private String category;
    private String title;
    private String content;
    private String imgname;
    private String status;
}
