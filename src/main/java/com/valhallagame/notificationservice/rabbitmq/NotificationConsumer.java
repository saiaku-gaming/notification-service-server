package com.valhallagame.notificationservice.rabbitmq;

import com.valhallagame.common.rabbitmq.NotificationMessage;
import com.valhallagame.notificationservice.model.NotificationType;
import com.valhallagame.notificationservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
	private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

	@Autowired
	private NotificationService notificationService;

	@RabbitListener(queues = { "#{notificationFriendAddQueue.name}", "#{notificationFriendRemoveQueue.name}",
			"#{notificationFriendSentInviteQueue.name}", "#{notificationFriendDeclineInviteQueue.name}" })
	public void receiveFriendNotification(NotificationMessage message) {
		logger.info("Received friend notification with message {}");
		notificationService.addNotification(NotificationType.FRIENDCHANGE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationAddWardrobeItemQueue.name}" })
	public void receiveAddWardrobeItem(NotificationMessage message) {
		logger.info("Received add wardrobe with message {}");
		notificationService.addNotification(NotificationType.WARDROBE_ITEM_ADDED, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationTraitLockQueue.name}" })
	public void receiveLockTrait(NotificationMessage message) {
		logger.info("Received Lock Trait notification with message {}");
		notificationService.addNotification(NotificationType.TRAIT_LOCKED, message.getUsername(),
				message.getData());
	}
	
	@RabbitListener(queues = { "#{notificationTraitUnlockQueue.name}" })
	public void receiveUnlockTrait(NotificationMessage message) {
		logger.info("Received Unlock Trait notification with message {}");
		notificationService.addNotification(NotificationType.TRAIT_UNLOCKED, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationFeatAddQueue.name}" })
	public void receiveFeatAdd(NotificationMessage message) {
		logger.info("Received Feat add notification with message {}");
		notificationService.addNotification(NotificationType.FEAT_ADDED, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationFeatRemoveQueue.name}" })
	public void receiveFeatRemove(NotificationMessage message) {
		logger.info("Received feat remove notification with message {}");
		notificationService.addNotification(NotificationType.FEAT_REMOVED, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationFriendOnlineQueue.name}" })
	public void receiveFriendOnline(NotificationMessage message) {
		logger.info("Received friend online notification with message {}");
		notificationService.addNotification(NotificationType.FRIEND_ONLINE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationFriendOfflineQueue.name}" })
	public void receiveFriendOffline(NotificationMessage message) {
		logger.info("Received friend offline notification with message {}");
		notificationService.addNotification(NotificationType.FRIEND_OFFLINE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationPartyCancelInviteQueue.name}",
			"#{notificationPartyAcceptInviteQueue.name}", "#{notificationPartyDeclineInviteQueue.name}",
			"#{notificationPartySentInviteQueue.name}", "#{notificationPartyLeaveQueue.name}",
			"#{notificationPartyKickFromPartyQueue.name}", "#{notificationPartyPromoteLeaderQueue.name}",
			"#{notificationPartySelectCharacterQueue.name}" })
	public void receivePartyNotification(NotificationMessage message) {
		logger.info("Received Party notification with message {}");
		notificationService.addNotification(NotificationType.PARTYCHANGE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationPartyPersonOnlineQueue.name}" })
	public void receivedPartyPersonOnlineNotification(NotificationMessage message) {
		logger.info("Received Party person online notification with message {}");
		notificationService.addNotification(NotificationType.PARTY_PERSON_ONLINE, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationPartyPersonOfflineQueue.name}" })
	public void receivedPartyPersonOfflineNotification(NotificationMessage message) {
		logger.info("Received Party person offline notification with message {}");
		notificationService.addNotification(NotificationType.PARTY_PERSON_OFFLINE, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationPartyReceivedInviteQueue.name}" })
	public void receivedInvitePartyNotification(NotificationMessage message) {
		logger.info("Received invite Party notification with message {}");
		notificationService.addNotification(NotificationType.PARTY_RECEIVED_INVITE, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationPersonDeleteQueue.name}", "#{notificationPersonCreateQueue.name}",
			"#{notificationPersonOnlineQueue.name}", "#{notificationPersonOfflineQueue.name}" })
	public void receivePersonNotification(NotificationMessage message) {
		logger.info("Received person notification with message {}");
		notificationService.addNotification(NotificationType.PERSONCHANGE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationChatReceivedMessageQueue.name}" })
	public void receiveChatNotification(NotificationMessage message) {
		logger.info("Received chat notification with message {}");
		notificationService.addNotification(NotificationType.CHAT_MESSAGE_RECEIVED, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationInstanceDungeonActiveQueue.name}" })
	public void receiveInstanceNotification(NotificationMessage message) {
		logger.info("Received instance notification with message {}");
		notificationService.addNotification(NotificationType.DUNGEONACTIVE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationInstanceDungeonQueuedQueue.name}" })
	public void receiveDungeonQueuedNotification(NotificationMessage message) {
		logger.info("Received dungeon queued notification with message {}");
		notificationService.addNotification(NotificationType.DUNGEON_QUEUED, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationInstanceDungeonFinishingQueue.name}" })
	public void receiveDungeonFinishingNotification(NotificationMessage message) {
		logger.info("Received instance dungeon finishing notification with message {}");
		notificationService.addNotification(NotificationType.DUNGEON_FINISHING, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationInstanceDungeonFinishedQueue.name}" })
	public void receiveDungeonFinishedNotification(NotificationMessage message) {
		notificationService.addNotification(NotificationType.DUNGEON_FINISHED, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationInstanceQueuePlacementFulfilledQueue.name}" })
	public void receiveQueuePlacementFulfilledNotification(NotificationMessage message) {
		logger.info("Received queue placement fulfilled notification with message {}");
		notificationService.addNotification(NotificationType.QUEUE_PLACEMENT_FULFILLED, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationInstancePersonLoginQueue.name}" })
	public void receivePersonLoginNotification(NotificationMessage message) {
		logger.info("Received person login notification with message {}");
		notificationService.addPersonServerLocation(message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationInstancePersonLogoutQueue.name}" })
	public void receivePersonLogoutNotification(NotificationMessage message) {
		logger.info("Received person logout notification with message {}");
		notificationService.removePersonServerLocation(message.getUsername());
	}

	@RabbitListener(queues = { "#{notificationFriendReceivedInviteQueue.name}" })
	public void receiveFriendReceivedInviteNotification(NotificationMessage message) {
		logger.info("Received friend received invite notification with message {}");
		notificationService.addNotification(NotificationType.FRIEND_RECEIVED_INVITE, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = {"#{notificationAddRecipeQueue.name}"})
	public void receiveAddRecipe(NotificationMessage message) {
		logger.info("Received add recipe notification with message {}");
		notificationService.addNotification(NotificationType.RECIPE_ADDED, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = {"#{notificationRemoveRecipeQueue.name}"})
	public void receiveRemoveRecipe(NotificationMessage message) {
		logger.info("Received remove recipe notification with message {}");
		notificationService.addNotification(NotificationType.RECIPE_REMOVED, message.getUsername(),
				message.getData());
	}
}
