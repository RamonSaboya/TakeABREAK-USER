package br.ufpe.cin.if678.test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ClientSender {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 4848);

			File file = new File("origin.rar");
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("1234567812345678".getBytes("UTF-8"), "AES"), new IvParameterSpec("masi129fapwQMV0W".getBytes()));

			FileInputStream FIS = new FileInputStream(file);
			
			// Envia tamanho do arquivo a ser enviado
			DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
			long fileSize = file.length();
			DOS.writeLong(fileSize);
			
			CipherOutputStream COS = new CipherOutputStream(socket.getOutputStream(), cipher);

			byte[] buffer = new byte[4 * 1024];

			int count;
			long uploaded = 0;
			while ((count = FIS.read(buffer)) > 0) {
				uploaded += count;
				System.out.println(uploaded/fileSize);
				COS.write(buffer, 0, count);
			}

			COS.close();
			FIS.close();

			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
