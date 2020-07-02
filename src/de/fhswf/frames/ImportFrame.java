package de.fhswf.frames;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import de.fhswf.utils.FrameSize;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImportFrame extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 2327052766844304740L;
	private JTextField gdiPath;
	private JTextField gdipPath;
	
	
	public ImportFrame() {
		setTitle("Graphimportierung");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(282, 200);
		setLocationRelativeTo(null);
		setModal(true);
		
		gdiPath = new JTextField();
		gdiPath.setEditable(false);
		gdiPath.setBounds(81, 11, 138, 20);
		getContentPane().add(gdiPath);
		gdiPath.setColumns(10);
		
		JLabel gdiLabel = new JLabel("\".gdi\"-Datei:");
		gdiLabel.setFont(new Font("Dialog", Font.PLAIN, 13));
		gdiLabel.setBounds(10, 12, 69, 17);
		getContentPane().add(gdiLabel);
		
		JButton gdiButton = new JButton();
		try {
			gdiButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("..\\resources\\icon_folder.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		gdiButton.setActionCommand("gdiSel");
		gdiButton.addActionListener(this);
		gdiButton.setBounds(223, 11, 20, 20);
		getContentPane().add(gdiButton);
		
		JLabel gdipLabel = new JLabel("\".gdip\"-Datei:");
		gdipLabel.setFont(new Font("Dialog", Font.PLAIN, 13));
		gdipLabel.setBounds(10, 39, 76, 17);
		getContentPane().add(gdipLabel);
		
		gdipPath = new JTextField();
		gdipPath.setEditable(false);
		gdipPath.setColumns(10);
		gdipPath.setBounds(89, 38, 130, 20);
		getContentPane().add(gdipPath);
		
		JButton gdipButton = new JButton();
		try {
			gdipButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("..\\resources\\icon_folder.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		gdipButton.setActionCommand("gdipSel");
		gdipButton.addActionListener(this);
		gdipButton.setBounds(223, 38, 20, 20);
		getContentPane().add(gdipButton);
		
		JLabel frameSizeL = new JLabel("Framegr\u00F6\u00DFe:");
		frameSizeL.setFont(new Font("Dialog", Font.PLAIN, 13));
		frameSizeL.setBounds(10, 75, 76, 17);
		getContentPane().add(frameSizeL);
		
		JComboBox<Object> frameSizeB = new JComboBox<Object>();
		frameSizeB.setModel((ComboBoxModel<Object>) new DefaultComboBoxModel<Object>(FrameSize.values()));
		frameSizeB.setSelectedIndex(0);
		frameSizeB.setEnabled(false);
		frameSizeB.setBounds(88, 74, 61, 20);
		getContentPane().add(frameSizeB);
		
		JLabel knotSizeL = new JLabel("Knotengr\u00F6\u00DFe:");
		knotSizeL.setFont(new Font("Dialog", Font.PLAIN, 13));
		knotSizeL.setBounds(10, 100, 84, 17);
		getContentPane().add(knotSizeL);
		
		JSpinner knotSizeS = new JSpinner();
		knotSizeS.setModel(new SpinnerNumberModel(65, 25, 100, 1));
		knotSizeS.setBounds(92, 99, 46, 20);
		knotSizeS.setEnabled(false);
		getContentPane().add(knotSizeS);
		
		JLabel knotSizePL = new JLabel("(In Pixeln | 25 - 100)");
		knotSizePL.setFont(new Font("Dialog", Font.PLAIN, 13));
		knotSizePL.setBounds(143, 100, 123, 17);
		getContentPane().add(knotSizePL);
		
		JButton importButton = new JButton("Importieren");
		importButton.setBounds(70, 128, 130, 23);
		importButton.addActionListener(this);
		importButton.setActionCommand("import");
		getContentPane().add(importButton);
		
		JCheckBox gdipCheck = new JCheckBox("");
		gdipCheck.setBounds(243, 35, 20, 25);
		gdipCheck.setToolTipText("Aktiviert/Deaktiviert die Nutzung der \".gdip\"-Datei.");
		gdipCheck.setSelected(true);
		gdipCheck.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				frameSizeB.setEnabled(!gdipCheck.isSelected());
				knotSizeS.setEnabled(!gdipCheck.isSelected());
				gdipButton.setEnabled(gdipCheck.isSelected());
				repaint();
			}
		});
		getContentPane().add(gdipCheck);
		
		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("gdiSel")) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("GDIDatei", "gdi");
			fc.setFileFilter(filter);

			fc.setCurrentDirectory(new File("."));
			fc.setDialogTitle(".gdi Datei auswählen");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				String path = fc.getSelectedFile().getAbsolutePath();
				if (!path.toLowerCase().endsWith(".gdi"))
					return;
				gdiPath.setText(path);
			}
		} else if(e.getActionCommand().equalsIgnoreCase("gdipSel")) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("GDIPDatei", "gdip");
			fc.setFileFilter(filter);

			fc.setCurrentDirectory(new File("."));
			fc.setDialogTitle(".gdip Datei auswählen");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				String path = fc.getSelectedFile().getAbsolutePath();
				if (!path.toLowerCase().endsWith(".gdip"))
					return;
				gdipPath.setText(path);
			}
		}
	}
}
