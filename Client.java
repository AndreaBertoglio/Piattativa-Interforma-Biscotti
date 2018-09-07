package it.uni.provaclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.*;

import javax.swing.JButton;

import it.uni.provaserver.Choice;
import it.uni.provaserver.ThreadInput;

public class Client {
	
	private static final int WIDTH=450, HEIGHT=300;
	public String risposta;

	public static void main(String[] args) throws ClassNotFoundException{
		
		final int port = 9999;
		final String hostName = "localHost";
	try (
					Socket client = new Socket(hostName,port);
					ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream()); 
					ObjectInputStream in = new ObjectInputStream(client.getInputStream());
			){
		
				Choice a=readChoice(in);
				
				Graphic g=graphicMod(a);
							
			    out.writeObject(gerryScottie(g));
			    
				result(g,in);
			     
			} catch(IOException e){}
		} 

	private static Integer gerryScottie(Graphic g) {
		Integer d;
		do {
			d=g.getLaccendiamo();
			} while(g.getLaccendiamo()==null);
		g.laSpegnamo();
		return d;
	}
	
	private static Choice readChoice(ObjectInputStream in){
		Choice a= null;
		try {
			do {
			a = (Choice)in.readObject();
			} while(a==null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
		
	}
	
	private static Graphic graphicMod(Choice a){
		Graphic g= new Graphic();
		
		Vector<JButton> answers= g.buttons;
		for(int i=0;i<answers.size();i++) {
			answers.get(i).setText(a.getOptions().get(i).getTesto());
		}
		g.textArea.setText(a.getQuestion().getTesto());
		g.getMainPanel().repaint(); 
		return g;
	}
	
	private static void result(Graphic g,ObjectInputStream in) throws ClassNotFoundException, IOException{
		Object answer=null;
		
		do{
			answer=(String)in.readObject();
		    String scossa=(String)answer;
		    g.textArea.setText(scossa);
	    } while(answer==null);
	    g.getMainPanel().repaint();
	}
}
