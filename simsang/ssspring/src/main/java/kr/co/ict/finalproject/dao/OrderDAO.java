package kr.co.ict.finalproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.ict.finalproject.vo.OrderVO;

@Mapper
public interface OrderDAO {
  // 개발자 : 이승희

  public void orderadd(OrderVO vo);

  public List<OrderVO> orderlist(@Param("requestnum") Long requestnum);

  public List<OrderVO> memlist(@Param("membernum") Long membernum);

  public void orderupdate(@Param("requestnum") Long requestnum, @Param("productnum") String productnum,
      @Param("count") int count);

  public void ordercancel(@Param("membernum") Long membernum, @Param("requestnum") String requestnum,
      @Param("delivery") String delivery);

  public OrderVO orderdetail(@Param("ordernum") Long ordernum);

  public void orderdelete(@Param("requestnum") Long requestnum, @Param("productnum") String productnum);

  public List<OrderVO> ordersearch(@Param("membernum") Long membernum, @Param("content") String content);

}
