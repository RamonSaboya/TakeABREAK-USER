package br.ufpe.cin.if678.transport;

import java.io.IOException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import br.ufpe.cin.if678.crypt.Methods;

public class MessagesClient implements Runnable{

	private Socket socket;
	private Scanner in;
	private byte[] encrypted;
	
	
	public MessagesClient(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		in = new Scanner(System.in);
		boolean stop = true;
		while(stop){
			String message = in.nextLine();
			if(message.equals("/exit")){
				stop = false;
			} else {
					try {
						encrypted = Methods.encrypt(message);
						
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException
							| InvalidAlgorithmParameterException | IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
}
