package br.ufpe.cin.if678.transport;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import br.ufpe.cin.if678.Encryption;

public class Test {
	public static void main(String[] args) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException{
		File file = new File("teste.avi");
		Encryption.encrypt(12345, file);
		
	}
}
