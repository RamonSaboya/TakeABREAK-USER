package br.ufpe.cin.if678.communication;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.business.Group;
import br.ufpe.cin.if678.util.Pair;

public class Listener {

	private UserController controller;

	public Listener(UserController controller) {
		this.controller = controller;
	}

	public void onUserListUpdate(HashMap<InetSocketAddress, String> data) {
		controller.getAddressToName().clear();
		controller.getNameToAddress().clear();

		for (Map.Entry<InetSocketAddress, String> entry : data.entrySet()) {
			controller.getAddressToName().put(entry.getKey(), entry.getValue());
			controller.getNameToAddress().put(entry.getValue(), entry.getKey());
		}

		System.out.println("Lista de clientes recebida com sucesso");
	}

	public void onUserConnect(Pair<InetSocketAddress, String> data) {
		controller.getAddressToName().put(data.getFirst(), data.getSecond());
		controller.getNameToAddress().put(data.getSecond(), data.getFirst());
	}

	public void onGroupReceive(Group object) {
	}

}
