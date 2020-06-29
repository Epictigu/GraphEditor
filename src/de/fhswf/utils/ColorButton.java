package de.fhswf.utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;

import javax.swing.JButton;

public class ColorButton extends JButton{
	
	private static final long serialVersionUID = -9073214470757987653L;
	public Color c;
	
	public ColorButton(Color c) {
		super();
		this.c = c;
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(c);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}
	
}
