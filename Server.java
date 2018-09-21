package it.uni.provaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class Server extends Thread {
	
	private static final int PORT = 9999;
	private static final int NO_PLAYERS = 0;
	private static final int MAX_PLAYERS = 4;
	private static final long JOIN_TIME = 15000;
	private static final double POINTS_RIGHT_ANSWER = 100;
	private static final double POINTS_WRONG_ANSWER = 0;

	private static Vector<Connect> connections;
	
	private ServerSocket server;
	private int playersNumber;
	private Players players;
	
	public static void main(String[] args) throws Exception {
		connections = new Vector<>();
		new Server();
	}
	
	public Server() throws Exception {
		players= new Players();
		playersNumber=NO_PLAYERS;
		server = new ServerSocket(PORT);
		this.start();
	}
	
	
	public void run() {
		//---------------------------FASE 1----------------------------------------
		long timePassed = 0;
		long startTime = System.currentTimeMillis();
		int connectedClients=0;
		while (timePassed<JOIN_TIME && connectedClients<MAX_PLAYERS) {
			try {
				Socket client = server.accept();
				System.out.println(client.getInetAddress().toString());
				if (client!=null) {
					connectedClients ++;
					Connect c = new Connect(client,this);
					connections.add(c);
				}
				timePassed=System.currentTimeMillis()-startTime;	
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
		//----------------------------FASE 2-scelta gioco---------------------------------
		
		chooseGame();
		
		for (int i=0; i<connections.size(); i++) {
			try {
				connections.get(i).getOut().writeObject(new String("Gioco"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//----------------------------FASE 3---------------------------------
		Choice a = choiceCreator();
		
		for (int i=0; i<connections.size(); i++) {
		
			try {
	
				connections.get(i).getOut().writeObject(players.toString());
				
				addNewPlayer(connections.get(i));
				
				connections.get(i).getOut().writeObject(players.toString());
				
					
				connections.get(i).getOut().writeObject("START");
				
				connections.get(i).getOut().writeObject(a);
				
				boolean responso = answerCheck(a, i);
			
				connections.get(i).getOut().writeObject(responso);
				
				connections.get(i).getOut().flush();
				connections.get(i).getOut().close();
				connections.get(i).getIn().close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}

	private void chooseGame() {
		
		
		
	}

	public int getPlayersNumber() {
		return playersNumber;
	}

	/**
	* Incrementa di 1 il numero dei giocatori
	* @return void
	*/
	public void addPlayer() {
		playersNumber++;
	}
	

	/**
	* Aggiunge un nuovo giocatore 
	* @param c !!!NON SO COSA SCRIVERE!!!<-------------------------------DA MODIFICARE 8==========D
	* @return void
	*/
	private void addNewPlayer(Connect c) throws IOException, ClassNotFoundException {
		String playerName=(String)c.getIn().readObject();
		players.getGiocatori().add(new Player(playerName));
		addPlayer();
	}

	/**
	* Crea una domanda con le relative opzioni di risposta numerate
	* @return void
	*/
	private Choice choiceCreator(){
		Vector<Option> optionA = new Vector<>();
		optionA.add(new Option("A",1));
		optionA.add(new Option("B",2));
		optionA.add(new Option("C",3));
		optionA.add(new Option("D",4));
		Choice a = new Choice(new Question("domanda"),optionA, 2);
		return a;
	}
	
	/**
	* Controlla che la risposta sia corretta e assegna i punti di conseguenza
	* @param a	Domanda da controllare
	* @param i	Numero del giocatore che ha risposto
	* @return true se la risposta è corretta, false altrimenti
	*/
	private boolean answerCheck(Choice a, int i) throws ClassNotFoundException, IOException{
		
		Integer accesa=null;
		boolean scossa;
		
		
		do{
			accesa=(Integer)connections.get(i).getIn().readObject();
		}while(accesa==null);
	
		if(accesa.equals(a.getCorrectAnswer())==true) {
			scossa= true;
			players.getGiocatori().get(i).modScore(POINTS_RIGHT_ANSWER);
		}
		else {
			scossa=false;
			players.getGiocatori().get(i).modScore(POINTS_WRONG_ANSWER);
		}
		
		return scossa;
	}
	
}
