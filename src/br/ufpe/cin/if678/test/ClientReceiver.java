package br.ufpe.cin.if678.test;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ClientReceiver {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(6666);

			Socket socket = serverSocket.accept();

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec("1234567812345678".getBytes("UTF-8"), "AES"), new IvParameterSpec("masi129fapwQMV0W".getBytes()));

			DataInputStream DIS = new DataInputStream(socket.getInputStream());
			long fileSize = DIS.readLong();
			
			CipherInputStream CIS = new CipherInputStream(socket.getInputStream(), cipher);
			FileOutputStream FOS = new FileOutputStream("received.rar");

			byte[] buffer = new byte[4 * 1024];

			int count;
			long downloaded = 0;
			while ((count = CIS.read(buffer)) > 0) {
				downloaded += count;
				System.out.println("DOWNLOADED: " + (downloaded/fileSize));
				FOS.write(buffer, 0, count);
			}

			FOS.close();
			CIS.close();

			socket.close();

			serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
