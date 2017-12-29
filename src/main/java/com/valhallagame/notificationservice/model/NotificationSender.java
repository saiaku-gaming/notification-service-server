package com.valhallagame.notificationservice.model;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valhallagame.notificationservice.message.NotificationData;

import lombok.Data;

@Data
public class NotificationSender {

	private static final String SOCKET_FAILURE = "Socket failure";

	private static final Logger logger = LoggerFactory.getLogger(NotificationSender.class);

	private static final long FLATLINE_TIME_MS = 2000L;

	private String address;
	private int port;
	private long lastHeartbeat;
	private Thread heartbeatThread;

	private Socket socket;
	private PrintWriter writer;

	public NotificationSender(String address, int port) {
		this.address = address;
		this.port = port;
		this.lastHeartbeat = 0L;

		try {
			open();
		} catch (IOException e) {
			logger.error("Failed to open", e);
		}
	}

	public boolean sendNotification(NotificationData message) {
		logger.info("send notification inner");
		try {
			logger.info("send notification try");
			if (socket == null) {
				logger.info("send notification null or close");
				open();
			} else if (System.currentTimeMillis() > (lastHeartbeat + FLATLINE_TIME_MS)) {
				logger.info("flatline detected");
				close();
				logger.info("reconnecting to: {}:{}", address, port);
				open();
				logger.info("reconnection successful");
			}

			logger.info("send notification sending");
			writer.println(new ObjectMapper().writeValueAsString(message));
		} catch (ConnectException e) {
			logger.info("unable to reconnect to: {}:{}, unregistering listener", address, port);
			return false;
		} catch (IOException e) {
			logger.error(SOCKET_FAILURE, e);
			return false;
		}

		return true;
	}

	public void open() throws IOException {
		logger.info("Trying To Open: {}:{}", address, port);
		socket = new Socket(address, port);
		writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
		heartbeatThread = new Thread(() -> {
			try {
				while (!heartbeatThread.isInterrupted()) {
					int beat = socket.getInputStream().read();
					if (beat != -1) {
						lastHeartbeat = System.currentTimeMillis();
					}
				}
			} catch (IOException e) {
				logger.error(SOCKET_FAILURE, e);
			}
		});
		heartbeatThread.start();
		lastHeartbeat = System.currentTimeMillis();
	}

	public void close() {
		if (heartbeatThread != null && heartbeatThread.isAlive()) {
			heartbeatThread.interrupt();
		}
		if (writer != null) {
			writer.close();
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				logger.error(SOCKET_FAILURE, e);
			}
		}
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}
		if (!(other instanceof NotificationSender)) {
			return false;
		}

		NotificationSender otherNl = (NotificationSender) other;

		return otherNl.address.equals(address) && otherNl.port == port;
	}

	@Override
	public int hashCode() {
		return 37 * (address == null ? 0 : address.hashCode()) * port;
	}

}
