package com.travelease.travelease.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService  {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String toEmail,String subject,String body) throws MessagingException{
       
        MimeMessage msg = mailSender.createMimeMessage();

	    MimeMessageHelper helper = new MimeMessageHelper(msg, true);

	    helper.setTo(toEmail);
	    helper.setSubject(subject);
	    helper.setText(body, true);
        System.out.println(helper);
	    mailSender.send(msg);
        
    }   
}
