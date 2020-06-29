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
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.fhswf.utils.ColorButton;
import de.fhswf.utils.GraphPainter;
import de.fhswf.utils.Kanten;
import de.fhswf.utils.Knoten;
import de.fhswf.utils.KnotenType;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class KnotFrame extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = -1867545155496855858L;

	private GraphPainter gP;
	private Knoten k;
	private JTextField nameTF;
	private JComboBox<String> comboBox;
	
	private ColorButton cB, cB2;
	
	public KnotFrame(GraphPainter gP, Knoten k, int id) {
		this.gP = gP;
		this.k = k;
		setSize(250, 202);
		setTitle("Knotendetails");
		setLocationRelativeTo(null);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setResizable(false);
		
		JLabel idL = new JLabel("ID: " + id);
		idL.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
		idL.setBounds(28, 10, 200, 25);
		getContentPane().add(idL);
		
		JLabel nameL = new JLabel("Name: ");
		nameL.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
		nameL.setBounds(28, 35, 44, 25);
		getContentPane().add(nameL);
		
		nameTF = new JTextField(k.knotName);
		nameTF.setBounds(75, 35, 80, 25);
		nameTF.setEditable(false);
		getContentPane().add(nameTF);
		
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
		getContentPane().add(nameCheck);
		
		
		JButton closeB = new JButton("Speichern");
		closeB.setBounds(28, 142, 80, 20);
		closeB.setActionCommand("save");
		closeB.addActionListener(this);
		getContentPane().add(closeB);
		
		JButton deleteB = new JButton("Löschen");
		deleteB.setBackground(Color.RED);
		deleteB.setActionCommand("delete");
		deleteB.addActionListener(this);
		deleteB.setBounds(145, 142, 80, 20);
		getContentPane().add(deleteB);
		
		JLabel lblNewLabel = new JLabel("Knotenform:");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblNewLabel.setBounds(28, 60, 80, 25);
		getContentPane().add(lblNewLabel);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Kreis", "Quadrat", "Gerundetes Quadrat"}));
		if(k.knotType == KnotenType.Kreis)
			comboBox.setSelectedIndex(0);
		else if(k.knotType == KnotenType.Quadrat)
			comboBox.setSelectedIndex(1);
		else if(k.knotType == KnotenType.GerundetesQuadrat)
			comboBox.setSelectedIndex(2);
		comboBox.setBounds(109, 64, 116, 20);
		getContentPane().add(comboBox);
		
		JLabel lblKnotenfarbe = new JLabel("Knotenfarbe:");
		lblKnotenfarbe.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblKnotenfarbe.setBounds(28, 85, 91, 25);
		getContentPane().add(lblKnotenfarbe);
		
		Color cBc = gP.mainColor;
		if(k.main != null) cBc = k.main;
		cB = new ColorButton(cBc);
		cB.setBounds(115, 87, 70, 21);
		cB.addActionListener(this);
		cB.setActionCommand("mainColor");
		getContentPane().add(cB);
		
		JLabel lblTextfarbe = new JLabel("Textfarbe:");
		lblTextfarbe.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblTextfarbe.setBounds(28, 110, 70, 25);
		getContentPane().add(lblTextfarbe);
		
		Color cBc2 = gP.fontColor;
		if(k.font != null) cBc2 = k.font;
		cB2 = new ColorButton(cBc2);
		cB2.setBounds(97, 112, 70, 21);
		cB2.addActionListener(this);
		cB2.setActionCommand("fontColor");
		getContentPane().add(cB2);
		
		repaint();
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("save")) {
			k.knotName = nameTF.getText();
			String s = ((String) comboBox.getSelectedItem()).replaceAll(" ", "");
			k.knotType = Enum.valueOf(KnotenType.class, s);
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
		} else if(e.getActionCommand().equalsIgnoreCase("mainColor")) {
			Color c = gP.mainColor;
			if(k.main != null) c = k.main;
			Color ausgewaehlteFarbe = JColorChooser.showDialog(null, "Knotenfarbauswahl", c);
			if(ausgewaehlteFarbe == null) return;
			k.main = ausgewaehlteFarbe;
			cB.c = ausgewaehlteFarbe;
		} else if(e.getActionCommand().equalsIgnoreCase("fontColor")) {
			Color c = gP.fontColor;
			if(k.font != null) c = k.font;
			Color ausgewaehlteFarbe = JColorChooser.showDialog(null, "Textfarbauswahl", c);
			if(ausgewaehlteFarbe == null) return;
			k.font = ausgewaehlteFarbe;
			cB2.c = ausgewaehlteFarbe;
		}
	}
}
