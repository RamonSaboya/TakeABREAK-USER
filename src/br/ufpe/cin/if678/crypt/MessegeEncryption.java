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
 * Classe que faz a criptiografia das mensagens de texto
 * 
 * @author João Filipe
 *
 * @param <T> T pode ser uma String ou um arquivo
 */
public class MessegeEncryption {

	private byte[] encryptedMessege;
	private SecretKey key;
	private byte[] IV;
	
	/**
	 * Construtor da Menssagem criptografada
	 * 
	 * @param encryptedMessege contem o array de bytes da menssagem criptografada
	 * @param key contem a chave usada na criptografia
	 * @param IV contém o IV usado na cruptografia
	 */
	public MessegeEncryption(byte[] encryptedMessege, SecretKey key, byte[] IV) {
		this.key = key;
		this.encryptedMessege = encryptedMessege;
		this.IV = IV;
	}

	/**
	 * Retorna o array de bytes da menssagem criptografada
	 * 
	 * @return o array de bytes da menssagem criptografada
	 * 	 
	 */
	public byte[] getEncryptedMessege() {
		return encryptedMessege;
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
}
