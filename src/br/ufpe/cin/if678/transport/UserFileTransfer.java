package br.ufpe.cin.if678.transport;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufpe.cin.if678.UserController;

public class UserFileTransfer {

	@SuppressWarnings({ "static-access", "unused", "resource" })
	public static void sendFileToServer(byte[] encryptedFile) throws UnknownHostException, IOException{
		Socket socket = new Socket(UserController.getInstance().getIP(), UserController.getInstance().MAIN_PORT);
		BufferedOutputStream bf = new BufferedOutputStream(socket.getOutputStream());
		
		bf.write(encryptedFile);
		bf.flush();
	}
}
