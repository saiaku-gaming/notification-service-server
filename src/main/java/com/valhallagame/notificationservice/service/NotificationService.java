package com.valhallagame.notificationservice.service;

import java.util.HashSet;
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
		for (NotificationSender notificationSender : notificationSenders) {
			System.out.println("looping through senders");
			NotificationMessage message = new NotificationMessage();
			System.out.println("sending notification");
			message.addNotification(username, type.name());
			notificationSender.sendNotification(message);
		}
	}

	// public void addNotifications(NotificationType type, String reason,
	// List<Person> persons) {
	// this.addNotifications(type, reason, persons.toArray(new
	// Person[persons.size()]));
	// }
	//
	// public void notifyPartyAndFriends(Person person, String reason) {
	// List<Person> persons = getPersonsFromParty(person.getParty());
	// persons.addAll(person.getFriends());
	// addNotifications(NotificationType.FRIENDCHANGE, reason, persons);
	// }
	//
	// public void notifyParty(NotificationType type, String reason, Party
	// party) {
	// if (party != null) {
	// addNotifications(type, reason, getPersonsFromParty(party));
	// }
	// }
	//
	// public void notifyDungeon(Dungeon dungeon, String reason) {
	// Person owner = dungeon.getPerson();
	// Party party = owner.getParty();
	// if (party != null) {
	// notifyParty(NotificationType.DUNGEONCHANGE, reason, party);
	// } else {
	// addNotifications(NotificationType.DUNGEONCHANGE, reason, owner);
	// }
	// }
	//
	// public void notifyPartyAboutChange(Person user, String reason) {
	// List<Person> persons = new ArrayList<>();
	// persons.add(user);
	// Party party = user.getParty();
	// if (party == null) {
	// party = partyService.getPartyFromLeader(user).orElse(null);
	// }
	// if (party != null) {
	// persons.addAll(party.getPartyMembers());
	// persons.addAll(party.getPartyPending());
	// }
	//
	// addNotifications(NotificationType.DUNGEONCHANGE, reason, persons);
	// }
	//
	// public List<Person> getPersonsFromParty(Party party) {
	// if (party == null) {
	// return new ArrayList<>();
	// }
	//
	// List<Person> persons = new ArrayList<>();
	//
	// persons.add(party.getPartyLeader());
	// persons.addAll(party.getPartyMembers());
	// persons.addAll(party.getPartyPending());
	//
	// return persons;
	// }
}
