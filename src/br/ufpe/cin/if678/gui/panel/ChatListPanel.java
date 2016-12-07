package br.ufpe.cin.if678.gui.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.business.Group;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;

@SuppressWarnings("serial")
public class ChatListPanel extends JPanel {

	private JPanel container;
	private List<JButton> overlayButtons;
	private HashMap<String, JLabel> messageLabels;

	/**
	 * Cria o painel lateral do usuário.
	 */
	public ChatListPanel(TakeABREAK frame) {
		super();

		this.overlayButtons = new ArrayList<JButton>();
		this.messageLabels = new HashMap<String, JLabel>();

		// Seta as características do painel
		setBounds(0, 125, 300, 575);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);

		// Separador da direita
		JSeparator rightBorder = new JSeparator(SwingConstants.VERTICAL);
		rightBorder.setBounds(299, 0, 1, 700);
		rightBorder.setForeground(Color.BLACK);
		rightBorder.setBackground(Color.BLACK);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 299, 575);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		container = new JPanel();
		container.setLayout(null);
		container.setLocation(0, 0);
		container.setMinimumSize(new Dimension(299, 570));
		container.setPreferredSize(container.getMinimumSize());
		container.setBackground(TakeABREAK.BACKGROUND_COLOR);

		scrollPane.setViewportView(container);

		add(rightBorder);
		add(scrollPane);

		updateChatList();
	}

	public void updateChatList() {
		overlayButtons.clear();
		messageLabels.clear();
		for (Component component : container.getComponents()) {
			remove(component);
		}

		HashMap<String, Group> groups = UserController.getInstance().getGroups();

		int y = 0;

		JLabel groupLabel;
		JLabel messageLabel;
		JButton overlayButton;
		for (Map.Entry<String, Group> group : groups.entrySet()) {
			String username = UserController.getInstance().getUser().getSecond();

			overlayButton = new JButton();
			overlayButton.setBounds(0, y, 300, 50);
			overlayButton.setBackground(Color.BLACK);
			overlayButton.setContentAreaFilled(false);
			overlayButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					TakeABREAK.getInstance().getChatPanel().setCurrent(group.getKey());

					resetButtons();
					((JButton) event.getSource()).setContentAreaFilled(true);
				}
			});

			groupLabel = new JLabel(group.getKey());
			groupLabel.setBounds(0, y + 5, 300, 20);
			if (group.getKey().contains(":!:")) {
				if (group.getKey().split(":!:")[0].equals(username)) {
					groupLabel.setText(group.getKey().split(":!:")[1]);
				} else {
					groupLabel.setText(group.getKey().split(":!:")[0]);
				}
			}
			groupLabel.setHorizontalAlignment(SwingConstants.CENTER);

			messageLabel = new JLabel(UserController.getInstance().getLastMessage(group.getKey()));
			messageLabel.setBounds(0, y + 25, 300, 20);

			y += 50;

			container.add(overlayButton);
			container.add(groupLabel);
			container.add(messageLabel);

			overlayButtons.add(overlayButton);
			messageLabels.put(group.getKey(), messageLabel);

			repaint();
			revalidate();
		}
	}

	public void updateLastMessage(String groupName) {
		if (messageLabels.containsKey(groupName)) {
			messageLabels.get(groupName).setText(UserController.getInstance().getLastMessage(groupName));
		}
	}

	public void resetButtons() {
		for (JButton button : overlayButtons) {
			button.setContentAreaFilled(false);
		}
	}

	public void lock() {
		for (JButton button : overlayButtons) {
			button.setEnabled(false);
		}
	}

	public void unlock() {
		for (JButton button : overlayButtons) {
			button.setEnabled(true);
		}
	}

}
