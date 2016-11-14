package test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientListener implements Runnable {

	private Socket socket;

	public ClientListener(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			
			String message;
			while(!(message = input.readUTF()).equals("EXIT;")) {
				System.out.println(message);
			}
			
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
