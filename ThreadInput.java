package it.uni.provaserver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ThreadInput implements Callable <Object> {

	private ObjectInputStream in;
	
	public ThreadInput (Socket _client) throws IOException {
		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(_client.getInputStream()));
	}
	
	public Object call() throws Exception {
		
		return (Object)in.readObject();
		
	}

}
