package br.ufpe.cin.if678.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import br.ufpe.cin.if678.util.Pair;

/**
 * Gerenciador de escrita de um socket
 * 
 * @author Ramon
 */
public class Writer implements Runnable {

	private ObjectOutputStream OOS; // Interface de saída de objetos

	/*
	 * Fila bloqueante de ações a serem executadas
	 * A intenção de utilizar uma fila bloqueante
	 * é evitar que as threads passem por espera
	 * ociosa
	 */
	private BlockingQueue<Pair<UserAction, Object>> queue;

	/*
	 * Boolean para controlar execução da thread
	 * Utilização do modificador volatile para
	 * garantir que, no acesso, o valor seja
	 * buscado diretamente da memória. Necessário,
	 * pois será modificado a partir de chamada
	 * de outra thread
	 */
	private volatile boolean run;

	/**
	 * Construtor do gerenciador de escrita
	 * 
	 * @param socket instância do socket
	 */
	public Writer(Socket socket) {
		// Tenta iniciar a interface de saída de objetos
		try {
			this.OOS = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Inicia a fila bloqueante com uma estrutura de LinkedLis
		this.queue = new LinkedBlockingQueue<Pair<UserAction, Object>>();

		// Inicialmente o thread irá rodar normalmente
		this.run = true;
	}

	/**
	 * Método que será executado pela thread
	 */
	@Override
	public void run() {
		while (true) {
			try {
				/*
				 * Tenta pegar a próxima ação da fila e,
				 * caso a mesma esteja vazia, aguarda 100
				 * milisegundos por uma possível inserção
				 */
				Pair<UserAction, Object> pair = queue.poll(100, TimeUnit.MILLISECONDS);

				/*
				 * Caso, mesmo após a espera, não tenha
				 * ação disponível, é necessário verificar
				 * se a thread recebeu sinal de parada
				 */
				if (pair == null) {
					/*
					 * Caso tenha recebido sinal de parada
					 * e a fila esteja vazia, encerra a
					 * execução da thread
					 */
					if (!run && queue.isEmpty()) {
						return;
					}

					continue;
				}

				// Pega a ação e seu objeto
				UserAction action = pair.getFirst();
				Object object = pair.getSecond();

				// Manda os objetos pela stream
				OOS.writeObject(action);
				OOS.writeObject(object);
				OOS.flush();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Tenta inserir uma ação na fila de execução
	 * 
	 * @param action ID da ação que será executada
	 * @param object objeto referente a ação
	 */
	public void queueAction(UserAction action, Object object) {
		try {
			queue.put(new Pair<UserAction, Object>(action, object));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Marca o thread para encerrar a execução
	public void forceStop() {
		run = false;
	}

}
