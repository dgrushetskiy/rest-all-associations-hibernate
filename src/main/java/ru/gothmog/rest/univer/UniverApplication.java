package ru.gothmog.rest.univer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UniverApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniverApplication.class, args);
    }

}
