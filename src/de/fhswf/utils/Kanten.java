package de.fhswf.utils;

import java.awt.geom.Line2D;

public class Kanten {
	
	public Knoten k1;
	public Knoten k2;
	public int länge;
	
	public Kanten(Knoten k1, Knoten k2, int länge) {
		this.k1 = k1;
		this.k2 = k2;
		this.länge = länge;
	}
	
	public boolean schneidetMit(Kanten k, int iD) {
		float f = 0f + iD / 2;
		return Line2D.linesIntersect(k1.pos.x + f, k1.pos.y + f,
									 k2.pos.x + f, k2.pos.y + f,
									 k.k1.pos.x + f, k.k1.pos.y + f,
									 k.k2.pos.x + f, k.k2.pos.y + f);
	}
	
}
