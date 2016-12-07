package br.ufpe.cin.if678.threads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import br.ufpe.cin.if678.gui.panel.AuthenticationPanel;
import br.ufpe.cin.if678.gui.panel.StartupPanel;

public class ThreadManager implements ActionListener {

	private TakeABREAK frame;
	private JPanel panel;
	private JTextField textField;
	private int ID;

	public ThreadManager(TakeABREAK frame, JPanel panel, JTextField textField, int ID) {
		this.frame = frame;
		this.panel = panel;
		this.textField = textField;
		this.ID = ID;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton button = (JButton) event.getSource();

		switch (button.getName()) {
		case "connectButton":
			new ServerConnectionThread(frame, (StartupPanel) panel, textField).start();
			break;
		case "loginButton":
			new InitialRequestThread(frame, (AuthenticationPanel) panel, textField).start();
			break;
		case "overlayButton":
			new GroupCreationThread(ID).start();
			break;
		}
	}

}
