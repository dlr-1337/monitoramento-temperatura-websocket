package com.dlr1337.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MonitoramentoTemperaturaWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoramentoTemperaturaWebsocketApplication.class, args);
	}

}
