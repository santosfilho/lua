package com.automacao.lua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class LuaApplication {
	public static void main(String[] args) {
		SpringApplication.run(LuaApplication.class, args);
	}

	@Bean
	public Docket swagger() {
		//http://localhost:8079/v0.1/swagger-ui.html
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.automacao.lua"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("API - Lua")
				.description("API para controle, gerenciamento e monitoração de equipamentos elétricos.\n" +
						"O cadastro de medições e informação de status dos equipamentos são realizadas via protocolo MQTT, utilizando os seguintes tópicos:\n " +
						"* sensor_data: Tópico destinado ao envio de medicao do sensor, exemplo de mensagem com sintex correta:\n {\"idSensor\": 1, \"medicao\": 5.2}\n" +
						"* equipamento_status: Tópico destinado a capturar as mensagens que deverão ser capturadas pelo usuario para realizar a mudanã de status do equipamento," +
						" exemplo de mensagem com sintex correta:\n {\"idEquipamento\": 1, \"status\": 1}")
				.version("0.1")
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
				.build();
	}

	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-u.html")
				.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
