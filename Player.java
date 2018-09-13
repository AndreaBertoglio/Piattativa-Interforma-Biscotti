package it.uni.provaserver;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable{

	private final static double INITIAL_SCORE=0;
	private String name;
	private double score;
	
	public Player(String nome) {
		name=nome;
		score=/*INITIAL_SCORE*/ 250;
	}
	
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

	public int compareTo(Player _giocatore) {
		if(this.getScore()>_giocatore.getScore()) return 10;
		return -10;
	}

}
