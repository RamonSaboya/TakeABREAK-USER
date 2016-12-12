package br.ufpe.cin.if678.gui;

import java.io.File;

public class DisplayFile extends DisplayMessage {

	private static final long serialVersionUID = 4663335110848869385L;

	private File file;
	private long bytesSent;

	public DisplayFile(int senderID, File file) {
		super(senderID, file.getName());

		this.file = file;
		this.bytesSent = 0L;
	}

	public File getFile() {
		return file;
	}

	public long getBytesSent() {
		return bytesSent;
	}

	public void setBytesSent(long bytesSent) {
		this.bytesSent = bytesSent;
	}

}
