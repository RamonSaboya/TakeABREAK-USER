package br.ufpe.cin.if678.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import br.ufpe.cin.if678.Encryption;
import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.communication.UserAction;
import br.ufpe.cin.if678.gui.ButtonTextKeyListener;
import br.ufpe.cin.if678.gui.DisplayFile;
import br.ufpe.cin.if678.gui.DisplayMessage;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import br.ufpe.cin.if678.threads.FileUploadThread;
import br.ufpe.cin.if678.util.Pair;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel {

	private String current;

	private JLabel title;
	private JScrollPane scrollPane;
	private JPanel container;
	private JTextField textField;
	private JButton sendButton;

	/**
	 * Cria o painel da página HOME.
	 */
	public ChatPanel(TakeABREAK frame) {
		super();

		// Seta as características do painel
		setBounds(300, 0, 900, 700);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);

		// Separador da esquerda
		JSeparator leftBorder = new JSeparator(SwingConstants.VERTICAL);
		leftBorder.setBounds(0, 0, 1, 700);
		leftBorder.setForeground(Color.BLACK);
		leftBorder.setBackground(Color.BLACK);

		title = new JLabel("");
		title.setBounds(6, 5, 889, 65);
		title.setHorizontalAlignment(SwingConstants.CENTER);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 75, 889, 555);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		container = new JPanel();
		container.setLayout(null);
		container.setLocation(0, 0);
		container.setMinimumSize(new Dimension(869, 550));
		container.setPreferredSize(container.getMinimumSize());
		container.setBackground(TakeABREAK.BACKGROUND_COLOR);

		sendButton = new JButton("Enviar");
		sendButton.setBounds(795, 640, 95, 50);

		textField = new JTextField();
		textField.setBounds(5, 640, 795, 50);
		textField.setMargin(new Insets(0, 10, 0, 10));
		resetField();
		textField.addKeyListener(new ButtonTextKeyListener(sendButton, "Digite uma mensagem..."));

		JLabel promptLabel = new JLabel("Escolha uma conversa para começar");
		promptLabel.setBounds(200, 150, 500, 400);
		promptLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		promptLabel.setHorizontalAlignment(SwingConstants.CENTER);

		scrollPane.setViewportView(container);

		container.add(promptLabel);

		add(title);
		add(scrollPane);
		add(textField);
		add(sendButton);
		add(leftBorder);
	}

	public void setCurrent(String groupName) {
		if (groupName.equals(current)) {
			resetField();
			return;
		}
		current = groupName;

		for (int c = 0; c < sendButton.getActionListeners().length; c++) {
			sendButton.removeActionListener(sendButton.getActionListeners()[c]);
		}

		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (textField.getText().equals("Digite uma mensagem...") || textField.getText().equals("")) {
					resetField();
					return;
				}

				String message = textField.getText();

				if (Files.exists(Paths.get(message), LinkOption.NOFOLLOW_LINKS)) {
					UserController.getInstance().sendFile(groupName, new File(message));
					resetField();
					return;
				}

				try {
					byte[] encrypted = Encryption.encryptMessage(UserController.getInstance().getUser().getFirst(), message);
					UserController.getInstance().getWriter().queueAction(UserAction.SEND_MESSAGE, new Pair<String, byte[]>(groupName, encrypted));
				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException
						| InvalidAlgorithmParameterException | IOException e1) {
					e1.printStackTrace();
				}

				resetField();
			}
		});

		updateScreen();

		title.setText("Conversa com: " + current);
		if (current.contains(":!:")) {
			if (current.split(":!:")[0].equals(UserController.getInstance().getUser().getSecond())) {
				title.setText("Conversa com: " + current.split(":!:")[1]);
			} else {
				title.setText("Conversa com: " + current.split(":!:")[0]);
			}
		}

		repaint();
		revalidate();
	}

	public String getCurrent() {
		return current;
	}

	public void resetField() {
		textField.setText("Digite uma mensagem...");
		textField.setForeground(Color.GRAY);
		textField.grabFocus();
		textField.setCaretPosition(0);
	}

	public void updateScreen() {
		container.removeAll();

		if (UserController.getInstance().getMessages(current) == null) {
			container.setPreferredSize(container.getMinimumSize());

			repaint();
			revalidate();
			return;
		}

		int y = 0;
		for (DisplayMessage message : UserController.getInstance().getMessages(current)) {
			JTextPane textPane = new JTextPane();

			if (message.getSenderID() == UserController.getInstance().getUser().getFirst()) {
				textPane.setBounds(355, 5 + y, 500, 40);
			} else {
				textPane.setBounds(5, 5 + y, 500, 40);
			}

			textPane.setText(message.getMessage());

			if (message instanceof DisplayFile) {
				DisplayFile displayFile = (DisplayFile) message;
				File file = displayFile.getFile();

				boolean download = UserController.getInstance().getUser().getFirst() != message.getSenderID();
				textPane.setText("FILE " + (download ? "DOWNLOAD" : "UPLOAD") + ": " + displayFile.getMessage() + " (" + file.length() + ")");

				JProgressBar bar = new JProgressBar();
				JButton start = new JButton("Iniciar");
				JButton pause = new JButton("Pausar");
				JButton stop = new JButton("Cancelar");
				JButton restart = new JButton("Reiniciar");

				int percentage = (int) ((displayFile.getBytesSent() * 100L) / file.length());
				bar.setMinimum(0);
				bar.setMaximum(100);
				bar.setValue(percentage);

				if (displayFile.getBytesSent() == file.length()) {
					start.setEnabled(false);
				}
				pause.setEnabled(false);
				stop.setEnabled(false);
				restart.setEnabled(false);

				int x = (int) textPane.getLocation().getX();
				bar.setBounds(x, y + 45, 500, 10);
				start.setBounds(x, y + 55, 125, 35);
				pause.setBounds(x + 125, y + 55, 125, 35);
				stop.setBounds(x + 250, y + 55, 125, 35);
				restart.setBounds(x + 375, y + 55, 125, 35);

				container.add(bar);
				container.add(start);
				container.add(pause);
				container.add(stop);
				container.add(restart);

				setupFileTransfer(current, message.getSenderID(), displayFile, bar, start, pause, stop, restart);

				y += 45;
			}

			container.add(textPane);

			y += 45;
		}

		container.setPreferredSize(new Dimension(299, y + 5));

		repaint();
		revalidate();
	}

	public void grabFocus() {
		textField.grabFocus();
		textField.setCaretPosition(0);
	}

	private void setupFileTransfer(String groupName, int senderID, DisplayFile displayFile, JProgressBar bar, JButton start, JButton pause, JButton stop, JButton restart) {
		boolean download = UserController.getInstance().getUser().getFirst() != senderID;

		if (download) {

		} else {
			start.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					new FileUploadThread(groupName, senderID, displayFile, bar, start, pause, stop, restart).start();
				}
			});
			restart.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					displayFile.setBytesSent(0L);

					new FileUploadThread(groupName, senderID, displayFile, bar, start, pause, stop, restart).start();
				}
			});
		}
	}

}
