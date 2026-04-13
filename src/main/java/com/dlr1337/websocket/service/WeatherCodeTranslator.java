package com.dlr1337.websocket.service;

import org.springframework.stereotype.Component;

@Component
public class WeatherCodeTranslator {

	public String translate(int weatherCode) {
		return switch (weatherCode) {
			case 0 -> "Céu limpo";
			case 1 -> "Predominantemente limpo";
			case 2 -> "Parcialmente nublado";
			case 3 -> "Nublado";
			case 45 -> "Nevoeiro";
			case 48 -> "Nevoeiro com geada";
			case 51 -> "Garoa leve";
			case 53 -> "Garoa moderada";
			case 55 -> "Garoa intensa";
			case 56 -> "Garoa congelante leve";
			case 57 -> "Garoa congelante intensa";
			case 61 -> "Chuva leve";
			case 63 -> "Chuva moderada";
			case 65 -> "Chuva forte";
			case 66 -> "Chuva congelante leve";
			case 67 -> "Chuva congelante forte";
			case 71 -> "Neve leve";
			case 73 -> "Neve moderada";
			case 75 -> "Neve intensa";
			case 77 -> "Grãos de neve";
			case 80 -> "Pancadas de chuva leves";
			case 81 -> "Pancadas de chuva moderadas";
			case 82 -> "Pancadas de chuva fortes";
			case 85 -> "Pancadas de neve leves";
			case 86 -> "Pancadas de neve fortes";
			case 95 -> "Trovoada";
			case 96 -> "Trovoada com granizo leve";
			case 99 -> "Trovoada com granizo forte";
			default -> "Condição desconhecida";
		};
	}
}
