package com.gocpf.configuration;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.models.dto.builder.ApiInfoBuilder;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;


@Configuration
@EnableSwagger
public class SwaggerConfiguration {
	
	@Inject
	private SpringSwaggerConfig springSwaggerConfig;

	@Bean
	public SwaggerSpringMvcPlugin configureSwagger() {

		ApiInfo apiInfo = new ApiInfoBuilder()
		.title("GoCPF REST API")
		.description("GoCPF REST API Api for search and recommendations")
		.termsOfServiceUrl("http://example.com/terms-of-service")
		.contact("info@example.com").license("MIT License")
		.licenseUrl("http://opensource.org/licenses/MIT").build();
		
		SwaggerSpringMvcPlugin swaggerSpringMvcPlugin = new SwaggerSpringMvcPlugin(
				this.springSwaggerConfig).apiInfo(apiInfo);


		
		swaggerSpringMvcPlugin
		.apiInfo(apiInfo)
		.apiVersion("1.0")
		.includePatterns("/service/*.*");

		// swaggerSpringMvcPlugin.useDefaultResponseMessages(false);
		return swaggerSpringMvcPlugin;
	}
}