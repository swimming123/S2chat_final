package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Alias("simpletestvo")
public class SimpletestVO {
    // 개발자 : 윤수영
    private int num;
    private String question;
    private String cause;
    private String description;
}
