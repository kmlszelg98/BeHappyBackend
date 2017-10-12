package com.behappy.mail;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {
    @Bean
    public JavaMailSenderImpl javaMailSenderImpl(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.rozkmin.com");
        mailSender.setPort(587);
        //Set gmail emailId
        mailSender.setUsername("behappy@rozkmin.com");
        //Set gmail email password
        mailSender.setPassword("Testowe1@");
        Properties prop = mailSender.getJavaMailProperties();
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.debug", "true");
        prop.put("mail.smtp.ssl.trust", "mail.rozkmin.com");


        return mailSender;
    }
}
