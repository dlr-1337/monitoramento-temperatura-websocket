package com.dlr1337.websocket.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class WeatherCodeTranslatorTest {

	private final WeatherCodeTranslator translator = new WeatherCodeTranslator();

	@Test
	void shouldTranslateKnownCodesToPortugueseDescriptions() {
		assertThat(translator.translate(0)).isEqualTo("Céu limpo");
		assertThat(translator.translate(63)).isEqualTo("Chuva moderada");
		assertThat(translator.translate(95)).isEqualTo("Trovoada");
	}

	@Test
	void shouldReturnFallbackForUnknownCodes() {
		assertThat(translator.translate(999)).isEqualTo("Condição desconhecida");
	}
}
