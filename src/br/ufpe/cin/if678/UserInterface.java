package br.ufpe.cin.if678;

import static br.ufpe.cin.if678.communication.UserAction.REQUEST_USER_LIST;
import static br.ufpe.cin.if678.communication.UserAction.SEND_USERNAME;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import br.ufpe.cin.if678.business.Group;
import br.ufpe.cin.if678.communication.UserAction;
import br.ufpe.cin.if678.util.Pair;

public class UserInterface {

	public static void main(String[] args) {
		new UserInterface();
	}

	private UserController controller;
	private Scanner in;

	public UserInterface() {
		this.controller = UserController.getInstance();
		this.in = new Scanner(System.in);

		initialize();
	}

	private void initialize() {
		System.out.print("Digite o IP do servidor: ");
		String IP = "192.168.15.104";
		System.out.println();

		System.out.println("Tentando inicializar conexão");
		controller.initialize(IP);
		System.out.println("Conexão bem sucedida");
		System.out.println();

		System.out.print("Digite seu nome de usuário: ");
		String username = in.next();
		controller.getWriter().queueAction(SEND_USERNAME, username);
		System.out.println("Username escolhido com sucesso (" + username + ")");

		System.out.println("Requisitando lista de usuários online...");
		controller.getWriter().queueAction(REQUEST_USER_LIST, null);

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
	}

	public void displayUserList() {
		HashMap<InetSocketAddress, String> userList = controller.getAddressToName();

		int c = 1;
		System.out.println("Lista de usuários online:");
		for (Map.Entry<InetSocketAddress, String> entry : userList.entrySet()) {
			InetSocketAddress address = entry.getKey();
			String username = entry.getValue();

			System.out.println(c++ + " - " + username + " (" + address.getAddress().getHostAddress() + ":" + address.getPort() + ")");
		}

		System.out.print("Digite o nick de uma pessoa para conversar ou /exit para voltar ao menu: ");
		String name = in.next();
		System.out.println();

		if (name.equals("/exit")) {
			return;
		}

		InetSocketAddress user = controller.getUser();
		String groupName = controller.getName(user) + ":!:" + name;

		System.out.println("Requisitando grupo pra o Servidor...");
		controller.getWriter().queueAction(UserAction.GROUP_CREATE, new Pair<InetSocketAddress, String>(user, groupName));
		System.out.println("Requisição enviada");

		System.out.println("Aguardando recebimento do grupo");
		Group group;
		while ((group = controller.getGroup(groupName)) == null);
		System.out.println("Grupo recebido");

		System.out.println("Adcionando usuário ao grupo");
		controller.getWriter().queueAction(UserAction.GROUP_ADD_MEMBER, new Pair<String, InetSocketAddress>(group.getName(), controller.getAddress(name)));

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();

		System.out.println("Você está em uma conversa com: " + name);
		System.out.println("Digite suas mensagens e /exit para sair da conversa:");

		String message = in.nextLine();
		while (!(message = in.nextLine()).equals("/exit")) {
			controller.getWriter().queueAction(UserAction.SEND_MESSAGE, new Pair<String, String>(group.getName(), message));
		}
		
		System.out.println("Você encerrou a conversa com: " + name);
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		return;
	}

}
