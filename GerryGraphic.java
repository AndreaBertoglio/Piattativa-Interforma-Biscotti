package it.uni.provaclient;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import it.uni.provaserver.Choice;

public class GerryGraphic {
	
	private static final int BUTTON_RATE_X= 3; 
	private static final int SPACE_RATE_X= 9;
	private static final int BUTTON_RATE_Y= 10; 
	private static final int SPACE_RATE_Y= 10;
	private static final int TEXT_AREA_RATE= 2;
	
	
	private Vector<JButton> buttons= new Vector<>();
    private JTextArea textArea = new JTextArea();
    private Integer laccendiamo=null;
    private JPanel panel = new JPanel();
    
    private void initialize(ObjectInputStream _in, ObjectOutputStream _out, JPanel _panel) {
		
		textArea.setBounds(0, 0, _panel.getWidth(), _panel.getHeight()/TEXT_AREA_RATE);
		textArea.setBorder(new LineBorder(Color.BLACK, 2));
		int buttonWidth= _panel.getWidth()/BUTTON_RATE_X;
		int buttonHeight= _panel.getHeight()/BUTTON_RATE_Y;
		int buttonSpaceX= _panel.getWidth()/SPACE_RATE_X;
		int buttonSpaceY= _panel.getHeight()/SPACE_RATE_Y;
		
		JButton answer_one = new JButton("New button");
		answer_one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				laccendiamo=1;
				try {
					_out.writeObject(laccendiamo);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		answer_one.setBounds(buttonSpaceX, buttonSpaceY+textArea.getHeight(), buttonWidth , buttonHeight);
		buttons.add(answer_one);
		
		
		
		JButton answer_two = new JButton("New button");
		answer_two.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				laccendiamo=2;
				try {
					_out.writeObject(laccendiamo);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		answer_two.setBounds(buttonSpaceX*2+buttonWidth, buttonSpaceY+textArea.getHeight(), buttonWidth, buttonHeight);
		buttons.add(answer_two);
		
		
		
		JButton answer_three = new JButton("New button");
		answer_three.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				laccendiamo=3;
				try {
					_out.writeObject(laccendiamo);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});
		answer_three.setBounds(buttonSpaceX, buttonSpaceY*2+textArea.getHeight()+buttonHeight, buttonWidth, buttonHeight);
		buttons.add(answer_three);
		
		
		
		JButton answer_four = new JButton("New button");
		answer_four.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				laccendiamo=4;
				try {
					_out.writeObject(laccendiamo);
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}
		});
		answer_four.setBounds(buttonSpaceX*2+buttonWidth, buttonSpaceY*2+textArea.getHeight()+buttonHeight, buttonWidth, buttonHeight);
		buttons.add(answer_four);
		
		buildPanel(_panel);
	}
    
    public void buildPanel(JPanel _panel) {
    	_panel.add(textArea);
    	for(int i=0; i<4; i++) {
    		_panel.add(buttons.get(i));
    	}
    }
    
    public JPanel getPanel() {
    	return panel;
    }
    

	public void showQuestion(Choice a){
		Vector<JButton> answers= buttons;
		for(int i=0;i<answers.size();i++) {
			answers.get(i).setText(a.getOptions().get(i).getTesto());
		}
		textArea.setText(a.getQuestion().getTesto());
		panel.repaint();
	}
	
	public void result(ObjectInputStream _in) {
		String scossa=null;
		try {
			do{
				scossa=(String)_in.readObject();
		    } while(scossa==null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		textArea.setText(scossa);
	    panel.repaint();
	}
	
	public Choice readChoice(ObjectInputStream in){
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
	

	public void paintGame(ObjectInputStream _in, ObjectOutputStream _out, JPanel _panel) {
		
		this.initialize(_in, _out, _panel);
		this.showQuestion(readChoice(_in));
		
	}
	
	public Vector<JButton> getButtons(){
		return buttons;
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}
	
	public Integer getLaccendiamo() {
		return laccendiamo;
	}
	
	public void laSpegnamo() {
		laccendiamo=null;
	}

}
