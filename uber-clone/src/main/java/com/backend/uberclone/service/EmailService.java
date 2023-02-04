package com.backend.uberclone.service;

import com.backend.uberclone.model.User;
import net.bytebuddy.utility.RandomString;
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

    public void sendResetPasswordEmail(String email, String resetPasswordLink){

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + resetPasswordLink + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        sendEmail(email, subject, content);
    }

    public void sendActivationEmail(User user){

        sendEmail(user.getUsername(),"Fish&Ships actiovation email","Confirm your email: http://localhost:8080/auth/activate/" + user.getId().toString());

    }
}
