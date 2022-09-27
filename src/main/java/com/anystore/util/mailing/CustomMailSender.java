package com.anystore.util.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CustomMailSender {
    private static final String sender = "aregism@gmail.com";
    private static final String recipientAreg = "aregism@gmail.com";
    private static final String recipientArpi = "grigorianarpi@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmailToAdmins(String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(recipientAreg);
        //message.setCc(recipientArpi);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    @Async
    public void sendEmail(String subject, String text, String recipient) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }


}
