package de.fhswf.utils;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import de.fhswf.GraphPainter;
import de.fhswf.frames.KnotFrame;

public class GraphMouseAdapter extends MouseAdapter{
	
	private GraphPainter gP;
	public GraphMouseAdapter(GraphPainter gP) {
		this.gP = gP;
	}
	
	public void mousePressed(MouseEvent e) {
		if(gP.eM == EditorMode.SelectKnoten) {
			int i = gP.getCurrentCircle(e);
			if(i > 0) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					gP.setCurrentCircle(i);
				} else if(SwingUtilities.isRightMouseButton(e)) {
					new KnotFrame(gP, gP.graph.knotList.get(i - 1), i);
				}
			}
		} else if(gP.eM == EditorMode.AddKnoten) {
			String s = JOptionPane.showInputDialog("Knotenname:");
			if(s == null) return;
			Point cP = e.getPoint();
			int x = (int) (cP.getX() - gP.getInnerDiameter() / 2);
			int y = (int) (cP.getY() - gP.getInnerDiameter() / 2);
			if(x < 0) x = 0; if(y < 0) y = 0;
			if(x > gP.getWidth() - gP.getInnerDiameter()) x = gP.getWidth() - gP.getInnerDiameter();
			if(y > gP.getHeight() - gP.getInnerDiameter()) y = gP.getHeight() - gP.getInnerDiameter();
			
			cP.setLocation(x, y);
			gP.graph.addKnoten(s, cP);
			gP.repaint();
		} else if(gP.eM == EditorMode.AddKante) {
			int i = gP.getCurrentCircle(e);
			if(i > 0) {
				if(gP.firstKnotenSel == 0) {
					gP.firstKnotenSel = i;
					gP.repaint();
					return;
				}
				if(gP.firstKnotenSel == i) return;
				
				for(Kanten k : gP.graph.edgeList) {
					if(k.k1 == gP.graph.knotList.get(i - 1) || k.k2 == gP.graph.knotList.get(i - 1)) {
						if(k.k1 == gP.graph.knotList.get(gP.firstKnotenSel - 1) || k.k2 == gP.graph.knotList.get(gP.firstKnotenSel - 1)) {
							gP.resetSelected();
							return;
						}
					}
				}
				
				gP.secondKnotenSel = i;
				gP.repaint();
				
				String s = JOptionPane.showInputDialog("Kantenlänge:");
				try {
					int l = Integer.parseInt(s);
					gP.graph.addKante(gP.graph.knotList.get(gP.firstKnotenSel - 1),
							gP.graph.knotList.get(i - 1), l);
					gP.resetSelected();
				} catch(NumberFormatException e2) {
					gP.firstKnotenSel = 0; gP.secondKnotenSel = 0;
					JOptionPane.showMessageDialog(null, "Ungültige Kantenlänge angegeben.");
				}
			}
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
		if(gP.eM == EditorMode.SelectKnoten || gP.eM == EditorMode.AddKante) {
			int i = gP.getCurrentCircle(e);
			if(i > 0) gP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			else gP.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
}
