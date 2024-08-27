package kr.co.ict.finalproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ict.finalproject.vo.CommVO;

@Mapper
public interface CommDAO {
    // 개발자 : 정준영
    public void add(CommVO vo);

    public List<CommVO> list();

    public CommVO detail(int num);
}
