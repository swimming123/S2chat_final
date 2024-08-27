package kr.co.ict.finalproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ict.finalproject.vo.SimulationVO;

@Mapper
public interface SimulationDAO {
    // 개발자 : 이지영
    List<SimulationVO> findAll();

    Long insertSimulation(SimulationVO simulation);

    void updateSimulation(SimulationVO simulation);

    void deleteSimulation(Long num);

    List<SimulationVO> findSimulationWithDialog();

    List<SimulationVO> findSimulationWithDialogAndEmotion(Long simulationNum); // 시뮬레이션과 대화 및 감정 데이터 조회

}
