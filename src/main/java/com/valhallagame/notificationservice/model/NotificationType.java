package com.valhallagame.notificationservice.model;

public enum NotificationType {
	PARTYCHANGE,
	FRIENDCHANGE,
	REWARDCHANGE,
	CHATCHANGE,
	PERSONCHANGE,
	DUNGEONACTIVE, 
	CHAT_MESSAGE_RECEIVED,
	PARTY_RECEIVED_INVITE,
	FRIEND_RECEIVED_INVITE,
	DUNGEON_QUEUED,
	DUNGEON_FINISHING,
	DUNGEON_FINISHED,
	QUEUE_PLACEMENT_FULFILLED, 
	PARTY_PERSON_ONLINE,
	PARTY_PERSON_OFFLINE, FRIEND_ONLINE, FRIEND_OFFLINE;

	public String getValue() {
		return this.name();
	}
}