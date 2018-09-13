package it.uni.provaclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;

import it.uni.provaserver.Choice;

public class Client {
	
	private static final int WIDTH=450, HEIGHT=300;

	public static void main(String[] args) {
		
		final int port = 9999;
		final String hostName = "localHost";
		
		ObjectOutputStream out=null; 
		ObjectInputStream in=null;
		Socket client = null;
		try {
				client = new Socket(hostName,port);
				out = new ObjectOutputStream(client.getOutputStream()); 
				in = new ObjectInputStream(client.getInputStream());
				
				MainGraphic mainGraphic=new MainGraphic();
				mainGraphic.setOut(out);
				mainGraphic.setIn(in);

				mainGraphic.updateRanking();
				
				mainGraphic.updateRanking();
				
				String start=null;
				
				do {
					start=(String)in.readObject();
				} while(start==null);
				mainGraphic.loadGraphic();
				
				out.flush();
				out.close();
			    in.close();
			    
			} catch(IOException e){
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
		} 
}