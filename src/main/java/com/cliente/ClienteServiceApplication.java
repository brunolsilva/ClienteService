package com.cliente;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.time.LocalDate;

import org.jboss.jandex.WildcardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import com.cliente.config.ClienteApiProperty;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties(ClienteApiProperty.class)
@EnableCircuitBreaker
@EnableSwagger2
public class ClienteServiceApplication {

	@Autowired
	private TypeResolver typeResolver;

	public static void main(String[] args) {
		SpringApplication.run(ClienteServiceApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.directModelSubstitute(LocalDate.class,
						String.class)
				.genericModelSubstitutes(ResponseEntity.class)
				.alternateTypeRules(
						newRule(typeResolver.resolve(DeferredResult.class,
								typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
								typeResolver.resolve(WildcardType.class)))
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET,
						newArrayList(new ResponseMessageBuilder()
								.code(500)
								.message("500 message")
								.responseModel(new ModelRef("Error"))
								.build()))
				 .enableUrlTemplating(true);
	}

	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration(
				"validatorUrl",
				"none",
				"alpha",
				"schema",
				UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
				false,
				true,
				60000L);
	}
}
