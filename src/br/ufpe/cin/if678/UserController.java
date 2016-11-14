package br.ufpe.cin.if678;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufpe.cin.if678.communication.Action;
import br.ufpe.cin.if678.communication.Reader;
import br.ufpe.cin.if678.communication.Writer;
import br.ufpe.cin.if678.util.Pair;

public class UserController {

	private static UserController INSTANCE;

	public static UserController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserController();
		}
		return INSTANCE;
	}

	private Pair<Reader, Thread> readerPair;
	private Pair<Writer, Thread> writerPair;

	public UserController() {
		try {
			Socket socket = new Socket("192.168.15.104", 6666);

			Reader reader = new Reader(socket);
			Writer writer = new Writer(socket);

			Thread readerThread = new Thread(reader);
			Thread writerThread = new Thread(writer);

			this.readerPair = new Pair<Reader, Thread>(reader, readerThread);
			this.writerPair = new Pair<Writer, Thread>(writer, writerThread);
			
			readerThread.start();
			writerThread.start();

			writer.queueAction(Action.LOGIN, "Macarena");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void serverUnnavailble() {
		readerPair.getSecond().interrupt();
		writerPair.getFirst().forceStop();
		System.out.println("Server encerrou!");
	}

}
