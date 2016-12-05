package br.ufpe.cin.if678.gui.panel;

import java.awt.Color;

import javax.swing.JPanel;

import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

@SuppressWarnings("serial")
public class DisconnectedPanel extends JPanel {

	/**
	 * Cria o painel de entrada.
	 */
	public DisconnectedPanel(TakeABREAK frame) {
		super();

		// Seta as características do painel
		setBounds(0, 0, 1200, 700);
		setBackground(Color.GRAY);
		setLayout(null);
		
		JLabel messageLabel = new JLabel("Perdeu conexão com o servidor, tentando reconectar");
		messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setBounds(275, 241, 395, 178);
		add(messageLabel);
	}
}
