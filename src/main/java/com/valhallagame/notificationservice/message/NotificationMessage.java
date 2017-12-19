package com.valhallagame.notificationservice.message;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {
	private Set<NotificationData> notifications = new HashSet<>();

	public void addNotification(String username, String notification, Map<String, Object> data) {
		notifications.add(new NotificationData(username, notification, data));
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class NotificationData {
		private String username;
		private String notification;
		private Map<String, Object> data;
	}
}
