package com.valhallagame.notificationservice.model;

public enum NotificationType {
	PARTYCHANGE, FRIENDCHANGE, REWARDCHANGE, CHATCHANGE, PERSONCHANGE, DUNGEONACTIVE, CHAT_MESSAGE_RECEIVED;

	public String getValue() {
		return this.name();
	}
}