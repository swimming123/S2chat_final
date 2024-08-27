package kr.co.ict.finalproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.ict.finalproject.vo.AddressVO;

@Mapper
public interface AddressDAO {
        // 개발자 : 이승희
        public void addressadd(AddressVO vo);

        public List<AddressVO> addresslist(@Param("membernum") Long membernum);

        public AddressVO addressdetail(@Param("addressnum") Long addressnum);

        public void addressupdate(AddressVO vo);

        public void addressdelete(@Param("num") Long num);

}