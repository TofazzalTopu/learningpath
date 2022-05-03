package com.gr.learningpath.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.gr.learningpath.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private Contact contact() {
        Contact contact = new Contact(
                "Learning Path",
                "http://learningpath.com/",
                "info@learningpath.com"
        );
        return contact;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Learning Path Api")
                .description("Learning Path Api")
                .version("1.0")
                .license("Learning Path Service")
                .licenseUrl("http://learningpath.com/")
                .contact(contact())
                .build();
    }
}
