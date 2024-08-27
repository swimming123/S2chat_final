package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Alias("simulresultvo")
public class SimulresultVO {
    // 개발자 : 이지영
    private Long num;
    private Long simulationnum;
    private String emotionname;
    private Long emotionscore;
    private Long stressscore;
    private Long heartrate;
    private String topemotion;
    private Long topemotionscore;
    private String topemotionimg;
    private String evaluation;
}
