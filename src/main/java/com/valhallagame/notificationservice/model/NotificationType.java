package com.valhallagame.notificationservice.model;

public enum NotificationType {
	PARTYCHANGE, FRIENDCHANGE, REWARDCHANGE, CHATCHANGE, PERSONCHANGE, DUNGEONACTIVE, CHAT_MESSAGE_RECEIVED, PARTY_RECEIVED_INVITE, FRIEND_RECEIVED_INVITE, DUNGEON_QUEUED, DUNGEON_FINISHED;

	public String getValue() {
		return this.name();
	}
}