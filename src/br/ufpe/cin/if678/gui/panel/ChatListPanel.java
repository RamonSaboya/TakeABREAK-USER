package br.ufpe.cin.if678.gui.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.business.Group;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;

@SuppressWarnings("serial")
public class ChatListPanel extends JPanel {

	private TakeABREAK frame;

	private List<JComponent> components;

	/**
	 * Cria o painel lateral do usuário.
	 */
	public ChatListPanel(TakeABREAK frame) {
		super();

		this.frame = frame;
		this.components = new ArrayList<JComponent>();

		// Seta as características do painel
		setBounds(0, 125, 300, 575);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);

		// Separador da direita
		JSeparator rightBorder = new JSeparator(SwingConstants.VERTICAL);
		rightBorder.setBounds(299, 0, 1, 700);
		rightBorder.setForeground(Color.BLACK);
		rightBorder.setBackground(Color.BLACK);
		add(rightBorder);
	}

	public void updateChatList() {
		for (JComponent component : components) {
			remove(component);
		}
		repaint();
		revalidate();

		HashMap<String, Group> groups = UserController.getInstance().getGroups();

		int y = 0;

		JSeparator separator;
		JLabel groupLabel;
		JButton overlayButton;
		for (Map.Entry<String, Group> group : groups.entrySet()) {
			String username = UserController.getInstance().getName(UserController.getInstance().getUser());

			overlayButton = new JButton();
			overlayButton.setBounds(0, y, 300, 50);
			overlayButton.setContentAreaFilled(false);
			overlayButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					frame.getChatPanel().setCurrent(group.getKey());
				}
			});

			groupLabel = new JLabel(group.getKey());
			if (group.getKey().contains(":!:")) {
				if (group.getKey().split(":!:")[0].equals(username)) {
					groupLabel.setText(group.getKey().split(":!:")[1]);
				} else {
					groupLabel.setText(group.getKey().split(":!:")[0]);
				}
			}
			groupLabel.setHorizontalAlignment(SwingConstants.CENTER);
			groupLabel.setBounds(0, y + 5, 300, 20);

			separator = new JSeparator(SwingConstants.HORIZONTAL);
			separator.setBounds(25, y + 49, 250, 1);
			separator.setForeground(Color.BLACK);
			separator.setBackground(Color.BLACK);

			y += 50;

			add(overlayButton);
			add(groupLabel);
			add(separator);
			components.add(overlayButton);
			components.add(groupLabel);
			components.add(separator);

			repaint();
			revalidate();
		}
	}

}
