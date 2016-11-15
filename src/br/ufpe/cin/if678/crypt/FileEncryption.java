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
	
	public static FileEncryption encrypt(File file) throws IllegalBlockSizeException, BadPaddingException, IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException{
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		SecureRandom secureRandom = new SecureRandom();
		byte[] IV = new byte[cipher.getBlockSize()];
		secureRandom.nextBytes(IV);

		KeyGenerator encriptKey = KeyGenerator.getInstance("AES");
		encriptKey.init(128);
		SecretKey key = encriptKey.generateKey();

		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));

		byte[] encryptedFile = cipher.doFinal(Files.readAllBytes(file.toPath()));
			
		byte[] fileName = file.getName().getBytes();
		byte[] encryptedFileName = cipher.doFinal(fileName);
		
		return new FileEncryption(encryptedFileName, encryptedFile, key, IV);
	}
	
	public static File decrypt(FileEncryption encryptedFile, Path recivingPath) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException{
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.DECRYPT_MODE, encryptedFile.getKey(), new IvParameterSpec(encryptedFile.getIV()));
		
		byte[] decryptedFile = cipher.doFinal(encryptedFile.getFile());
		
		byte[] decryptedFileName = cipher.doFinal(encryptedFile.getFileName());
		
		return new File()
	}
}
