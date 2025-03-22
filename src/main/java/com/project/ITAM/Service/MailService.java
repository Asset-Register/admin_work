package com.project.ITAM.Service;

import com.project.ITAM.Model.SmtpConfig;
import com.project.ITAM.Service.SmtpConfigService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class MailService {

    private final SmtpConfigService smtpConfigService;

    public MailService(SmtpConfigService smtpConfigService) {
        this.smtpConfigService = smtpConfigService;
    }

    public JavaMailSender getJavaMailSender(String provider) {
        SmtpConfig smtpConfig = smtpConfigService.getSmtpConfigByProvider(provider)
                .orElseThrow(() -> new RuntimeException("SMTP Configuration not found for provider: " + provider));

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpConfig.getHost());
        mailSender.setPort(smtpConfig.getPort());
        mailSender.setUsername(smtpConfig.getUsername());
        mailSender.setPassword(smtpConfig.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");

        if ("TLS".equalsIgnoreCase(smtpConfig.getEncryptionType())) {
            props.put("mail.smtp.starttls.enable", "true");
        } else if ("SSL".equalsIgnoreCase(smtpConfig.getEncryptionType())) {
            props.put("mail.smtp.ssl.enable", "true");
        }

        props.put("mail.debug", "true");

        return mailSender;
    }
}
