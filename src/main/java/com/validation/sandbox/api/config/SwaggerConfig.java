package com.validation.sandbox.api.config;

import com.google.common.base.Predicate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;


import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		
		Predicate<RequestHandler> apiPredicates = (basePackage("com.validation.sandbox.api.controller"));
		return new Docket(DocumentationType.SWAGGER_2).select().apis(apiPredicates).paths(PathSelectors.any()).build();
		
		
	}

}

