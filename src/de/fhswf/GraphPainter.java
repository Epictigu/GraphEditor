package de.fhswf;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import de.fhswf.utils.FrameSize;
import de.fhswf.utils.Graph;
import de.fhswf.utils.GraphMouseAdapter;
import de.fhswf.utils.Kanten;
import de.fhswf.utils.Knoten;

public class GraphPainter extends JPanel {

	private static final long serialVersionUID = 2L;

	public Graph graph = null;
	private List<Knoten> knotList = new ArrayList<Knoten>();
	private List<Kanten> edgeList = new ArrayList<Kanten>();

	public Color backgroundColor = new Color(51, 51, 51), mainColor = Color.WHITE, fontColor = Color.BLACK,
			overlappingEdge = Color.RED;
	
	public int size = 80;
	private FrameSize fSize;
	private int currentCircle = 0;

	public GraphPainter() {
		this(FrameSize.Small);
	}
	
	public GraphPainter(FrameSize fsize) {
		GraphMouseAdapter gMA = new GraphMouseAdapter(this);
		addMouseListener(gMA);
		addMouseMotionListener(gMA);
		this.fSize = fsize;
	}

	public GraphPainter(Graph g, FrameSize fsize) {
		this(fsize);
		prepGraph(g);
	}

	public void setFile(Graph g) {
		prepGraph(g);
		repaint();
	}

	private void prepGraph(Graph g) {
		this.graph = g;
		
		if(g == null) return;

		float degreeC = 360f;
		knotList.clear();
		edgeList.clear();
		
		if (g.getAmountKnots() > 1)
			degreeC = degreeC / g.getAmountKnots();

		for (int i = 1; i <= graph.getAmountKnots(); i++) {
			knotList.add(new Knoten(addEcke((i - 1) * degreeC), graph.getKnotNames()[i]));
		}
		boolean[][] adjM = graph.getAdjacencyMatrix();
		for (int i = 1; i <= graph.getAmountKnots(); i++) {
			for (int j = 1; j <= graph.getAmountKnots(); j++) {
				if (j != i) {
					if (adjM[i][j]) {
						if (i < j || adjM[j][i] == false)
							edgeList.add(new Kanten(knotList.get(i - 1), knotList.get(j - 1)));
					}
				}
			}
		}

	}
	
	public void reset() {
		prepGraph(graph);
		repaint();
	}

	@Override
	public String getToolTipText(MouseEvent event) {
		int i = getCurrentCircle(event);
		if (i > 0)
			return "" + graph.getKnotNames()[i];
		return null;
	}

	@Override
	public Point getToolTipLocation(MouseEvent event) {
		return new Point(event.getX() + 10, event.getY() + 10);
	}

	public List<Knoten> getKnots() {
		return knotList;
	}

	public int getInnerDiameter() {
		return (int) size;
	}

	public int getCurrentCircle() {
		return currentCircle;
	}

	public void setCurrentCircle(int i) {
		currentCircle = i;
	}

	public int getCurrentCircle(MouseEvent event) {
		for (int i = 1; i <= knotList.size(); i++) {
			Point oP = knotList.get(i - 1).pos;
			double d = Math.hypot(event.getX() - (oP.getX() + getInnerDiameter() / 2), event.getY() - (oP.getY() + getInnerDiameter() / 2));
			if (d < getInnerDiameter() / 2)
				return i;
		}
		return 0;
	}

	private Point addEcke(float degree) {
		Point p = getPointOnCircle(degree, ((getWidth() - 50) / 2) - (getInnerDiameter() / 2));
		p.setLocation(p.x - (getInnerDiameter() / 2), p.y - (getInnerDiameter() / 2));

		return p;
	}

	void drawKante(Graphics2D g2d, Kanten k) {
		int aL = getInnerDiameter() / 2;

		Point t1 = k.k1.pos;
		Point t2 = k.k2.pos;
		g2d.drawLine(t1.x + aL, t1.y + aL, t2.x + aL, t2.y + aL);
	}

	protected Point getPointOnCircle(float degress, int radius) {

		int x = Math.round(getWidth() / 2);
		int y = x;

		double rad = Math.toRadians(degress - 90);

		int xPosy = Math.round((float) (x + Math.cos(rad) * radius));
		int yPosy = Math.round((float) (y + Math.sin(rad) * radius));

		return new Point(xPosy, yPosy);

	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(backgroundColor);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(mainColor);
		
		if (graph == null) {
			g2d.setFont(new Font("Serif", Font.BOLD, 20));
			int width = g2d.getFontMetrics().stringWidth("Kein Graph ausgewählt!");
			g2d.drawString("Kein Graph ausgewählt!", getWidth() / 2 - (width / 2), getHeight() / 2 - 15);
			return;
		}

		if (graph.getAmountKnots() > fSize.maxKnoten) {
			g2d.setFont(new Font("Serif", Font.BOLD, 20));
			int width = g2d.getFontMetrics().stringWidth("Der gewählte Graph ist zu groß!");
			g2d.drawString("Der gewählte Graph ist zu groß!", getWidth() / 2 - (width / 2), getHeight() / 2 - 15);
			
			int width2 = g2d.getFontMetrics().stringWidth("Bitte wenn möglich größeres Fenster öffnen.");
			g2d.drawString("Bitte wenn möglich größeres Fenster öffnen.", getWidth() / 2 - (width2 / 2), getHeight() / 2 + 10);
			return;
		}

		g2d.setStroke(new BasicStroke((float) (((2.5f - ((0f + graph.getAmountKnots()) / 20f * (30f / fSize.maxKnoten))) * ((1.0 + size) / 80)))));
		for (Kanten k : edgeList) {
			g2d.setColor(mainColor);
			for (Kanten k2 : edgeList) {
				if (k != k2) {
					if (!(k.k1 == k2.k1 || k.k1 == k2.k2 || k.k2 == k2.k1 || k.k2 == k2.k2)) {
						if (k.schneidetMit(k2, getInnerDiameter())) {
							g2d.setColor(overlappingEdge);
						}
					}
				}
			}
			drawKante(g2d, k);
		}

		int fontSize = (int) ((30.0 - (graph.getAmountKnots() * 0.5  * ((30.0 / fSize.maxKnoten)))));
		g2d.setFont(new Font("Serif", Font.BOLD, (int) (fontSize * ((1.0 + size) / 80))));
		int cP = 0;
		for (Knoten k : knotList) {
			Point p = k.pos;
			cP++;
			g2d.setColor(mainColor);
			g2d.fillOval(p.x, p.y, getInnerDiameter(), getInnerDiameter());
			String knotName = graph.getKnotNames()[cP];
			if (knotName.length() < 4) {
				g2d.setColor(fontColor);
				Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(knotName, g2d);
				g2d.drawString(knotName, (int) (p.x + getInnerDiameter() / 2 - bounds.getWidth() / 2),
						(int) (p.y + getInnerDiameter() / 2 + bounds.getHeight() / 4));
			}
		}

		g2d.dispose();
	}

}
