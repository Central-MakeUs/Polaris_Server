package com.example.dobit.utils;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.user.models.PostMailAuthRes;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.example.dobit.config.BaseResponseStatus.FAILED_TO_SEND_MAIL;


@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "dobit0628@gmail.com";

    /**
     * 인증번호 발송하기 API
     * @param email
     * @return PostMailAuthRes
     * @throws BaseException
     */
    public PostMailAuthRes sendMailAuth(String email) throws BaseException {
        String key="";
        try {
            Random random=new Random();  //난수 생성을 위한 랜덤 클래스
              //인증번호
            for(int i =0; i<3;i++) {
                int index=random.nextInt(25)+65; //A~Z까지 랜덤 알파벳 생성
                key+=(char)index;
            }
            int numIndex=random.nextInt(9999)+1000; //4자리 랜덤 정수를 생성
            key+=numIndex;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setFrom(MailService.FROM_ADDRESS);
            message.setSubject("인증번호 메일 발송");
            message.setText("인증번호: "+ key);

            mailSender.send(message);
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_SEND_MAIL);
        }
        return new PostMailAuthRes(key);
    }

}