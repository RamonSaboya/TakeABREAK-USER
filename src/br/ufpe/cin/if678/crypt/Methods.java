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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import br.ufpe.cin.if678.util.Pair;

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
	
	// Strings usadas para gerar IV e chave de criptografia
	private static String IV = "masi129fapwQMV0W";
	private static String encryptKey = "Qlaj92kKSKAPlskq";
	
	public static byte[] encrypt(String message) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		// Inicia o Cipher para AES
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("UTF-8"), "AES");
		
		// Inicia o Cipher para modo de encriptação
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes()));

		// Encrypta a menssagem
		byte[] encrypted = cipher.doFinal(((String) message).getBytes("UTF-8"));

		return encrypted;
	}

	/**
	 * Recebe um objeto array de bytes e retorna uma String com a mensagem decriptada
	 * 
	 * @param encryptedMessege
	 * @return String descriptografada
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
	public static String decrypt(byte[] encryptedMessege) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
		// Inicia o Cipher para modo de decyptação AES
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encryptKey.getBytes("UTF-8"), "AES"), new IvParameterSpec(IV.getBytes()));

		// Retorna a mensagem decriptografada
		return new String(cipher.doFinal(encryptedMessege), "UTF-8");
	}

	/**
	 * Criptografa o arquivo de entrada, devolve um Par de array de bytes, contendo o arquivo e o nome dele, criptografados, respectivamente * 
	 * @param file arquivo que será criptografado
	 * @return Pair contendo arquivo criptografado, e nome do arquivo criptografado, respectivamente.
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Pair<byte[], byte[]> encrypt(File file) throws IllegalBlockSizeException, BadPaddingException, IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		// Inicia Cipher para a criptografia AES
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		// Inicia cipher para o medo de encriptação, com a SecretKey e IV gerados
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes("UTF-8"), "AES"), new IvParameterSpec(IV.getBytes()));

		// Encripta o arquivo, a partir do array de bytes
		byte[] encryptedFile = cipher.doFinal(Files.readAllBytes(file.toPath()));

		// Encripta o nome do arquivo
		byte[] fileName = file.getName().getBytes();
		byte[] encryptedFileName = cipher.doFinal(fileName);

		return new Pair(encryptedFile, encryptedFileName);
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
	public static void decrypt(@SuppressWarnings("rawtypes") Pair encryptedFile) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IOException {
		// Inicia cipher para o modo AES, de decriptação, com a SecretKey e IV do FileEncryption recebido
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(encryptKey.getBytes("UTF-8"), "AES"), new IvParameterSpec(IV.getBytes()));

		// Decripta o arquivo
		byte[] decryptedFile = cipher.doFinal((byte[]) encryptedFile.getFirst());

		// Decripta o nome do arquivo
		String decryptedFileName = new String (cipher.doFinal((byte[]) encryptedFile.getSecond()), "UTF-8");
		
		// Escreve o arquivo no Path definido.
		Path writePath = Paths.get("" + decryptedFileName);
		Files.write(writePath, decryptedFile);
	}
}
