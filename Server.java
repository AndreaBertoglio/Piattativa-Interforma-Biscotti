package it.uni.provaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class Server extends Thread {
	
	private static final int PORT = 9999;
	private static final int NO_PLAYERS = 0;
	
	
	private ServerSocket server;
	private int playersNumber;
	private Vector<Player> ranking;
	
	public static void main(String[] args) throws Exception {
		new Server();
	}
	
	public Server() throws Exception {
		ranking= new Vector<Player>();
		playersNumber=NO_PLAYERS;
		server = new ServerSocket(PORT);
		this.start();
	}
	
	
	public void run() {
		
		while (true) {
			try {
				Socket client = server.accept();
				System.out.println(client.getInetAddress().toString());
				if (client!=null) {
					Connect c = new Connect(client,this);
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	public int getPlayersNumber() {
		return playersNumber;
	}

	public void addPlayer() {
		playersNumber++;
	}
	
	public Vector<Player> getRanking(){
		return ranking;
	}
	
}