package de.fhswf.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

import de.fhswf.utils.ColorButton;
import de.fhswf.utils.GraphPainter;
import de.fhswf.utils.Kanten;
import de.fhswf.utils.Knoten;
import de.fhswf.utils.KnotenType;

public class KnotFrame extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = -1867545155496855858L;

	private GraphPainter gP;
	private Knoten k;
	private JTextField nameTF;
	private JComboBox<String> comboBox;
	private JSpinner spinner;
	private JCheckBox sizeCheck;
	
	private ColorButton cB, cB2;
	
	public KnotFrame(GraphPainter gP, Knoten k, int id) {
		this.gP = gP;
		this.k = k;
		setSize(250, 226);
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
		closeB.setBounds(28, 166, 80, 20);
		closeB.setActionCommand("save");
		closeB.addActionListener(this);
		getContentPane().add(closeB);
		
		JButton deleteB = new JButton("Löschen");
		deleteB.setBackground(Color.RED);
		deleteB.setActionCommand("delete");
		deleteB.addActionListener(this);
		deleteB.setBounds(145, 166, 80, 20);
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
		
		JButton btnNewButton = new JButton();
		btnNewButton.setIcon(new ImageIcon(KnotFrame.class.getResource("/icon_close.png")));
		btnNewButton.setBounds(185, 88, 19, 19);
		btnNewButton.addActionListener(this);
		btnNewButton.setActionCommand("delMainColor");
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton();
		btnNewButton_1.setIcon(new ImageIcon(KnotFrame.class.getResource("/icon_close.png")));
		btnNewButton_1.setBounds(168, 113, 19, 19);
		btnNewButton_1.addActionListener(this);
		btnNewButton_1.setActionCommand("delFontColor");
		getContentPane().add(btnNewButton_1);
		
		JLabel lblGre = new JLabel("Größe:");
		lblGre.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblGre.setBounds(28, 135, 50, 25);
		getContentPane().add(lblGre);
		
		
		int size = k.size; if(size == -1)size = gP.size;
		SpinnerModel model = new SpinnerNumberModel(size, 25, 100, 1);
		spinner = new JSpinner();
		spinner.setModel(model);
		((NumberFormatter)((JSpinner.NumberEditor)spinner.getEditor()).getTextField().getFormatter()).setAllowsInvalid(true);
		if(k.size == -1) 
			spinner.setEnabled(false);
		spinner.setBounds(79, 139, 40, 20);
		spinner.addKeyListener( new KeyAdapter() {
            @Override
            public void keyReleased( final KeyEvent e ) {
                if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
                	if((int)spinner.getValue() < 25)spinner.setValue(25);
                	if((int)spinner.getValue() > 100)spinner.setValue(100);
                }
            }
        } );
		getContentPane().add(spinner);
		
		sizeCheck = new JCheckBox();
		sizeCheck.setBounds(120, 137, 25, 25);
		if(k.size != -1)
			sizeCheck.setSelected(true);
		sizeCheck.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					spinner.setEnabled(true);
				} else {
					spinner.setEnabled(false);
				}
			}
		});
		getContentPane().add(sizeCheck);
		
		JLabel lblNewLabel_1 = new JLabel(" (25-100)");
		lblNewLabel_1.setFont(new Font("Dialog", Font.ITALIC, 11));
		lblNewLabel_1.setBounds(141, 142, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		repaint();
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("save")) {
			k.knotName = nameTF.getText();
			String s = ((String) comboBox.getSelectedItem()).replaceAll(" ", "");
			k.knotType = Enum.valueOf(KnotenType.class, s);
			if(!sizeCheck.isEnabled()) {
				k.size = -1;
			} else {
				k.size = (int) spinner.getValue();
			}
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
			cB.repaint();
		} else if(e.getActionCommand().equalsIgnoreCase("fontColor")) {
			Color c = gP.fontColor;
			if(k.font != null) c = k.font;
			Color ausgewaehlteFarbe = JColorChooser.showDialog(null, "Textfarbauswahl", c);
			if(ausgewaehlteFarbe == null) return;
			k.font = ausgewaehlteFarbe;
			cB2.c = ausgewaehlteFarbe;
			cB2.repaint();
		} else if(e.getActionCommand().equalsIgnoreCase("delMainColor")) {
			k.main = null;
			cB.c = gP.mainColor;
			cB.repaint();
		} else if(e.getActionCommand().equalsIgnoreCase("delFontColor")) {
			k.font = null;
			cB2.c = gP.fontColor;
			cB2.repaint();
		} 
	}
}
