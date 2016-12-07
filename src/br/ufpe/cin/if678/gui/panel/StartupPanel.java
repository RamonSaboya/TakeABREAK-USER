package br.ufpe.cin.if678.gui.panel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.ufpe.cin.if678.gui.ButtonTextKeyListener;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import br.ufpe.cin.if678.threads.ThreadManager;

@SuppressWarnings("serial")
public class StartupPanel extends JPanel {

	private JTextField addressField;

	/**
	 * Cria o painel de entrada.
	 */
	public StartupPanel(TakeABREAK frame) {
		super();

		// Seta as características do painel
		setBounds(0, 0, 1200, 700);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);

		// Label contendo a parte menor do slogan
		JLabel smallText = new JLabel();
		smallText.setForeground(Color.BLACK);
		smallText.setFont(new Font("Tahoma", Font.PLAIN, 32));
		smallText.setText("Take a");
		smallText.setVerticalAlignment(JLabel.BOTTOM);
		smallText.setBounds(65, 220, 200, 60);

		// Label contendo a parte grande do slogan
		JLabel bigText = new JLabel();
		bigText.setForeground(Color.BLACK);
		bigText.setFont(new Font("Tahoma", Font.PLAIN, 220));
		bigText.setText("BREAK;");
		bigText.setBounds(50, 200, 750, 300);

		// Logo
		JLabel logoLabel = new JLabel(new ImageIcon("dependencies\\logo.jpg"));
		logoLabel.setBounds(800, 175, 350, 350);

		JLabel addressLabel = new JLabel("Digite o IP do servidor:");
		addressLabel.setBounds(450, 50, 300, 20);
		addressLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JButton connectButton = new JButton("Conectar");
		connectButton.setName("connectButton");

		addressField = new JTextField();
		addressField.setBounds(450, 75, 145, 20);
		addressField.setColumns(10);
		addressField.setText("localhost");
		addressField.setForeground(Color.GRAY);
		addressField.grabFocus();
		addressField.setCaretPosition(0);
		addressField.addKeyListener(new ButtonTextKeyListener(connectButton, "localhost"));

		ThreadManager thread = new ThreadManager(frame, this, addressField, 0);
		connectButton.setBounds(605, 75, 145, 20);
		connectButton.addActionListener(thread);

		// Insere todos os elementos no painel
		add(smallText);
		add(bigText);
		add(logoLabel);

		add(addressLabel);
		add(addressField);
		add(connectButton);
	}

	public void resetField() {
		addressField.setForeground(Color.GRAY);
		addressField.grabFocus();
		addressField.setText("localhost");
		addressField.setCaretPosition(0);
	}

}
