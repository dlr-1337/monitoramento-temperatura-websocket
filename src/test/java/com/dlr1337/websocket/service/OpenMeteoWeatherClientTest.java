package com.dlr1337.websocket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import com.dlr1337.websocket.model.City;

class OpenMeteoWeatherClientTest {

	private MockRestServiceServer server;
	private OpenMeteoWeatherClient client;

	@BeforeEach
	void setUp() {
		RestClient.Builder builder = RestClient.builder()
			.baseUrl("https://api.open-meteo.com");
		server = MockRestServiceServer.bindTo(builder).build();
		client = new OpenMeteoWeatherClient(builder.build(), new WeatherCodeTranslator());
	}

	@Test
	void shouldConvertOpenMeteoResponseIntoWeatherReading() {
		server.expect(requestTo("https://api.open-meteo.com/v1/forecast?latitude=-16.7414&longitude=-48.5162&current=temperature_2m,weather_code&timezone=auto"))
			.andExpect(method(GET))
			.andRespond(withSuccess("""
				{
				  "current": {
				    "time": "2026-04-13T18:25",
				    "temperature_2m": 28.5,
				    "weather_code": 0
				  }
				}
				""", MediaType.APPLICATION_JSON));

		var reading = client.fetchCurrentWeather(new City("Vianópolis", -16.7414, -48.5162));

		assertThat(reading.temperature()).isEqualTo(28.5);
		assertThat(reading.description()).isEqualTo("Céu limpo");
		assertThat(reading.time()).isEqualTo("18:25:00");
		server.verify();
	}
}
