package kr.co.ict.finalproject.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.co.ict.finalproject.dao.CertificationNumberDAO;
import kr.co.ict.finalproject.dao.MemberDAO;

@Service
public class EmailSenderService {
    // 개발자 : 이승희, 이지영
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MemberDAO memberdao;

    @Autowired
    private CertificationNumberDAO certificationNumberDAO;
    private String authCode;

    public int duplicateEmail(String email) {
        int checkEmail = memberdao.emailCheck(email);
        return checkEmail > 0 ? 1 : 0;
    }

    public int duplicateIdPwd(String id, String pwd) {
        int checkidpwd = memberdao.checkidpwd(id, pwd);
        return checkidpwd > 0 ? 1 : 0;
    }

    public int duplicateNameEmail(String name, String email) {
        int checkEmail = memberdao.nameEmailCheck(name, email);
        return checkEmail > 0 ? 1 : 0;
    }

    public int duplicateIdEmail(String id, String email) {
        int checkEmail = memberdao.idEmailCheck(id, email);
        return checkEmail > 0 ? 1 : 0;
    }

    public void createAuthCode() {
        int length = 6;
        StringBuilder authCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int type = random.nextInt(3);

            switch (type) {
                case 0:
                    authCode.append(random.nextInt(10));
                    break;
                case 1:
                    authCode.append((char) (random.nextInt(26) + 65));
                    break;
                case 2:
                    authCode.append((char) (random.nextInt(26) + 97));
                    break;
            }
        }
        this.authCode = authCode.toString();
    }

    public void sendEmail(String toEmail) {
        createAuthCode();
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("ictmarket02@naver.com");
            helper.setTo(toEmail);
            helper.setSubject("S2 Chat 회원가입 인증번호 발송");

            String body = "<html>" +
                    "<body>" +
                    "<h1>S2 Chat 회원가입을 위한 인증번호</h1>" +
                    "<p>회원가입을 완료하기 위해 아래의 인증코드를 입력해주세요.</p>" +
                    "<p>인증코드: <strong>" + authCode + "</strong></p>" +
                    "</body>" +
                    "</html>";

            helper.setText(body, true);
            mailSender.send(message);
            certificationNumberDAO.saveCertificationNumber(toEmail, authCode);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // 비밀번호 찾기 : 이메일 인증번호 발송
    public void sendPwdEmail(String id, String toEmail) {
        createAuthCode();
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("ictmarket02@naver.com");
            helper.setTo(toEmail);
            helper.setSubject("S2 Chat 임시비밀번호 이메일");
            String body = "<html>" +
                    "<body>" +
                    "<h1>" + toEmail + "로 임시 비밀번호가 발송되었습니다.</h1>" +
                    "<p>임시비밀번호: <strong>" + authCode + "</strong></p>" +
                    "</body>" +
                    "</html>";
            helper.setText(body, true);
            mailSender.send(message);
            memberdao.pwdUpdate(id, toEmail, authCode);
            certificationNumberDAO.saveCertificationNumber(toEmail, authCode);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isVerify(String email, String authCode) {
        if (certificationNumberDAO.hasKey(email) && certificationNumberDAO
                .getCertificationNumber(email).equals(authCode)) {
            certificationNumberDAO.deleteCertificationNumber(email);
            return true;
        } else {
            return false;
        }
    }

}
