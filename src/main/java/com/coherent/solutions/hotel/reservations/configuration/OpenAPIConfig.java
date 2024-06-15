package com.coherent.solutions.hotel.reservations.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    @Value("${coherent.solution.hotel.app.openapi.dev-url}")
    private String devUrl;

    @Value("${coherent.solution.hotel.app.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("josesaid@gmail.com");
        contact.setName("Jose Said");
        contact.setUrl("https://d1cl2ufw0q34yu.cloudfront.net/");

        License mitLicense = new License().name("Apache 2 License").url("https://www.apache.org/licenses/LICENSE-2.0.txt");

        Info info = new Info()
                .title("Coherent Solutions - Hotel Reservations API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage reservations.").termsOfService("https://www.termsandcondiitionssample.com/")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
    
}