package br.ufpe.cin.if678.crypt;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
 * Metodos usados para criptogrfar e descriptografar mensagens e arquivos
 * 
 * @author joao
 *
 */
public class Methods {
	/**
	 * Recebe uma menssagem no formato de String e retorna um objeto MessegeEncryption, que contém a mensagem encripatada, a chave usada pra encriptá-la, e o IV
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
	 * Recebe um objeto MessegeEncrypted e retorna uma String com a mensagem decriptada
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
	 */
	public static String decrypt(MessegeEncryption encryptedMessege) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
		// Inicia o Cipher para modo de decyptação AES
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.DECRYPT_MODE, encryptedMessege.getKey(), new IvParameterSpec(encryptedMessege.getIV()));

		// Retorna a mensagem decriptografada
		return new String(cipher.doFinal(encryptedMessege.getEncryptedMessege()), "UTF-8");
	}

	/**
	 * Criptografa o arquivo de entrada, devolve o tipo FileEncryption que contém o arquivo e o nome do arquivo criptografados, a key e IV usados para criptografá-los
	 * 
	 * @param file arquivo que será criptografado
	 * @return objeto que contém o arquivo e o nome do arquivo criptografados, a key e IV usados na criptografia
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static FileEncryption encrypt(File file) throws IllegalBlockSizeException, BadPaddingException, IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		// Inicia Cipher para a criptografia AES
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		// Gera um IV aleatório
		SecureRandom secureRandom = new SecureRandom();
		byte[] IV = new byte[cipher.getBlockSize()];
		secureRandom.nextBytes(IV);

		// Gera uma chave secreta aleatórioa
		KeyGenerator encriptKey = KeyGenerator.getInstance("AES");
		encriptKey.init(128);
		SecretKey key = encriptKey.generateKey();

		// Inicia cipher para o medo de encriptação, com a SecretKey e IV gerados
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));

		// Encripta o arquivo, a partir do array de bytes
		byte[] encryptedFile = cipher.doFinal(Files.readAllBytes(file.toPath()));

		// Encripta o nome do arquivo
		byte[] fileName = file.getName().getBytes();
		byte[] encryptedFileName = cipher.doFinal(fileName);

		return new FileEncryption(encryptedFileName, encryptedFile, key, IV);
	}

	/**
	 * Recebe um arquivo encriptado para ser decriptado, que será escrito em um diretório
	 * 
	 * @param encryptedFile arquivo que será decriptado
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws IOException
	 */
	public static void decrypt(FileEncryption encryptedFile) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IOException {
		// Inicia cipher para o modo AES, de decriptação, com a SecretKey e IV do FileEncryption recebido
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.DECRYPT_MODE, encryptedFile.getKey(), new IvParameterSpec(encryptedFile.getIV()));

		// Decripta o arquivo
		byte[] decryptedFile = cipher.doFinal(encryptedFile.getFile());

		// Decripta o nome do arquivo
		String decryptedFileName = new String (cipher.doFinal(encryptedFile.getFileName()), "UTF-8");
		
		// Escreve o arquivo no Path definido.
		Path writePath = Paths.get("" + decryptedFileName);
		Files.write(writePath, decryptedFile);
	}
}
