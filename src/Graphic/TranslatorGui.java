package Graphic;
import jade.core.AID;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Translator_Pack.AgentTraducteur;

/**
  @author Giovanni Caire - TILAB
 */
public class TranslatorGui extends JFrame {	
	private AgentTraducteur myAgent;
	private JTextField textEng	, textAl;
	
	public TranslatorGui(AgentTraducteur a) {
		super(a.getLocalName()+" List");
		
		myAgent = a;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 2));
		p.add(new JLabel("Mot English"));
		textEng = new JTextField(15);
		p.add(textEng);
		p.add(new JLabel("en Alemand:"));
		textAl = new JTextField(15);
		p.add(textAl);
		getContentPane().add(p, BorderLayout.CENTER);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String eng = textEng.getText().trim();
					String alm = textAl.getText().trim();
					myAgent.updateCatalogue(eng, alm);
					textEng.setText("");
					textAl.setText("");
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(TranslatorGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );
		p = new JPanel();
		p.add(addButton);
		getContentPane().add(p, BorderLayout.SOUTH);
		setResizable(false);
	}
	
	public void affiche() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}	
}


