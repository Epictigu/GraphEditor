package de.fhswf.manager;

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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.fhswf.Main;
import de.fhswf.frames.GUI;
import de.fhswf.utils.FrameSize;
import de.fhswf.utils.Graph;
import de.fhswf.utils.Themes;

public class MenuManager implements ActionListener, ChangeListener {

	private GUI guiInstance;
	private JMenuBar menuBar;
	private JMenu menu, edit, customColors;
	private ButtonGroup group;
	private JLabel pixelSize;

	public JMenuBar initMenu(GUI f) {
		guiInstance = f;
		menuBar = new JMenuBar();
		menu = new JMenu("File");

		addMenuItem("Select File", "selectFile", "resources/folder.png");
		addMenuItem("Save", "saveFile");
		addMenuItem("Save As...", "saveFileAs");
		addMenuItem("New Window", "openWindow", "resources/newWindow.png");
		addMenuItem("Reset Graph", "reset");
		menu.addSeparator();
		addMenuItem("Exit", "exitWindow");

		edit = new JMenu("Edit");
		customColors = new JMenu("Custom Colors");
		group = new ButtonGroup();

		for (Themes t : Themes.values()) {
			addThemeButton(t.buttonText, "theme_" + t.name());
		}

		edit.add(customColors);
		customColors.setEnabled(false);

		addCustomColorButton("Backgroundcolor", "custom_1");
		addCustomColorButton("Graphcolor", "custom_2");
		addCustomColorButton("Fontcolor", "custom_3");
		addCustomColorButton("Intersectioncolor", "custom_4");

		menuBar.add(menu);
		edit.addSeparator();
		edit.addSeparator();

		JMenu knG = new JMenu("Knotengröße");
		edit.add(knG);

		JPanel pH = new JPanel();

		JSlider slider = new JSlider(25, 100);
		slider.setValue(80);
		slider.setMajorTickSpacing(50);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(this);
		knG.add(slider);

		pixelSize = new JLabel("in Pixel: 80");
		pH.add(pixelSize);

		knG.add(pH);

		menuBar.add(edit);
		return menuBar;
	}

	private JMenuItem addMenuItem(String text, String actionCommand) {
		return addMenuItem(text, actionCommand, "");
	}

	private JMenuItem addMenuItem(String text, String actionCommand, String iconPath) {
		JMenuItem jMI = null;
		if (!iconPath.equalsIgnoreCase("")) {
			try {
				jMI = new JMenuItem(text, new ImageIcon(ImageIO.read(getClass().getResource("..\\" + iconPath))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			jMI = new JMenuItem(text);
		}
		jMI.addActionListener(this);
		jMI.setActionCommand(actionCommand);

		menu.add(jMI);
		return jMI;
	}

	private JRadioButtonMenuItem addThemeButton(String text, String actionCommand) {
		JRadioButtonMenuItem theme = new JRadioButtonMenuItem(text);
		theme.setSelected(false);
		theme.addActionListener(this);
		theme.setActionCommand(actionCommand);
		group.add(theme);
		edit.add(theme);

		return theme;
	}

	private JMenuItem addCustomColorButton(String text, String actionCommand) {
		JMenuItem jMI = new JMenuItem(text);
		jMI.addActionListener(this);
		jMI.setActionCommand(actionCommand);

		customColors.add(jMI);
		return jMI;
	}

	private void openNewFrame(Graph g) {
		String[] options = { "Klein", "Mittel", "Groß" };
		int x = JOptionPane.showOptionDialog(null, "Bitte wählen Sie eine Fenstergröße:", "Fenstergröße",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
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
	}

	private void openGraph(String path) {
		Graph g = FileManager.readFileScanner(path);

		if (guiInstance.k.graph != null) {
			int result = JOptionPane.showConfirmDialog(null, "Soll der Graph in einem neuen Fenster geöffnet werden?");
			if (result == 2)
				return;
			if (result == 0) {
				openNewFrame(g);
				return;
			}
		}

		guiInstance.setTitle("Graphen-Editor - " + g.getPath());
		guiInstance.k.setFile(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("selectFile")) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("GDIDatei", "gdi");
			fc.setFileFilter(filter);

			fc.setCurrentDirectory(new File("."));
			fc.setDialogTitle(".gdi Datei auswählen");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (fc.showOpenDialog(guiInstance) == JFileChooser.APPROVE_OPTION) {
				String path = fc.getSelectedFile().getAbsolutePath();
				if (!path.toLowerCase().endsWith(".gdi"))
					return;
				openGraph(path);
			}

		} else if (e.getActionCommand().startsWith("theme")) {
			String theme = e.getActionCommand().split("_")[1];
			Themes t = Enum.valueOf(Themes.class, theme);

			guiInstance.k.backgroundColor = t.backgroundColor;
			guiInstance.k.mainColor = t.mainColor;
			guiInstance.k.fontColor = t.fontColor;
			guiInstance.k.overlappingEdge = t.overlappingColor;
			if (t == Themes.custom)
				customColors.setEnabled(true);
			else
				customColors.setEnabled(false);

			guiInstance.eM.edPanel.setBackground(t.mainColor);
			guiInstance.k.repaint();
		} else if (e.getActionCommand().startsWith("custom")) {
			Color newColor = JColorChooser.showDialog(guiInstance, "Choose a Background Color", new Color(0, 0, 0));
			if (newColor == null)
				return;
			int i = Integer.parseInt(e.getActionCommand().split("_")[1]);
			switch (i) {
			case 1:
				guiInstance.k.backgroundColor = newColor;
				break;
			case 2:
				guiInstance.k.mainColor = newColor;
				break;
			case 3:
				guiInstance.k.fontColor = newColor;
				break;
			case 4:
				guiInstance.k.overlappingEdge = newColor;
				break;
			}
			guiInstance.repaint();
		} else if (e.getActionCommand().equalsIgnoreCase("openWindow")) {
			openNewFrame(null);
		} else if (e.getActionCommand().equalsIgnoreCase("exitWindow")) {
			guiInstance.dispose();
		} else if (e.getActionCommand().equalsIgnoreCase("reset")) {
			guiInstance.k.reset();
		} else if (e.getActionCommand().equalsIgnoreCase("saveFileAs")) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("GDIDatei", "gdi");
			fc.setFileFilter(filter);

			fc.setCurrentDirectory(new File("."));
			fc.setDialogTitle("Speicherort wählen");
			// fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			int retrival = fc.showSaveDialog(null);
			if (retrival == JFileChooser.APPROVE_OPTION) {
				String path = fc.getSelectedFile().getAbsolutePath();

				if (!path.toLowerCase().endsWith(".gdi")) {
					path = path + ".gdi";
				}

				FileManager.writeFile(path, guiInstance.k.graph);
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider js = (JSlider) e.getSource();

		guiInstance.k.size = js.getValue();
		pixelSize.setText("in Pixel: " + js.getValue());
		guiInstance.k.repaint();
	}

}
