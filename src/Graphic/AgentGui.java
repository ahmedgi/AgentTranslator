package Graphic;

import java.awt.Dimension;
import jade.core.AID;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Event.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Client_pack.AgentDemandeur;

import java.awt.Color;
import javax.swing.DropMode;

public class AgentGui {

	private AgentDemandeur myAgent;
	private JFrame frame;
	private JTextField textField;
	private JButton Envpoye;
	private JTextArea TextAfiche;
	private String mot;
	private String resp=null;;
	

	/**
	 * Launch the application.
	 */
	/**
	 * Create the application.
	 */
	public AgentGui(AgentDemandeur a) {
		myAgent=a;
		initialize(a);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(AgentDemandeur a) {
		frame = new JFrame("Traducteur de Eng Vers Alm");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Envpoye = new JButton("Envoyer");
		Envpoye.setBounds(344, 242, 94, 25);
		frame.getContentPane().add(Envpoye);
		
		textField = new JTextField();
		textField.setBounds(12, 242, 320, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		TextAfiche = new JTextArea();
		TextAfiche.setLocation(10, 10);
		TextAfiche.setEditable(false);
		JScrollPane scroll = new JScrollPane(TextAfiche);
		scroll.setBounds(12, 12, 426, 217);
		frame.getContentPane().add(scroll);
		Envpoye.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mot=textField.getText().trim();
				TextAfiche.append("me : "+mot+"\n");
				myAgent.updatemot(mot);
				textField.setText("");
				System.out.println(TextAfiche.getText());
				
			}
		});
		// Make the agent terminate when the user closes 
		// the GUI using the button on the upper right corner	
		frame.addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		} );
	}
	public void updateTextArea(String text,String name){;
		TextAfiche.append(name +" : "+text+"\n");
		//frame.revalidate();
		//frame.repaint();
		System.out.println("update text");
	}
	public void affiche() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgentGui window = new AgentGui(myAgent);
					//window.frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
