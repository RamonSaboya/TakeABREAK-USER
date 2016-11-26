package br.ufpe.cin.if678;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import br.ufpe.cin.if678.business.Group;
import br.ufpe.cin.if678.communication.Reader;
import br.ufpe.cin.if678.communication.UserAction;
import br.ufpe.cin.if678.communication.Writer;
import br.ufpe.cin.if678.util.Pair;

/**
 * Controla as threads de leitura e escrita do socket de conexão com o servidor
 * 
 * @author Ramon
 */
public class UserController {

	public static final int MAIN_PORT = 6666;

	// Como estamos usando uma classe Singleton, precisamos da variável para
	// salvar a instância
	private static UserController INSTANCE;

	/**
	 * Retorna a instância inicianda da classe
	 * 
	 * @return instância da classe
	 */
	public static UserController getInstance() {
		// Caso seja o primeiro uso, é necessário iniciar a instância
		if (INSTANCE == null) {
			INSTANCE = new UserController();
		}

		return INSTANCE;
	}

	// Threads de leitura e escrita
	private Pair<Reader, Thread> readerPair;
	private Pair<Writer, Thread> writerPair;

	private InetSocketAddress user;

	private HashMap<InetSocketAddress, String> userList;

	private HashMap<String, Group> groups;

	private UserController() {
		this.userList = new HashMap<InetSocketAddress, String>();

		this.groups = new HashMap<String, Group>();
	}

	public void initialize(String IP) {
		try {
			// Cria o socket no endereço do servidor
			Socket socket = new Socket(IP, MAIN_PORT);

			user = (InetSocketAddress) socket.getLocalSocketAddress();

			// Inicia os gerenciadores de leitura e escrita
			Reader reader = new Reader(socket);
			Writer writer = new Writer(socket);

			// Inicia as instâncias das threads de leitura e escrita
			Thread readerThread = new Thread(reader);
			Thread writerThread = new Thread(writer);

			// Armazena as instancias dos gerenciadores de leitura e escrita, e
			// suas threads
			this.readerPair = new Pair<Reader, Thread>(reader, readerThread);
			this.writerPair = new Pair<Writer, Thread>(writer, writerThread);

			// Inicia a exeucão das threads de leitura e escrita
			readerThread.start();
			writerThread.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setUsername(String username) {
		writerPair.getFirst().queueAction(UserAction.SEND_USERNAME, username);
	}

	public void downloadUserList() {
		writerPair.getFirst().queueAction(UserAction.REQUEST_USER_LIST, null);
	}

	public void updateUserList(HashMap<InetSocketAddress, String> userList) {
		this.userList = userList;

		System.out.println("Lista de clientes recebida com sucesso");
	}

	public HashMap<InetSocketAddress, String> getUserList() {
		return userList;
	}

	public void userConnected(Pair<InetSocketAddress, String> data) {
		userList.put(data.getFirst(), data.getSecond());
	}

	public void requestGroup(String name) {
		name = userList.get(user) + ":!:" + name;

		writerPair.getFirst().queueAction(UserAction.CREATE_GROUP, new Pair<InetSocketAddress, String>(user, name));
	}

	public void loadGroup(Group group) {
		groups.put(group.getName(), group);
	}

	public Group getGroup(String name) {
		name = userList.get(user) + ":!:" + name;

		while (!groups.containsKey(name));

		return groups.get(name);
	}

	/**
	 * Avisa à thread de leitura que a conexão do socket foi encerrada
	 */
	public void serverUnnavailble() {
		readerPair.getSecond().interrupt(); // Interrompe a thread de leitura
											 // (apenas segurança, thread já deve
											 // estar parada nesse ponto)
		writerPair.getFirst().forceStop();  // Força o encerramento da thread de
											  // escrita
	}

	public Reader getReader() {
		return readerPair.getFirst();
	}

	public Writer getWriter() {
		return writerPair.getFirst();
	}

}
