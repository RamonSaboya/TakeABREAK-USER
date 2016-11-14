package br.ufpe.cin.if678.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import br.ufpe.cin.if678.util.Pair;

public class Writer implements Runnable {

	private ObjectOutputStream OOS;

	private BlockingQueue<Pair<Action, Object>> queue;

	private volatile boolean run;

	public Writer(Socket socket) {
		try {
			this.OOS = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.queue = new LinkedBlockingQueue<Pair<Action, Object>>();

		this.run = true;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Pair<Action, Object> pair = queue.poll(100, TimeUnit.MILLISECONDS);

				if (pair == null) {
					if (!run && queue.isEmpty()) {
						return;
					}

					continue;
				}

				Action action = pair.getFirst();
				Object object = pair.getSecond();

				OOS.writeObject(action);
				OOS.writeObject(object);
				OOS.flush();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void queueAction(Action action, Object object) {
		try {
			queue.put(new Pair<Action, Object>(action, object));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void forceStop() {
		run = false;
	}

}
