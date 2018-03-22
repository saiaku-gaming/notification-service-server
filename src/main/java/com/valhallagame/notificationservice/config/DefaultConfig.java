package com.valhallagame.notificationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.valhallagame.instanceserviceclient.InstanceServiceClient;

@Configuration
@Profile("default")
public class DefaultConfig {
	@Bean
	public InstanceServiceClient instanceServiceClient() {
		return InstanceServiceClient.get();
	}
}
