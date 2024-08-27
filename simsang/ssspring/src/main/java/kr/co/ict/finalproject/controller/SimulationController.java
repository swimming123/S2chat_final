package kr.co.ict.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict.finalproject.service.SimulationService;
import kr.co.ict.finalproject.vo.SimulationVO;

@RestController
@RequestMapping("/simulations")
public class SimulationController {
    // 개발자 : 이지영
    @Autowired
    private SimulationService simulationService;

    @GetMapping("/simulationlist")
    public List<SimulationVO> getAllSimulations() {
        return simulationService.getAllSimulations();
    }

    @GetMapping("/with-dialogs")
    public List<SimulationVO> getAllSimulationsWithDialogs() {
        return simulationService.getAllSimulationsWithDialog();
    }

    @PostMapping("/simulationinsert")
    public ResponseEntity<?> createSimulation(@RequestBody SimulationVO simulation) {
        Long num = simulationService.insertSimulation(simulation);
        return ResponseEntity.ok(num);
    }

    @DeleteMapping("/simulationdelete/{num}")
    public ResponseEntity<?> deleteSimulation(@PathVariable Long num) {
        simulationService.deleteSimulation(num);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/simulationupdate")
    public ResponseEntity<?> updateSimulation(@RequestBody SimulationVO simulation) {
        simulationService.updateSimulation(simulation);
        return ResponseEntity.ok().build();
    }

}
