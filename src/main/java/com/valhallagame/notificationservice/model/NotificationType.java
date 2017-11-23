package com.valhallagame.notificationservice.model;

public enum NotificationType {
	PARTYCHANGE, DUNGEONCHANGE, FRIENDCHANGE, REWARDCHANGE, CHATCHANGE, PERSONCHANGE;

	public String getValue() {
		return this.name();
	}
}