package br.ufpe.cin.if678.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(4848);

			Socket socket = serverSocket.accept();

			InputStream IS = socket.getInputStream();
			FileOutputStream FOS = new FileOutputStream("server-file.rar");

			byte[] buffer = new byte[4 * 1024];

			int count;
			while ((count = IS.read(buffer)) > 0) {
				FOS.write(buffer, 0, count);
			}

			FOS.close();
			IS.close();

			socket.close();

			serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Socket socket = new Socket("localhost", 6666);

			File file = new File("server-file.rar");

			FileInputStream FIS = new FileInputStream(file);
			OutputStream OS = socket.getOutputStream();

			byte[] buffer = new byte[4 * 1024];

			int count;
			while ((count = FIS.read(buffer)) > 0) {
				OS.write(buffer, 0, count);
			}

			OS.close();
			FIS.close();

			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
