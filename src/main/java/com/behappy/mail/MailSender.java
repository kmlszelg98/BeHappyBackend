package com.behappy.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailSender {

    private final JavaMailSenderImpl mailSender;

    @Autowired
    public MailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String mailTo, String messageBody) throws MessagingException {;
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
        mailMsg.setFrom("behappy@knbit.edu.pl");
        mailMsg.setTo(mailTo);
        mailMsg.setSubject("Mail powitalny");
        mailMsg.setText(messageBody);
        mailSender.send(mimeMessage);
        System.out.println("---Done---");

    }
}

