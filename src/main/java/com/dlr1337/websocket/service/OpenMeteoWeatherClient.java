package com.dlr1337.websocket.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.dlr1337.websocket.model.City;
import com.dlr1337.websocket.model.WeatherReading;

@Service
public class OpenMeteoWeatherClient implements WeatherClient {

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

	private final RestClient restClient;
	private final WeatherCodeTranslator weatherCodeTranslator;

	public OpenMeteoWeatherClient(RestClient openMeteoRestClient, WeatherCodeTranslator weatherCodeTranslator) {
		this.restClient = openMeteoRestClient;
		this.weatherCodeTranslator = weatherCodeTranslator;
	}

	@Override
	public WeatherReading fetchCurrentWeather(City city) {
		OpenMeteoResponse response = restClient.get()
			.uri(uriBuilder -> uriBuilder.path("/v1/forecast")
				.queryParam("latitude", city.latitude())
				.queryParam("longitude", city.longitude())
				.queryParam("current", "temperature_2m,weather_code")
				.queryParam("timezone", "auto")
				.build())
			.retrieve()
			.body(OpenMeteoResponse.class);

		if (response == null || response.current() == null || response.current().time() == null) {
			throw new IllegalStateException("Resposta inválida recebida do Open-Meteo");
		}

		String description = weatherCodeTranslator.translate(response.current().weatherCode());
		String formattedTime = LocalDateTime.parse(response.current().time())
			.toLocalTime()
			.format(TIME_FORMATTER);

		return new WeatherReading(response.current().temperature2m(), description, formattedTime);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	record OpenMeteoResponse(OpenMeteoCurrent current) {
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	record OpenMeteoCurrent(
		String time,
		@JsonProperty("temperature_2m")
		double temperature2m,
		@JsonProperty("weather_code")
		int weatherCode
	) {
	}
}
