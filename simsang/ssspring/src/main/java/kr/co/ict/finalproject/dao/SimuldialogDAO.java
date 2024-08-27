package kr.co.ict.finalproject.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ict.finalproject.vo.SimuldialogVO;

@Mapper
public interface SimuldialogDAO {
    // 개발자 : 이지영
    List<SimuldialogVO> findAll();

    void insertSimuldialog(SimuldialogVO simuldialog);

    void updateSimuldialog(SimuldialogVO simuldialog);

    void deleteSimuldialog(Long num);

    List<SimuldialogVO> findByStatusAndSimulationnum(Map<String, Object> params);

    List<SimuldialogVO> findBySimulationnum(Long simulationNum); // 추가된 메서드
}
