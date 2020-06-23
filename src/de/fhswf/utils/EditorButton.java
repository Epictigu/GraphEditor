package de.fhswf.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JButton;

public class EditorButton extends JButton{
	
	private static final long serialVersionUID = -2398739829611011274L;
	private boolean isHovering = false;
	
	public EditorButton(Icon mainIcon) {
		super(mainIcon);
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				isHovering = true;
			}
			public void mouseExited(MouseEvent e) {
				isHovering = false;
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		if(isSelected()) {
			g.setColor(new Color(0, 0, 0, 102));
			g.fillRect(0, 0, getWidth(), getHeight());
		} else if(isHovering) {
			g.setColor(new Color(0, 0, 0, 51));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		super.paint(g);
	}
	
	
}
