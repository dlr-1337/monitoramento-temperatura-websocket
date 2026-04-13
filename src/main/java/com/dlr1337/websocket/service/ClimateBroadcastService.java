package com.dlr1337.websocket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.dlr1337.websocket.model.City;
import com.dlr1337.websocket.model.ClimateUpdate;
import com.dlr1337.websocket.model.WeatherReading;

@Service
public class ClimateBroadcastService {

	public static final String CLIMATE_TOPIC = "/topic/clima";

	private static final Logger logger = LoggerFactory.getLogger(ClimateBroadcastService.class);

	private final CityCatalog cityCatalog;
	private final WeatherClient weatherClient;
	private final SimpMessagingTemplate messagingTemplate;

	public ClimateBroadcastService(
		CityCatalog cityCatalog,
		WeatherClient weatherClient,
		SimpMessagingTemplate messagingTemplate
	) {
		this.cityCatalog = cityCatalog;
		this.weatherClient = weatherClient;
		this.messagingTemplate = messagingTemplate;
	}

	public void publishRandomClimateUpdate() {
		City city = cityCatalog.randomCity();

		try {
			WeatherReading reading = weatherClient.fetchCurrentWeather(city);
			ClimateUpdate update = new ClimateUpdate(
				city.name(),
				reading.temperature(),
				reading.description(),
				reading.time()
			);

			messagingTemplate.convertAndSend(CLIMATE_TOPIC, update);
			logger.info("Atualizacao enviada para {}: {}°C - {}", city.name(), reading.temperature(), reading.description());
		}
		catch (RestClientException | IllegalStateException exception) {
			logger.warn("Falha ao consultar o clima de {}: {}", city.name(), exception.getMessage());
		}
	}
}
