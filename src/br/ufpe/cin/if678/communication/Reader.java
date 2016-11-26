package br.ufpe.cin.if678.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.util.Pair;

/**
 * Gerenciador de leitura de um socket
 * 
 * @author Ramon
 */
public class Reader implements Runnable {

	private UserController controller;

	private Socket socket;

	private ObjectInputStream OIS;

	/**
	 * Construtor do gerenciador de leitura
	 * 
	 * @param socket instância do socket
	 */
	public Reader(Socket socket) {
		this.controller = UserController.getInstance();

		this.socket = socket;

		this.OIS = null;
	}

	/**
	 * Método que será executado pela thread
	 */
	@Override
	public void run() {
		while (true) {
			try {
				if (OIS == null) {
					OIS = new ObjectInputStream(socket.getInputStream());
				}

				// Lê a ação e o objecto que esteja relacionado a mesma
				ServerAction action = (ServerAction) OIS.readObject();
				Object object = OIS.readObject();

				if (action == ServerAction.SEND_USER_LIST) {
					@SuppressWarnings("unchecked")
					HashMap<InetSocketAddress, String> userList = (HashMap<InetSocketAddress, String>) object;

					controller.updateUserList(userList);
				} else if (action == ServerAction.SEND_USER_CONNECTED) {
					Pair<InetSocketAddress, String> data = (Pair<InetSocketAddress, String>) object;

					controller.userConnected(data); // TODO colocar no listener
				}
			} catch (SocketException e) {
				// Essa exeção será chamada quando o servidor não conseguir conexão com o servidor
				UserController.getInstance().serverUnnavailble(); // Avisa ao controlador que o cliente desconectou
				return; // Encerra a execução da thread
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
