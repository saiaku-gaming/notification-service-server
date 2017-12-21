package com.valhallagame.notificationservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registered_server")
public class RegisteredServer {
	@Id
	@Column(name = "registered_server_id")
	private String id;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "port")
	private Integer port;
}
