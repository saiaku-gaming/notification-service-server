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

	// ADD, REMOVE, RECEIVED_INVITE, DECLINE_INVITE
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

	// CANCEL_INVITE, ACCEPT_INVITE, DECLINE_INVITE, LEAVE, KICK_FROM_PARTY,
	// PROMOTE_LEADER

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

	// START_DUNGEON, PERSON_LOGIN, PERSON_LOGOUT

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
	public Jackson2JsonMessageConverter jacksonConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public SimpleRabbitListenerContainerFactory ContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setMessageConverter(jacksonConverter());
		return factory;
	}
}
