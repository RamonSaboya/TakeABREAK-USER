package br.ufpe.cin.if678.gui.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;

@SuppressWarnings("serial")
public class SidebarPanel extends JPanel {

	/**
	 * Cria o painel lateral do usuário.
	 */
	public SidebarPanel(TakeABREAK frame) {
		super();

		// Seta as características do painel
		setBounds(0, 0, 300, 125);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);

		InetSocketAddress user = UserController.getInstance().getUser().getThird();
		JLabel usernameLabel = new JLabel("Username: " + UserController.getInstance().getUser().getSecond());
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setBounds(50, 15, 200, 15);
		add(usernameLabel);

		JLabel IPLabel = new JLabel("IP: " + user.getHostString() + ":" + user.getPort());
		IPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IPLabel.setBounds(50, 40, 200, 15);
		add(IPLabel);

		JSeparator topSeparator = new JSeparator(SwingConstants.HORIZONTAL);
		topSeparator.setBounds(0, 75, 300, 1);
		topSeparator.setForeground(Color.BLACK);
		topSeparator.setBackground(Color.BLACK);
		add(topSeparator);

		JSeparator bottomSeparator = new JSeparator(SwingConstants.HORIZONTAL);
		bottomSeparator.setBounds(0, 124, 300, 1);
		bottomSeparator.setForeground(Color.BLACK);
		bottomSeparator.setBackground(Color.BLACK);
		add(bottomSeparator);

		// Separador da direita
		JSeparator rightBorder = new JSeparator(SwingConstants.VERTICAL);
		rightBorder.setBounds(299, 0, 1, 125);
		rightBorder.setForeground(Color.BLACK);
		rightBorder.setBackground(Color.BLACK);
		add(rightBorder);

		JButton onlineButton = new JButton("<html>Usuários<br />&nbsp;&nbsp;online</html>");
		onlineButton.setBounds(5, 75, 145, 50);
		onlineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.remove(frame.getChatListPanel());

				frame.repaint();
				frame.revalidate();

				frame.addPanel(frame.getUserListPanel());

				frame.repaint();
				frame.revalidate();
			}
		});
		add(onlineButton);

		JButton chatButton = new JButton("<html>Conversas<br />&nbsp;e grupos</html>");
		chatButton.setBounds(150, 75, 145, 50);
		chatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.remove(frame.getUserListPanel());

				frame.repaint();
				frame.revalidate();

				frame.addPanel(frame.getChatListPanel());

				frame.repaint();
				frame.revalidate();
			}
		});
		add(chatButton);
	}
}
