package com.project.ITAM.Controller;

import com.project.ITAM.Service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final MailService mailService;

    public EmailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam String provider, @RequestParam String to, 
                            @RequestParam String subject, @RequestParam String message) {
        try {
            JavaMailSender mailSender = mailService.getJavaMailSender(provider);

            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom("itamexperts@gmail.com");  // Can be overridden by stored `fromEmail`
            email.setTo(to);
            email.setSubject(subject);
            email.setText(message);

            mailSender.send(email);
            return "Email sent successfully using " + provider;
        } catch (Exception e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}
