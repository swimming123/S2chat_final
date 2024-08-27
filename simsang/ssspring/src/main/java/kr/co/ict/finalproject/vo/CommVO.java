package kr.co.ict.finalproject.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("comm")
public class CommVO {
    // 개발자 : 정준영
    private int num;
    private int membernum;
    private String name;
    private int people;
    private int favorite;
    private String communitydate;
}
