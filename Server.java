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
		
		try(	
				ServerSocket server = new ServerSocket(port);
				Socket client = server.accept();
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(client.getInputStream());
		){
			
			Choice a = choiceCreator();
			out.writeObject(a);
			
			out.writeObject(answerCheck(in,a,giocatore));
			
		} catch (IOException e){
			e.printStackTrace();
		}
	
	}
	
	private static Choice choiceCreator(){
		Vector<Option> optionA = new Vector<>();
		optionA.add(new Option("A",1));
		optionA.add(new Option("B",2));
		optionA.add(new Option("C",3));
		optionA.add(new Option("D",4));
		Choice a = new Choice(new Question("domanda"),optionA, 2);
		return a;
	}
	
	private static String answerCheck(ObjectInputStream in,Choice a,Player giocatore) throws ClassNotFoundException, IOException{
		
		Integer accesa=null;
		String scossa;
		
		
		do{
			accesa=(Integer)in.readObject();
		}while(accesa==null);
		
	
		if(accesa==a.getCorrectAnswer()) {
			scossa= "va beeneeeee";
			giocatore.modScore(POINTS_RIGHT_ANSWER);
		}
		else {
			scossa="BZZZZZZZ";
			giocatore.modScore(POINTS_WRONG_ANSWER);
		}
		
		return scossa;
	}
}