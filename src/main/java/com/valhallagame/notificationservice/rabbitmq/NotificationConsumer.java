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

	@RabbitListener(queues = { "#{notificationFriendAddQueue.name}", "#{notificationFriendRemoveQueue.name}",
			"#{notificationFriendSentInviteQueue.name}", "#{notificationFriendDeclineInviteQueue.name}" })
	public void receiveFriendNotification(NotificationMessage message) {
		notificationService.addNotifications(NotificationType.FRIENDCHANGE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationPartyCancelInviteQueue.name}",
			"#{notificationPartyAcceptInviteQueue.name}", "#{notificationPartyDeclineInviteQueue.name}",
			"#{notificationPartySentInviteQueue.name}", "#{notificationPartyLeaveQueue.name}",
			"#{notificationPartyKickFromPartyQueue.name}", "#{notificationPartyPromoteLeaderQueue.name}" })
	public void receivePartyNotificaiton(NotificationMessage message) {
		notificationService.addNotifications(NotificationType.PARTYCHANGE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationPartyReceivedInviteQueue.name}" })
	public void receivedInvitePartyNotification(NotificationMessage message) {
		notificationService.addNotifications(NotificationType.PARTY_RECEIVED_INVITE, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationPersonDeleteQueue.name}", "#{notificationPersonCreateQueue.name}",
			"#{notificationPersonOnlineQueue.name}", "#{notificationPersonOfflineQueue.name}" })
	public void receivePersonNotification(NotificationMessage message) {
		notificationService.addNotifications(NotificationType.PERSONCHANGE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{notificationChatReceivedMessageQueue.name}" })
	public void receiveChatNotification(NotificationMessage message) {
		notificationService.addNotifications(NotificationType.CHAT_MESSAGE_RECEIVED, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{notificationInstanceDungeonActiveQueue.name}" })
	public void receiveInstanceNotification(NotificationMessage message) {
		notificationService.addNotifications(NotificationType.DUNGEONACTIVE, message.getUsername(), message.getData());
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
		notificationService.addNotifications(NotificationType.FRIEND_RECEIVED_INVITE, message.getUsername(),
				message.getData());
	}
}
