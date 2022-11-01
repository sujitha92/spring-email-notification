package com.spring.emailnotificationdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    private String to = "sundar.sujitha87@gmail.com";

    public void sendMail() throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("sundar.sujitha87@gmail.com");
        helper.setTo(to);
        helper.setSubject("Test mail");
        helper.setText("Please find attached the csv.");

        FileSystemResource file
                = new FileSystemResource(new File("src/main/resources/books.csv"));
        helper.addAttachment("books", file);

        emailSender.send(message);
    }
}
