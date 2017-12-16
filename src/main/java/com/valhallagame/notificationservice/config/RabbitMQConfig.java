package com.valhallagame.notificationservice.config;

import org.springframework.amqp.core.AnonymousQueue;
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
	public Queue friendAddQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Queue friendRemoveQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Queue friendReceivedInviteQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Queue friendDeclineInviteQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Binding bindingFriendAdd(DirectExchange friendExchange, Queue friendAddQueue) {
		return BindingBuilder.bind(friendAddQueue).to(friendExchange).with(RabbitMQRouting.Friend.ADD);
	}

	@Bean
	public Binding bindingFriendRemove(DirectExchange friendExchange, Queue friendRemoveQueue) {
		return BindingBuilder.bind(friendRemoveQueue).to(friendExchange).with(RabbitMQRouting.Friend.REMOVE);
	}

	@Bean
	public Binding bindingFriendReceivedInvite(DirectExchange friendExchange, Queue friendReceivedInviteQueue) {
		return BindingBuilder.bind(friendReceivedInviteQueue).to(friendExchange)
				.with(RabbitMQRouting.Friend.RECEIVED_INVITE);
	}

	@Bean
	public Binding bindingFriendDeclineInvite(DirectExchange friendExchange, Queue friendDeclineInviteQueue) {
		return BindingBuilder.bind(friendDeclineInviteQueue).to(friendExchange)
				.with(RabbitMQRouting.Friend.DECLINE_INVITE);
	}

	// CANCEL_INVITE, ACCEPT_INVITE, DECLINE_INVITE, LEAVE

	@Bean
	public DirectExchange partyExchange() {
		return new DirectExchange(RabbitMQRouting.Exchange.PARTY.name());
	}

	@Bean
	public Queue partyCancelInviteQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Queue partyAcceptInviteQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Queue partyDeclineInviteQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Queue partyLeaveQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Queue partySentInviteQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Binding bindingPartyCancelInvite(DirectExchange partyExchange, Queue partyCancelInviteQueue) {
		return BindingBuilder.bind(partyCancelInviteQueue).to(partyExchange).with(RabbitMQRouting.Party.CANCEL_INVITE);
	}

	@Bean
	public Binding bindingPartyAcceptInvite(DirectExchange partyExchange, Queue partyAcceptInviteQueue) {
		return BindingBuilder.bind(partyAcceptInviteQueue).to(partyExchange).with(RabbitMQRouting.Party.ACCEPT_INVITE);
	}

	@Bean
	public Binding bindingPartyDeclineInvite(DirectExchange partyExchange, Queue partyDeclineInviteQueue) {
		return BindingBuilder.bind(partyDeclineInviteQueue).to(partyExchange)
				.with(RabbitMQRouting.Party.DECLINE_INVITE);
	}

	@Bean
	public Binding bindingPartySentInvite(DirectExchange partyExchange, Queue partySentInviteQueue) {
		return BindingBuilder.bind(partySentInviteQueue).to(partyExchange).with(RabbitMQRouting.Party.SENT_INVITE);
	}

	@Bean
	public Binding bindingPartyLeave(DirectExchange partyExchange, Queue partyLeaveQueue) {
		return BindingBuilder.bind(partyLeaveQueue).to(partyExchange).with(RabbitMQRouting.Party.LEAVE);
	}

	// DELETE, CREATE, ONLINE, OFFLINE

	@Bean
	public DirectExchange personExchange() {
		return new DirectExchange(RabbitMQRouting.Exchange.PERSON.name());
	}

	@Bean
	public Queue personDeleteQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Queue personCreateQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Queue personOnlineQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Queue personOfflineQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Binding bindingPersonDelete(DirectExchange personExchange, Queue personDeleteQueue) {
		return BindingBuilder.bind(personDeleteQueue).to(personExchange).with(RabbitMQRouting.Person.DELETE);
	}

	@Bean
	public Binding bindingPersonCreate(DirectExchange personExchange, Queue personCreateQueue) {
		return BindingBuilder.bind(personCreateQueue).to(personExchange).with(RabbitMQRouting.Person.CREATE);
	}

	@Bean
	public Binding bindingPersonOnline(DirectExchange personExchange, Queue personOnlineQueue) {
		return BindingBuilder.bind(personOnlineQueue).to(personExchange).with(RabbitMQRouting.Person.ONLINE);
	}

	@Bean
	public Binding bindingPersonOffline(DirectExchange personExchange, Queue personOfflineQueue) {
		return BindingBuilder.bind(personOfflineQueue).to(personExchange).with(RabbitMQRouting.Person.OFFLINE);
	}

	// RECEIVED_MESSAGE

	@Bean
	public DirectExchange chatExchange() {
		return new DirectExchange(RabbitMQRouting.Exchange.CHAT.name());
	}

	@Bean
	public Queue chatReceivedMessageQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Binding bindingchatReceivedMessage(DirectExchange chatExchange, Queue chatReceivedMessageQueue) {
		return BindingBuilder.bind(chatReceivedMessageQueue).to(chatExchange)
				.with(RabbitMQRouting.Chat.RECEIVED_MESSAGE);
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
