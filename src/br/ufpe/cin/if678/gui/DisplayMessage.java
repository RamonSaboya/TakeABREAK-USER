package br.ufpe.cin.if678.gui;

import java.net.InetSocketAddress;

public class DisplayMessage {

	private InetSocketAddress sender;
	private String senderName;
	private String message;

	public DisplayMessage(InetSocketAddress sender, String senderName, String message) {
		this.sender = sender;
		this.senderName = senderName;
		this.message = message;
	}

	public InetSocketAddress getSender() {
		return sender;
	}

	public String getSenderName() {
		return senderName;
	}

	public String getMessage() {
		return message;
	}

}
