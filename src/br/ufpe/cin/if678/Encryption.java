package br.ufpe.cin.if678;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Metodos usados para criptogrfar e descriptografar mensagens e arquivos
 * 
 * @author joao
 */
public class Encryption {

	// Strings usadas para gerar IV e chave de criptografia
	private static String IV = "masi129fapwQMV0W";
	private static String encryptKey = "Qlaj92kKSKw";

	/**
	 * Criptografa uma mensagem, usando a chave de criptografia concatenada com
	 * o ID do usuário que enviou
	 * 
	 * @param ID
	 *            ID do usuário que escreveu a mensagem
	 * @param message
	 *            mensagem que será criptografada
	 * @return mensagem criptografada
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 */
	public static byte[] encryptMessage(int ID, String message)
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		String fullID = String.valueOf(ID);
		while (fullID.length() < 5) {
			fullID = "0" + fullID;
		}

		SecretKeySpec key = new SecretKeySpec(encryptKey.concat(fullID).getBytes("UTF-8"), "AES");

		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes()));

		byte[] encrypted = cipher.doFinal(message.getBytes("UTF-8"));

		return encrypted;
	}

	/**
	 * Descriptografa uma mensagem, usando a chave de criptografria concatenada
	 * com o ID do usuário que enviou
	 * 
	 * @param ID
	 *            ID do usuário que escreveu a mensagem
	 * @param encryptedMessage
	 *            mensagem que será descriptografada
	 * @return mensagem descriptografada
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws UnsupportedEncodingException
	 */
	public static String decryptMessage(int ID, byte[] encryptedMessage) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		String fullID = String.valueOf(ID);
		while (fullID.length() < 5) {
			fullID = "0" + fullID;
		}

		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encryptKey.concat(fullID).getBytes("UTF-8"), "AES"),
				new IvParameterSpec(IV.getBytes()));

		return new String(cipher.doFinal(encryptedMessage), "UTF-8");
	}

	/**
	 * Criptografa um arquivo, usando a chave de criptografria concatenada com o
	 * ID do usuário que enviou
	 * 
	 * @param ID
	 *            ID do usuário que escreveu a mensagem
	 * @param file
	 *            arquivo que será criptografado
	 * @return arquivo criptografado
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static byte[] encryptFile(int ID, File file)
			throws IllegalBlockSizeException, BadPaddingException, IOException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		String fullID = String.valueOf(ID);
		while (fullID.length() < 5) {
			fullID = "0" + fullID;
		}

		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.concat(fullID).getBytes("UTF-8"), "AES"),
				new IvParameterSpec(IV.getBytes()));

		byte[] encryptedFile = cipher.doFinal(Files.readAllBytes(file.toPath()));

		return encryptedFile;
	}

	/**
	 * Descriptografa um arquivo, usando a chave de criptografria concatenada
	 * com o ID do usuário que enviou
	 * 
	 * @param ID
	 *            ID do usuário que escreveu a mensagem
	 * @param encryptedFile
	 *            arquivo criptografado
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws IOException
	 */
	public static byte[] decryptFile(int ID, byte[] encryptedFile) throws IllegalBlockSizeException,
			BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException, IOException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		String fullID = String.valueOf(ID);
		while (fullID.length() < 5) {
			fullID = "0" + fullID;
		}

		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encryptKey.concat(fullID).getBytes("UTF-8"), "AES"),
				new IvParameterSpec(IV.getBytes()));

		byte[] decryptedFile = cipher.doFinal(encryptedFile);

		return decryptedFile;
	}

	public static Cipher getEncryptCipher(int ID)
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		String fullID = String.valueOf(ID);
		while (fullID.length() < 5) {
			fullID = "0" + fullID;
		}

		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.concat(fullID).getBytes("UTF-8"), "AES"),
				new IvParameterSpec(IV.getBytes()));

		return cipher;
	}

	public static Cipher getDecryptCipher(int ID)
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		String fullID = String.valueOf(ID);
		while (fullID.length() < 5) {
			fullID = "0" + fullID;
		}

		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encryptKey.concat(fullID).getBytes("UTF-8"), "AES"),
				new IvParameterSpec(IV.getBytes()));

		return cipher;
	}
}
