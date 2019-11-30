package com.valhallagame.notificationservice.rabbitmq;

import com.valhallagame.common.rabbitmq.NotificationMessage;
import com.valhallagame.notificationservice.model.NotificationType;
import com.valhallagame.notificationservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.valhallagame.notificationservice.model.NotificationType.*;

@Component
public class NotificationConsumer {
	private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

	@Autowired
	private NotificationService notificationService;

	@Value("${spring.application.name}")
	private String appName;

	@RabbitListener(queues = { "#{notificationFriendAddQueue.name}", "#{notificationFriendRemoveQueue.name}",
			"#{notificationFriendSentInviteQueue.name}", "#{notificationFriendDeclineInviteQueue.name}" })
	public void receiveFriendNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received friend notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.FRIENDCHANGE, message.getUsername(), message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Friend notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = {"#{notificationFriendCancelInviteQueue.name}"})
	public void receiveFriendCancelNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String) message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received friend notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.FRIEND_CANCELED_INVITE, message.getUsername(), message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Friend notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationAddWardrobeItemQueue.name}" })
	public void receiveAddWardrobeItem(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received add wardrobe with message {}", message);

		try {
			notificationService.addNotification(NotificationType.WARDROBE_ITEM_ADDED, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Add Wardrobe Item notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationTraitLockQueue.name}" })
	public void receiveLockTrait(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received Lock Trait notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.TRAIT_LOCKED, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Lock Trait notification", e);
		} finally {
			MDC.clear();
		}
	}
	
	@RabbitListener(queues = { "#{notificationTraitUnlockQueue.name}" })
	public void receiveUnlockTrait(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received Unlock Trait notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.TRAIT_UNLOCKED, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Unlock Trait notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationFeatAddQueue.name}" })
	public void receiveFeatAdd(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received Feat add notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.FEAT_ADDED, message.getUsername(), message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Feat Add notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationFeatRemoveQueue.name}" })
	public void receiveFeatRemove(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received feat remove notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.FEAT_REMOVED, message.getUsername(), message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Feat Remove notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationFriendOnlineQueue.name}" })
	public void receiveFriendOnline(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received friend online notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.FRIEND_ONLINE, message.getUsername(), message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Friend Online notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationFriendOfflineQueue.name}" })
	public void receiveFriendOffline(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received friend offline notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.FRIEND_OFFLINE, message.getUsername(), message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Friend Offline notification", e);
		} finally {
			MDC.clear();
		}
	}

	private void receivePartyNotification(NotificationMessage message, NotificationType notificationType) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received Party notification with message {}", message);

		try {
			notificationService.addNotification(notificationType, message.getUsername(), message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Party notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = {"#{notificationPartyCancelInviteQueue.name}"})
	public void receivePartyCancelInviteNotification(NotificationMessage message) {
		receivePartyNotification(message, PARTY_CANCEL_INVITE);
	}

	@RabbitListener(queues = {"#{notificationPartyAcceptInviteQueue.name}"})
	public void receivePartyAcceptInviteNotification(NotificationMessage message) {
		receivePartyNotification(message, PARTY_ACCEPT_INVITE);
	}

	@RabbitListener(queues = {"#{notificationPartyDeclineInviteQueue.name}"})
	public void receivePartyDeclineInviteNotification(NotificationMessage message) {
		receivePartyNotification(message, PARTY_DECLINE_INVITE);
	}

	@RabbitListener(queues = {"#{notificationPartySentInviteQueue.name}"})
	public void receivePartySentInviteNotification(NotificationMessage message) {
		receivePartyNotification(message, PARTY_SENT_INVITE);
	}

	@RabbitListener(queues = {"#{notificationPartyLeaveQueue.name}"})
	public void receivePartyLeaveNotification(NotificationMessage message) {
		receivePartyNotification(message, PARTY_LEAVE);
	}

	@RabbitListener(queues = {"#{notificationPartyKickFromPartyQueue.name}"})
	public void receivePartyKickFromPartyNotification(NotificationMessage message) {
		receivePartyNotification(message, PARTY_KICK_FROM_PARTY);
	}

	@RabbitListener(queues = {"#{notificationPartyPromoteLeaderQueue.name}"})
	public void receivePartyPromoteLeaderNotification(NotificationMessage message) {
		receivePartyNotification(message, PARTY_PROMOTE_LEADER);
	}

	@RabbitListener(queues = {"#{notificationPartySelectCharacterQueue.name}"})
	public void receivePartySelectCharacterNotification(NotificationMessage message) {
		receivePartyNotification(message, PARTY_SELECT_CHARACTER);
	}

	@RabbitListener(queues = { "#{notificationPartyPersonOnlineQueue.name}" })
	public void receivedPartyPersonOnlineNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received Party person online notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.PARTY_PERSON_ONLINE, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Party Person Online notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationPartyPersonOfflineQueue.name}" })
	public void receivedPartyPersonOfflineNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received Party person offline notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.PARTY_PERSON_OFFLINE, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Party Person Offline notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationPartyReceivedInviteQueue.name}" })
	public void receivedInvitePartyNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received invite Party notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.PARTY_RECEIVED_INVITE, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Invite Party notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationPersonDeleteQueue.name}", "#{notificationPersonCreateQueue.name}",
			"#{notificationPersonOnlineQueue.name}", "#{notificationPersonOfflineQueue.name}" })
	public void receivePersonNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received person notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.PERSONCHANGE, message.getUsername(), message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Person notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationChatReceivedMessageQueue.name}" })
	public void receiveChatNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received chat notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.CHAT_MESSAGE_RECEIVED, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Chat notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationInstanceDungeonActiveQueue.name}" })
	public void receiveInstanceNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received instance notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.DUNGEONACTIVE, message.getUsername(), message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Instance notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationInstanceDungeonQueuedQueue.name}" })
	public void receiveDungeonQueuedNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received dungeon queued notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.DUNGEON_QUEUED, message.getUsername(), message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Dungeon Queued notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationInstanceDungeonFinishingQueue.name}" })
	public void receiveDungeonFinishingNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received instance dungeon finishing notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.DUNGEON_FINISHING, message.getUsername(),
					message.getData());
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationInstanceDungeonFinishedQueue.name}" })
	public void receiveDungeonFinishedNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received dungeon finished notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.DUNGEON_FINISHED, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Instance Dungeon Finished notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationInstanceQueuePlacementFulfilledQueue.name}" })
	public void receiveQueuePlacementFulfilledNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received queue placement fulfilled notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.QUEUE_PLACEMENT_FULFILLED, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Queue Placement Fulfilled notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationInstancePersonLoginQueue.name}" })
	public void receivePersonLoginNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received person login notification with message {}", message);

		try {
			notificationService.addPersonServerLocation(message.getUsername(), message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Person Login notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationInstancePersonLogoutQueue.name}" })
	public void receivePersonLogoutNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received person logout notification with message {}", message);

		try {
			notificationService.removePersonServerLocation(message.getUsername());
		} catch (Exception e) {
			logger.error("Error while processing Person Logout notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = { "#{notificationFriendReceivedInviteQueue.name}" })
	public void receiveFriendReceivedInviteNotification(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received friend received invite notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.FRIEND_RECEIVED_INVITE, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Friend Received Invite notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = {"#{notificationAddRecipeQueue.name}"})
	public void receiveAddRecipe(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received add recipe notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.RECIPE_ADDED, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Add Recipe notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = {"#{notificationRemoveRecipeQueue.name}"})
	public void receiveRemoveRecipe(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received remove recipe notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.RECIPE_REMOVED, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Remove Recipe notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = {"#{notificationAddCurrencyQueue.name}"})
	public void receiveAddCurrency(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received add currency notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.CURRENCY_ADDED, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Add Currency notification", e);
		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(queues = {"#{notificationRemoveCurrencyQueue.name}"})
	public void receiveRemoveCurrency(NotificationMessage message) {
		MDC.put("service_name", appName);
		MDC.put("request_id", message.getData().get("requestId") != null ? (String)message.getData().get("requestId") : UUID.randomUUID().toString());

		logger.info("Received remove currency notification with message {}", message);

		try {
			notificationService.addNotification(NotificationType.CURRENCY_REMOVED, message.getUsername(),
					message.getData());
		} catch (Exception e) {
			logger.error("Error while processing Remove Currency notification", e);
		} finally {
			MDC.clear();
		}
	}
}
