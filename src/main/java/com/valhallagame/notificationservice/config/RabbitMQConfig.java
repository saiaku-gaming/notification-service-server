package com.valhallagame.notificationservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.valhallagame.common.rabbitmq.RabbitMQRouting;
import com.valhallagame.notificationservice.rabbitmq.NotificationConsumer;

@Configuration
public class RabbitMQConfig {

	@Bean
	public NotificationConsumer consumer() {
		return new NotificationConsumer();
	}

	@Bean
	public DirectExchange friendExchange() {
		return new DirectExchange(RabbitMQRouting.Exchange.FRIEND.name());
	}

	// ADD, REMOVE, RECEIVED_INVITE, DECLINE_INVITE, SENT_INVITE
	@Bean
	public Queue notificationFriendAddQueue() {
		return new Queue("notificationFriendAddQueue");
	}

	@Bean
	public Queue notificationFriendRemoveQueue() {
		return new Queue("notificationFriendRemoveQueue");
	}

	@Bean
	public Queue notificationFriendReceivedInviteQueue() {
		return new Queue("notificationFriendReceivedInviteQueue");
	}

	@Bean
	public Queue notificationFriendDeclineInviteQueue() {
		return new Queue("notificationFriendDeclineInviteQueue");
	}

	@Bean
	public Queue notificationFriendSentInviteQueue() {
		return new Queue("notificationFriendSentInviteQueue");
	}
	
	@Bean
	public Queue notificationFriendOnlineQueue() {
		return new Queue("notificationFriendOnlineQueue");
	}
	
	@Bean
	public Queue notificationFriendOfflineQueue() {
		return new Queue("notificationFriendOfflineQueue");
	}

	@Bean
	public Binding bindingFriendAdd(DirectExchange friendExchange, Queue notificationFriendAddQueue) {
		return BindingBuilder.bind(notificationFriendAddQueue).to(friendExchange).with(RabbitMQRouting.Friend.ADD);
	}

	@Bean
	public Binding bindingFriendRemove(DirectExchange friendExchange, Queue notificationFriendRemoveQueue) {
		return BindingBuilder.bind(notificationFriendRemoveQueue).to(friendExchange)
				.with(RabbitMQRouting.Friend.REMOVE);
	}

	@Bean
	public Binding bindingFriendReceivedInvite(DirectExchange friendExchange,
			Queue notificationFriendReceivedInviteQueue) {
		return BindingBuilder.bind(notificationFriendReceivedInviteQueue).to(friendExchange)
				.with(RabbitMQRouting.Friend.RECEIVED_INVITE);
	}

	@Bean
	public Binding bindingFriendDeclineInvite(DirectExchange friendExchange,
			Queue notificationFriendDeclineInviteQueue) {
		return BindingBuilder.bind(notificationFriendDeclineInviteQueue).to(friendExchange)
				.with(RabbitMQRouting.Friend.DECLINE_INVITE);
	}

	@Bean
	public Binding bindingFriendSentInvite(DirectExchange friendExchange, Queue notificationFriendSentInviteQueue) {
		return BindingBuilder.bind(notificationFriendSentInviteQueue).to(friendExchange)
				.with(RabbitMQRouting.Friend.SENT_INVITE);
	}
	
	@Bean
	public Binding bindingFriendOnline(DirectExchange friendExchange, Queue notificationFriendOnlineQueue) {
		return BindingBuilder.bind(notificationFriendOnlineQueue).to(friendExchange)
				.with(RabbitMQRouting.Friend.ONLINE);
	}

	@Bean
	public Binding bindingFriendOffline(DirectExchange friendExchange, Queue notificationFriendOfflineQueue) {
		return BindingBuilder.bind(notificationFriendOfflineQueue).to(friendExchange)
				.with(RabbitMQRouting.Friend.OFFLINE);
	}
	
	// CANCEL_INVITE, ACCEPT_INVITE, DECLINE_INVITE, LEAVE, KICK_FROM_PARTY,
	// PROMOTE_LEADER, RECEIVED_INVITE

	@Bean
	public DirectExchange partyExchange() {
		return new DirectExchange(RabbitMQRouting.Exchange.PARTY.name());
	}

	@Bean
	public Queue notificationPartyCancelInviteQueue() {
		return new Queue("notificationPartyCancelInviteQueue");
	}

	@Bean
	public Queue notificationPartyAcceptInviteQueue() {
		return new Queue("notificationPartyAcceptInviteQueue");
	}

	@Bean
	public Queue notificationPartyDeclineInviteQueue() {
		return new Queue("notificationPartyDeclineInviteQueue");
	}

	@Bean
	public Queue notificationPartyLeaveQueue() {
		return new Queue("notificationPartyLeaveQueue");
	}

	@Bean
	public Queue notificationPartySentInviteQueue() {
		return new Queue("notificationPartySentInviteQueue");
	}

	@Bean
	public Queue notificationPartyKickFromPartyQueue() {
		return new Queue("notificationPartyKickFromPartyQueue");
	}

	@Bean
	public Queue notificationPartyPromoteLeaderQueue() {
		return new Queue("notificationPartyPromoteLeaderQueue");
	}

	@Bean
	public Queue notificationPartyReceivedInviteQueue() {
		return new Queue("notificationPartyReceivedInviteQueue");
	}

	@Bean
	public Queue notificationPartySelectCharacterQueue() {
		return new Queue("notificationPartySelectCharacterQueue");
	}

	@Bean
	public Queue notificationPartyPersonOnlineQueue() {
		return new Queue("notificationPartyPersonOnlineQueue");
	}

	@Bean
	public Queue notificationPartyPersonOfflineQueue() {
		return new Queue("notificationPartyPersonOfflineQueue");
	}
	
	@Bean
	public Binding bindingPartyCancelInvite(DirectExchange partyExchange, Queue notificationPartyCancelInviteQueue) {
		return BindingBuilder.bind(notificationPartyCancelInviteQueue).to(partyExchange)
				.with(RabbitMQRouting.Party.CANCEL_INVITE);
	}

	@Bean
	public Binding bindingPartyAcceptInvite(DirectExchange partyExchange, Queue notificationPartyAcceptInviteQueue) {
		return BindingBuilder.bind(notificationPartyAcceptInviteQueue).to(partyExchange)
				.with(RabbitMQRouting.Party.ACCEPT_INVITE);
	}

	@Bean
	public Binding bindingPartyDeclineInvite(DirectExchange partyExchange, Queue notificationPartyDeclineInviteQueue) {
		return BindingBuilder.bind(notificationPartyDeclineInviteQueue).to(partyExchange)
				.with(RabbitMQRouting.Party.DECLINE_INVITE);
	}

	@Bean
	public Binding bindingPartySentInvite(DirectExchange partyExchange, Queue notificationPartySentInviteQueue) {
		return BindingBuilder.bind(notificationPartySentInviteQueue).to(partyExchange)
				.with(RabbitMQRouting.Party.SENT_INVITE);
	}

	@Bean
	public Binding bindingPartyLeave(DirectExchange partyExchange, Queue notificationPartyLeaveQueue) {
		return BindingBuilder.bind(notificationPartyLeaveQueue).to(partyExchange).with(RabbitMQRouting.Party.LEAVE);
	}

	@Bean
	public Binding bindingPartyKickFromParty(DirectExchange partyExchange, Queue notificationPartyKickFromPartyQueue) {
		return BindingBuilder.bind(notificationPartyKickFromPartyQueue).to(partyExchange)
				.with(RabbitMQRouting.Party.KICK_FROM_PARTY);
	}

	@Bean
	public Binding bindingPartyPromoteLeader(DirectExchange partyExchange, Queue notificationPartyPromoteLeaderQueue) {
		return BindingBuilder.bind(notificationPartyPromoteLeaderQueue).to(partyExchange)
				.with(RabbitMQRouting.Party.PROMOTE_LEADER);
	}

	@Bean
	public Binding bindingPartyReceivedInvite(DirectExchange partyExchange,
			Queue notificationPartyReceivedInviteQueue) {
		return BindingBuilder.bind(notificationPartyReceivedInviteQueue).to(partyExchange)
				.with(RabbitMQRouting.Party.RECEIVED_INVITE);
	}

	@Bean
	public Binding bindingPartySelectCharacter(DirectExchange partyExchange,
			Queue notificationPartySelectCharacterQueue) {
		return BindingBuilder.bind(notificationPartySelectCharacterQueue).to(partyExchange)
				.with(RabbitMQRouting.Party.SELECT_CHARACTER);
	}

	@Bean
	public Binding bindingPartyPersonOnline(DirectExchange partyExchange,
			Queue notificationPartyPersonOnlineQueue) {
		return BindingBuilder.bind(notificationPartyPersonOnlineQueue).to(partyExchange)
				.with(RabbitMQRouting.Party.PERSON_ONLINE);
	}
	
	@Bean
	public Binding bindingPartyPersonOffline(DirectExchange partyExchange,
			Queue notificationPartyPersonOfflineQueue) {
		return BindingBuilder.bind(notificationPartyPersonOfflineQueue).to(partyExchange)
				.with(RabbitMQRouting.Party.PERSON_OFFLINE);
	}

	
	// DELETE, CREATE, ONLINE, OFFLINE

	@Bean
	public DirectExchange personExchange() {
		return new DirectExchange(RabbitMQRouting.Exchange.PERSON.name());
	}

	@Bean
	public Queue notificationPersonDeleteQueue() {
		return new Queue("notificationPersonDeleteQueue");
	}

	@Bean
	public Queue notificationPersonCreateQueue() {
		return new Queue("notificationPersonCreateQueue");
	}

	@Bean
	public Queue notificationPersonOnlineQueue() {
		return new Queue("notificationPersonOnlineQueue");
	}

	@Bean
	public Queue notificationPersonOfflineQueue() {
		return new Queue("notificationPersonOfflineQueue");
	}

	@Bean
	public Binding bindingPersonDelete(DirectExchange personExchange, Queue notificationPersonDeleteQueue) {
		return BindingBuilder.bind(notificationPersonDeleteQueue).to(personExchange)
				.with(RabbitMQRouting.Person.DELETE);
	}

	@Bean
	public Binding bindingPersonCreate(DirectExchange personExchange, Queue notificationPersonCreateQueue) {
		return BindingBuilder.bind(notificationPersonCreateQueue).to(personExchange)
				.with(RabbitMQRouting.Person.CREATE);
	}

	@Bean
	public Binding bindingPersonOnline(DirectExchange personExchange, Queue notificationPersonOnlineQueue) {
		return BindingBuilder.bind(notificationPersonOnlineQueue).to(personExchange)
				.with(RabbitMQRouting.Person.ONLINE);
	}

	@Bean
	public Binding bindingPersonOffline(DirectExchange personExchange, Queue notificationPersonOfflineQueue) {
		return BindingBuilder.bind(notificationPersonOfflineQueue).to(personExchange)
				.with(RabbitMQRouting.Person.OFFLINE);
	}

	// RECEIVED_MESSAGE

	@Bean
	public DirectExchange chatExchange() {
		return new DirectExchange(RabbitMQRouting.Exchange.CHAT.name());
	}

	@Bean
	public Queue notificationChatReceivedMessageQueue() {
		return new Queue("notificationChatReceivedMessageQueue");
	}

	@Bean
	public Binding bindingchatReceivedMessage(DirectExchange chatExchange, Queue notificationChatReceivedMessageQueue) {
		return BindingBuilder.bind(notificationChatReceivedMessageQueue).to(chatExchange)
				.with(RabbitMQRouting.Chat.RECEIVED_MESSAGE);
	}

	// DUNGEON_ACTIVE, PERSON_LOGIN, PERSON_LOGOUT, DUNGEON_QUEUED,
	// DUNGEON_FINISHED, QUEUE_PLACEMENT_FULFILLED

	@Bean
	public DirectExchange instanceExchange() {
		return new DirectExchange(RabbitMQRouting.Exchange.INSTANCE.name());
	}

	@Bean
	public Queue notificationInstanceDungeonActiveQueue() {
		return new Queue("notificationInstanceDungeonActiveQueue");
	}

	@Bean
	public Queue notificationInstancePersonLoginQueue() {
		return new Queue("notificationInstancePersonLoginQueue");
	}

	@Bean
	public Queue notificationInstancePersonLogoutQueue() {
		return new Queue("notificationInstancePersonLogoutQueue");
	}

	@Bean
	public Queue notificationInstanceDungeonQueuedQueue() {
		return new Queue("notificationInstanceDungeonQueuedQueue");
	}

	@Bean
	public Queue notificationInstanceDungeonFinishingQueue() {
		return new Queue("notificationInstanceDungeonFinishingQueue");
	}
	
	@Bean
	public Queue notificationInstanceDungeonFinishedQueue() {
		return new Queue("notificationInstanceDungeonFinishedQueue");
	}

	@Bean
	public Queue notificationInstanceQueuePlacementFulfilledQueue() {
		return new Queue("notificationInstanceQueuePlacementFulfilledQueue");
	}

	@Bean
	public Binding bindingInstanceDungeonActive(DirectExchange instanceExchange,
			Queue notificationInstanceDungeonActiveQueue) {
		return BindingBuilder.bind(notificationInstanceDungeonActiveQueue).to(instanceExchange)
				.with(RabbitMQRouting.Instance.DUNGEON_ACTIVE);
	}

	@Bean
	public Binding bindingInstancePersonLogin(DirectExchange instanceExchange,
			Queue notificationInstancePersonLoginQueue) {
		return BindingBuilder.bind(notificationInstancePersonLoginQueue).to(instanceExchange)
				.with(RabbitMQRouting.Instance.PERSON_LOGIN);
	}

	@Bean
	public Binding bindingInstancePersonLogout(DirectExchange instanceExchange,
			Queue notificationInstancePersonLogoutQueue) {
		return BindingBuilder.bind(notificationInstancePersonLogoutQueue).to(instanceExchange)
				.with(RabbitMQRouting.Instance.PERSON_LOGOUT);
	}

	@Bean
	public Binding bindingInstanceDungeonQueued(DirectExchange instanceExchange,
			Queue notificationInstanceDungeonQueuedQueue) {
		return BindingBuilder.bind(notificationInstanceDungeonQueuedQueue).to(instanceExchange)
				.with(RabbitMQRouting.Instance.DUNGEON_QUEUED);
	}

	@Bean
	public Binding bindingInstanceDungeonFinishing(DirectExchange instanceExchange,
			Queue notificationInstanceDungeonFinishingQueue) {
		return BindingBuilder.bind(notificationInstanceDungeonFinishingQueue).to(instanceExchange)
				.with(RabbitMQRouting.Instance.DUNGEON_FINISHING);
	}

	
	@Bean
	public Binding bindingInstanceDungeonFinished(DirectExchange instanceExchange,
			Queue notificationInstanceDungeonFinishedQueue) {
		return BindingBuilder.bind(notificationInstanceDungeonFinishedQueue).to(instanceExchange)
				.with(RabbitMQRouting.Instance.DUNGEON_FINISHED);
	}

	@Bean
	public Binding bindingInstanceQueuePlacementFulfilled(DirectExchange instanceExchange,
			Queue notificationInstanceQueuePlacementFulfilledQueue) {
		return BindingBuilder.bind(notificationInstanceQueuePlacementFulfilledQueue).to(instanceExchange)
				.with(RabbitMQRouting.Instance.QUEUE_PLACEMENT_FULFILLED);
	}

	@Bean
	public Jackson2JsonMessageConverter jacksonConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public SimpleRabbitListenerContainerFactory containerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setMessageConverter(jacksonConverter());
		return factory;
	}
}
