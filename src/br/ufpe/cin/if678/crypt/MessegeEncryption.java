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
 * @author João Filipe
 *
 * @param <T> T pode ser uma String ou um arquivo
 */
public class MessegeEncryption {

	private byte[] byteArray;
	private SecretKey key;
	private byte[] IV;

	public MessegeEncryption(byte[] byteArray, SecretKey key, byte[] IV) {
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

	/**
	 * 
	 * @param Sring message
	 * @return MessegeEncryption
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 * 
	 *             Recebe uma menssagem no formato de String e retorna um objeto MessegeEncryption, que contém a mensagem encripatada, a chave usada pra encriptá-la, e o IV
	 */
	public static MessegeEncryption encrypt(String message) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		// Inicia o Cipher para AES
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		// Gera o IV
		SecureRandom secureRandom = new SecureRandom();
		byte[] IV = new byte[cipher.getBlockSize()];
		secureRandom.nextBytes(IV);

		// Gera a Key
		KeyGenerator encriptKey = KeyGenerator.getInstance("AES");
		encriptKey.init(128);
		SecretKey key = encriptKey.generateKey();

		// Inicia o Cipher para modo de encriptação
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));

		// Encrypta a menssagem
		byte[] encripted = cipher.doFinal(((String) message).getBytes("UTF-8"));

		return new MessegeEncryption(encripted, key, IV);
	}

	/**
	 * 
	 * @param encryptedMessege
	 * @return decryptedMessege
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws UnsupportedEncodingException
	 * 
	 *             Recebe um objeto MessegeEncrypted e retorna uma String com a mensagem decriptada
	 */
	public static String decrypt(MessegeEncryption encryptedMessege) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
		// Inicia o Cipher para modo de decyptação AES
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.DECRYPT_MODE, encryptedMessege.getKey(), new IvParameterSpec(encryptedMessege.getIV()));

		// Decrypta a mensagem
		return new String(cipher.doFinal(encryptedMessege.getByteArray()), "UTF-8");
	}

}
