package it.uni.provaserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Collections;
import java.util.Vector;

public class Connect extends Thread {
	
	
	

	private Server server;
	private Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	
	public Connect(Socket _client, Server _server) {
		server=_server;
		client = _client;
		try {
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
		
		} catch (IOException e){
			try {
				client.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			  }
		  }
		this.start();
	}
	

	public ObjectOutputStream getOut() {
		return out;
	}

	public ObjectInputStream getIn() {
		return in;
	}
	
	
}