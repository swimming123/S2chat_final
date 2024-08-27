package kr.co.ict.finalproject.controller;

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

import kr.co.ict.finalproject.service.SimuldialogService;
import kr.co.ict.finalproject.vo.SimuldialogVO;

@RestController
@RequestMapping("/simuldialogs")
public class SimuldialogController {
    // 개발자 : 이지영
    @Autowired
    private SimuldialogService simuldialogService;

    @GetMapping("/simuldialoglist")
    public ResponseEntity<?> getSimuldialogsByStatusAndSimulationnum(
            @RequestParam String status,
            @RequestParam Long simulationnum) {
        return ResponseEntity.ok(simuldialogService.getSimuldialogsByStatusAndSimulationnum(status, simulationnum));
    }

    @PostMapping("/simuldialoginsert")
    public ResponseEntity<?> createSimuldialog(@RequestBody SimuldialogVO simuldialog) {
        simuldialogService.saveSimuldialog(simuldialog);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/simuldialogdelete/{num}")
    public ResponseEntity<?> deleteSimuldialog(@PathVariable Long num) {
        simuldialogService.deleteSimuldialog(num);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/simuldialogupdate")
    public ResponseEntity<?> updateSimuldialog(@RequestBody SimuldialogVO simuldialog) {
        simuldialogService.updateSimuldialog(simuldialog);
        return ResponseEntity.ok().build();
    }
}
