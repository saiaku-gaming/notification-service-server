package com.valhallagame.notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valhallagame.common.JS;
import com.valhallagame.notificationservice.message.NotificationListenerParameter;
import com.valhallagame.notificationservice.message.UnregisterNotificationListenerParameter;
import com.valhallagame.notificationservice.service.NotificationService;

@Controller
@RequestMapping(path = "/v1/notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@RequestMapping(path = "/register-notification-listener", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> registerNotificationListener(@RequestBody NotificationListenerParameter input) {
		if (input == null) {
			return JS.message(HttpStatus.UNPROCESSABLE_ENTITY, "The parameter has to be set");
		}

		notificationService.registerNotificationListener(input.getGameSessionId(), input.getAddress(), input.getPort());

		return JS.message(HttpStatus.OK, "listener registered");
	}

	@RequestMapping(path = "/unregister-notification-listener", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> unregisterNotificationListener(
			@RequestBody UnregisterNotificationListenerParameter input) {
		if (input == null) {
			return JS.message(HttpStatus.UNPROCESSABLE_ENTITY, "The parameter has to be set");
		}

		notificationService.unregisterNotificationListener(input.getGameSessionId());

		return JS.message(HttpStatus.OK, "listener unregistered");
	}
}
