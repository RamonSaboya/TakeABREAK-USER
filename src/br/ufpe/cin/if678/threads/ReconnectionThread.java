package br.ufpe.cin.if678.threads;

import java.io.IOException;

import br.ufpe.cin.if678.UserController;
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
