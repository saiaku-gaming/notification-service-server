package com.valhallagame.notificationservice.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valhallagame.common.RestResponse;
import com.valhallagame.instanceserviceclient.InstanceServiceClient;
import com.valhallagame.instanceserviceclient.model.InstanceData;
import com.valhallagame.notificationservice.message.NotificationMessage;
import com.valhallagame.notificationservice.model.NotificationSender;
import com.valhallagame.notificationservice.model.NotificationType;
import com.valhallagame.notificationservice.model.RegisteredServer;

@Service
public class NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

	private static InstanceServiceClient instanceServiceClient = InstanceServiceClient.get();

	private static ConcurrentMap<String, NotificationSender> notificationSenders = new ConcurrentHashMap<>();
	private static ConcurrentMap<String, String> personServerLocations = new ConcurrentHashMap<>();

	@Autowired
	private RegisteredServerService registeredServerService;

	@PostConstruct
	public void init() {
		try {
			RestResponse<List<InstanceData>> allInstancesResp = instanceServiceClient.getAllInstances();
			Optional<List<InstanceData>> allInstancesOpt = allInstancesResp.get();
			if (!allInstancesOpt.isPresent()) {
				logger.error("Get all instance did not work: " + allInstancesResp.getStatusCode() + ", "
						+ allInstancesResp.getErrorMessage());
				return;
			}

			List<InstanceData> instances = allInstancesOpt.get();
			for (InstanceData instance : instances) {
				for (String member : instance.getMembers()) {
					personServerLocations.put(member, instance.getId());
				}
			}

		} catch (IOException e) {
			logger.error("Something gon' wrong", e);
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
			logger.error("Got person login notification with null gameSessionId for username: {} with data: {}",
					username, data);
			return;
		}
		personServerLocations.put(username, gameSessionId);
	}

	public void removePersonServerLocation(String username) {
		personServerLocations.remove(username);
	}

	public void registerNotificationListener(String gameSessionId, String address, Integer port) {
		RegisteredServer registeredServer = new RegisteredServer(gameSessionId, address, port);
		registeredServerService.saveRegisteredServer(registeredServer);

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

	public synchronized void addNotifications(NotificationType type, String username, Map<String, Object> data) {
		logger.info("Add Notification");

		String playerServerLocation = personServerLocations.get(username);

		if (playerServerLocation == null) {
			logger.info("Could not find {} in a server", username);
			// TODO do some shit...
			return;
		}

		NotificationSender notificationSender = notificationSenders.get(playerServerLocation);

		if (notificationSender == null) {
			logger.error("The server {} does not have a registered listener", playerServerLocation);
			return;
		}

		NotificationMessage message = new NotificationMessage();
		message.addNotification(username, type.name(), data);
		if (!notificationSender.sendNotification(message)) {
			notificationSenders.remove(playerServerLocation);
		}
	}

	public synchronized void syncSendersAndLocations() throws IOException {
		Set<String> missingInstances = new HashSet<>();
		missingInstances.addAll(notificationSenders.keySet());
		
		RestResponse<List<InstanceData>> allInstancesResp = instanceServiceClient.getAllInstances();
		Optional<List<InstanceData>> allInstancesOpt = allInstancesResp.get();
		if (allInstancesOpt.isPresent()) {
			for (InstanceData instance : allInstancesOpt.get()) {
				if (!missingInstances.remove(instance.getId())) {
					registerNotificationListener(instance.getId(), instance.getAddress(), instance.getPort());
				}
			}
		} else {
			logger.error("Unable to get all instances status code: {}, message: {}", allInstancesResp.getStatusCode(),
					allInstancesResp.getErrorMessage());
		}

		for (String missingInstance : missingInstances) {
			unregisterNotificationListener(missingInstance);
		}
	}
}
