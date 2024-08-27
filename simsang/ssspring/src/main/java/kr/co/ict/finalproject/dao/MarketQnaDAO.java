package kr.co.ict.finalproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ict.finalproject.vo.MarketQnaVO;

@Mapper
public interface MarketQnaDAO {
    // 개발자 : 김건우
    public void add(MarketQnaVO vo);

    public List<MarketQnaVO> list();

    public MarketQnaVO detail(int num);

    public void delete(int num);

    public void update(MarketQnaVO vo);
}
