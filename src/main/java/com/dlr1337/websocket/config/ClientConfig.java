package com.dlr1337.websocket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration(proxyBeanMethods = false)
public class ClientConfig {

	@Bean
	RestClient openMeteoRestClient(
		@Value("${app.open-meteo.base-url:https://api.open-meteo.com}") String baseUrl
	) {
		return RestClient.builder()
			.baseUrl(baseUrl)
			.build();
	}
}
