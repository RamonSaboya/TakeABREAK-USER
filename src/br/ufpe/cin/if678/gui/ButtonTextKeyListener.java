package br.ufpe.cin.if678.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JTextField;

public class ButtonTextKeyListener implements KeyListener {

	private JButton button;
	private String defaultText;

	public ButtonTextKeyListener(JButton button, String defaultText) {
		this.button = button;
		this.defaultText = defaultText;
	}

	public void keyPressed(KeyEvent event) {
		JTextField field = (JTextField) event.getComponent();

		if (event.getKeyChar() == KeyEvent.VK_ENTER) {
			button.doClick();
			return;
		}

		if (field.getForeground() == Color.GRAY) {
			field.setText("");
			field.setForeground(Color.BLACK);
		}
	}

	public void keyReleased(KeyEvent event) {
		JTextField field = (JTextField) event.getComponent();

		if (event.getKeyChar() == KeyEvent.VK_BACK_SPACE && field.getText().equals("")) {
			field.setForeground(Color.GRAY);
			field.setText(defaultText);
			field.setCaretPosition(0);
		}
	}

	public void keyTyped(KeyEvent event) {
	}

}
