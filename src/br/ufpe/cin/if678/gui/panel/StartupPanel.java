package br.ufpe.cin.if678.gui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.gui.BreakButton;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;

@SuppressWarnings("serial")
public class StartupPanel extends JPanel {

	private JLabel smallText;
	private JLabel bigText;
	private JLabel logoLabel;

	private JLabel addressLabel;
	private JTextField addressField;
	private BreakButton connectButton;

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
		smallText = new JLabel();
		smallText.setForeground(Color.BLACK);
		smallText.setFont(new Font("Tahoma", Font.PLAIN, 32));
		smallText.setText("Take a");
		smallText.setVerticalAlignment(JLabel.BOTTOM);
		smallText.setBounds(65, 220, 200, 60);

		// Label contendo a parte grande do slogan
		bigText = new JLabel();
		bigText.setForeground(Color.BLACK);
		bigText.setFont(new Font("Tahoma", Font.PLAIN, 220));
		bigText.setText("BREAK;");
		bigText.setBounds(50, 200, 750, 300);

		// Logo
		logoLabel = new JLabel(new ImageIcon("dependencies\\logo.jpg"));
		logoLabel.setBounds(800, 175, 350, 350);

		addressLabel = new JLabel("DIgite o IP do servidor:");
		addressLabel.setBounds(566, 63, 120, 14);

		addressField = new JTextField();
		addressField.setBounds(538, 88, 167, 20);
		addressField.setColumns(10);
		addressField.setText("25.0.85.228");

		connectButton = new BreakButton("Conectar", 576, 119, 89, 23);
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (UserController.getInstance().initialize(addressField.getText())) {
					frame.clearFrame();
					frame.addPanel(frame.getAuthenticationPanel());
				} else {
					addressField.setText("SE FUDEU");
				}
			}
		});

		// Insere todos os elementos no painel
		add(smallText);
		add(bigText);
		add(logoLabel);

		add(addressLabel);
		add(addressField);
		add(connectButton);
	}
}
