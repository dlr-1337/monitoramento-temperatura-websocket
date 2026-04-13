package com.dlr1337.websocket.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import com.dlr1337.websocket.model.City;

@Component
public class CityCatalog {

	private final List<City> cities = List.of(
		new City("São Paulo", -23.5505, -46.6333),
		new City("Rio de Janeiro", -22.9068, -43.1729),
		new City("Brasília", -15.7939, -47.8828),
		new City("Salvador", -12.9777, -38.5016),
		new City("Recife", -8.0476, -34.8770),
		new City("Fortaleza", -3.7319, -38.5267),
		new City("Curitiba", -25.4284, -49.2733),
		new City("Porto Alegre", -30.0346, -51.2177),
		new City("Manaus", -3.1190, -60.0217),
		new City("Vianópolis", -16.7414, -48.5162)
	);

	public City randomCity() {
		return cities.get(ThreadLocalRandom.current().nextInt(cities.size()));
	}

	public List<City> getCities() {
		return cities;
	}
}
