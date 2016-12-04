package br.ufpe.cin.if678.gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;

@SuppressWarnings("serial")
public class AuthenticationPanel extends JPanel {

	/**
	 * Cria o painel inicial.
	 * 
	 * @param frame
	 */
	public AuthenticationPanel(TakeABREAK frame) {
		super();

		// Seta as características do painel
		setBounds(0, 0, 1200, 700);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);

		JLabel promptLabel = new JLabel("Escolha seu nome de usuário");
		promptLabel.setBounds(450, 284, 189, 14);
		add(promptLabel);

		JTextField usernameField = new JTextField();
		usernameField.setBounds(415, 327, 224, 20);
		usernameField.setColumns(10);
		usernameField.setText("Ramon");
		add(usernameField);

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(482, 376, 89, 23);
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				UserController.getInstance().setUsername(usernameField.getText());

				frame.initializePanels();

				frame.clearFrame();
				frame.addPanel(frame.getSidebarPanel());
				frame.addPanel(frame.getUserListPanel());
				frame.addPanel(frame.getChatPanel());
			}
		});
		add(loginButton);
	}
}
