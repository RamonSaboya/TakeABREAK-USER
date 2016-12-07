package br.ufpe.cin.if678.gui.panel;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.ufpe.cin.if678.gui.ButtonTextKeyListener;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import br.ufpe.cin.if678.threads.ThreadManager;

@SuppressWarnings("serial")
public class AuthenticationPanel extends JPanel {

	private JTextField usernameField;

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
		promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
		promptLabel.setBounds(450, 310, 300, 20);

		JButton loginButton = new JButton("Login");
		loginButton.setName("loginButton");

		usernameField = new JTextField();
		usernameField.setBounds(500, 340, 200, 20);
		usernameField.setColumns(10);
		usernameField.setText("Ramon");
		usernameField.setForeground(Color.GRAY);
		usernameField.grabFocus();
		usernameField.setCaretPosition(0);
		usernameField.addKeyListener(new ButtonTextKeyListener(loginButton, "Ramon"));

		ThreadManager thread = new ThreadManager(frame, this, usernameField, 0);
		loginButton.setBounds(550, 370, 100, 20);
		loginButton.addActionListener(thread);

		add(promptLabel);
		add(usernameField);
		add(loginButton);
	}

	public void resetField() {
		usernameField.setForeground(Color.GRAY);
		usernameField.grabFocus();
		usernameField.setText("Ramon");
		usernameField.setCaretPosition(0);
	}

	@Override
	public void grabFocus() {
		super.grabFocus();
		usernameField.grabFocus();
		usernameField.setCaretPosition(0);
	}

}
