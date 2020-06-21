package de.fhswf;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.fhswf.utils.FrameSize;
import de.fhswf.utils.Graph;

public class GUI extends JFrame implements ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	public GraphPainter k;

	private JMenuBar menuBar;
	private JMenu menu, edit, customColors;
	private JMenuItem selectFile, exitWindow, openWindow, backgroundcolor, graphcolor, fontcolor,
			overlappingEdgeColor;

	private boolean allowCustomColors = false;
	
	public GUI(Graph g) {
		initWindow(g, FrameSize.Small);
	}

	public GUI(Graph g, FrameSize size) {
		initWindow(g, size);
	}

	private void initWindow(Graph g, FrameSize size) {
		setTitle("GDI Projekt");
		if (g != null)
			setTitle(getTitle() + " - " + g.getPath());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(size.width, size.height + 50);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null); // setzt das Fenster in die Mitte des Bildschirms
		setBounds(getX() + (25 * Main.guiList.size()), getY() + (25 * Main.guiList.size()), size.width, size.height + 50);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		getContentPane().setBackground(new Color(51, 51, 51));

		k = new GraphPainter(size);
		k.setBounds(0, 0, size.width - 7, size.height);
		if (g != null)
			k.setFile(g);
		k.setToolTipText("Platzhalter");
		add(k);

		menuBar = new JMenuBar();

		// File
		menu = new JMenu("File");

		try {
			selectFile = new JMenuItem("Select File", new ImageIcon(ImageIO.read(getClass().getResource("resources/folder.png"))));
			selectFile.addActionListener(this);
			selectFile.setActionCommand("selectFile");
			menu.add(selectFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			openWindow = new JMenuItem("New Window", new ImageIcon(ImageIO.read(getClass().getResource("resources/newWindow.png"))));
			openWindow.addActionListener(this);
			openWindow.setActionCommand("openWindow");
			menu.add(openWindow);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JMenuItem reset = new JMenuItem("Reset Graph");
		reset.addActionListener(this);
		reset.setActionCommand("reset");
		menu.add(reset);

		menu.addSeparator();

		exitWindow = new JMenuItem("Exit");
		exitWindow.addActionListener(this);
		exitWindow.setActionCommand("exitWindow");
		menu.add(exitWindow);

		// --------------------------------- Edit -------------------------
		edit = new JMenu("Edit");

		customColors = new JMenu("Custom Colors");

		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem defaultTheme, darkTheme, wunderlandTheme, customTheme, iceTheme, orangeTheme;

		defaultTheme = new JRadioButtonMenuItem("Default Color Theme");
		defaultTheme.setSelected(true);
		defaultTheme.addActionListener(this);
		defaultTheme.setActionCommand("defaultTheme");
		group.add(defaultTheme);
		edit.add(defaultTheme);

		darkTheme = new JRadioButtonMenuItem("Dark Color Theme");
		darkTheme.setSelected(false);
		darkTheme.addActionListener(this);
		darkTheme.setActionCommand("darkTheme");
		group.add(darkTheme);
		edit.add(darkTheme);

		wunderlandTheme = new JRadioButtonMenuItem("Wunderland Color Theme");
		wunderlandTheme.setSelected(false);
		wunderlandTheme.addActionListener(this);
		wunderlandTheme.setActionCommand("wunderlandTheme");
		group.add(wunderlandTheme);
		edit.add(wunderlandTheme);

		iceTheme = new JRadioButtonMenuItem("Ice Color Theme");
		iceTheme.setSelected(false);
		iceTheme.addActionListener(this);
		iceTheme.setActionCommand("iceTheme");
		group.add(iceTheme);
		edit.add(iceTheme);

		orangeTheme = new JRadioButtonMenuItem("Orange Color Theme");
		orangeTheme.setSelected(false);
		orangeTheme.addActionListener(this);
		orangeTheme.setActionCommand("orangeTheme");
		group.add(orangeTheme);
		edit.add(orangeTheme);

		customTheme = new JRadioButtonMenuItem("Custom Color Theme");
		customTheme.setSelected(false);
		customTheme.addActionListener(this);
		customTheme.setActionCommand("customTheme");
		group.add(customTheme);
		edit.add(customTheme);

		edit.addSeparator();

		// ----------------------------- Custom ------------------------------
		edit.add(customColors);
		customColors.setEnabled(allowCustomColors);

		backgroundcolor = new JMenuItem("Backgroundcolor");
		backgroundcolor.addActionListener(this);
		backgroundcolor.setActionCommand("backgroundcolor");
		customColors.add(backgroundcolor);

		graphcolor = new JMenuItem("Graphcolor");
		graphcolor.addActionListener(this);
		graphcolor.setActionCommand("graphcolor");
		customColors.add(graphcolor);

		fontcolor = new JMenuItem("Fontcolor");
		fontcolor.addActionListener(this);
		fontcolor.setActionCommand("fontcolor");
		customColors.add(fontcolor);

		overlappingEdgeColor = new JMenuItem("Intersectioncolor");
		overlappingEdgeColor.addActionListener(this);
		overlappingEdgeColor.setActionCommand("overlappingEdgeColor");
		customColors.add(overlappingEdgeColor);

		menuBar.add(menu);
		edit.add(edit);

		edit.addSeparator();

		JMenu knG = new JMenu("Knotengröße");
		edit.add(knG);

		JSlider slider = new JSlider(50, 150);
		slider.setMajorTickSpacing(50);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(this);
		knG.add(slider);

		menuBar.add(edit);

		setJMenuBar(menuBar);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ------------------------------- SelectFile ------------------------------
		if (e.getActionCommand().equalsIgnoreCase("selectFile")) {

			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("GDIDatei", "gdi");
			fc.setFileFilter(filter);

			fc.setCurrentDirectory(new File("."));
			fc.setDialogTitle(".gdi Datei auswählen");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				String path = fc.getSelectedFile().getAbsolutePath();
				if (!path.toLowerCase().endsWith(".gdi"))
					return;
				Graph g = ReadFile.readFileScanner(path);

				if (k.graph != null) {
					int result = JOptionPane.showConfirmDialog(null,
							"Soll der Graph in einem neuen Fenster geöffnet werden?");
					if (result == 2)
						return;
					if (result == 0) {
						String[] options = { "Klein", "Mittel", "Groß" };
						int x = JOptionPane.showOptionDialog(null, "Bitte wählen Sie eine Fenstergröße:",
								"Fenstergröße", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
								options, options[0]);
						switch (x) {
						case 0:
							Main.openNewFrame(g);
							break;
						case 1:
							Main.openNewFrame(g, FrameSize.Medium);
							break;
						case 2:
							Main.openNewFrame(g, FrameSize.Large);
							break;
						}
						return;
					}
				}

				setTitle("GDI Projekt - " + g.getPath());
				k.setFile(g);
			}

		}
		// ---------------------------- ColorThemes ------------------------
		else if (e.getActionCommand().equalsIgnoreCase("defaultTheme")) {
			allowCustomColors = false;
			customColors.setEnabled(allowCustomColors);
			Color backgroundColor = new Color(51, 51, 51);
			Color graphColor = Color.WHITE;
			Color fontColor = Color.BLACK;
			Color overlappingEdge = Color.RED;
			k.backgroundColor = backgroundColor;
			k.mainColor = graphColor;
			k.fontColor = fontColor;
			k.overlappingEdge = overlappingEdge;
			k.repaint();
		} else if (e.getActionCommand().equalsIgnoreCase("darkTheme")) {
			allowCustomColors = false;
			customColors.setEnabled(allowCustomColors);
			Color backgroundColor = Color.BLACK;
			Color graphColor = new Color(100, 100, 100);
			Color fontColor = Color.BLACK;
			Color overlappingEdge = Color.RED;
			k.backgroundColor = backgroundColor;
			k.mainColor = graphColor;
			k.fontColor = fontColor;
			k.overlappingEdge = overlappingEdge;
			k.repaint();
		} else if (e.getActionCommand().equalsIgnoreCase("wunderlandTheme")) {
			allowCustomColors = false;
			customColors.setEnabled(allowCustomColors);
			Color backgroundColor = new Color(43, 45, 66);
			Color graphColor = new Color(239, 35, 60);
			Color fontColor = new Color(237, 242, 244);
			Color overlappingEdge = Color.WHITE;
			k.backgroundColor = backgroundColor;
			k.mainColor = graphColor;
			k.fontColor = fontColor;
			k.overlappingEdge = overlappingEdge;
			k.repaint();
		} else if (e.getActionCommand().equalsIgnoreCase("iceTheme")) {
			allowCustomColors = false;
			customColors.setEnabled(allowCustomColors);
			Color backgroundColor = Color.WHITE;
			Color graphColor = new Color(128, 255, 255);
			Color fontColor = Color.WHITE;
			Color overlappingEdge = Color.BLUE;
			k.backgroundColor = backgroundColor;
			k.mainColor = graphColor;
			k.fontColor = fontColor;
			k.overlappingEdge = overlappingEdge;
			k.repaint();
		} else if (e.getActionCommand().equalsIgnoreCase("orangeTheme")) {
			allowCustomColors = false;
			customColors.setEnabled(allowCustomColors);
			Color backgroundColor = Color.WHITE;
			Color graphColor = new Color(255, 96, 0);
			Color fontColor = Color.WHITE;
			Color overlappingEdge = Color.ORANGE;
			k.backgroundColor = backgroundColor;
			k.mainColor = graphColor;
			k.fontColor = fontColor;
			k.overlappingEdge = overlappingEdge;
			k.repaint();
		} else if (e.getActionCommand().equalsIgnoreCase("customTheme")) {
			allowCustomColors = true;
			customColors.setEnabled(allowCustomColors);
		}
		// --------------------------------CustomColors--------------------------------
		else if (e.getActionCommand().equalsIgnoreCase("backgroundcolor") && allowCustomColors) {
			Color newColor = JColorChooser.showDialog(this, "Choose a Background Color", new Color(51, 51, 51));
			if (newColor != null) {
				k.backgroundColor = newColor;
				k.repaint();
			}
		} else if (e.getActionCommand().equalsIgnoreCase("graphcolor") && allowCustomColors) {
			Color newColor = JColorChooser.showDialog(this, "Choose a Graph Color", Color.WHITE);
			if (newColor != null) {
				k.mainColor = newColor;
				k.repaint();
			}
		} else if (e.getActionCommand().equalsIgnoreCase("fontcolor") && allowCustomColors) {
			Color newColor = JColorChooser.showDialog(this, "Choose Font Color", Color.BLACK);
			if (newColor != null) {
				k.fontColor = newColor;
				k.repaint();
			}
		} else if (e.getActionCommand().equalsIgnoreCase("overlappingEdgeColor") && allowCustomColors) {
			Color newColor = JColorChooser.showDialog(this, "Choose overlapp Color", Color.BLACK);
			if (newColor != null) {
				k.overlappingEdge = newColor;
				k.repaint();
			}
		}
		// -------------------------------Utils-------------------------------------------------
		else if (e.getActionCommand().equalsIgnoreCase("openWindow")) {
			String[] options = { "Klein", "Mittel", "Groß" };
			int x = JOptionPane.showOptionDialog(null, "Bitte wählen Sie eine Fenstergröße:", "Fenstergröße",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			switch (x) {
			case 0:
				Main.openNewFrame(null);
				break;
			case 1:
				Main.openNewFrame(null, FrameSize.Medium);
				break;
			case 2:
				Main.openNewFrame(null, FrameSize.Large);
				break;
			}
		} else if (e.getActionCommand().equalsIgnoreCase("exitWindow")) {
			dispose();
		} else if (e.getActionCommand().equalsIgnoreCase("reset")) {
			k.reset();
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider js = (JSlider) e.getSource();
		
		k.size = (0.0 + js.getValue()) / 100;
		k.repaint();
	}
	
}
