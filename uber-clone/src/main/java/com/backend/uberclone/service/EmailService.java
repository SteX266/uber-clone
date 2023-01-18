package com.backend.uberclone.service;

import com.backend.uberclone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;


    private void sendEmail(String recieverEmail, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fishnships266@gmail.com");
        message.setTo(recieverEmail);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendActivationEmail(User user){

        sendEmail(user.getUsername(),"Fish&Ships actiovation email","Confirm your email: http://localhost:8080/auth/activate/" + user.getId().toString());

    }
}
