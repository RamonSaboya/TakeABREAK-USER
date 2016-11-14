package br.ufpe.cin.if678.crypt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * 
 * @author joao
 *
 * @param <T> T pode ser uma String ou um arquivo
 */
public class MessageEncryption {

	private byte[] byteArray;
	private SecretKey key;
	private byte[] IV;

	public MessageEncryption(byte[] byteArray, SecretKey key, byte[] IV) {
		// Gera uma String aleatória
		this.key = key;
		// Encripta um objeto (file ou string)
		this.byteArray = byteArray;
		this.IV = IV;
	}

	public byte[] getByteArray() {
		return byteArray;
	}

	/**
	 * Getter da Cipher Key
	 * 
	 * @return chave de encriptação
	 */
	public SecretKey getKey() {
		return key;
	}

	/**
	 * Getter do IV
	 * 
	 * @return IV para decriptaçao
	 */
	public byte[] getIV() {
		return IV;
	}

	public static MessageEncryption encrypt(String message) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		SecureRandom secureRandom = new SecureRandom();
		byte[] IV = new byte[cipher.getBlockSize()];
		secureRandom.nextBytes(IV);

		KeyGenerator encriptKey = KeyGenerator.getInstance("AES");
		encriptKey.init(128);
		SecretKey key = encriptKey.generateKey();

		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));

		byte[] encripted = cipher.doFinal(((String) message).getBytes("UTF-8"));

		return new MessageEncryption(encripted, key, IV);
	}

	public static String decrypt(MessageEncryption input) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.DECRYPT_MODE, input.getKey(), new IvParameterSpec(input.getIV()));
		return new String(cipher.doFinal(input.getByteArray()), "UTF-8");
	}

}
