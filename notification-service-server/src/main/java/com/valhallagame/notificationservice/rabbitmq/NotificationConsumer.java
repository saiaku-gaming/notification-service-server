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
		notificationService.addNotifications(NotificationType.FRIENDCHANGE, message.getReason(), message.getUsername());
	}

	@RabbitListener(queues = { "#{partyCancelInviteQueue.name}", "#{partyAcceptInviteQueue.name}",
			"#{partyDeclineInviteQueue.name}", "#{partyLeaveQueue.name}" })
	public void receivePartyNotificaiton(NotificationMessage message) {
		notificationService.addNotifications(NotificationType.PARTYCHANGE, message.getReason(), message.getUsername());
	}

	@RabbitListener(queues = { "#{personDeleteQueue.name}", "#{personCreateQueue.name}", "#{personOnlineQueue.name}",
			"#{personOfflineQueue.name}" })
	public void receivePersonNotification(NotificationMessage message) {
		notificationService.addNotifications(NotificationType.PERSONCHANGE, message.getReason(), message.getUsername());
	}
}
