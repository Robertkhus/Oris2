package org.example.orishw2.config;

import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.ui.freemarker.SpringTemplateLoader;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class ApplicationConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public Template freemarkerTemplate() {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(
                freemarker.template.Configuration.VERSION_2_3_30
        );
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(
                new SpringTemplateLoader(new ClassRelativeResourceLoader(this.getClass()), "/")
        );
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        try {
            return configuration.getTemplate("templates/confirm_mail.ftlh");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    @Bean
    public JavaMailSender mailSender() {
        return new JavaMailSenderImpl();
    }
}

