package com.valhallagame.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valhallagame.notificationservice.model.RegisteredServer;

public interface RegisteredServerRepository extends JpaRepository<RegisteredServer, String> {

}
