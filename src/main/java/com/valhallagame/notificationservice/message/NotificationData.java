package com.valhallagame.notificationservice.message;

import java.util.Map;

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
}
