package kr.co.ict.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict.finalproject.service.EmotionService;
import kr.co.ict.finalproject.vo.EmotionVO;

@RestController
@RequestMapping("/emotion")
public class EmotionController {
    // 개발자 : 박성호
    @Autowired
    private EmotionService emotionService;

    @GetMapping("/emotioncount")
    public List<EmotionVO> listEmotion(@RequestParam("membernum") int membernum) {
        return emotionService.listEmotion(membernum);
    }

    @GetMapping("/weekemotion")
    public List<EmotionVO> weekEmotion(@RequestParam("membernum") int membernum) {
        return emotionService.weekEmotion(membernum);
    }

    @GetMapping("/monthEmotion")
    public List<EmotionVO> monthEmotion(@RequestParam("membernum") int membernum) {
        return emotionService.monthEmotion(membernum);
    }

    @GetMapping("/happydays")
    public List<EmotionVO> happydays(@RequestParam("membernum") int membernum) {
        return emotionService.happydays(membernum);
    }

    @GetMapping("/bubbleemotion")
    public List<EmotionVO> bubbleemotion(@RequestParam("membernum") int membernum) {
        return emotionService.bubbleemotion(membernum);
    }
}
