package com.valhallagame.notificationservice.job;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.valhallagame.notificationservice.service.NotificationService;

@Component
public class InstanceSyncJob {

	@Autowired
	private NotificationService notificationService;

	@Scheduled(fixedRate = 60000, initialDelay = 5000)
	public void execute() {
		try {
			notificationService.syncSendersAndLocations();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
