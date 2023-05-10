package com.turkey.walkingwith7puppy.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(
	info = @Info(title = "WWP API 명세서",
		description = "WWP 테스트 API 명세서",
		version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi chatOpenApi() {
		String[] paths = {"/**"};

		return GroupedOpenApi.builder()
			.group("WWP v1")
			.pathsToMatch(paths)
			.build();
	}

}