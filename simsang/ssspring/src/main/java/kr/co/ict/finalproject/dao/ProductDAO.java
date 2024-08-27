package kr.co.ict.finalproject.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.ict.finalproject.vo.ProductVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductDAO {
    // 개발자 : 송지미
    void proAdd(ProductVO vo);

    List<ProductVO> proList();

    ProductVO proDetail(@Param("num") int num);

    void proDelete(@Param("num") int num);

    int proTotalCount(Map<String, String> map);

    void proUpdate(ProductVO vo);

    void proHit(@Param("num") int num);
}
