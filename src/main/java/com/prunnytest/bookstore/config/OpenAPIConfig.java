package com.prunnytest.bookstore.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

//SWAGGER FOR API DOCUMENTATION
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("salamkorede345@gmail.com");
        contact.setName("Salami Korede");
        contact.setUrl("korede.vercel.app");


        Info info = new Info()
                .title("Bookstore (Prunny Test) ")
                .contact(contact)
                .description("This API exposes the prunny test bookshop endpoints.");

        return new OpenAPI().info(info);
    }
}