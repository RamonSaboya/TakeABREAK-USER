package br.ufpe.cin.if678.crypt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

public class FileEncryption {
	
	private byte[] fileName;
	private byte[] file;
	private SecretKey key;
	private byte[] IV;
	
	public FileEncryption(byte[] fileName, byte[] file, SecretKey key, byte[] IV){
		this.file = file;
		this.fileName = fileName;
		this.key = key;
		this.IV = IV;
	}
	
	public byte[] getFileName() {
		return fileName;
	}

	public byte[] getFile() {
		return file;
	}

	public SecretKey getKey() {
		return key;
	}

	public byte[] getIV() {
		return IV;
	}
	
	/**
	 * 
	 * @param file
	 * @return	FileEncryption
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * 
	 * Encripta o arquivo de entrada, e devolve o tipo FileEncryption que coném o arquivo e o nome do arquivo encriptados, e a key e IV usados para encriptá-los
	 */
	public static FileEncryption encrypt(File file) throws IllegalBlockSizeException, BadPaddingException, IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException{
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
	
	public static void decrypt(FileEncryption encryptedFile, Path writePath) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IOException{
		// Inicia cipher para o modo AES, de decriptação, com a SecretKey e IV do FileEncryption recebido
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.DECRYPT_MODE, encryptedFile.getKey(), new IvParameterSpec(encryptedFile.getIV()));
		
		// Decripta o arquivo
		byte[] decryptedFile = cipher.doFinal(encryptedFile.getFile());
		
		// Decripta o nome do arquivo
		String decryptedFileName = cipher.doFinal(encryptedFile.getFileName()).toString();
		
		// Escreve o arquivo no Path recebido.
		Files.write(writePath, decryptedFile);
	}
}
