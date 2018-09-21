package it.uni.provaclient;

import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Graphic {

	private JFrame frame;
	
	public JPanel panel = new JPanel();
	public Vector<JButton> buttons= new Vector<>();
    JTextArea textArea = new JTextArea();
    
    private Integer laccendiamo=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Graphic window = new Graphic();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Carica la grafica iniziale
	 * @return void
	 */
	public Graphic() {
		initialize();
	}

	/**
	 * Inizializza il contenuto della frame
	 * @return void
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		panel.setBounds(0, 0, 434, 261);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		textArea.setBounds(84, 21, 267, 99);
		panel.add(textArea);
		
		
		JButton answer_one = new JButton("New button");
		answer_one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				laccendiamo=1;
			}
		});
		answer_one.setBounds(31, 157, 158, 40);
		panel.add(answer_one);
		buttons.add(answer_one);
		
		
		
		JButton answer_two = new JButton("New button");
		answer_two.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				laccendiamo=2;
			}
		});
		answer_two.setBounds(235, 157, 158, 40);
		panel.add(answer_two);
		buttons.add(answer_two);
		
		
		
		JButton answer_three = new JButton("New button");
		answer_three.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				laccendiamo=3;
			}
		});
		answer_three.setBounds(31, 214, 158, 36);
		panel.add(answer_three);
		buttons.add(answer_three);
		
		
		
		JButton answer_four = new JButton("New button");
		answer_four.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				laccendiamo=4;
			}
		});
		answer_four.setBounds(235, 214, 158, 36);
		panel.add(answer_four);
		buttons.add(answer_four);
		
		
		
	}
	
	public Vector<JButton> getButtons(){
		return buttons;
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}
	
	public JPanel getMainPanel() {
		return panel;
	}
	
	public Integer getLaccendiamo() {
		return laccendiamo;
	}
	
	/**
	* Resetta la variabile contenente la risposta corretta
	* @return void
	*/
	public void laSpegnamo() {
		laccendiamo=null;
	}
	
}
