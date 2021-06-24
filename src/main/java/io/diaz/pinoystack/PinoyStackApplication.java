package io.diaz.pinoystack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PinoyStackApplication {

    public static void main(String[] args) {
        SpringApplication.run(PinoyStackApplication.class, args);
    }

}
