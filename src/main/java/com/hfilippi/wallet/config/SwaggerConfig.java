package com.hfilippi.wallet.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * http://localhost:8080/swagger-ui/index.html
 */
@Configuration
public class SwaggerConfig {

	@Bean
	GroupedOpenApi api() {
		// @formatter:off
		return GroupedOpenApi.builder()
				.group("apis")
				.pathsToMatch("/**")
				.build();
		// @formatter:on
	}

	@Bean
	OpenAPI customOpenAPI() {
		// @formatter:off
		return new OpenAPI().info(new Info()
				.title("RecargaPay Wallet Service")
				.version("1.0.0"));
		// @formatter:on
	}

}
