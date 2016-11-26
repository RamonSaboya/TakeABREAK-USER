package br.ufpe.cin.if678.crypt;

import java.io.File;
import java.io.IOException;
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
 * Guarda o nome do arquivo e o arquivo criptografado, a key e o IV usados na criptografia. Contém também os métodos para criptografar e decriptografar o arquivo.
 * 
 * @author joao
 *
 */
public class FileEncryption {

	private byte[] fileName;
	private byte[] file;
	private SecretKey key;
	private byte[] IV;

	/**
	 * Construtor do objeto do FileEncryption
	 * 
	 * @param fileName Array de bytes contendo nome do arquivo criptografado
	 * @param file array de bytes contendo nome do arquivo criptografado
	 * @param key SecretKey contendo a chave usada para criptografia
	 * @param IV Array de bytes contendo IV usado na criptografia
	 */
	public FileEncryption(byte[] fileName, byte[] file, SecretKey key, byte[] IV) {
		this.file = file;
		this.fileName = fileName;
		this.key = key;
		this.IV = IV;
	}
	
	/**
	 * Retorna o atributo de array de bytes do nome do arquivo criptografado
	 * 
	 * @return o nome do arquivo criptografado em array de bytes
	 */
	public byte[] getFileName() {
		return fileName;
	}
	
	/**
	 * Retorna o atributo de array de bytes do arquivo criptografado
	 * 
	 * @return o arquivo criptografado em array de bytes
	 */
	public byte[] getFile() {
		return file;
	}

	/**
	 * Retorna o atributo SecretKey com a chave usada na criptografia
	 * 
	 * @return chave usada na criptografia
	 */
	public SecretKey getKey() {
		return key;
	}

	/**
	 * Retorna o atributo de array de bytes com o IV usado na criptografia
	 * 
	 * @return o IV usado na criptografia
	 */
	public byte[] getIV() {
		return IV;
	}

}
