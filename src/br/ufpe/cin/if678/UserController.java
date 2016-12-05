package br.ufpe.cin.if678;

import static br.ufpe.cin.if678.communication.UserAction.REQUEST_USER_LIST;
import static br.ufpe.cin.if678.communication.UserAction.SEND_USERNAME;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import br.ufpe.cin.if678.business.Group;
import br.ufpe.cin.if678.communication.Listener;
import br.ufpe.cin.if678.communication.Reader;
import br.ufpe.cin.if678.communication.ServerAction;
import br.ufpe.cin.if678.communication.Writer;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import br.ufpe.cin.if678.util.Pair;
import br.ufpe.cin.if678.util.Tuple;

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

	private String IP;

	// Threads de leitura e escrita
	private Pair<Reader, Thread> readerPair;
	private Pair<Writer, Thread> writerPair;

	private Listener listener;

	private InetSocketAddress user;

	private HashMap<InetSocketAddress, String> addressToName;
	private HashMap<String, InetSocketAddress> nameToAddress;

	private HashMap<String, Group> groups;

	private UserController() {
		this.listener = new Listener(this);

		this.addressToName = new HashMap<InetSocketAddress, String>();
		this.nameToAddress = new HashMap<String, InetSocketAddress>();

		this.groups = new HashMap<String, Group>();
	}

	public boolean initialize(String IP) {
		this.IP = IP;

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

			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void setUsername(String username) {
		getWriter().queueAction(SEND_USERNAME, username);

		addressToName.put(user, username);
		nameToAddress.put(username, user);

		getWriter().queueAction(REQUEST_USER_LIST, null);
	}

	public String getIP() {
		return IP;
	}

	public Reader getReader() {
		return readerPair.getFirst();
	}

	public Writer getWriter() {
		return writerPair.getFirst();
	}

	public InetSocketAddress getUser() {
		return user;
	}

	public HashMap<InetSocketAddress, String> getAddressToName() {
		return addressToName;
	}

	public String getName(InetSocketAddress address) {
		return addressToName.get(address);
	}

	public HashMap<String, InetSocketAddress> getNameToAddress() {
		return nameToAddress;
	}

	public InetSocketAddress getAddress(String name) {
		return nameToAddress.get(name);
	}

	public HashMap<String, Group> getGroups() {
		return groups;
	}

	public Group getGroup(String name) {
		return groups.get(name);
	}

	@SuppressWarnings("unchecked")
	public void callEvent(ServerAction action, Object object) {
		switch (action) {
		case SEND_USERS_LIST:
			listener.onUserListUpdate((HashMap<InetSocketAddress, String>) object);
			break;
		case SEND_USER_CONNECTED:
			listener.onUserConnect((Pair<InetSocketAddress, String>) object);
			break;
		case SEND_GROUP:
			listener.onGroupReceive((Group) object);
			break;
		case GROUP_ADD_MEMBER:
			listener.onGroupAddMember((Pair<String, InetSocketAddress>) object);
			break;
		case GROUP_MESSAGE:
			listener.onGroupMessage((Tuple<String, InetSocketAddress, Object>) object);
			break;
		}
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

		TakeABREAK.getInstance().setDisconnected();
	}

	public void tryReconnect() {

	}

}
