package br.ufpe.cin.if678.gui.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
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
import br.ufpe.cin.if678.communication.UserAction;
import br.ufpe.cin.if678.gui.Test;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import br.ufpe.cin.if678.util.Pair;

@SuppressWarnings("serial")
public class UserListPanel extends JPanel {

	private TakeABREAK frame;

	private List<JComponent> components;

	/**
	 * Cria o painel lateral do usuário.
	 */
	public UserListPanel(TakeABREAK frame) {
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

	public void updateUsers() {
		for (JComponent component : components) {
			remove(component);
		}
		repaint();
		revalidate();

		HashMap<String, InetSocketAddress> users = UserController.getInstance().getNameToAddress();

		int y = 0;

		JSeparator separator;
		JLabel usernameLabel;
		JLabel IPLabel;
		JButton overlayButton;
		for (Map.Entry<String, InetSocketAddress> user : users.entrySet()) {
			overlayButton = new JButton();
			overlayButton.setBounds(0, y, 300, 50);
			overlayButton.setContentAreaFilled(false);
			
			Test overlayButtonThread = new Test(frame, user);
			overlayButton.addActionListener(overlayButtonThread);

			usernameLabel = new JLabel(user.getKey());
			usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			usernameLabel.setBounds(0, y + 5, 300, 20);

			IPLabel = new JLabel(user.getValue().getHostString() + ":" + user.getValue().getPort());
			IPLabel.setHorizontalAlignment(SwingConstants.CENTER);
			IPLabel.setBounds(0, y + 25, 300, 20);

			separator = new JSeparator(SwingConstants.HORIZONTAL);
			separator.setBounds(25, y + 49, 250, 1);
			separator.setForeground(Color.BLACK);
			separator.setBackground(Color.BLACK);

			y += 50;

			add(overlayButton);
			add(usernameLabel);
			add(IPLabel);
			add(separator);
			components.add(overlayButton);
			components.add(usernameLabel);
			components.add(IPLabel);
			components.add(separator);

			repaint();
			revalidate();
		}
	}

}
