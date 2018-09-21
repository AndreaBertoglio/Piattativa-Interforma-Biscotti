package it.uni.provaserver;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable{

	private final static double INITIAL_SCORE=0;
	private String name;
	private double score;
	
	public Player(String nome) {
		name=nome;
		score=INITIAL_SCORE;
	}
	
	/**
	* IModifica il punteggio del giocatore
	* @param value 	Punteggio da assegnare 
	* @return void
	*/
	public void modScore(double value) {
		score=score+value;
	}
	
	public double getScore() {
		return score;
	}
	
	public String getName() {
		return name;
	}

	public String toString() {
		return name + " " + score ;
	}

	/**
	* Verifica quale tra due giocatori ha il punteggio maggiore
	* @param _giocatore 	Giocatore da confrontare
	* @return 10 se il giocatore con cui viene invocato il metodo ha il punteggio maggiore, -10 altrimenti
	*/
	public int compareTo(Player _giocatore) {
		if(this.getScore()>_giocatore.getScore()) return 10;
		return -10;
	}

}
