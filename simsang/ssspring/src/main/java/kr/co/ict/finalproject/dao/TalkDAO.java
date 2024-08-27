package kr.co.ict.finalproject.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ict.finalproject.vo.TalkVO;

@Mapper
public interface TalkDAO {
    // 개발자 : 정준영
    public void add(TalkVO vo);
}
