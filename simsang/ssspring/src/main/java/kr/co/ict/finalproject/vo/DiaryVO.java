package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Alias("diaryvo")
public class DiaryVO {
    // 개발자 : 김건우
    private int num; // 일기 번호
    private int membernum; // 회원 번호
    private String emotion; // 감정 상태
    private String week; // 주간 정보
    private String title; // 제목
    private String content; // 내용
    private Date diarydate; // 일기 작성 날짜
    private Date diaryupdate; // 일기 수정 날짜
    private String diaryimg; // 이미지 파일명
    private MultipartFile diaryfile; // 파일 업로드를 위한 MultipartFile 객체

}
