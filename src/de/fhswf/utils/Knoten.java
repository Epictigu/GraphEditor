 package de.fhswf.utils;

import java.awt.Color;
import java.awt.Point;

public class Knoten {
	
	public Point pos;
	public String knotName;
	public KnotenType knotType = KnotenType.Kreis;
	public Color main = null;
	public Color font = null;
	public int size = -1;
	
	public Knoten(String name) {
		pos = null;
		this.knotName = name;
	}
	
	public Knoten(Point pos, String name) {
		this.pos = pos;
		this.knotName = name;
	}
	
}
