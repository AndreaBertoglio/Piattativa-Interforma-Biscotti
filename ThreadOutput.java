package it.uni.provaserver;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadOutput implements Runnable {

    private Socket client;
	
	public ThreadOutput (Socket _client) {
		client=_client;
	}
	
	public void run(){
		
		try {
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
