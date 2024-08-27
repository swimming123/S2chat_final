package kr.co.ict.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ict.finalproject.dao.SimulationDAO;
import kr.co.ict.finalproject.vo.SimulationVO;

@Service
@Transactional
public class SimulationService {
    // 개발자 : 이지영
    @Autowired
    private SimulationDAO simulationDao;

    public List<SimulationVO> getAllSimulations() {
        return simulationDao.findAll();
    }

    @Transactional
    public List<SimulationVO> getAllSimulationsWithDialog() {
        simulationDao.findSimulationWithDialog();
        return simulationDao.findSimulationWithDialog();
    }

    public Long insertSimulation(SimulationVO simulation) {
        simulationDao.insertSimulation(simulation);
        return simulation.getNum();
    }

    public void updateSimulation(SimulationVO simulation) {
        simulationDao.updateSimulation(simulation);
    }

    public void deleteSimulation(Long num) {
        simulationDao.deleteSimulation(num);
    }

}
