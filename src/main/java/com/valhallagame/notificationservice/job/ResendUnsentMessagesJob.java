package com.valhallagame.notificationservice.job;

import com.valhallagame.notificationservice.service.NotificationService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ResendUnsentMessagesJob {

	@Autowired
	private NotificationService notificationService;

	private boolean sending = false;

	@Value("${spring.application.name}")
	private String appName;

	@Scheduled(fixedRate = 1000, initialDelay = 1000)
	public void execute() {
		MDC.put("service_name", appName);
		MDC.put("request_id", UUID.randomUUID().toString());

		try {
			if (!sending) {
				sending = true;
				notificationService.resendUnsentMessages();
				sending = false;
			}
		} finally {
			MDC.clear();
		}
	}
}
