package br.ufpe.cin.if678;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufpe.cin.if678.communication.Reader;
import br.ufpe.cin.if678.communication.Writer;
import br.ufpe.cin.if678.util.Pair;

/**
 * Controla as threads de leitura e escrita do socket de conexão com o servidor
 * 
 * @author Ramon
 */
public class UserController {

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

	public UserController() {
		try {
			// Cria o socket no endereço do servidor
			Socket socket = new Socket("192.168.15.104", 6666);

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
