package com.springframework.projectshoptoy.security.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//Nơi để cấu hình swagger
@EnableSwagger2
@Configuration
public class SwapperConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                //xóa errorController default in swagger ui
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .pathMapping("/")
                .apiInfo(metaData());
    }
    

    //các thông tin trên swagger-ui
    private ApiInfo metaData(){
        Contact contact = new Contact("Nhóm ...", "...",
                "john@springfrmework.guru");

        return new ApiInfo(
                "Quản lý thú bông",
                "Nhóm ",
                "1.0",
                "Terms of Service: blah",
                contact,
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }

}

