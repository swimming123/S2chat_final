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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict.finalproject.service.SimulresultService;
import kr.co.ict.finalproject.vo.SimulresultVO;

@RestController
@RequestMapping("/simulresults")
public class SimulresultController {
    // 개발자 : 이지영

    @Autowired
    private SimulresultService simulresultService;

    @GetMapping("/simulresultlist")
    public ResponseEntity<?> getSimulresultsBySimulationnum(
            @RequestParam(name = "simulationnum") Long simulationnum) {
        List<SimulresultVO> results = simulresultService.getSimulresultsBySimulationnum(simulationnum);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/simulresultinsert")
    public ResponseEntity<?> createSimulresult(@RequestBody SimulresultVO simulresult) {
        simulresultService.saveSimulresult(simulresult);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/simulresultdelete/{num}")
    public ResponseEntity<?> deleteSimulresult(@PathVariable Long num) {
        simulresultService.deleteSimulresult(num);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/simulresultupdate")
    public ResponseEntity<?> updateSimulresult(@RequestBody SimulresultVO simulresult) {
        simulresultService.updateSimulresult(simulresult);
        return ResponseEntity.ok().build();
    }
}
