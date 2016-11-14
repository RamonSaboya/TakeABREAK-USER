package br.ufpe.cin.if678.crypt;

import javax.crypto.SecretKey;


/**
 * 
 * @author joao
 *
 * @param <T> T pode ser uma String ou um arquivo
 */
public class Encrypted {
	
	private byte[] byteArray;
	private SecretKey key;
	private String IV;
	
	public Encrypted(byte[] byteArray, SecretKey key, String IV) {
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
	public String getIV(){
		return IV;
	}
	
}
