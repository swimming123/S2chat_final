package kr.co.ict.finalproject.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ict.finalproject.vo.QnaVO;

import java.util.List;

@Mapper
public interface QnaDAO {
    // 개발자 : 정준영
    public void add(QnaVO vo);

    public List<QnaVO> list(int membernum);

    public QnaVO detail(int num);

    public void delete(int num);

    public void update(QnaVO vo);

}
