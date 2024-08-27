package kr.co.ict.finalproject.controller;

import kr.co.ict.finalproject.service.SimpletestService;
import kr.co.ict.finalproject.vo.SimpletestVO;
import kr.co.ict.finalproject.vo.UserResponseVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/survey")
public class SimpletestController {
    // 개발자 : 윤수영
    @Autowired
    private SimpletestService simpletestService;

    @GetMapping("/getQuestion")
    public List<SimpletestVO> getQuestion() {
        return simpletestService.getQuestion();
    }

    @PostMapping("/insertResponse")
    public ResponseEntity<?> insertResponse(@RequestBody UserResponseVO vo) {
        try {
            simpletestService.insertResponse(vo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving response");
        }
    }

    @GetMapping("/getRespDetail")
    public List<SimpletestVO> getRespDetail(@RequestParam("userid") String userid,
            @RequestParam("respdate") String respdate) {

        return simpletestService.getRespDetail(userid, respdate);
    }

    @GetMapping("/getRespTotal")
    public int getRespTotal(@RequestParam("userid") String userid, @RequestParam("respdate") String respdate) {
        int score = simpletestService.getRespTotal(userid, respdate);
        int state = 0;
        if (10 <= score) {
            state = 1;
        } else if (5 <= score) {
            state = 2;
        } else if (0 <= score) {
            state = 3;
        } else {
            state = 0;
        }

        return state;
    }

    @GetMapping("/responses")
    public List<Map<String, Object>> getUserResponses(@RequestParam("userid") String userId) {
        return simpletestService.getUserResponses(userId);
    }

    @GetMapping("/info")
    public Map<String, Object> getUserInfo() {
        Map<String, Object> userInfo = new HashMap<>();

        userInfo.put("userName", "고영희");
        userInfo.put("userAddr", "서울특별시 서초구 서초동 대동빌딩 4층");
        userInfo.put("surveyName", "역학연구를 위한 우울척도 (CES-D)");
        userInfo.put("surveyType", "v");
        userInfo.put("surveyScore", 25);
        userInfo.put("surveyResult", "");
        userInfo.put("surveyKeyword", Arrays.asList("회사", "상사", "실수"));
        userInfo.put("surveyEmotion", "기쁨");
        userInfo.put("surveyEmtScore", 30);
        userInfo.put("emotionGauge", 0);

        Map<String, String> recommendActivity = new HashMap<>();
        recommendActivity.put("title", "산책");
        recommendActivity.put("comm", "오늘 가벼운 산책 어때요?");
        userInfo.put("recommendActivity", Arrays.asList(recommendActivity));

        return userInfo;
    }
}
