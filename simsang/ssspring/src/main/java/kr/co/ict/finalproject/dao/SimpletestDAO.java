package kr.co.ict.finalproject.dao;

import java.util.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ict.finalproject.vo.SimpletestVO;
import kr.co.ict.finalproject.vo.UserResponseVO;

@Mapper
public interface SimpletestDAO {
    // 개발자 : 윤수영
    List<SimpletestVO> getQuestion();

    void insertResponse(UserResponseVO userResponseVO);

    List<SimpletestVO> getRespDetail(@Param("userid") String userid, @Param("respdate") String respdate);

    int getRespTotal(@Param("userid") String userid, @Param("respdate") String respdate);

    List<UserResponseVO> getUserResponses(@Param("userid") String userid, @Param("respdate") String respdate);

    List<Map<String, Object>> getUserResponses(@Param("userid") String userid);
}