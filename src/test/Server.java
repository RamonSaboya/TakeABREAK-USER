package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		
		 try {
			ServerSocket serverSocket = new ServerSocket(3322);
			
			while(true){
				Socket socket = serverSocket.accept();
				InputStream inputStream = socket.getInputStream();
				DataInputStream dataInputStream = new DataInputStream(inputStream);
				System.out.println(socket.getInetAddress().getHostAddress());
				
				
				System.out.println("Mensagens: \n");
				
				socket.setSoTimeout(30 *1000);
				
				String message;
				
				while(!(message= dataInputStream.readUTF()).equals("exit")){
					
					System.out.println(message);
					
					DataOutputStream output = new DataOutputStream(socket.getOutputStream());
					
					output.writeUTF("Olar!");
				}
				
			
				//output.flush();
			}
			
		} catch (IOException e) {
			
			System.out.println(e.getMessage());
		}
		 

	}

}
