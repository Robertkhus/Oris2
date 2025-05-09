package org.example.orishw2.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${spring.mail.username}")
    private String mailFrom;

    private final JavaMailSender javaMailSender;

    private final Template confirmMailTemplate;


    public void sendEmailForConfirm(String email, String code) {
        String mailText = getEmailText(code);
        MimeMessagePreparator messagePreparator = getEmail(email, mailText);
        javaMailSender.send(messagePreparator);
    }

    private String getEmailText(String code) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("confirm_code", code);
        StringWriter writer = new StringWriter();
        try {
            confirmMailTemplate.process(attributes, writer);
        } catch (TemplateException | IOException e) {
            throw new IllegalStateException(e);
        }
        return writer.toString();
    }

    private MimeMessagePreparator getEmail (String email, String mailText) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mailFrom);
            messageHelper.setTo(email);
            messageHelper.setSubject("Регистрация");
            messageHelper.setText(mailText, true);
        };
    }
}

