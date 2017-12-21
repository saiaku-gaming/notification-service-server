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

	@RabbitListener(queues = { "#{friendAddQueue.name}", "#{friendRemoveQueue.name}",
			"#{friendReceivedInviteQueue.name}", "#{friendDeclineInviteQueue.name}" })
	public void receiveFriendNotification(NotificationMessage message) {
		notificationService.addNotifications(NotificationType.FRIENDCHANGE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{partyCancelInviteQueue.name}", "#{partyAcceptInviteQueue.name}",
			"#{partyDeclineInviteQueue.name}", "#{partySentInviteQueue.name}", "#{partyLeaveQueue.name}",
			"#{partyKickFromPartyQueue.name}", "#{partyPromoteLeaderQueue.name}" })
	public void receivePartyNotificaiton(NotificationMessage message) {
		notificationService.addNotifications(NotificationType.PARTYCHANGE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{personDeleteQueue.name}", "#{personCreateQueue.name}", "#{personOnlineQueue.name}",
			"#{personOfflineQueue.name}" })
	public void receivePersonNotification(NotificationMessage message) {
		notificationService.addNotifications(NotificationType.PERSONCHANGE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{chatReceivedMessageQueue.name}" })
	public void receiveChatNotification(NotificationMessage message) {
		notificationService.addNotifications(NotificationType.CHAT_MESSAGE_RECEIVED, message.getUsername(),
				message.getData());
	}

	@RabbitListener(queues = { "#{instanceDungeonActiveQueue.name}" })
	public void receiveInstanceNotification(NotificationMessage message) {
		notificationService.addNotifications(NotificationType.DUNGEONACTIVE, message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{instancePersonLoginQueue.name}" })
	public void receivePersonLoginNotification(NotificationMessage message) {
		notificationService.addPersonServerLocation(message.getUsername(), message.getData());
	}

	@RabbitListener(queues = { "#{instancePersonLogoutQueue.name}" })
	public void receivePersonLogoutNotification(NotificationMessage message) {
		notificationService.removePersonServerLocation(message.getUsername());
	}
}
