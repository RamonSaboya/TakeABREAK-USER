package br.ufpe.cin.if678.gui.panel;

import java.awt.Color;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import br.ufpe.cin.if678.gui.frame.TakeABREAK;

@SuppressWarnings("serial")
public class HomePanel extends JPanel {

	private Box leftBorder;

	/**
	 * Cria o painel da página HOME.
	 */
	public HomePanel() {
		super();

		// Seta as características do painel
		setBounds(240, 0, 960, 550);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);

		// Separador da esquerda
		int borderThickness = TakeABREAK.BORDER_THICKNESS;
		leftBorder = Box.createHorizontalBox();
		leftBorder.setBorder(new LineBorder(new Color(0, 0, 0), borderThickness));
		leftBorder.setBounds(0, -borderThickness, this.getWidth() + borderThickness, this.getHeight() + (borderThickness * 2));

		// Insere todos os elementos no painel
		add(leftBorder);
	}
}
