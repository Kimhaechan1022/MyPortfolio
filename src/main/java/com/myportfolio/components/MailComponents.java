package com.myportfolio.components;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class MailComponents {
    public static final String ENCODING = "UTF-8";
    private final JavaMailSender javaMailSender;

    public void sendMailDemo(){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("ckssmsla98@naver.com");
        msg.setSubject("hello");
        msg.setText("testMail");
        javaMailSender.send(msg);
    }

    public boolean sendMail(String email, String subject, String text){

        boolean isSendMailSuccess = false;

        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true, ENCODING);
                mimeMessageHelper.setTo(email);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(text);
            }
        };
        try {
            javaMailSender.send(msg);
            isSendMailSuccess = true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return isSendMailSuccess;
    }

}
