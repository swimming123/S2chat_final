package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Alias("simuldialogvo")
public class SimuldialogVO {
    // 개발자 : 이지영
    private Long num;
    private Long simulationnum; // 추가된 simulationnum 필드
    private String conversation;
    private String status;

}
