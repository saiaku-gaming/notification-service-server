package com.valhallagame.notificationservice.service;

import com.valhallagame.common.RestResponse;
import com.valhallagame.instanceserviceclient.InstanceServiceClient;
import com.valhallagame.instanceserviceclient.model.InstanceData;
import com.valhallagame.notificationservice.message.NotificationData;
import com.valhallagame.notificationservice.model.NotificationSender;
import com.valhallagame.notificationservice.model.NotificationType;
import com.valhallagame.notificationservice.model.RegisteredServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

@Service
public class NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

	private final InstanceServiceClient instanceServiceClient;

	private static ConcurrentMap<String, NotificationSender> notificationSenders = new ConcurrentHashMap<>();
	private static ConcurrentMap<String, String> personServerLocations = new ConcurrentHashMap<>();
	private static ConcurrentLinkedQueue<NotificationData> unsentNotifications = new ConcurrentLinkedQueue<>();

	private final RegisteredServerService registeredServerService;
    private static final int NOTIFICATION_DEFAULT_PORT = 8990;
    private static final int UNREAL_DEFAULT_PORT = 7777;
    private static final int NOTIFICATION_PORT_OFFSET = NOTIFICATION_DEFAULT_PORT - UNREAL_DEFAULT_PORT;

	@Autowired
	public NotificationService(
			InstanceServiceClient instanceServiceClient,
			RegisteredServerService registeredServerService
	) {
		this.instanceServiceClient = instanceServiceClient;
		this.registeredServerService = registeredServerService;
	}

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
		new Thread(() -> {
			List<RegisteredServer> allRegisteredServers = registeredServerService.getAllRegisteredServers();
			for (RegisteredServer registeredServer : allRegisteredServers) {
				NotificationSender notificationSender = new NotificationSender(registeredServer.getIpAddress(),
						registeredServer.getPort());
				notificationSenders.put(registeredServer.getId(), notificationSender);
			}
		}).start();
	}

	public void addPersonServerLocation(String username, Map<String, Object> data) {
		logger.info("Adding person to server for user {} with data {}", username, data);
		String gameSessionId = (String) data.get("gameSessionId");
		logger.info("user " + username + " logged in with gameSessionId " + gameSessionId);

		if (gameSessionId == null) {
			logger.error("Got person login notification with null gameSessionId for username: {} with data: {}",
					username, data);
			return;
		}
		personServerLocations.put(username, gameSessionId);
	}

	public void removePersonServerLocation(String username) {
		logger.info("Removing person from server with user {}", username);
		personServerLocations.remove(username);
	}

	public void registerNotificationListener(String gameSessionId, String address, Integer port) {
        logger.info("Registering notification listener for game session id {} address {}:{}", gameSessionId, address, port);
		RegisteredServer registeredServer = new RegisteredServer(gameSessionId, address, port);
		registeredServerService.saveRegisteredServer(registeredServer);

		NotificationSender notificationSender = new NotificationSender(address, port);

		notificationSenders.put(gameSessionId, notificationSender);
	}

	public void unregisterNotificationListener(String gameSessionId) {
		logger.info("Unregistering notification listener for game session id {}", gameSessionId);
		registeredServerService.deleteRegisteredServer(gameSessionId);
		NotificationSender notificationSender = notificationSenders.remove(gameSessionId);

		if (notificationSender != null) {
			notificationSender.close();
		}
	}

	public synchronized void addNotification(NotificationType type, String username, Map<String, Object> data) {
		logger.info("Adding notification of type {} for user {} with data {}", type, username, data);
		addNotification(new NotificationData(username, type.name(), data));
	}

	private synchronized void addNotification(NotificationData message) {
		logger.info("Add Notification: {}", message);
		if(message.getUsername() == null){
			logger.error("Tried to add a notification without username! " + message);
			return;
		}
		String playerServerLocation = personServerLocations.get(message.getUsername());

		if (playerServerLocation == null) {
			logger.info("Could not find {} in a server", message.getUsername());
			message.setRetries(message.getRetries() + 1);
			unsentNotifications.add(message);
			return;
		}

		NotificationSender notificationSender = notificationSenders.get(playerServerLocation);

		if (notificationSender == null) {
			logger.error("The server {} does not have a registered listener", playerServerLocation);
			return;
		}

		if (!notificationSender.sendNotification(message)) {
			unregisterNotificationListener(playerServerLocation);
		}
	}

	public synchronized void syncSendersAndLocations() throws IOException {
		logger.info("Syncing senders and locations");
		Set<String> missingInstances = new HashSet<>(notificationSenders.keySet());

		RestResponse<List<InstanceData>> allInstancesResp = instanceServiceClient.getAllInstances();
		Optional<List<InstanceData>> allInstancesOpt = allInstancesResp.get();
		logger.info("Checking all instances from instance service");
		if (allInstancesOpt.isPresent()) {
			for (InstanceData instance : allInstancesOpt.get()) {
				logger.info("Checking instance: " + instance.getId());
				if (!missingInstances.remove(instance.getId())) {
					logger.info("Unable to find instance: " + instance.getId() + " in: " + missingInstances.toString());
					logger.info("Instance not registered, registering now");
                    // instance.getPort is unreal port number, notification port is always a set number above it.
                    registerNotificationListener(instance.getId(), instance.getAddress(), NOTIFICATION_PORT_OFFSET + instance.getPort());
				}
			}
		} else {
			logger.error("Unable to get all instances status code: {}, message: {}", allInstancesResp.getStatusCode(),
					allInstancesResp.getErrorMessage());
		}

		logger.info("Checking instances to be removed");
		for (String missingInstance : missingInstances) {
			logger.info("Unregistering instance: {}", missingInstance);
			unregisterNotificationListener(missingInstance);
		}
	}

	public synchronized void resendUnsentMessages() {
		List<NotificationData> unsentMessages = new ArrayList<>(unsentNotifications);
		unsentNotifications.clear();

        if (!unsentMessages.isEmpty()) {
            logger.info("Resending unsent messages: {}", unsentMessages);
        }
		for (NotificationData notificationData : unsentMessages) {
			if (notificationData.getRetries() <= 10) {
				addNotification(notificationData);
			}
		}
	}
}
