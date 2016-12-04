package br.ufpe.cin.if678;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import br.ufpe.cin.if678.communication.UserAction;
import br.ufpe.cin.if678.communication.Writer;
import br.ufpe.cin.if678.crypt.Methods;

public class UserListener {

	private UserController controller;

	public UserListener() {
		this.controller = UserController.getInstance();
	}

	public void onUserListRequest() {
		Writer writer = controller.getWriter();

		writer.queueAction(UserAction.REQUEST_USER_LIST, null);
	}
	
	public void sendMessege(String messege){
		try {
			
			Writer writer = controller.getWriter();
			
			byte[] encryptedMessege = Methods.encrypt(messege);
			
			writer.queueAction(UserAction.SEND_MESSAGE, encryptedMessege);
			
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
