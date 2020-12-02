package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@SpringBootApplication
@ComponentScan
@Configuration
public class Main {

    public static void main(String[] args) throws LoginException {
        SpringApplication.run(Main.class, args);
    }
}

