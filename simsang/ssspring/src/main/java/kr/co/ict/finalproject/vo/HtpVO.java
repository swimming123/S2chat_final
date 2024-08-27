package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("htpvo")
public class HtpVO {
    // 개발자 : 허호준
    private int htpnum;
    private String id;
    private String htpimg; // CLOB은 String으로 매핑
    private MultipartFile htpfile; // 파일 업로드용, DB와는 관련 없음
    private int roof;
    private int wall;
    private int door;
    private int windows;
    private int chimney;
    private int smoke;
    private int road;
    private int house;
    private int grass;
    private int tree;
    private int pond;
    private int fence;
    private int sun;
    private int mountain;
    private int flower;
    private String htpdate;
}