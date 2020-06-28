package de.fhswf.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.fhswf.utils.GraphPainter;
import de.fhswf.utils.Kanten;

public class EdgeFrame extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 107265808655190614L;
	
	private GraphPainter gP;
	private Kanten k;
	private JTextField l�ngeTF;
	
	public EdgeFrame(GraphPainter gP, Kanten k) {
		this.gP = gP;
		this.k = k;
		setSize(250, 130);
		setTitle("Kantendetails");
		setLocationRelativeTo(null);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		
		JLabel idL = new JLabel("Verlauf: " + (gP.graph.getKnotPosInList(k.k1) + 1) + " - " + (gP.graph.getKnotPosInList(k.k2) + 1));
		idL.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
		idL.setBounds(28, 10, 200, 25);
		add(idL);
		
		JLabel nameL = new JLabel("L�nge: ");
		nameL.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
		nameL.setBounds(28, 35, 200, 25);
		add(nameL);
		
		l�ngeTF = new JTextField("" + k.l�nge);
		l�ngeTF.setBounds(75, 35, 80, 25);
		l�ngeTF.setEditable(false);
		add(l�ngeTF);
		
		JCheckBox nameCheck = new JCheckBox();
		nameCheck.setBounds(155, 35, 25, 25);
		nameCheck.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					l�ngeTF.setEditable(true);
				} else {
					l�ngeTF.setEditable(false);
				}
			}
		});
		add(nameCheck);
		
		
		JButton closeB = new JButton("Speichern");
		closeB.setBounds(25, 70, 80, 20);
		closeB.setActionCommand("save");
		closeB.addActionListener(this);
		add(closeB);
		JButton deleteB = new JButton("L�schen");
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
			try {
				int l = Integer.parseInt(l�ngeTF.getText());
				if(l < 0) {
					JOptionPane.showMessageDialog(null, "Ung�ltige Kantenl�nge angegeben. (>=0 erforderlich)");
					dispose();
					return;
				}
				k.l�nge = l;
				gP.repaint();
				dispose();
			} catch (NumberFormatException e2) {
				dispose();
				JOptionPane.showMessageDialog(null, "Ung�ltige L�nge");
			}
		} else if(e.getActionCommand().equalsIgnoreCase("delete")) {
			gP.graph.edgeList.remove(k);
			gP.repaint();
			dispose();
		}
	}
	
}
