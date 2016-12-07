package test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		Scanner in  = new Scanner(System.in);
		try{
			Socket socket = new Socket("G4C12",3322);
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            boolean stop = true;
            while(stop){
            	String next = in.nextLine();
            	if(!next.equals("exit")){
            		dataOutputStream.writeUTF(next);
            		dataOutputStream.flush();            		
            	} else {
            		stop = false;
            	}
            }
            
            
		}catch (UnknownHostException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

}
