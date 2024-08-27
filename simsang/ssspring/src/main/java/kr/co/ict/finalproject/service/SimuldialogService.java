package kr.co.ict.finalproject.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ict.finalproject.dao.SimulationDAO;
import kr.co.ict.finalproject.dao.SimuldialogDAO;
import kr.co.ict.finalproject.vo.SimulationVO;
import kr.co.ict.finalproject.vo.SimuldialogVO;

@Service
@Transactional
public class SimuldialogService {
    // 개발자 : 이지영
    @Autowired
    private SimuldialogDAO simuldialogDAO;

    @Autowired
    private SimulationDAO simulationDAO;

    public List<SimuldialogVO> getAllSimuldialogs() {
        return simuldialogDAO.findAll();
    }

    public List<SimuldialogVO> getSimuldialogsByStatusAndSimulationnum(String status, Long simulationnum) {
        Map<String, Object> params = Map.of("status", status, "simulationnum", simulationnum);
        return simuldialogDAO.findByStatusAndSimulationnum(params);
    }

    public void saveSimuldialog(SimuldialogVO simuldialog) {
        simuldialogDAO.insertSimuldialog(simuldialog);
    }

    public void updateSimuldialog(SimuldialogVO simuldialog) {
        simuldialogDAO.updateSimuldialog(simuldialog);
    }

    @Transactional
    public Long createSimulationAndSaveDialog(SimulationVO simulation, SimuldialogVO simuldialog) {
        // 시뮬레이션 생성
        simulationDAO.insertSimulation(simulation);

        // 생성된 시뮬레이션 번호 가져오기
        Long simulationNum = simulation.getNum(); // 또는 simulationDAO.getNextSimulationId()를 사용하여 시퀀스 값을 직접 조회

        // 시뮬레이션 번호를 SimuldialogVO에 설정
        simuldialog.setSimulationnum(simulationNum);

        // Simuldialog 저장
        simuldialogDAO.insertSimuldialog(simuldialog);

        return simulationNum;
    }

    @Transactional
    public void deleteSimuldialog(Long num) {
        // 1. 시뮬대화 레코드 삭제
        simuldialogDAO.deleteSimuldialog(num);

        // 2. 시뮬레이션 레코드 삭제
        simulationDAO.deleteSimulation(num);
    }

}
