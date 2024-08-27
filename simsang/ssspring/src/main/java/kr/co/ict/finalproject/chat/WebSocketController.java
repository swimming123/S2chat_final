package kr.co.ict.finalproject.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class WebSocketController {
    // 개발자 : 정준영
    @GetMapping("/")
    public String home() {
        return "index";
    }
}