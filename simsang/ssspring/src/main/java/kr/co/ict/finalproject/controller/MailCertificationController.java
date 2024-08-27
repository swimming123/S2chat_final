package kr.co.ict.finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict.finalproject.service.EmailSenderService;

@RestController
@RequestMapping("/api/auth")
public class MailCertificationController {
    // 개발자 : 이승희, 이지영
    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/emailCheck")
    public int sendEmail(@RequestParam String email) {
        int checkEmail = emailSenderService.duplicateEmail(email);
        if (checkEmail == 0) {
            emailSenderService.sendEmail(email);
            return 0;
        } else {
            return 1;
        }
    }

    @PostMapping("/emailCheck/certification")
    public boolean verifyCertificationNumber(@RequestParam("email") String email, @RequestParam("code") String code) {
        return emailSenderService.isVerify(email, code);
    }
}
