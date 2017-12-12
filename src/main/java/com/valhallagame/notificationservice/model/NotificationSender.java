package com.valhallagame.notificationservice.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valhallagame.notificationservice.message.NotificationMessage;

import lombok.Data;

@Data
public class NotificationSender {
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
	}

	public boolean sendNotification(NotificationMessage message) {
		System.out.println("send notification inner");
		try {
			System.out.println("send notification try");
			if (socket == null) {
				System.out.println("send notification null or close");
				open();
			} else if (System.currentTimeMillis() > (lastHeartbeat + FLATLINE_TIME_MS)) {
				System.out.println("flatline detected");
				close();
				System.out.println("reconnecting to: " + address + ":" + port);
				try {
					open();
				} catch (ConnectException e) {
					System.out.println("unable to reconnect to: " + address + ":" + port + ", unregistering listener");
					return false;
				}
			}

			System.out.println("send notification sending");
			writer.println(new ObjectMapper().writeValueAsString(message));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public void open() throws UnknownHostException, IOException {
		System.out.println("Trying To Open: " + address + ", Port: " + port);
		socket = new Socket(address, port);
		writer = new PrintWriter(socket.getOutputStream(), true);
		heartbeatThread = new Thread(() -> {
			try {
				while (!heartbeatThread.isInterrupted()) {
					// System.out.println("listening for heartbeat");
					int beat = socket.getInputStream().read();
					// System.out.println("heartbeat received: " + beat);
					if (beat != -1) {
						lastHeartbeat = System.currentTimeMillis();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		heartbeatThread.start();
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
				e.printStackTrace();
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
		int result = 23;

		result = 37 * (address == null ? 0 : address.hashCode());
		result = 37 * port;

		return result;
	}
}
