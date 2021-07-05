package com.example.dobit.src.auth;

import com.example.dobit.config.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthService {
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "dobit0628@gmail.com";

    /**
     * 인증번호 발송하기 API
     * @param email
     * @return void
     * @throws BaseException
     */
    public void  sendMailAuth(String email) throws BaseException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(AuthService.FROM_ADDRESS);
        message.setSubject("인증번호 메일 발송");
        message.setText("6자리 번호");

        mailSender.send(message);

    }

}