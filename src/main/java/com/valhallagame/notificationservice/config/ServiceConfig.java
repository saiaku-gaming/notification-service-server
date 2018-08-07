package com.valhallagame.notificationservice.config;

import com.valhallagame.common.DefaultServicePortMappings;
import com.valhallagame.instanceserviceclient.InstanceServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"production", "development"})
public class ServiceConfig {
	@Bean
	public InstanceServiceClient instanceServiceClient() {
		InstanceServiceClient.init("http://instance-service:" + DefaultServicePortMappings.INSTANCE_SERVICE_PORT);
		return InstanceServiceClient.get();
	}
}
