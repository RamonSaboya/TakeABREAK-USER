package test;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import br.ufpe.cin.if678.Encryption;

public class Client {

	public static void main(String[] args) {
		try {
			byte[] bytes = Encryption.encryptMessage(44, "Olá");
			for (int i = 0; i < bytes.length; i++)
				System.out.print(new Integer(bytes[i]) + " ");
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException
				| InvalidAlgorithmParameterException | IOException e) {
			e.printStackTrace();
		}
	}

}