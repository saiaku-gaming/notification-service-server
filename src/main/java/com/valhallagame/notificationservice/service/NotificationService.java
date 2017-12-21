package com.valhallagame.notificationservice.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valhallagame.common.RestResponse;
import com.valhallagame.instanceserviceclient.InstanceServiceClient;
import com.valhallagame.instanceserviceclient.message.Instance;
import com.valhallagame.notificationservice.message.NotificationMessage;
import com.valhallagame.notificationservice.model.NotificationSender;
import com.valhallagame.notificationservice.model.NotificationType;
import com.valhallagame.notificationservice.model.RegisteredServer;

@Service
public class NotificationService {
	private static InstanceServiceClient instanceServiceClient = InstanceServiceClient.get();

	private static ConcurrentMap<String, NotificationSender> notificationSenders = new ConcurrentHashMap<>();
	private static ConcurrentMap<String, String> personServerLocations = new ConcurrentHashMap<>();

	@Autowired
	private RegisteredServerService registeredServerService;

	@PostConstruct
	public void init() {
		try {
			RestResponse<List<Instance>> allInstances = instanceServiceClient.getAllInstances();
			if (!allInstances.isOk()) {
				System.err.println("Get all instance did not work: " + allInstances.getStatusCode() + ", "
						+ allInstances.getErrorMessage());
				return;
			}

			List<Instance> instances = allInstances.getResponse().get();
			for (Instance instance : instances) {
				for (String member : instance.getMembers()) {
					personServerLocations.put(member, instance.getId());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		List<RegisteredServer> allRegisteredServers = registeredServerService.getAllRegisteredServers();
		for (RegisteredServer registeredServer : allRegisteredServers) {
			NotificationSender notificationSender = new NotificationSender(registeredServer.getIpAddress(),
					registeredServer.getPort());
			notificationSenders.put(registeredServer.getId(), notificationSender);
		}
	}

	public void addPersonServerLocation(String username, Map<String, Object> data) {
		String gameSessionId = (String) data.get("gameSessionId");

		if (gameSessionId == null) {
			System.err.println("Got person login notification with null gameSessionId for username: " + username
					+ " with data: " + data);
			return;
		}
		personServerLocations.put(username, gameSessionId);
	}

	public void removePersonServerLocation(String username) {
		personServerLocations.remove(username);
	}

	public void registerNotificationListener(String gameSessionId, String address, Integer port) {
		RegisteredServer registeredServer = new RegisteredServer(gameSessionId, address, port);
		registeredServer = registeredServerService.saveRegisteredServer(registeredServer);

		NotificationSender notificationSender = new NotificationSender(address, port);

		notificationSenders.put(gameSessionId, notificationSender);
	}

	public void unregisterNotificationListener(String gameSessionId) {
		registeredServerService.deleteRegisteredServer(gameSessionId);
		NotificationSender notificationSender = notificationSenders.remove(gameSessionId);

		if (notificationSender != null) {
			notificationSender.close();
		}
	}

	public /* synchronized */ void addNotifications(NotificationType type, String username, Map<String, Object> data) {
		System.out.println("Add Notification");

		String playerServerLocation = personServerLocations.get(username);

		if (playerServerLocation == null) {
			System.out.println("Could not find " + username + " in a server");
			// TODO do some shit...
			return;
		}

		NotificationSender notificationSender = notificationSenders.get(playerServerLocation);

		if (notificationSender == null) {
			System.err.println("The server " + playerServerLocation + " does not have a registered listener");
			return;
		}

		NotificationMessage message = new NotificationMessage();
		message.addNotification(username, type.name(), data);
		if (!notificationSender.sendNotification(message)) {
			notificationSenders.remove(playerServerLocation);
		}
	}
}
