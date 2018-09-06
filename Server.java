package it.uni.provaserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.util.Vector;
import java.util.concurrent.*;

public class Server {

	private static final double POINTS_RIGHT_ANSWER = 100;
	private static final double POINTS_WRONG_ANSWER = 0;

	public static void main(String[] args) throws ClassNotFoundException {
	
		int port = 9999;
		
		Player giocatore= new Player("bug");
		Integer accesa=2;
		String scossa;
		
		try(	
				ServerSocket server = new ServerSocket(port);
				Socket client = server.accept();
				ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
		){
		
			ThreadInput input= new ThreadInput(client);
			
			ExecutorService executor = Executors.newFixedThreadPool(1);
			Future<Object> read= executor.submit(input);
			
			Vector<Option> optionA = new Vector<>();
			optionA.add(new Option("A",1));
			optionA.add(new Option("B",2));
			optionA.add(new Option("C",3));
			optionA.add(new Option("D",4));
			Choice a = new Choice(new Question("domanda"),optionA, 2);
			out.writeObject(a);
			
			try {
				accesa=(Integer)read.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		
			if(accesa==a.getCorrectAnswer()) {
				scossa= "va beeneeeee";
				giocatore.modScore(POINTS_RIGHT_ANSWER);
			}
			else {
				scossa="BZZZZZZZ";
				giocatore.modScore(POINTS_WRONG_ANSWER);
			}
			out.writeObject(scossa);
			
			
		} catch (IOException e){
			e.printStackTrace();
		}
	
	}
	
}