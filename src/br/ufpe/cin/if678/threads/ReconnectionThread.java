package br.ufpe.cin.if678.threads;

import java.io.IOException;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.communication.UserAction;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;

public class ReconnectionThread extends Thread {

	private UserController controller;

	public ReconnectionThread() {
		this.controller = UserController.getInstance();
	}

	@Override
	public void run() {
		while (true) {
			try {
				controller.initialize(controller.getIP());

				controller.getListener().waitReconnection(this);
				try {
					synchronized (this) {
						controller.getWriter().queueAction(UserAction.RECONNECT, controller.getUser());
						wait();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				TakeABREAK.getInstance().reconnected();
				break;
			} catch (IOException e) {
				try {
					sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
