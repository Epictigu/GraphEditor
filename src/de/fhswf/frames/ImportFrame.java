package de.fhswf.frames;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.fhswf.Main;
import de.fhswf.manager.FileManager;
import de.fhswf.utils.FrameSize;
import de.fhswf.utils.Graph;

public class ImportFrame extends JDialog implements ActionListener {

	private static final long serialVersionUID = 2327052766844304740L;
	private JTextField gdiPath;
	private JTextField gdipPath;
	private JCheckBox gdipCheck;
	private JComboBox<Object> frameSizeB;
	private JSpinner knotSizeS;
	public boolean successful = false;

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
			gdiButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icon_folder.png"))));
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
			gdipButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icon_folder.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		gdipButton.setActionCommand("gdipSel");
		gdipButton.addActionListener(this);
		gdipButton.setBounds(223, 38, 20, 20);
		getContentPane().add(gdipButton);

		JLabel frameSizeL = new JLabel("Framegröße:");
		frameSizeL.setFont(new Font("Dialog", Font.PLAIN, 13));
		frameSizeL.setBounds(10, 75, 76, 17);
		getContentPane().add(frameSizeL);

		frameSizeB = new JComboBox<Object>();
		frameSizeB.setModel((ComboBoxModel<Object>) new DefaultComboBoxModel<Object>(FrameSize.values()));
		frameSizeB.setSelectedIndex(0);
		frameSizeB.setBounds(88, 74, 61, 20);
		getContentPane().add(frameSizeB);

		JLabel knotSizeL = new JLabel("Knotengröße:");
		knotSizeL.setFont(new Font("Dialog", Font.PLAIN, 13));
		knotSizeL.setBounds(10, 100, 84, 17);
		getContentPane().add(knotSizeL);

		knotSizeS = new JSpinner();
		knotSizeS.setModel(new SpinnerNumberModel(65, 25, 100, 1));
		knotSizeS.setBounds(92, 99, 46, 20);
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

		gdipCheck = new JCheckBox("");
		gdipCheck.setBounds(243, 35, 20, 25);
		gdipCheck.setToolTipText("Aktiviert/Deaktiviert die Nutzung der \".gdip\"-Datei.");
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
		if (e.getActionCommand().equalsIgnoreCase("gdiSel")) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("GDIDatei", "gdi");
			fc.setFileFilter(filter);

			fc.setCurrentDirectory(new File("."));
			fc.setDialogTitle(".gdi Datei ausw�hlen");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				String path = fc.getSelectedFile().getAbsolutePath();
				if (!path.toLowerCase().endsWith(".gdi"))
					return;
				gdiPath.setText(path);
				File f = new File(path + "p");
				if (f.exists()) {
					gdipPath.setText(f.getPath());
					gdipCheck.setSelected(true);
					frameSizeB.setEnabled(false);
					knotSizeS.setEnabled(false);
				}
			}
		} else if (e.getActionCommand().equalsIgnoreCase("gdipSel")) {
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
		} else if (e.getActionCommand().equalsIgnoreCase("import")) {
			if (gdiPath.getText().equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(null, "Wähle zuerst eine \".gdi\"-Datei aus.");
				return;
			}
			if (!(new File(gdiPath.getText()).exists())) {
				JOptionPane.showMessageDialog(null, "Ungültige Datei ausgewählt.");
				return;
			}
			Graph g = null;
			if(gdipCheck.isSelected()) {
				g = FileManager.readFileScanner(gdiPath.getText(), gdipPath.getText());
			} else {
				g = FileManager.readFileScanner(gdiPath.getText(), null);
				g.fSize = Enum.valueOf(FrameSize.class, frameSizeB.getSelectedItem() + "");
				try {
					int i = (Integer) knotSizeS.getValue();
					if (i < 25)
						i = 25;
					if (i > 100)
						i = 100;
					g.kSize = i;
				} catch (Exception e2) {
					e2.printStackTrace();
					g.kSize = 65;
				}
			}
			Main.openNewFrame(g, g.fSize);
			successful = true;
			dispose();
		}
	}
}
