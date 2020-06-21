package de.fhswf.utils;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import de.fhswf.GraphPainter;

public class GraphMouseAdapter extends MouseAdapter{
	
	private GraphPainter gP;
	public GraphMouseAdapter(GraphPainter gP) {
		this.gP = gP;
	}
	
	public void mousePressed(MouseEvent e) {
		int i = gP.getCurrentCircle(e);
		if(i > 0) {
			gP.setCurrentCircle(i);
		}
	}
	public void mouseReleased(MouseEvent e) {
		gP.setCurrentCircle(0);
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
			
			gP.getKnots().get(gP.getCurrentCircle() - 1).pos.setLocation(x - (iR), y - (iR));
			gP.repaint();
		}
	}
	public void mouseMoved(MouseEvent e) {
		int i = gP.getCurrentCircle(e);
		if(i > 0) gP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else gP.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
}
