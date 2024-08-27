package kr.co.ict.finalproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import kr.co.ict.finalproject.vo.ResultVO;

@Mapper
public interface ResultDao {
    // 개발자 : 박성호
    public List<ResultVO> listResult();

    public List<ResultVO> weekCount(int membernum);
}
