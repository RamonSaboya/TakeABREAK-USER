package br.ufpe.cin.if678.threads;

import br.ufpe.cin.if678.UserController;

public class ReconnectionThread extends Thread {

	private UserController controller;

	public ReconnectionThread() {
		this.controller = UserController.getInstance();
	}

	@Override
	public void run() {
		controller.initialize(controller.getIP());
	}

}
