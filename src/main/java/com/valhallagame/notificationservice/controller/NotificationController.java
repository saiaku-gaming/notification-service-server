package com.valhallagame.notificationservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.valhallagame.common.JS;
import com.valhallagame.notificationservice.service.NotificationService;
import com.valhallagame.notificationserviceclient.message.NotificationListenerParameter;
import com.valhallagame.notificationserviceclient.message.UnregisterNotificationListenerParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/v1/notification")
public class NotificationController {
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

	@Autowired
	private NotificationService notificationService;

	@RequestMapping(path = "/register-notification-listener", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<JsonNode> registerNotificationListener(@Valid @RequestBody NotificationListenerParameter input) {
		logger.info("Register Notification Listener called with {}", input);
		if (input == null) {
			return JS.message(HttpStatus.UNPROCESSABLE_ENTITY, "The parameter has to be set");
		}

		notificationService.registerNotificationListener(input.getGameSessionId(), input.getAddress(), input.getPort());

		return JS.message(HttpStatus.OK, "listener registered");
	}

	@RequestMapping(path = "/unregister-notification-listener", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<JsonNode> unregisterNotificationListener(@Valid 
			@RequestBody UnregisterNotificationListenerParameter input) {
		logger.info("Unregister Notification Listener called with {}", input);
		if (input == null) {
			return JS.message(HttpStatus.UNPROCESSABLE_ENTITY, "The parameter has to be set");
		}

		notificationService.unregisterNotificationListener(input.getGameSessionId());

		return JS.message(HttpStatus.OK, "listener unregistered");
	}
}
