package io.diaz.pinoystack;

import io.diaz.pinoystack.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfig.class)
public class PinoyStackApplication {

    public static void main(String[] args) {
        SpringApplication.run(PinoyStackApplication.class, args);
    }

}
