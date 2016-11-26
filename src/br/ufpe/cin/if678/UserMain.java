package br.ufpe.cin.if678;

import java.util.Scanner;

public class UserMain {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Digite o IP do servidor");
		String IP = in.nextLine();
	
		System.out.println("Digite a opção que deseja:");
		System.out.println("1. Iniciar conversa");
		int command = in.nextInt();
		
		switch(command){
		case 1:
			
			break;
		}
	}

}
