package com.valhallagame.notificationservice.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.valhallagame.notificationservice.service.NotificationService;

@Component
public class ResendUnsentMessagesJob {

	@Autowired
	private NotificationService notificationService;

	private boolean sending = false;

	@Scheduled(fixedRate = 1000, initialDelay = 1000)
	public void execute() {
		if (!sending) {
			sending = true;
			notificationService.resendUnsentMessages();
			sending = false;
		}
	}
}
