package kr.co.ict.finalproject.service;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.ict.finalproject.dao.SimpletestDAO;
import kr.co.ict.finalproject.vo.SimpletestVO;
import kr.co.ict.finalproject.vo.UserResponseVO;

@Service
public class SimpletestService {
    // 개발자 : 이지영

    @Autowired
    private SimpletestDAO simpletestDAO;

    public List<SimpletestVO> getQuestion() {
        return simpletestDAO.getQuestion();
    }

    public void insertResponse(UserResponseVO userResponseVO) {
        try {
            simpletestDAO.insertResponse(userResponseVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting response", e);
        }
    }

    public List<SimpletestVO> getRespDetail(@Param("userid") String userid, @Param("respdate") String respdate) {
        return simpletestDAO.getRespDetail(userid, respdate);
    }

    public int getRespTotal(@Param("userid") String userid, @Param("respdate") String respdate) {
        return simpletestDAO.getRespTotal(userid, respdate);
    }

    public List<Map<String, Object>> getUserResponses(@Param("userid") String userid) {
        return simpletestDAO.getUserResponses(userid);
    }

}
