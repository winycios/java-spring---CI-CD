package com.example.springcicd.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Projetinho do winyc",
                description = "END-POINTS DE UMA API SPRING BOOT",
                contact = @Contact(
                        name = "Winyc",
                        url = "https://github.com/SPECTRUM-SYNC",
                        email = "syncgestaoprojetos@gmail.com"
                ),
                license = @License(name = "UNLICENSED"),
                version = "1.0.0"
        )
)

public class OpenApiConfig {

}