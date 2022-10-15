/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 12/10/22
 * */
package com.signify.reviewservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ReviewServiceApplication {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("review service").select()
                .apis(RequestHandlerSelectors.basePackage("com.signify.reviewservice")).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ReviewServiceApplication.class, args);
    }



}
