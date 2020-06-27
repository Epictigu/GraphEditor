package de.fhswf.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.fhswf.utils.GraphPainter;
import de.fhswf.utils.Kanten;
import de.fhswf.utils.Knoten;

public class KnotFrame extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = -1867545155496855858L;

	private GraphPainter gP;
	private Knoten k;
	private JTextField nameTF;
	
	public KnotFrame(GraphPainter gP, Knoten k, int id) {
		this.gP = gP;
		this.k = k;
		setSize(250, 130);
		setTitle("Knotendetails");
		setLocationRelativeTo(null);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		
		JLabel idL = new JLabel("ID: \t" + id);
		idL.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
		idL.setBounds(28, 10, 200, 25);
		add(idL);
		
		JLabel nameL = new JLabel("Name: ");
		nameL.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
		nameL.setBounds(28, 35, 200, 25);
		add(nameL);
		
		nameTF = new JTextField(k.knotName);
		nameTF.setBounds(75, 35, 80, 25);
		nameTF.setEditable(false);
		add(nameTF);
		
		JCheckBox nameCheck = new JCheckBox();
		nameCheck.setBounds(155, 35, 25, 25);
		nameCheck.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					nameTF.setEditable(true);
				} else {
					nameTF.setEditable(false);
				}
			}
		});
		add(nameCheck);
		
		
		JButton closeB = new JButton("Speichern");
		closeB.setBounds(25, 70, 80, 20);
		closeB.setActionCommand("save");
		closeB.addActionListener(this);
		add(closeB);
		JButton deleteB = new JButton("Löschen");
		deleteB.setBackground(Color.RED);
		deleteB.setActionCommand("delete");
		deleteB.addActionListener(this);
		deleteB.setBounds(145, 70, 80, 20);
		add(deleteB);
		
		repaint();
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("save")) {
			k.knotName = nameTF.getText();
			gP.repaint();
			dispose();
		} else if(e.getActionCommand().equalsIgnoreCase("delete")) {
			List<Kanten> delK = new ArrayList<Kanten>();
			for(Kanten ka : gP.graph.edgeList) {
				if(ka.k1 == k || ka.k2 == k) delK.add(ka);
			}
			for(Kanten ka : delK) {
				gP.graph.edgeList.remove(ka);
			}
			gP.graph.knotList.remove(k);
			gP.repaint();
			dispose();
		}
	}
	
}
