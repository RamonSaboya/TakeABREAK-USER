package br.ufpe.cin.if678;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserMain {

	private static UserController controller;

	public static void main(String[] args) {
		controller = UserController.getInstance();

		Scanner in = new Scanner(System.in);

		System.out.print("Digite o IP do servidor: ");
		String IP = "192.168.15.104";
		System.out.println();

		System.out.println("Tentando inicializar conexão");
		controller.initialize(IP);
		System.out.println("Conexão bem sucedida");
		System.out.println();

		System.out.print("Digite seu nome de usuário: ");
		String username = in.next();
		controller.setUsername(username);
		System.out.println("Username escolhido com sucesso (" + username + ")");

		System.out.println("Requisitando lista de usuários online...");
		controller.downloadUserList();

		outside: while (true) {
			System.out.println("Digite a opção que deseja:");
			System.out.println("1. Ver lista de usuários online");
			System.out.println("2. Sair");
			int command = in.nextInt();

			switch (command) {
			case 1:
				displayUserList();
				break;
			case 2:
				break outside;
			}
		}

		in.close();
	}

	public static void displayUserList() {
		HashMap<InetSocketAddress, String> userList = controller.getUserList();

		int c = 1;
		System.out.println("Lista de usuários online:");
		for (Map.Entry<InetSocketAddress, String> entry : userList.entrySet()) {
			InetSocketAddress address = entry.getKey();
			String username = entry.getValue();

			System.out.println(c++ + " - " + username + " (" + address.getAddress().getHostAddress() + ":" + address.getPort() + ")");
		}
	}

}
