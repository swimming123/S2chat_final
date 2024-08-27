package kr.co.ict.finalproject.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ict.finalproject.dao.SimulationDAO;
import kr.co.ict.finalproject.dao.SimulresultDAO;
import kr.co.ict.finalproject.vo.SimulationVO;
import kr.co.ict.finalproject.vo.SimulresultVO;

@Service
@Transactional
public class SimulresultService {
    // 개발자 : 이지영
    @Autowired
    private SimulresultDAO simulresultDAO;

    @Autowired
    private SimulationDAO simulationDAO;

    public List<SimulresultVO> getSimulresultsBySimulationnum(Long simulationnum) {
        Map<String, Object> params = Map.of("simulationnum", simulationnum);
        return simulresultDAO.findBySimulationnum(params);
    }

    public void saveSimulresult(SimulresultVO simulresult) {
        simulresultDAO.insertSimulresult(simulresult);
    }

    public void updateSimulresult(SimulresultVO simulresult) {
        simulresultDAO.updateSimulresult(simulresult);
    }

    @Transactional
    public Long createSimulationAndSaveResult(SimulationVO simulation, SimulresultVO simulresult) {
        // 시뮬레이션 생성
        simulationDAO.insertSimulation(simulation);

        // 생성된 시뮬레이션 번호 가져오기
        Long simulationNum = simulation.getNum(); // 또는 simulationDAO.getNextSimulationId()를 사용하여 시퀀스 값을 직접 조회

        // 시뮬레이션 번호를 SimuldialogVO에 설정
        simulresult.setSimulationnum(simulationNum);

        // Simuldialog 저장
        simulresultDAO.insertSimulresult(simulresult);

        return simulationNum;
    }

    @Transactional
    public void deleteSimulresult(Long num) {
        // 1. 시뮬대화 레코드 삭제
        simulresultDAO.deleteSimulresult(num);

        // 2. 시뮬레이션 레코드 삭제
        simulationDAO.deleteSimulation(num);
    }
}
