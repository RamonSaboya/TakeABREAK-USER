package br.ufpe.cin.if678;

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
public class Encryption {
	
	// Strings usadas para gerar IV e chave de criptografia
	private static String IV = "masi129fapwQMV0W";
	private static String encryptKey = "Qlaj92kKSK";

	/**
	 * Criptografa uma mensagem, usando a chave de criptografia concatenada com o ID do usuário que enviou
	 * 
	 * @param ID ID do usuário que escreveu a mensagem
	 * @param message mensagem que será criptografada
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
	public static byte[] encryptMessage(int ID, String message) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		// Inicia o Cipher para AES
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		SecretKeySpec key = new SecretKeySpec(encryptKey.concat(String.valueOf(ID)).getBytes("UTF-8"), "AES");
		
		// Inicia o Cipher para modo de encriptação
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes()));

		// Encrypta a menssagem
		byte[] encrypted = cipher.doFinal(message.getBytes("UTF-8"));

		return encrypted;
	}

	/**
	 * Descriptografa uma mensagem, usando a chave de criptografria concatenada com o ID do usuário que enviou
	 * 
	 * @param ID ID do usuário que escreveu a mensagem
	 * @param encryptedMessage mensagem que será descriptografada
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
	public static String decryptMessage(int ID, byte[] encryptedMessage) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
		// Inicia o Cipher para modo de decyptação AES
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encryptKey.concat(String.valueOf(ID)).getBytes("UTF-8"), "AES"), new IvParameterSpec(IV.getBytes()));

		// Retorna a mensagem decriptografada
		return new String(cipher.doFinal(encryptedMessage), "UTF-8");
	}

	/**
	 * Criptografa um arquivo e seu nome, usando a chave de criptografria concatenada com o ID do usuário que enviou
	 * 
	 * @param ID ID do usuário que escreveu a mensagem
	 * @param file arquivo que será criptografado
	 * @return par contendo nome do arquivo e arquivo, respectivamente, ambos criptografados
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static Pair<byte[], byte[]> encrypt(int ID, File file) throws IllegalBlockSizeException, BadPaddingException, IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		// Inicia Cipher para a criptografia AES
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");

		// Inicia cipher para o medo de encriptação, com a SecretKey e IV gerados
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.concat(String.valueOf(ID)).getBytes("UTF-8"), "AES"), new IvParameterSpec(IV.getBytes()));

		// Encripta o arquivo, a partir do array de bytes
		byte[] encryptedFile = cipher.doFinal(Files.readAllBytes(file.toPath()));

		// Encripta o nome do arquivo
		byte[] fileName = file.getName().getBytes();
		byte[] encryptedFileName = cipher.doFinal(fileName);

		return new Pair<byte[], byte[]>(encryptedFile, encryptedFileName);
	}

	/**
	 * Descriptografa um nome de arquivo e o arquivo, usando a chave de criptografria concatenada com o ID do usuário que enviou
	 * 
	 * @param ID ID do usuário que escreveu a mensagem
	 * @param encryptedFile par contendo nome do arquivo e arquivo, respectivamente, ambos criptografados
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws IOException
	 */
	public static void decrypt(int ID, Pair<byte[], byte[]> encryptedFile) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IOException {
		// Inicia cipher para o modo AES, de decriptação, com a SecretKey e IV do FileEncryption recebido
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(encryptKey.concat(String.valueOf(ID)).getBytes("UTF-8"), "AES"), new IvParameterSpec(IV.getBytes()));

		// Decripta o arquivo
		byte[] decryptedFile = cipher.doFinal(encryptedFile.getFirst());

		// Decripta o nome do arquivo
		String decryptedFileName = new String (cipher.doFinal(encryptedFile.getSecond()), "UTF-8");
		
		// Escreve o arquivo no Path definido.
		Path writePath = Paths.get("" + decryptedFileName);
		Files.write(writePath, decryptedFile);
	}
}
