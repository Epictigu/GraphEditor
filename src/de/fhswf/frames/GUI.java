package de.fhswf.frames;

import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.fhswf.Main;
import de.fhswf.manager.EditorManager;
import de.fhswf.manager.MenuManager;
import de.fhswf.utils.FrameSize;
import de.fhswf.utils.Graph;
import de.fhswf.utils.GraphPainter;

public class GUI extends JFrame{

	private static final long serialVersionUID = 1L;

	public GraphPainter k;
	public EditorManager eM;

	public GUI(Graph g) {
		initWindow(g, FrameSize.Small);
	}

	public GUI(Graph g, FrameSize size) {
		initWindow(g, size);
	}

	private void initWindow(Graph g, FrameSize size) {
		setTitle("Graphen-Editor");
		if (g != null)
			setTitle(getTitle() + " - " + g.getPath());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(size.width, size.height + 50);
		setResizable(false);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setBounds(getX() + (25 * Main.guiList.size()), getY() + (25 * Main.guiList.size()), size.width,
				size.height + 50);
		try {
			setIconImage(ImageIO.read(getClass().getResource("..\\resources\\icon_main_2.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

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
		k.setBounds(0, 0, size.width - 57, size.height);
		if (g != null)
			k.setFile(g);
		k.setToolTipText("Platzhalter");
		getContentPane().add(k);

		eM = new EditorManager();
		eM.initEditor(this);

		setJMenuBar(new MenuManager().initMenu(this));

		setVisible(true);
	}

}
