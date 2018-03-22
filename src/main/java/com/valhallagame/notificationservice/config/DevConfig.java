package com.valhallagame.notificationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.valhallagame.common.DefaultServicePortMappings;
import com.valhallagame.instanceserviceclient.InstanceServiceClient;

@Configuration
@Profile("development")
public class DevConfig {
	@Bean
	public InstanceServiceClient instanceServiceClient() {
		InstanceServiceClient.init("http://dev-instance-service:" + DefaultServicePortMappings.INSTANCE_SERVICE_PORT);
		return InstanceServiceClient.get();
	}
}
