package br.ufpe.cin.if678.gui;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DisplayMessage implements Serializable {

	private static final long serialVersionUID = -8712020972407287987L;

	private int senderID;
	private String message;

	private LocalDateTime time;

	public DisplayMessage(int senderID, String message) {
		this.senderID = senderID;
		this.message = message;
		this.time = LocalDateTime.now();
	}

	public int getSenderID() {
		return senderID;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getTime() {
		return time;
	}

}
