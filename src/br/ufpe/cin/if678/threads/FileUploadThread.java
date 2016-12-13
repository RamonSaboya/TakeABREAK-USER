package br.ufpe.cin.if678.threads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;

import br.ufpe.cin.if678.Encryption;
import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.communication.UserAction;
import br.ufpe.cin.if678.communication.Writer;
import br.ufpe.cin.if678.gui.DisplayFile;
import br.ufpe.cin.if678.util.Tuple;

public class FileUploadThread extends Thread {

	private String groupName;
	private int senderID;
	private DisplayFile displayFile;

	private JTextPane progress;
	private JTextPane time;
	private JProgressBar bar;
	private JButton start;
	private JButton pause;
	private JButton stop;
	private JButton restart;

	public FileUploadThread(String groupName, int senderID, DisplayFile displayFile, JTextPane progress, JTextPane time, JProgressBar bar, JButton start, JButton pause, JButton stop, JButton restart) {
		this.groupName = groupName;
		this.senderID = senderID;
		this.displayFile = displayFile;

		this.progress = progress;
		this.time = time;
		this.bar = bar;
		this.start = start;
		this.pause = pause;
		this.stop = stop;
		this.restart = restart;
	}

	@Override
	public void run() {
		try {
			start.setEnabled(false);
			restart.setEnabled(false);

			pause.setEnabled(true);
			stop.setEnabled(true);

			Thread thread = this;
			pause.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					synchronized (thread) {
						thread.interrupt();

						start.setEnabled(true);
						pause.setEnabled(false);
					}
				}
			});
			stop.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					synchronized (thread) {
						thread.interrupt();

						pause.setEnabled(false);
						stop.setEnabled(false);
						restart.setEnabled(true);
					}
				}
			});

			File file = displayFile.getFile();

			Cipher cipher = Encryption.getEncryptionCipher(senderID);

			byte[] encryptedFileName = Encryption.encryptMessage(senderID, file.getName());
			long length = file.length();

			Writer writer = UserController.getInstance().getWriter();
			Object object = new Tuple<String, Integer, Tuple<byte[], Long, Long>>(groupName, senderID, new Tuple<byte[], Long, Long>(encryptedFileName, displayFile.getBytesSent(), length));

			UserController.getInstance().getListener().waitFileUpload(this);
			synchronized (this) {
				try {
					writer.queueAction(UserAction.SEND_FILE, object);
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			Socket socket = new Socket(UserController.getInstance().getIP(), 4848);
			FileInputStream FIS = new FileInputStream(file);
			CipherOutputStream COS = new CipherOutputStream(socket.getOutputStream(), cipher);

			byte[] buffer = new byte[4 * 1024];

			int count;
			long uploaded = 0;
			while ((count = FIS.read(buffer)) > 0 && !isInterrupted()) {
				if (uploaded == displayFile.getBytesSent()) {
					displayFile.setBytesSent(displayFile.getBytesSent() + count);
					bar.setValue((int) ((displayFile.getBytesSent() * 100L) / length));
					progress.setText(String.format("%02d%%", bar.getValue()));
					COS.write(buffer, 0, count);
				}

				uploaded += count;
			}

			if (!isInterrupted()) {
				pause.setEnabled(false);
				stop.setEnabled(false);
			}

			COS.close();
			FIS.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
	}

}
