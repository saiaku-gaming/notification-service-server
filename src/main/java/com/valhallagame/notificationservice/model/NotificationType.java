package com.valhallagame.notificationservice.model;

public enum NotificationType {
	PARTYCHANGE, FRIENDCHANGE, REWARDCHANGE, CHATCHANGE, PERSONCHANGE, DUNGEONACTIVE, CHAT_MESSAGE_RECEIVED, PARTY_RECEIVED_INVITE;

	public String getValue() {
		return this.name();
	}
}