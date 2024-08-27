package kr.co.ict.finalproject.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Alias("simulationvo")
public class SimulationVO {
    // 개발자 : 이지영
    private Long num;
    private Long membernum; // 추가된 membernum 필드
    private String category;
    private String type;
    private java.sql.Date simuldate; // 또는 java.sql.Date
    private String title;

    private List<SimuldialogVO> dialogs; // 시뮬대화 리스트
    private List<SimulresultVO> results; // 시뮬결과 리스트

}