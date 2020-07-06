package de.fhswf.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import javax.swing.JPanel;

public class GraphPainter extends JPanel {

	private static final long serialVersionUID = 2L;

	public Graph graph = null;

	public Color backgroundColor = new Color(51, 51, 51), mainColor = Color.WHITE, fontColor = Color.BLACK,
			overlappingEdge = Color.RED, gridColor = new Color(72, 72, 72);

	public int firstKnotenSel = 0, secondKnotenSel = 0;
	public int size = 65;
	private FrameSize fSize;
	private int currentCircle = 0;
	public EditorMode eM = EditorMode.SelectKnoten;
	public Map<Knoten, Integer> bfs = null;

	public boolean tV = true;
	public boolean lV = false;

	public GraphPainter(int kSize) {
		this(FrameSize.Small, kSize);
	}

	public GraphPainter(FrameSize fsize, int kSize) {
		GraphMouseAdapter gMA = new GraphMouseAdapter(this);
		addMouseListener(gMA);
		addMouseMotionListener(gMA);
		this.fSize = fsize;
		this.size = kSize;
		this.graph = new Graph(null);
	}

	public GraphPainter(Graph g, FrameSize fsize, int kSize) {
		this(fsize, kSize);
		prepGraph(g);
	}

	public void setFile(Graph g) {
		prepGraph(g);
		repaint();
	}

	private void prepGraph(Graph g) {
		this.graph = g;

		if (g == null)
			return;

		float degreeC = 360f;

		if (g.getAmountKnots() > 1)
			degreeC = degreeC / g.getAmountKnots();

		for (int i = 0; i < graph.getAmountKnots(); i++) {
			if (graph.knotList.get(i).pos == null) {
				graph.knotList.get(i).pos = addEcke(i * degreeC);
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
			return "" + graph.knotList.get(i - 1).knotName;
		return null;
	}

	@Override
	public Point getToolTipLocation(MouseEvent event) {
		return new Point(event.getX() + 10, event.getY() + 10);
	}

	public int getInnerDiameter(Knoten k) {
		if (k.size != -1)
			return k.size;
		return (int) size;
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
		for (int i = 1; i <= graph.knotList.size(); i++) {
			Knoten k = graph.knotList.get(i - 1);
			Point oP = k.pos;

			if (k.knotType == KnotenType.Kreis) {
				double d = Math.hypot(event.getX() - (oP.getX() + getInnerDiameter(k) / 2),
						event.getY() - (oP.getY() + getInnerDiameter(k) / 2));
				if (d < getInnerDiameter(k) / 2)
					return i;
			} else if (k.knotType == KnotenType.Quadrat || k.knotType == KnotenType.GerundetesQuadrat) {
				if (new CustomRect(getInnerDiameter(k), oP).contains(event.getPoint())) {
					return i;
				}
			}
		}
		return 0;
	}

	private Point addEcke(float degree) {
		Point p = getPointOnCircle(degree, ((getWidth() - 50) / 2) - (getInnerDiameter() / 2));
		p.setLocation(p.x - (getInnerDiameter() / 2), p.y - (getInnerDiameter() / 2));

		return p;
	}

	void drawKante(Graphics2D g2d, Kanten k) {

		Point t1 = k.k1.pos;
		Point t2 = k.k2.pos;
		
		g2d.drawLine(t1.x + getInnerDiameter(k.k1) / 2, t1.y + getInnerDiameter(k.k1) / 2, t2.x + getInnerDiameter(k.k2) / 2, t2.y + getInnerDiameter(k.k2) / 2);
	}

	protected Point getPointOnCircle(float degress, int radius) {

		int x = Math.round(getWidth() / 2);
		int y = x;

		double rad = Math.toRadians(degress - 90);

		int xPosy = Math.round((float) (x + Math.cos(rad) * radius));
		int yPosy = Math.round((float) (y + Math.sin(rad) * radius));

		return new Point(xPosy, yPosy);

	}

	public void resetSelected() {
		firstKnotenSel = 0;
		secondKnotenSel = 0;
		repaint();
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

		double sizeMod = (double) (fSize.maxKnoten) / 30.0;
		g2d.setColor(gridColor);
		for (int i = 1; i < 12 * sizeMod; i++) {
			g2d.drawLine((int) (i * ((double) (getWidth()) / 12 * (1 / sizeMod))), 0,
					(int) (i * ((double) (getWidth()) / 12 * (1 / sizeMod))), getHeight());
		}
		for (int i = 1; i < 12 * sizeMod; i++) {
			g2d.drawLine(0, (int) (i * ((double) (getHeight()) / 12 * (1 / sizeMod))), getWidth(),
					(int) (i * ((double) (getHeight()) / 12 * (1 / sizeMod))));
		}

		int fontSize = (int) ((30.0 - (graph.getAmountKnots() * 0.5 * ((30.0 / fSize.maxKnoten)))));
		g2d.setStroke(new BasicStroke((float) (((2.5f - ((0f + graph.getAmountKnots()) / 20f * (30f / fSize.maxKnoten)))
				* ((1.0 + size) / 80)))));
		for (Kanten k : graph.edgeList) {
			g2d.setColor(mainColor);
			for (Kanten k2 : graph.edgeList) {
				if (k != k2) {
					if (!(k.k1 == k2.k1 || k.k1 == k2.k2 || k.k2 == k2.k1 || k.k2 == k2.k2)) {
						if (k.schneidetMit(k2, getInnerDiameter())) {
							g2d.setColor(overlappingEdge);
						}
					}
				}
			}
			drawKante(g2d, k);
			if (lV) {
				g2d.setColor(mainColor);
				g2d.setFont(new Font("Dialog", Font.BOLD, (int) ((fontSize * (1.0 + getInnerDiameter()) / 80) / 1.7)));

				int xDis = (k.k2.pos.x - k.k1.pos.x + getInnerDiameter()) / 2;
				int yDis = (k.k2.pos.y - k.k1.pos.y + getInnerDiameter()) / 2;
				double xD = (0.0 + xDis - getInnerDiameter() / 2) / 90.0;// if(xD < 0) xD *=-1;
				double yD = (0.0 + yDis - getInnerDiameter() / 2) / 90.0;// if(yD < 0) yD *=-1;
				int xMod = 0, yMod = 0;

				if ((xD < 0 && yD < 0) || xD > 0 && yD > 0) {
					xMod = 10;
					yMod = -10;
				} else {
					xMod = 10;
					yMod = 10;
				}

				g2d.drawString("" + k.länge, k.k1.pos.x + xDis + xMod, k.k1.pos.y + yDis + yMod);
			}
		}
		int cP = 0;
		for (Knoten k : graph.knotList) {
			g2d.setFont(new Font("Serif", Font.BOLD, (int) (fontSize * ((1.0 + getInnerDiameter(k)) / 80))));
			Point p = k.pos;
			cP++;
			g2d.setColor(mainColor);
			if (k.main != null) {
				g2d.setColor(k.main);
			}
			if (k.knotType == KnotenType.Kreis) {
				g2d.fillOval(p.x, p.y, getInnerDiameter(k), getInnerDiameter(k));
			} else if (k.knotType == KnotenType.Quadrat) {
				g2d.fillRect(p.x, p.y, getInnerDiameter(k), getInnerDiameter(k));
			} else if (k.knotType == KnotenType.GerundetesQuadrat) {
				g2d.fillRoundRect(p.x, p.y, getInnerDiameter(k), getInnerDiameter(k), getInnerDiameter(k) / 2,
						getInnerDiameter(k) / 2);
			}

			if (firstKnotenSel == cP || secondKnotenSel == cP) {
				g2d.setColor(overlappingEdge);
				if (k.knotType == KnotenType.Kreis) {
					g2d.drawOval(p.x, p.y, getInnerDiameter(k), getInnerDiameter(k));
				} else if (k.knotType == KnotenType.Quadrat) {
					g2d.drawRect(p.x, p.y, getInnerDiameter(k), getInnerDiameter(k));
				} else if (k.knotType == KnotenType.GerundetesQuadrat) {
					g2d.drawRoundRect(p.x, p.y, getInnerDiameter(k), getInnerDiameter(k), getInnerDiameter(k) / 2,
							getInnerDiameter(k) / 2);
				}
			}
			String knotName = graph.knotList.get(cP - 1).knotName;
			if (eM == EditorMode.KnotenPos) {
				if (bfs != null) {
					if (bfs.containsKey(k)) {
						knotName = bfs.get(k) + "";
						if (knotName.equalsIgnoreCase("-1"))
							knotName = "∞";
					}
				}
			}
			if (knotName.length() < 4) {
				g2d.setColor(fontColor);
				if (k.font != null) {
					g2d.setColor(k.font);
				}
				if (tV) {
					Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(knotName, g2d);
					g2d.drawString(knotName, (int) (p.x + getInnerDiameter(k) / 2 - bounds.getWidth() / 2),
							(int) (p.y + getInnerDiameter(k) / 2 + bounds.getHeight() / 4));
				} else if (!knotName.equalsIgnoreCase(graph.knotList.get(cP - 1).knotName)) {
					Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(knotName, g2d);
					g2d.drawString(knotName, (int) (p.x + getInnerDiameter(k) / 2 - bounds.getWidth() / 2),
							(int) (p.y + getInnerDiameter(k) / 2 + bounds.getHeight() / 4));
				}
			}
		}

		g2d.dispose();
	}

}
