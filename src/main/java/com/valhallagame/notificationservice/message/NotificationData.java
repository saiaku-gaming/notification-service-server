package com.valhallagame.notificationservice.message;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationData {
	private String username;
	private String notification;
	private Map<String, Object> data;

	@JsonIgnore
	private int retries;

	public NotificationData(String username, String notification, Map<String, Object> data) {
		this.username = username;
		this.notification = notification;
		this.data = data;

		retries = 0;
	}
}
