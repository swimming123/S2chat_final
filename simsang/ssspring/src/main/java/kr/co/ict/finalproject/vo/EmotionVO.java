package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Alias("emotionvo")
public class EmotionVO {
    // 개발자 : 박성호
    private int num;
    private int resultnum;
    private int membernum;
    private String name;
    private float score;
    private int count; // 감정횟수
    private float scoreaverage; // 감정지수평균
    private String weekday; // 무슨요일인지
    private int weekdaycount; // 요일별 카운트
    private String month; // 몇월인지
    private int happyday; // 회복기간
}
