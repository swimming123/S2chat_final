package kr.co.ict.finalproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.ict.finalproject.vo.RequestVO;

@Mapper
public interface RequestDAO {
  // 개발자 : 이승희

  public void requestadd(RequestVO vo);

  public List<RequestVO> requestlist(@Param("membernum") Long membernum);

  public void requestupdate(RequestVO vo);

  public void requestdelete(@Param("requestnum") Long requestnum);
}
