package com.andrealves.chat.cliente;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import gui.JanelaGui;

public class Cliente extends JanelaGui{
	
	
	private Socket socket;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new Cliente();

	}
	
	@Override
	protected boolean conectar() {
		System.out.println("Conectando ao servidor");
		try {
			this.socket = new Socket("127.0.0.1", 3333);
			
			RecebeMensagemServidor recebeMensagemServidor = new RecebeMensagemServidor(this.socket, this);
			new Thread(recebeMensagemServidor).start();
			
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	protected void sendMessage(String mensagem) {
		System.out.println("Envia mensagem via socket para o servidor - " + mensagem);
		
		try {
			OutputStream os = this.socket.getOutputStream();
			DataOutput dos = new DataOutputStream(os);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
	}

}
