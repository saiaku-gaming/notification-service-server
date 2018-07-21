package com.valhallagame.notificationservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.valhallagame.notificationservice.model.RegisteredServer;
import com.valhallagame.notificationservice.repository.RegisteredServerRepository;

@Service
public class RegisteredServerService {

	private static final Logger logger = LoggerFactory.getLogger(RegisteredServerService.class);

	@Autowired
	private RegisteredServerRepository registeredServerRepository;

	public RegisteredServer saveRegisteredServer(RegisteredServer registeredServer) {
		return registeredServerRepository.save(registeredServer);
	}

	public void deleteRegisteredServer(RegisteredServer registeredServer) {
		registeredServerRepository.delete(registeredServer);
	}

	public void deleteRegisteredServer(String gameSessionId) {
		try {
			registeredServerRepository.delete(gameSessionId);
		} catch (EmptyResultDataAccessException e){
			logger.warn("Tried to remove instance that did not exist in the database.", e);
		}
	}

	public List<RegisteredServer> getAllRegisteredServers() {
		return registeredServerRepository.findAll();
	}

}
