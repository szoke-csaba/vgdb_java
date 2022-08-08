package io.github.szokecsaba.vgdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class VgdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(VgdbApplication.class, args);
    }

}
