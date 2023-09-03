package com.github.pielena.postal.tracking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact();
        contact.setEmail("elena.pilyugina.job@gmail.com");
        contact.setName("Elena Pilyugina");

        return new OpenAPI()
                .info(new Info()
                        .title("Postal Tracking API")
                        .description("This API exposes endpoints to manage postal tracking.")
                        .version("1.0")
                        .contact(contact)
                );
    }
}
