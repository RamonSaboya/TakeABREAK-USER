package test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Runnable {

	public static void main(String[] args) {
		Client client = new Client(new Scanner(System.in), "192.168.15.104", 6666);
		client.run();
	}

	private Scanner scanner;

	private String ip;
	private int port;

	private Socket socket;

	public Client(Scanner scanner, String ip, int port) {
		this.scanner = scanner;

		this.ip = ip;
		this.port = port;

		try {
			this.socket = new Socket("192.168.15.104", 6666);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		new Thread(new ClientListener(socket)).start();
	}

	public void run() {
		while (true) {
			try {
				System.out.println("[CLIENTE] Pronto para enviar mensagens!");
				System.out.print("[CLIENTE] Digite seu nickname: ");
				String nickname = scanner.next();

				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				output.writeUTF(nickname);
				output.flush();
				scanner.nextLine();

				System.out.println("[CLIENTE] Digite suas mensagens: ");
				String message;
				while (!(message = scanner.nextLine()).equals("EXIT;")) {
					output.writeUTF(message);
					output.flush();
				}

				output.writeUTF(message);
				output.flush();

				socket.close();
				this.socket = null;
				System.out.println("[CLIENTE] Conex�o CU encerrada!");

				System.out.println("Digite o ip e a porta do cliente, separados por espa�o para iniciar a conex�o");
				this.ip = scanner.next();
				this.port = scanner.nextInt();

				run();

				scanner.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}