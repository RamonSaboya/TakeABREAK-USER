package br.ufpe.cin.if678.gui;

import java.io.File;

public class DisplayFile extends DisplayMessage {

	private static final long serialVersionUID = 4663335110848869385L;

	private File file;
	private int tempFileName;
	private long bytesSent;

	public DisplayFile(int senderID, File file) {
		super(senderID, file.getName());

		this.file = file;
		this.tempFileName = -1;
		this.bytesSent = 0L;
	}

	public File getFile() {
		return file;
	}

	public int getTempFileName() {
		return tempFileName;
	}

	public void setTempFileName(int tempFileName2) {
		this.tempFileName = tempFileName2;
	}

	public long getBytesSent() {
		return bytesSent;
	}

	public void setBytesSent(long bytesSent) {
		this.bytesSent = bytesSent;
	}

}
