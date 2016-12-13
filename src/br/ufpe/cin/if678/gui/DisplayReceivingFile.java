package br.ufpe.cin.if678.gui;

public class DisplayReceivingFile extends DisplayMessage {

	private static final long serialVersionUID = 4663335110848869385L;

	private int tempFileName;
	private String fileName;
	private long length;
	private long bytesReceived;

	public DisplayReceivingFile(int senderID, int tempFileName, String fileName, long length) {
		super(senderID, fileName);

		this.tempFileName = tempFileName;
		this.fileName = fileName;
		this.length = length;
		this.bytesReceived = 0L;
	}

	public int getTempFileName() {
		return tempFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public long getLength() {
		return length;
	}

	public long getBytesReceived() {
		return bytesReceived;
	}

	public void setBytesReceived(long bytesReceived) {
		this.bytesReceived = bytesReceived;
	}

}
