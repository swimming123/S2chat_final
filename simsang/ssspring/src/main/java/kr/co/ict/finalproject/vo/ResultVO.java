package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("result")
public class ResultVO {
    // 개발자 : 박성호
    private int num;
    private int membernum;
    private String testspecies;
    private String testcategory;
    private String testvideotext;
    private String simulationprofile;
    private String simulationresult;
    private String selfquestionresult;
    private String htpimgname;
    private String htpresult;
    private java.sql.Date testdate;
}
