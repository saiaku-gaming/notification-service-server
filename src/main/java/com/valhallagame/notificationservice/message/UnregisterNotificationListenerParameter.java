package com.valhallagame.notificationservice.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnregisterNotificationListenerParameter {
	private String gameSessionId;
}
