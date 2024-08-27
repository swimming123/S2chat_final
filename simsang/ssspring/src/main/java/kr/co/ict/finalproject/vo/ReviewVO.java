package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("reviewvo")
public class ReviewVO {
    // 개발자 : 송지미
    private Long review_num; // 리뷰 번호 (NUMBER(10))
    private Long member_num;
    private String review_content; // 리뷰 내용 (CLOB)
    private String review_productnum; // 리뷰 내용 (CLOB)
    private int review_stars; // 별점 (NUMBER(2))
    private String review_date; // 리뷰 작성 날짜 (DATE)
    private String review_imagename; // 이미지 파일 이름
    private String review_category; // 리뷰 카테고리 (VARCHAR2(255))
    private MultipartFile rf;
}
