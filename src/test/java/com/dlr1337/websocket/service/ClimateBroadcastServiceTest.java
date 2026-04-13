package com.dlr1337.websocket.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.client.RestClientException;

import com.dlr1337.websocket.model.City;
import com.dlr1337.websocket.model.ClimateUpdate;
import com.dlr1337.websocket.model.WeatherReading;

class ClimateBroadcastServiceTest {

	@Test
	void shouldBroadcastClimateUpdateToTopic() {
		CityCatalog cityCatalog = mock(CityCatalog.class);
		WeatherClient weatherClient = mock(WeatherClient.class);
		SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);
		ClimateBroadcastService service = new ClimateBroadcastService(cityCatalog, weatherClient, messagingTemplate);
		City city = new City("Fortaleza", -3.7319, -38.5267);

		when(cityCatalog.randomCity()).thenReturn(city);
		when(weatherClient.fetchCurrentWeather(city)).thenReturn(new WeatherReading(31.2, "Céu limpo", "14:10:00"));

		service.publishRandomClimateUpdate();

		verify(messagingTemplate).convertAndSend(
			ClimateBroadcastService.CLIMATE_TOPIC,
			new ClimateUpdate("Fortaleza", 31.2, "Céu limpo", "14:10:00")
		);
	}

	@Test
	void shouldSkipBroadcastWhenApiCallFails() {
		CityCatalog cityCatalog = mock(CityCatalog.class);
		WeatherClient weatherClient = mock(WeatherClient.class);
		SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);
		ClimateBroadcastService service = new ClimateBroadcastService(cityCatalog, weatherClient, messagingTemplate);
		City city = new City("Recife", -8.0476, -34.8770);

		when(cityCatalog.randomCity()).thenReturn(city);
		when(weatherClient.fetchCurrentWeather(city)).thenThrow(new RestClientException("timeout"));

		service.publishRandomClimateUpdate();

		verify(messagingTemplate, never()).convertAndSend(ClimateBroadcastService.CLIMATE_TOPIC, new ClimateUpdate("Recife", 0, "", ""));
	}
}
