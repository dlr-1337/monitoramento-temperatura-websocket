package com.dlr1337.websocket.service;

import com.dlr1337.websocket.model.City;
import com.dlr1337.websocket.model.WeatherReading;

public interface WeatherClient {

	WeatherReading fetchCurrentWeather(City city);
}
