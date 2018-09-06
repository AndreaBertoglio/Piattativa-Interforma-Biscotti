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

	public static void main(String[] args){
		
		final int port = 9999;
		final String hostName = "localhost";
	try (
					Socket client = new Socket(hostName,port);
					ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream())); 
			){
		
				ThreadInput input= new ThreadInput(client);
		
				ExecutorService executor = Executors.newFixedThreadPool(1);
				Future<Object> read= executor.submit(input);
		
		        Choice a= null;
				try {
					a = (Choice)read.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Graphic g= new Graphic();
				Vector<JButton> answers= g.buttons;
				for(int i=0;i<answers.size();i++) {
					answers.get(i).setText(a.getOptions().get(i).getTesto());
				}
				g.textArea.setText(a.getQuestion().getTesto());
				g.getMainPanel().repaint(); 
				
				
			    out.writeObject(gerryScottie(g));
			    
				Object maria=null;
				
				do{
				    try {
						maria=(String)read.get();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				    String scossa=(String)maria;
				    g.textArea.setText(scossa);
			    } while(maria==null);
			    g.getMainPanel().repaint();
			    
			    
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
}



