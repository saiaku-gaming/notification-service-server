package com.valhallagame.notificationservice.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.valhallagame.notificationservice.message.NotificationMessage;
import com.valhallagame.notificationservice.model.NotificationSender;
import com.valhallagame.notificationservice.model.NotificationType;

@Service
public class NotificationService {
	private static Set<NotificationSender> notificationSenders = new HashSet<>();

	public void registerNotificationListener(NotificationSender notificationSender) {
		notificationSenders.add(notificationSender);
	}

	public void unregisterNotificationListener(String address, int port) {
		notificationSenders = notificationSenders.stream().filter(nl -> {
			if (nl.getAddress().equals(address) && nl.getPort() == port) {
				nl.close();
				return false;
			}
			return true;
		}).collect(Collectors.toSet());
	}

	public void addNotifications(NotificationType type, String reason, String username) {
		System.out.println("Add Notification");
		Iterator<NotificationSender> itr = notificationSenders.iterator();
		while (itr.hasNext()) {
			NotificationSender notificationSender = itr.next();
			System.out.println("looping through senders");
			NotificationMessage message = new NotificationMessage();
			System.out.println("sending notification");
			message.addNotification(username, type.name());
			if (!notificationSender.sendNotification(message)) {
				itr.remove();
			}
		}
	}
}
