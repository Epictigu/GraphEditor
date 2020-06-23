package de.fhswf;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class EditorPanel extends JPanel{
	
	private static final long serialVersionUID = -4475363763984235267L;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 2, getHeight());
	}
	
}
