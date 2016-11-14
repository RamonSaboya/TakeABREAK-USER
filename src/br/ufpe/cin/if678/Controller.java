package br.ufpe.cin.if678;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufpe.cin.if678.communication.Action;
import br.ufpe.cin.if678.communication.ReadThread;
import br.ufpe.cin.if678.communication.WriteThread;

public class Controller {

	private static Controller INSTANCE;

	public static Controller getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Controller();
		}
		return INSTANCE;
	}

	private ReadThread readThread;
	private WriteThread writeThread;

	public Controller() {
		try {
			Socket socket = new Socket("192.168.15.104", 6666);

			this.readThread = new ReadThread(socket);
			this.writeThread = new WriteThread(socket);

			new Thread(writeThread).start();
			new Thread(readThread).start();
			
			writeThread.queueAction(Action.LOGIN, "Macarena");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
