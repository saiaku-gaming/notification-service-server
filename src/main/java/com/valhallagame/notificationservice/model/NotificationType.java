package com.valhallagame.notificationservice.model;

public enum NotificationType {
	PARTYCHANGE, FRIENDCHANGE, REWARDCHANGE, CHATCHANGE, PERSONCHANGE, DUNGEONACTIVE;

	public String getValue() {
		return this.name();
	}
}