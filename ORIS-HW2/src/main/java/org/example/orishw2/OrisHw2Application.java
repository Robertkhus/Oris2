package org.example.orishw2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OrisHw2Application {

    public static void main(String[] args) {
        SpringApplication.run(OrisHw2Application.class, args);
    }

}
