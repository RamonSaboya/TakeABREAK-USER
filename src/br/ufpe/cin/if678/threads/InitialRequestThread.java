package br.ufpe.cin.if678.threads;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.communication.UserAction;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import br.ufpe.cin.if678.gui.panel.AuthenticationPanel;

public class InitialRequestThread extends Thread implements ActionListener {

	private TakeABREAK frame;
	private UserController controller;

	private AuthenticationPanel panel;

	private JTextField usernameField;
	private int ID;

	public InitialRequestThread(TakeABREAK frame, AuthenticationPanel panel, JTextField usernameField) {
		this.frame = frame;
		this.controller = UserController.getInstance();

		this.panel = panel;

		this.usernameField = usernameField;
		this.ID = -1;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	@Override
	public void run() {
		String username = usernameField.getText();

		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]{3,15}");
		Matcher matcher = pattern.matcher(username);
		if (!matcher.matches()) {
			System.out.println("Nome de usuário não atende aos requisitos");
			return;
		}

		controller.getWriter().queueAction(UserAction.REQUEST_USERNAME, username);

		controller.getListener().waitUsername(this);
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try {
			synchronized (this) {
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (ID == -1) {
			System.out.println("Nome de usuário já usado");
			panel.resetField();
			frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			return;
		}
		controller.assignUsername(ID, username);

		controller.getWriter().queueAction(UserAction.REQUEST_USER_LIST, null);

		try {
			synchronized (this) {
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		frame.initializePanels();

		frame.clearFrame();
		frame.addPanel(frame.getSidebarPanel());
		frame.addPanel(frame.getUserListPanel());
		frame.addPanel(frame.getChatPanel());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (isAlive()) {
		}

		start();
	}

}
