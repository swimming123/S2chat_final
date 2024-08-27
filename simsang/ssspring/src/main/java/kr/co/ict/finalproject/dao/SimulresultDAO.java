package kr.co.ict.finalproject.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ict.finalproject.vo.SimulresultVO;

@Mapper
public interface SimulresultDAO {
    // 개발자 : 이지영
    List<SimulresultVO> findAll();

    void insertSimulresult(SimulresultVO simulresult);

    void updateSimulresult(SimulresultVO simulresult);

    void deleteSimulresult(Long num);

    List<SimulresultVO> findBySimulationnum(Map<String, Object> params);

}
