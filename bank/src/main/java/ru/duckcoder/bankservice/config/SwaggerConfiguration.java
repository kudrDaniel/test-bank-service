package ru.duckcoder.bankservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("Authentications")
                .pathsToMatch("/api/login")
                .build();
    }

    @Bean
    public GroupedOpenApi emailApi() {
        return GroupedOpenApi.builder()
                .group("Emails")
                .pathsToMatch("/api/users/*/emails", "/api/users/*/emails/*")
                .build();
    }

    @Bean
    public GroupedOpenApi phoneApi() {
        return GroupedOpenApi.builder()
                .group("Phones")
                .pathsToMatch("/api/users/*/phones", "/api/users/*/phones/*")
                .build();
    }

    @Bean
    public GroupedOpenApi usersApi() {
        return GroupedOpenApi.builder()
                .group("Users")
                .pathsToMatch("/api/users", "/api/users/*")
                .build();
    }

    @Bean
    public GroupedOpenApi walletApi() {
        return GroupedOpenApi.builder()
                .group("Wallets")
                .pathsToMatch("/api/users/*/transfer", "/api/users/*/wallet")
                .build();
    }

    @Bean
    public OpenAPI customOpenApi(@Value("${application-description}") String appDescription,
                                 @Value("${application-version}") String appVersion) {
        return new OpenAPI().info(new Info().title("Bank Service API")
                        .version(appVersion)
                        .description(appDescription)
                        .contact(new Contact()
                                .name("Danil Kudrickiy")
                                .email("kudr.daniel.vlad.ch@gmail.com")))
                .servers(List.of(new Server()
                        .url("http://localhost:8080")
                        .description("Development server")));
    }
}
