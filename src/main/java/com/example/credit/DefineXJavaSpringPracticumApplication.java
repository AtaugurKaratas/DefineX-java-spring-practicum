package com.example.credit;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DefineXJavaSpringPracticumApplication {

    public static void main(String[] args) {
        SpringApplication.run(DefineXJavaSpringPracticumApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${application-description}") String description,
                                 @Value("${application-version}") String version) {
        return new OpenAPI()
                .info(new Info()
                        .title("DefineX Credit API")
                        .version(version)
                        .description(description)
                        .license(new License().name("DefineX Credit API Licence")));
    }

}
