package br.ufpe.cin.if678.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import br.ufpe.cin.if678.threads.ThreadManager;
import br.ufpe.cin.if678.util.Pair;

@SuppressWarnings("serial")
public class UserListPanel extends JPanel {

	private JPanel container;
	private List<JButton> overlayButtons;

	/**
	 * Cria o painel lateral do usuário.
	 */
	public UserListPanel(TakeABREAK frame) {
		super();

		this.overlayButtons = new ArrayList<JButton>();

		// Seta as características do painel
		setBounds(0, 125, 300, 575);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);

		// Separador da direita
		JSeparator rightBorder = new JSeparator(SwingConstants.VERTICAL);
		rightBorder.setBounds(299, 0, 1, 575);
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
		container.setBackground(TakeABREAK.BACKGROUND_COLOR);

		scrollPane.setViewportView(container);

		add(rightBorder);
		add(scrollPane);

		updateUsers();
	}

	public void updateUsers() {
		overlayButtons.clear();
		container.removeAll();

		HashMap<Integer, Pair<String, InetSocketAddress>> IDToNameAddress = UserController.getInstance().getIDToNameAddress();
		
		container.setPreferredSize(new Dimension(299, (IDToNameAddress.size() * 50) + 1));

		int y = 0;

		JLabel usernameLabel;
		JLabel IPLabel;
		JButton overlayButton;
		for (int ID : IDToNameAddress.keySet()) {
			overlayButton = new JButton();
			overlayButton.setName("overlayButton");
			overlayButton.setBounds(0, y, 281, 50);
			overlayButton.setContentAreaFilled(false);

			ThreadManager thread = new ThreadManager(null, null, null, ID);
			overlayButton.addActionListener(thread);

			usernameLabel = new JLabel(IDToNameAddress.get(ID).getFirst());
			usernameLabel.setBounds(0, y + 5, 281, 20);
			usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);

			IPLabel = new JLabel(IDToNameAddress.get(ID).getSecond().getHostString() + ":" + IDToNameAddress.get(ID).getSecond().getPort());
			IPLabel.setBounds(0, y + 25, 281, 20);
			IPLabel.setHorizontalAlignment(SwingConstants.CENTER);

			y += 50;

			container.add(overlayButton);
			container.add(usernameLabel);
			container.add(IPLabel);

			overlayButtons.add(overlayButton);

			repaint();
			revalidate();
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
