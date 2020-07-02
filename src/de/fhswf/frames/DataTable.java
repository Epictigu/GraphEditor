package de.fhswf.frames;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class DataTable extends JDialog{
	
	private static final long serialVersionUID = -3235110609477155138L;
	public JTable kanten;
	public JTable knoten;

	public DataTable() {
		setTitle("Datentabelle");
		setBounds(0, 0, 240, 285);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JScrollPane knotenPane = new JScrollPane();
		tabbedPane.addTab("Knoten", null, knotenPane, null);
		
		knoten = new JTable();
		knoten.setEnabled(false);
		knoten.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"ID", "Name"
			}
		));
		knotenPane.setViewportView(knoten);
		
		JScrollPane kantenPane = new JScrollPane();
		tabbedPane.addTab("Kanten", null, kantenPane, null);
		
		kanten = new JTable();
		kanten.setEnabled(false);
		kanten.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"ID", "Länge", "Knoten"
			}
		));
		kantenPane.setViewportView(kanten);
		
		tabbedPane.setSelectedIndex(0);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		setVisible(true);
	}
	
	public void addRowKanten(String id, String length, String con) {
		((DefaultTableModel) kanten.getModel()).addRow(new Object[] {id, length, con});
	}
	
	public void addRowKnoten(String id, String name) {
		((DefaultTableModel) knoten.getModel()).addRow(new Object[] {id, name});
	}	
	
}
