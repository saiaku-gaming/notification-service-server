package com.valhallagame.notificationservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valhallagame.notificationservice.model.RegisteredServer;
import com.valhallagame.notificationservice.repository.RegisteredServerRepository;

@Service
public class RegisteredServerService {

	@Autowired
	private RegisteredServerRepository registeredServerRepository;

	public RegisteredServer saveRegisteredServer(RegisteredServer registeredServer) {
		return registeredServerRepository.save(registeredServer);
	}

	public void deleteRegisteredServer(RegisteredServer registeredServer) {
		registeredServerRepository.delete(registeredServer);
	}

	public void deleteRegisteredServer(String gameSessionId) {
		registeredServerRepository.delete(gameSessionId);
	}

	public List<RegisteredServer> getAllRegisteredServers() {
		return registeredServerRepository.findAll();
	}

}
