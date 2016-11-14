package br.ufpe.cin.if678.crypt;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption<T> {

     static String chaveencriptacao = "0123456789abcdef";
     
    private String IVGenerator(){
    	byte[] a = new byte[16];
    	Random r = new Random();
    	r.nextBytes(a);
    	return new String(a);
    }
    
    private String keyGenerator(){
    	byte[] a = new byte[32];
    	Random r = new Random();
    	r.nextBytes(a);
    	return new String(a);
    }
    
	public Encrypted encrypt(T obj) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException{
		String IV = IVGenerator();
		String encriptKey = keyGenerator(); 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encriptKey.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
		byte[] encripted;
		if(obj instanceof String){
			encripted = cipher.doFinal(((String) obj).getBytes("UTF-8"));
		} else {
			encripted = cipher.doFinal(Files.readAllBytes(((File) obj).toPath()));
		}
		return new Encrypted(encripted, key, IV);
	}
	
	public byte[] decrypt(Encrypted input) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException{
		 Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
         decripta.init(Cipher.DECRYPT_MODE, input.getKey(),new IvParameterSpec(input.getIV().getBytes("UTF-8")));
         return decripta.doFinal(input.getByteArray());
	}
}
