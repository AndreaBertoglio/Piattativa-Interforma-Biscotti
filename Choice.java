package it.uni.provaserver;

import java.io.Serializable;
import java.util.Vector;

public class Choice implements Serializable{
	
	private Question domanda;
	private Vector<Option> opzioni;
	private Integer rispostaCorretta;
	
	public Choice (Question question,Vector<Option> options, Integer correctAnswer){
		domanda=question;
		opzioni=options;
		rispostaCorretta=correctAnswer;
	}
	
	public Question getQuestion(){
		return domanda;
	}
	
	public Vector<Option> getOptions(){
		return opzioni;
	}
	
	public Option getOption(int index){
		return opzioni.get(index);
	}
	
	public Integer getCorrectAnswer() {
		return rispostaCorretta;
	}
}
