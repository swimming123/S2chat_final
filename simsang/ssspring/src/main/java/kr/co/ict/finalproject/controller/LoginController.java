package kr.co.ict.finalproject.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ict.finalproject.service.EmailSenderService;
import kr.co.ict.finalproject.service.JwtUtil;
import kr.co.ict.finalproject.service.MemberService;
import kr.co.ict.finalproject.vo.MemberVO;

@RestController
@RequestMapping("/login")
public class LoginController {
    // 개발자 : 송지미, 이승희
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MemberService memberService;

    @Autowired
    private EmailSenderService emailsenderservice;

    @PostMapping("/logintest")
    public ResponseEntity<Map<String, Object>> getSimulResult(
            @RequestBody Map<String, String> loginData) {
        String id = loginData.get("id");
        String pwd = loginData.get("password");
        int checkidpwd = emailsenderservice.duplicateIdPwd(id, pwd);
        if (checkidpwd == 1) {
            MemberVO member = memberService.getMemberById(id);
            if (member == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            String token = jwtUtil.generateToken(id);
            Map<String, Object> response = new HashMap<>();
            response.put("accessToken", token);
            response.put("id", id);
            response.put("membernum", member.getNum()); // member.getNum()을 사용하여 membernum 값 추가

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/findid")
    public ResponseEntity<?> findid(@RequestParam("name") String name,
            @RequestParam("email") String email) {

        int checkEmail = emailsenderservice.duplicateNameEmail(name, email);
        if (checkEmail == 1) {
            MemberVO vo = memberService.findid(name, email);
            Map<String, String> response = new HashMap<>();
            response.put("id", vo.getId());
            response.put("mdate", vo.getMdate());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ResponseEntity.ok(1);
        }

    }

    @PostMapping("/findpwd")
    public ResponseEntity<?> findpwd(@RequestParam("id") String id,
            @RequestParam("email") String email) {
        int checkEmail = emailsenderservice.duplicateIdEmail(id, email);
        if (checkEmail == 1) {
            emailsenderservice.sendPwdEmail(id, email);
            return ResponseEntity.ok(0);
        } else {
            return ResponseEntity.ok(1);
        }
    }
}