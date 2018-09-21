package it.uni.provaclient;

import java.awt.*;
import java.util.Vector;
import java.io.*;
import javax.swing.*;

import it.uni.provaserver.Player;
import it.uni.provaserver.Players;




public class MainGraphic {

	private static final int TITLE_SPACE=130;
	private static final int RANK_SPACE=50;
	private static final int FRAME_ERROR=50;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel gamePanel = new JPanel();
	private JPanel rankingPanel = new JPanel();
	private JTextField playerNameField;
	private JLabel playerNameLabel;
	private JButton joinButton;
	private JTextArea rankingTextArea;

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

	public MainGraphic() {
		initialize();
	}

	/**
	 * Inizializza la grafica della frame
	 * @return void
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.DARK_GRAY);
		mainPanel.setBounds(20, TITLE_SPACE, frame.getWidth()-FRAME_ERROR, frame.getHeight()-TITLE_SPACE-FRAME_ERROR);
		mainPanel.setLayout(null);
		frame.getContentPane().add(mainPanel);
		
		gamePanel.setBounds(10, 11, mainPanel.getWidth()*3/4, mainPanel.getHeight()-11*2);
		gamePanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		gamePanel.setBackground(Color.WHITE);
		gamePanel.setLayout(null);
		mainPanel.add(gamePanel);
		
		playerNameField = new JTextField();
		playerNameField.setBounds(186, 120, 266, 45);
		playerNameField.setBorder(new LineBorder(Color.BLACK, 1));
		playerNameField.setColumns(10);
		gamePanel.add(playerNameField);
		
		playerNameLabel = new JLabel("Nome Giocatore:");
		playerNameLabel.setFont(new Font("Segoe UI Historic", Font.PLAIN, 11));
		playerNameLabel.setBounds(89, 127, 87, 30);
		gamePanel.add(playerNameLabel);
		
		joinButton = new JButton("PARTECIPA");
		joinButton.setBackground(UIManager.getColor("Button.background"));
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
		

		rankingPanel.setBounds(10*2+gamePanel.getWidth(), 11, mainPanel.getWidth()-10*3-gamePanel.getWidth(),  mainPanel.getHeight()-(11*2));
		rankingPanel.setBackground(Color.WHITE);
		rankingPanel.setBorder(new LineBorder(Color.RED, 2));
		rankingPanel.setLayout(null);
		mainPanel.add(rankingPanel);
		
		rankingTextArea = new JTextArea();
		rankingTextArea.setBounds(0, RANK_SPACE, rankingPanel.getWidth(), rankingPanel.getHeight()-RANK_SPACE);
		rankingTextArea.setBorder(new LineBorder(Color.RED, 2));
		rankingPanel.add(rankingTextArea);
		
		
		gamePanel.repaint();
		
	}
	
	/**
	* Inizializza la grafica principale
	* @return void
	*/
	public void loadGraficaDelGioco() {
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
	
	/**
	* Aggiorna la classifica
	* @return void
	*/	
	public void updateRanking() {
		try {
			String newRank=(String)in.readObject();
			rankingTextArea.setText(newRank);
			rankingPanel.repaint();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
