package com.dlr1337.websocket.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClimateScheduler {

	private final ClimateBroadcastService climateBroadcastService;

	public ClimateScheduler(ClimateBroadcastService climateBroadcastService) {
		this.climateBroadcastService = climateBroadcastService;
	}

	@Scheduled(fixedRate = 5000)
	public void sendClimateUpdate() {
		climateBroadcastService.publishRandomClimateUpdate();
	}
}
