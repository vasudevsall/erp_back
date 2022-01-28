package com.management.erp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    public void sendSimpleMail(String toEmail, String body, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("ehealthcare8278@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }

    public void sendOtpMail(String toEmail, String name, int otp) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("email", toEmail);
        context.setVariable("otp", otp);

        String process = templateEngine.process("otp", context);

        htmlMailSender(toEmail, process, "OTP for resetting password");
    }

    public void sendPasswordChangeMail(String toEmail, String name) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("email", toEmail);

        String process = templateEngine.process("password-change", context);

        htmlMailSender(toEmail, process, "Password Changed");
    }

    // Private Methods
    private void htmlMailSender(String toEmail, String process, String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(subject);
        helper.setText(process, true);
        helper.setTo(toEmail);
        mailSender.send(mimeMessage);
    }
}
