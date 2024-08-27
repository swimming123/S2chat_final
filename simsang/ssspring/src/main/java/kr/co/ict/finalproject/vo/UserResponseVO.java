package kr.co.ict.finalproject.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("userresponsevo")
public class UserResponseVO {
    // 개발자 : 윤수영
    private int id;
    private String userid;
    private String surveycode;
    private int qnum;
    private int respscore;
    private Date respdate;
}
