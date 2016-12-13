package br.ufpe.cin.if678.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JTextPane;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;

public class RTTThread extends Thread {

	private static long estimatedRTT = 0;

	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;

	public RTTThread() {
		try {
			socket = new Socket(UserController.getInstance().getIP(), 4200);

			this.inputStream = socket.getInputStream();
			this.outputStream = socket.getOutputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				outputStream.write(1);

				long ping = System.nanoTime();

				inputStream.read();

				long RTT = System.nanoTime() - ping;
				estimatedRTT = (long) ((estimatedRTT * 0.875D) + (0.125D * RTT));

				List<JTextPane> RTTPanes = TakeABREAK.getInstance().getChatPanel().getRTTPanes();
				for (JTextPane pane : RTTPanes) {
					pane.setText(String.format("RTT: %.02fμs", (estimatedRTT / 1000.0)));
				}

				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static long getEstimatedRTT() {
		return estimatedRTT;
	}

}
