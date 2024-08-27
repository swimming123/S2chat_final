package kr.co.ict.finalproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.ict.finalproject.vo.CartVO;

@Mapper
public interface CartDAO {
        // 개발자 : 이승희
        public void cartadd(CartVO vo);

        public List<CartVO> cartlist(@Param("membernum") Long membernum);

        public CartVO cartorderlist(@Param("membernum") Long membernum, @Param("productnum") String productnum);

        public void cartupdate(@Param("membernum") Long membernum, @Param("productnum") String productnum,
                        @Param("count") int count);

        public void cartdelete(@Param("membernum") Long membernum, @Param("productnum") String productnum);
}