package br.ufpe.cin.if678.threads;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import br.ufpe.cin.if678.gui.panel.StartupPanel;

public class ServerConnectionThread extends Thread implements ActionListener {

	private TakeABREAK frame;
	private UserController controller;

	private StartupPanel panel;

	private String IP;

	public ServerConnectionThread(TakeABREAK frame, StartupPanel panel, String IP) {
		this.frame = frame;
		this.controller = UserController.getInstance();

		this.panel = panel;

		this.IP = IP;
	}

	@Override
	public synchronized void run() {
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try {
			controller.initialize(IP);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Não foi possível conectar com o servidor");
			panel.resetField();
			frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			return;
		}
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		frame.clearFrame();
		frame.addPanel(frame.getAuthenticationPanel());
		frame.getAuthenticationPanel().grabFocus();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		start();
	}

}
