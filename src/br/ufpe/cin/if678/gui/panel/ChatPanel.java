package br.ufpe.cin.if678.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.communication.UserAction;
import br.ufpe.cin.if678.gui.DisplayMessage;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import br.ufpe.cin.if678.util.Pair;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel {

	private TakeABREAK frame;

	private List<JComponent> components;

	private String current;
	private JScrollPane scrollPane;

	private HashMap<String, List<DisplayMessage>> messages;

	/**
	 * Cria o painel da página HOME.
	 */
	public ChatPanel(TakeABREAK frame) {
		super();

		this.frame = frame;
		this.components = new ArrayList<JComponent>();
		this.messages = new HashMap<String, List<DisplayMessage>>();

		// Seta as características do painel
		setBounds(300, 0, 900, 700);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);

		// Separador da esquerda
		JSeparator leftBorder = new JSeparator(SwingConstants.VERTICAL);
		leftBorder.setBounds(0, 0, 1, 700);
		leftBorder.setForeground(Color.BLACK);
		leftBorder.setBackground(Color.BLACK);

		// Insere todos os elementos no painel
		add(leftBorder);

		JLabel promptLabel = new JLabel("Escolha uma conversa para começar");
		promptLabel.setBounds(339, 176, 462, 370);
		add(promptLabel);

		components.add(promptLabel);
	}

	public void setCurrent(String groupName) {
		if (groupName.equals(current)) {
			return;
		}
		current = groupName;
		if (!messages.containsKey(groupName)) {
			messages.put(groupName, new ArrayList<DisplayMessage>());
		}

		scrollPane = null;
		for (JComponent component : components) {
			remove(component);
		}
		repaint();
		revalidate();

		JTextField textField = new JTextField("Digite uma mensagem...");
		textField.setForeground(Color.GRAY);
		textField.setBounds(5, 640, 795, 50);
		textField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
				textField.setForeground(Color.BLACK);
			}
		});

		JButton sendButton = new JButton("Enviar");
		sendButton.setBounds(795, 640, 95, 50);
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				UserController.getInstance().getWriter().queueAction(UserAction.SEND_MESSAGE, new Pair<String, String>(groupName, textField.getText()));
				textField.setText("Digite uma mensagem...");
				textField.setForeground(Color.GRAY);
				textField.setBounds(5, 640, 795, 50);
				textField.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						textField.setText("");
						textField.setForeground(Color.BLACK);
					}
				});
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(5, 5, 885, 630);

		updateScreen();

		add(textField);
		add(sendButton);
		add(scrollPane);
		components.add(textField);
		components.add(sendButton);
		components.add(scrollPane);

		repaint();
		revalidate();
	}

	public void receiveMessage(String groupName, InetSocketAddress sender, String message) {
		DisplayMessage display = new DisplayMessage(sender, UserController.getInstance().getName(sender), message);

		if (!messages.containsKey(groupName)) {
			messages.put(groupName, new ArrayList<DisplayMessage>());
		}
		messages.get(groupName).add(display);

		if (groupName.equals(current)) {
			updateScreen();
		}

		frame.getChatListPanel().updateLastMessage(groupName);
	}

	public void updateScreen() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(885, (messages.get(current).size() * 45) + 5));
		panel.setBackground(TakeABREAK.BACKGROUND_COLOR);

		int y = 0;
		for (DisplayMessage message : messages.get(current)) {
			JTextPane textPane = new JTextPane();

			if (message.getSender().equals(UserController.getInstance().getUser())) {
				textPane.setBounds(355, 5 + y, 500, 40);
			} else {
				textPane.setBounds(5, 5 + y, 500, 40);
			}

			textPane.setText(message.getMessage());
			panel.add(textPane);

			y += 45;
		}

		scrollPane.setViewportView(panel);
	}

	public String getLastMessage(String groupName) {
		if (!messages.containsKey(groupName) || messages.get(groupName).isEmpty()) {
			return "";
		}

		DisplayMessage message = messages.get(groupName).get(messages.get(groupName).size() - 1);
		return "  " + message.getSenderName() + ": " + message.getMessage();
	}

}
