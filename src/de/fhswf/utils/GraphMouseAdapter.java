package de.fhswf.utils;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import de.fhswf.GraphPainter;

public class GraphMouseAdapter extends MouseAdapter{
	
	private GraphPainter gP;
	public GraphMouseAdapter(GraphPainter gP) {
		this.gP = gP;
	}
	
	public void mousePressed(MouseEvent e) {
		if(gP.eM == EditorMode.SelectKnoten) {
			int i = gP.getCurrentCircle(e);
			if(i > 0) {
				gP.setCurrentCircle(i);
			}
		} else if(gP.eM == EditorMode.AddKnoten) {
			String s = JOptionPane.showInputDialog("Knotenname:");
			if(s == null) return;
			Point cP = e.getPoint();
			cP.setLocation(cP.getX() - gP.getInnerDiameter() / 2, cP.getY() - gP.getInnerDiameter() / 2);
			gP.graph.addKnoten(s, cP);
			gP.repaint();
		}
	}
	public void mouseReleased(MouseEvent e) {
		if(gP.eM == EditorMode.SelectKnoten) {
			gP.setCurrentCircle(0);
		}
	}
	public void mouseDragged(MouseEvent e) {
		if(gP.getCurrentCircle() > 0) {
			int x = e.getX();
			int y = e.getY();
			int iR = gP.getInnerDiameter() / 2;
			
			if(x < iR) x = iR;
			if(y < iR) y = iR;
			if(x > gP.getWidth() - iR) x = gP.getWidth() - iR;
			if(y > gP.getHeight() - iR) y = gP.getHeight() - iR;
			
			gP.graph.knotList.get(gP.getCurrentCircle() - 1).pos.setLocation(x - (iR), y - (iR));
			gP.repaint();
		}
	}
	public void mouseMoved(MouseEvent e) {
		if(gP.eM == EditorMode.SelectKnoten) {
			int i = gP.getCurrentCircle(e);
			if(i > 0) gP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			else gP.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
}
