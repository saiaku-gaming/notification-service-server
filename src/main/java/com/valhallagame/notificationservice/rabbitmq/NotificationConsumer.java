package com.valhallagame.notificationservice.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valhallagame.common.rabbitmq.NotificationMessage;
import com.valhallagame.notificationservice.model.NotificationType;
import com.valhallagame.notificationservice.service.NotificationService;

@Component
public class NotificationConsumer {

	@Autowired
	private NotificationService notificationService;

	@RabbitListener(queues = { 
			"#{notificationFriendAddQueue.name}",
			"#{notificationFriendRemoveQueue.name}",
			"#{notificationFriendSentInviteQueue.name}",
			"#{notificationFriendDeclineInviteQueue.name}" 
		})
	public void receiveFriendNotification(NotificationMessage message) {
		notificationService.addNotification(NotificationType.FRIENDCHANGE, message.getUsername(), message.getData());
	}
	
	
	@RabbitListener(queues = { "#{notificationFeatAddQueue.name}" })
	public void receiveFeatAdd(NotificationMessage message) {
		notificationService.addNotification(NotificationType.FEAT_ADDED, message.getUsername(),
				message.getData());
	}
	
	
	@RabbitListener(queues = { "#{notificationFeatRemoveQueue.name}" })
	public void receiveFeatRemove(NotificationMessage message) {
		notificationService.addNotification(NotificationType.FEAT_REMOVED, message.getUsername(),
				message.getData());
	}
	
	
	
	@RabbitListener(queues = { "#{notificationFriendOnlineQueue.name}" })
	public void receiveFriendOnline(NotificationMessage message) {
		notificationService.addNotification(NotificationType.FRIEND_ONLINE, message.getUsername(),
				message.getData());
	}
	
	@RabbitListener(queues = { "#{notificationFriendOfflineQueue.name}" })
	public void receiveFriendOffline(NotificationMessage message) {
		notificationService.addNotification(NotificationType.FRIEND_OFFLINE, message.getUsername(),
				message.getData());
	}
	
	@RabbitListener(queues = { 
			"#{notificationPartyCancelInviteQueue.name}",
			"#{notificationPartyAcceptInviteQueue.name}",
			"#{notificationPartyDeclineInviteQueue.name}",
			"#{notificationPartySentInviteQueue.name}",
			"#{notificationPartyLeaveQueue.name}",
			"#{notificationPartyKickFromPartyQueue.name}",
			"#{notificationPartyPromoteLeaderQueue.name}",
			"#{notificationPartySelectCharacterQueue.name}"
		})
	public void receivePartyNotificaiton(NotificationMessage message) {
		notificationService.addNotification(NotificationType.PARTYCHANGE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationPartyPersonOnlineQueue.name}" })
	public void receivedPartyPersonOnlineNotification(NotificationMessage message) {
		notificationService.addNotification(NotificationType.PARTY_PERSON_ONLINE, message.getUsername(),
				message.getData());
	}
	
	@RabbitListener(queues = { "#{notificationPartyPersonOfflineQueue.name}" })
	public void receivedPartyPersonOfflineNotification(NotificationMessage message) {
		notificationService.addNotification(NotificationType.PARTY_PERSON_OFFLINE, message.getUsername(),
				message.getData());
	}
	
	@RabbitListener(queues = { "#{notificationPartyReceivedInviteQueue.name}" })
	public void receivedInvitePartyNotification(NotificationMessage message) {
		notificationService.addNotification(NotificationType.PARTY_RECEIVED_INVITE, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationPersonDeleteQueue.name}", "#{notificationPersonCreateQueue.name}",
			"#{notificationPersonOnlineQueue.name}", "#{notificationPersonOfflineQueue.name}" })
	public void receivePersonNotification(NotificationMessage message) {
		notificationService.addNotification(NotificationType.PERSONCHANGE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationChatReceivedMessageQueue.name}" })
	public void receiveChatNotification(NotificationMessage message) {
		notificationService.addNotification(NotificationType.CHAT_MESSAGE_RECEIVED, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationInstanceDungeonActiveQueue.name}" })
	public void receiveInstanceNotification(NotificationMessage message) {
		notificationService.addNotification(NotificationType.DUNGEONACTIVE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationInstanceDungeonQueuedQueue.name}" })
	public void receiveDungeonQueuedNotification(NotificationMessage message) {
		notificationService.addNotification(NotificationType.DUNGEON_QUEUED, message.getUsername(), message.getData());
	}
	
	@RabbitListener(queues = { "#{notificationInstanceDungeonFinishingQueue.name}" })
	public void receiveDungeonFinishingNotification(NotificationMessage message) {
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
		notificationService.addNotification(NotificationType.QUEUE_PLACEMENT_FULFILLED, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationInstancePersonLoginQueue.name}" })
	public void receivePersonLoginNotification(NotificationMessage message) {
		notificationService.addPersonServerLocation(message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationInstancePersonLogoutQueue.name}" })
	public void receivePersonLogoutNotification(NotificationMessage message) {
		notificationService.removePersonServerLocation(message.getUsername());
	}

	@RabbitListener(queues = { "#{notificationFriendReceivedInviteQueue.name}" })
	public void receiveFriendReceivedInviteNotification(NotificationMessage message) {
		notificationService.addNotification(NotificationType.FRIEND_RECEIVED_INVITE, message.getUsername(),
				message.getData());
	}
}
