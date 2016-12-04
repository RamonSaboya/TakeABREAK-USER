package br.ufpe.cin.if678.gui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class BreakButton extends JButton {

	public BreakButton(String text, int x, int y, int width, int height) {
		super(text);

		setFont(new Font("Tahoma", Font.PLAIN, 14));
		setHorizontalAlignment(SwingConstants.CENTER);

		setBounds(x, y, width, height);
	}

}