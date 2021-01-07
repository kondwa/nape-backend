package com.mainlevel.monitoring.mail.configuration;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * Enables the swagger docket api.
     *
     * @return the docket api
     */
    @Bean
    public Docket api() {
        Docket docket = new Docket(SWAGGER_2);
        return docket.select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }
}
