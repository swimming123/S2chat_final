package kr.co.ict.finalproject.dao;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import kr.co.ict.finalproject.vo.HtpVO;

@Mapper
public interface HtpDao {
    // 개발자 : 허호준
    public void htpadd(HtpVO vo);

    public List<HtpVO> htplist();

    public void htpdelete(int num);
}