package br.ufpe.cin.if678.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import br.ufpe.cin.if678.util.Pair;

public class WriteThread implements Runnable {

	private ObjectOutputStream OOS;

	private BlockingQueue<Pair<Action, Object>> queue;

	public WriteThread(Socket socket) {
		try {
			this.OOS = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.queue = new LinkedBlockingQueue<Pair<Action, Object>>();
	}

	@Override
	public void run() {
		try {
			while (true) {
				Pair<Action, Object> pair = queue.take();

				Action action = pair.getFirst();
				Object object = pair.getSecond();

				OOS.writeObject(action);
				OOS.writeObject(object);
				OOS.flush();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void queueAction(Action action, Object object) {
		try {
			queue.put(new Pair<Action, Object>(action, object));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
