package com.valhallagame.notificationservice.service;

import com.valhallagame.notificationservice.model.RegisteredServer;
import com.valhallagame.notificationservice.repository.RegisteredServerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisteredServerService {

	private static final Logger logger = LoggerFactory.getLogger(RegisteredServerService.class);

	@Autowired
	private RegisteredServerRepository registeredServerRepository;

	public RegisteredServer saveRegisteredServer(RegisteredServer registeredServer) {
		logger.info("Saving registered server {}", registeredServer);
		return registeredServerRepository.save(registeredServer);
	}

	public void deleteRegisteredServer(RegisteredServer registeredServer) {
		logger.info("Deleting registered server {}", registeredServer);
		registeredServerRepository.delete(registeredServer);
	}

	public void deleteRegisteredServer(String gameSessionId) {
		logger.info("Deleting registered server with game session id {}", gameSessionId);
		try {
			registeredServerRepository.delete(gameSessionId);
		} catch (EmptyResultDataAccessException e){
			logger.warn("Tried to remove instance that did not exist in the database.", e);
		}
	}

	public List<RegisteredServer> getAllRegisteredServers() {
		logger.info("Getting all registered servers");
		return registeredServerRepository.findAll();
	}

}
