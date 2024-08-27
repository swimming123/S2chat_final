package kr.co.ict.finalproject.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("talk")
public class TalkVO {
    // 개발자 : 정준영
    private long num;
    private int communitynum;
    private int membernum;
    private String message;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date talkdate;
}
