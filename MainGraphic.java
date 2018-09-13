package it.uni.provaclient;

import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import it.uni.provaserver.Choice;
import it.uni.provaserver.Player;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.UIManager;

public class MainGraphic {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private JFrame frame;
	private JPanel gamePanel = new JPanel();
	private JPanel rankingPanel = new JPanel();
	private JTextField playerNameField;
	private JLabel playerNameLabel;
	private JButton joinButton;
	private JTextArea rankingTextArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGraphic window = new MainGraphic();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGraphic() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.LIGHT_GRAY);
		mainPanel.setBounds(0, 132, 874, 429);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		
		rankingPanel.setBounds(677, 11, 197, 390);
		mainPanel.add(rankingPanel);
		rankingPanel.setBackground(Color.WHITE);
		rankingPanel.setBorder(new LineBorder(Color.RED, 2));
		rankingPanel.setLayout(null);
		rankingTextArea = new JTextArea();
		rankingTextArea.setBounds(10, 58, 177, 321);
		rankingPanel.add(rankingTextArea);
		
		gamePanel.setBounds(10, 11, 650, 390);
		mainPanel.add(gamePanel);
		gamePanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		gamePanel.setBackground(Color.WHITE);
		gamePanel.setLayout(null);
		
		playerNameField = new JTextField();
		playerNameField.setBounds(186, 120, 266, 45);
		playerNameField.setBorder(new LineBorder(Color.BLACK, 1));
		gamePanel.add(playerNameField);
		playerNameField.setColumns(10);
		
		playerNameLabel = new JLabel("Nome Giocatore:");
		playerNameLabel.setFont(new Font("Segoe UI Historic", Font.PLAIN, 11));
		playerNameLabel.setBounds(89, 127, 87, 30);
		gamePanel.add(playerNameLabel);
		
		joinButton = new JButton("PARTECIPA");
		joinButton.setFont(new Font("Segoe UI Historic", Font.PLAIN, 11));
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!playerNameField.getText().isEmpty()) {
					try {
						out.writeObject(playerNameField.getText());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	
			}
		});
		joinButton.setBounds(186, 176, 266, 91);
		gamePanel.add(joinButton);
		
		gamePanel.setVisible(true);
		
	}
	
	public void loadGraphic() {
		playerNameField.setVisible(false);
		playerNameLabel.setVisible(false);
		joinButton.setVisible(false);
		
		GerryGraphic gameOne= new GerryGraphic();
		gameOne.paintGame(in, out, gamePanel);
		gamePanel=gameOne.getPanel();
		frame.repaint();
		gameOne.result(in);
		gamePanel.repaint();
	}
	
	public void updateRanking() {
		try {
			Vector<Player> giocatori=(Vector<Player>)in.readObject();
			rankingTextArea.setText(textRanking(giocatori));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String textRanking(Vector<Player> _giocatori) {
		
		StringBuffer str=new StringBuffer();
		for(int i=0; i<_giocatori.size(); i++) {
			str.append(_giocatori.get(i).toString()+"\n");
		}
		return str.toString();
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	public JPanel getMainPanel() {
		return gamePanel;
	}
}
