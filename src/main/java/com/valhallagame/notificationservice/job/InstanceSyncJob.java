package com.valhallagame.notificationservice.job;

import com.valhallagame.notificationservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class InstanceSyncJob {

	private static final Logger logger = LoggerFactory.getLogger(InstanceSyncJob.class);

	@Autowired
	private NotificationService notificationService;

	@Value("${spring.application.name}")
	private String appName;

	@Scheduled(fixedRate = 60000, initialDelay = 5000)
	public void execute() {
		MDC.put("service_name", appName);
		MDC.put("request_id", UUID.randomUUID().toString());

		logger.info("Syncing with instance service started...");
		try {
			notificationService.syncSendersAndLocations();
		} catch (IOException e) {
			logger.error("Could not sync senders and location", e);
		} finally {
			logger.info("Syncing with instance service finished");
			MDC.clear();
		}
	}
}
