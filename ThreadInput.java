package it.uni.provaserver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ThreadInput implements Callable <Object> {

	private ObjectInputStream in;
	
	public ThreadInput (Socket _client) throws IOException {
		ObjectInputStream in = new ObjectInputStream(_client.getInputStream());
	}
	
	public Object call() throws ClassNotFoundException, IOException  {
		
		Object letto=null;
	    do {
	    	letto=(Object)in.readObject();
	    } while (letto==null);
	    
	    return letto;
	}

}
