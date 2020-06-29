package de.fhswf.utils;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class CustomRect extends RoundRectangle2D{

	int iD;
	Point pos;
	
	public CustomRect(int iD, Point pos) {
		this.iD = iD;
		this.pos = pos;
	}
	
	@Override
	public Rectangle2D getBounds2D() {
		return null;
	}

	@Override
	public double getArcHeight() {
		return iD / 2;
	}

	@Override
	public double getArcWidth() {
		return iD / 2;
	}

	@Override
	public void setRoundRect(double x, double y, double w, double h, double arcWidth, double arcHeight) {}

	@Override
	public double getHeight() {
		return iD;
	}

	@Override
	public double getWidth() {
		return iD;
	}

	@Override
	public double getX() {
		return pos.x;
	}

	@Override
	public double getY() {
		return pos.y;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
	
	
	
}
