package br.ufpe.cin.if678.gui.panel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import br.ufpe.cin.if678.gui.frame.TakeABREAK;

@SuppressWarnings("serial")
public class UserPanel extends JPanel {

	private JLabel loggedInAsLabel;
	private JLabel userPicLabel;
	private JButton profileButton;

	private Box rightBorder;

	/**
	 * Cria o painel lateral do usuário.
	 */
	public UserPanel() {
		super();

		// Seta as características do painel
		setBounds(0, 0, 240, 550);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);

		// Label contendo o login do usuário
		loggedInAsLabel = new JLabel("LOGGED IN AS {USER}");
		loggedInAsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loggedInAsLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		loggedInAsLabel.setBounds(20, 100, 200, 25);

		// Label contendo a imagem de perfil do usuário
		userPicLabel = new JLabel(new ImageIcon("dependencies\\128.png"));
		userPicLabel.setBounds(56, 211, 128, 128);

		// Botão para a página de editar perfil
		profileButton = new JButton("PROFILE");
		profileButton.setBounds(56, 425, 128, 25);

		// Separador da direita
		int borderThickness = TakeABREAK.BORDER_THICKNESS;
		rightBorder = Box.createHorizontalBox();
		rightBorder.setBorder(new LineBorder(new Color(0, 0, 0), borderThickness));
		rightBorder.setBounds(-borderThickness, -borderThickness, this.getWidth() + borderThickness, this.getHeight() + (borderThickness * 2));

		// Insere todos os elementos no painel
		add(loggedInAsLabel);
		add(userPicLabel);
		add(profileButton);
		add(rightBorder);
	}
}
