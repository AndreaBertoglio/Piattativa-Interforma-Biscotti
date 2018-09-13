package it.uni.provaserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Collections;
import java.util.Vector;

public class Connect extends Thread {
	
	private static final double POINTS_RIGHT_ANSWER = 100;
	private static final double POINTS_WRONG_ANSWER = 0;
	
	private Socket client;
	private Player giocatore;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Server server;
	
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
	
	public void run() {
		
		Choice a = choiceCreator();
		
		try {

			out.writeObject(server.getRanking());
			
			String playerName=(String)in.readObject();
			giocatore= new Player(playerName);
			server.getRanking().add(giocatore);
			Collections.sort(server.getRanking());
			server.addPlayer();
			
			out.writeObject(server.getRanking());
			
			out.writeObject("START");
			
			out.writeObject(a);
			
			String responso = answerCheck(a);
		
			out.writeObject(responso);
			
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Choice choiceCreator(){
		Vector<Option> optionA = new Vector<>();
		optionA.add(new Option("A",1));
		optionA.add(new Option("B",2));
		optionA.add(new Option("C",3));
		optionA.add(new Option("D",4));
		Choice a = new Choice(new Question("domanda"),optionA, 2);
		return a;
	}
	
	private String answerCheck(Choice a) throws ClassNotFoundException, IOException{
		
		Integer accesa=null;
		String scossa;
		
		
		do{
			accesa=(Integer)in.readObject();
		}while(accesa==null);
	
		if(accesa.equals(a.getCorrectAnswer())==true) {
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