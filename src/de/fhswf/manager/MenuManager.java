package de.fhswf.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.fhswf.Main;
import de.fhswf.frames.DataTable;
import de.fhswf.frames.GUI;
import de.fhswf.frames.ImportFrame;
import de.fhswf.utils.FrameSize;
import de.fhswf.utils.Graph;
import de.fhswf.utils.Kanten;
import de.fhswf.utils.Knoten;
import de.fhswf.utils.Themes;

public class MenuManager implements ActionListener {

	private GUI guiInstance;
	private JMenuBar menuBar;
	private JMenu menu, edit, settings;
	private ButtonGroup group;

	public JMenuBar initMenu(GUI f) {
		guiInstance = f;
		menuBar = new JMenuBar();
		menu = new JMenu("File");

		addMenuItem("Select File", "selectFile", "resources/folder.png");
		addMenuItem("Datatable", "dataTable");
		addMenuItem("Save", "saveFile");
		addMenuItem("Save As...", "saveFileAs");
		addMenuItem("New Window", "openWindow", "resources/newWindow.png");
		addMenuItem("Reset Graph", "reset");
		menu.addSeparator();
		addMenuItem("Exit", "exitWindow");

		edit = new JMenu("Themes");
		settings = new JMenu("Settings");
		group = new ButtonGroup();

		for (Themes t : Themes.values()) {
			addThemeButton(t.buttonText, "theme_" + t.name());
		}

		JCheckBoxMenuItem tV = new JCheckBoxMenuItem("Knotennamen anzeigen");
		tV.setSelected(true);
		tV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiInstance.k.tV = tV.isSelected();
				guiInstance.k.repaint();
			}
		});
		settings.add(tV);

		JCheckBoxMenuItem lV = new JCheckBoxMenuItem("Kantenlänge anzeigen");
		lV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiInstance.k.lV = lV.isSelected();
				guiInstance.k.repaint();
			}
		});
		settings.add(lV);

		menuBar.add(menu);
		menuBar.add(edit);
		menuBar.add(settings);

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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("selectFile")) {
			if (new ImportFrame().successful) {
				if (guiInstance.k.graph.knotList.isEmpty() && guiInstance.k.graph.edgeList.isEmpty()) {
					guiInstance.dispose();
				}
			}
		} else if (e.getActionCommand().startsWith("theme")) {
			String theme = e.getActionCommand().split("_")[1];
			Themes t = Enum.valueOf(Themes.class, theme);

			guiInstance.k.backgroundColor = t.backgroundColor;
			guiInstance.k.mainColor = t.mainColor;
			guiInstance.k.fontColor = t.fontColor;
			guiInstance.k.overlappingEdge = t.overlappingColor;
			guiInstance.k.gridColor = t.gridColor;
			guiInstance.eM.slider.setBackground(t.mainColor);

			guiInstance.eM.edPanel.setBackground(t.mainColor);
			guiInstance.k.repaint();
		} else if (e.getActionCommand().equalsIgnoreCase("openWindow")) {
			openNewFrame(new Graph(""));
		} else if (e.getActionCommand().equalsIgnoreCase("exitWindow")) {
			guiInstance.dispose();
		} else if (e.getActionCommand().equalsIgnoreCase("reset")) {
			guiInstance.k.reset();
		} else if (e.getActionCommand().equalsIgnoreCase("saveFile")) {
			if (guiInstance.k.graph.getPath() != null) {
				FileManager.writeFile(guiInstance.k.graph.getPath(), guiInstance.k.graph, guiInstance.k.size,
						guiInstance.frameSize);
			} else {
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

					FileManager.writeFile(path, guiInstance.k.graph, guiInstance.k.size, guiInstance.frameSize);
				}
			}
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

				guiInstance.k.graph.setPath(path);
				FileManager.writeFile(path, guiInstance.k.graph, guiInstance.k.size, guiInstance.frameSize);
			}
		} else if (e.getActionCommand().equalsIgnoreCase("dataTable")) {
			DataTable dt = new DataTable();
			for (int i = 0; i < guiInstance.k.graph.knotList.size(); i++) {
				Knoten k = guiInstance.k.graph.knotList.get(i);
				dt.addRowKnoten((i + 1) + "", k.knotName);
			}
			for (int i = 0; i < guiInstance.k.graph.edgeList.size(); i++) {
				Kanten k = guiInstance.k.graph.edgeList.get(i);
				dt.addRowKanten((i + 1) + "", k.länge + "", (guiInstance.k.graph.getKnotPosInList(k.k1) + 1) + " - "
						+ (guiInstance.k.graph.getKnotPosInList(k.k2) + 1));
			}
		}
	}

}
