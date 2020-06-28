package de.fhswf.utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Graph {
	private int amountKnots;
	private String[] knotNames;
	private boolean[][] adjacencyMatrix;
	private int[][] length;

	public List<Knoten> knotList = new ArrayList<Knoten>();
	public List<Kanten> edgeList = new ArrayList<Kanten>();

	private String path;

	public Graph(String path) {
		this.path = path;
	}

	public void init(int amountKnots) {
		this.amountKnots = amountKnots;
		knotNames = new String[amountKnots + 1];
		adjacencyMatrix = new boolean[amountKnots + 1][amountKnots + 1];
		length = new int[amountKnots + 1][amountKnots + 1];
	}

	public void finalize() {
		for (int i = 1; i <= amountKnots; i++) {
			for (int j = i + 1; j <= amountKnots; j++) {
				if (adjacencyMatrix[i][j]) {
					edgeList.add(new Kanten(knotList.get(i - 1), knotList.get(j - 1), length[i][j]));
				}
			}
		}
	}

	public void addKnoten(String name) {
		knotList.add(new Knoten(name));
	}

	public void addKnoten(String name, Point pos) {
		knotList.add(new Knoten(pos, name));
	}

	public void addKante(Knoten k1, Knoten k2, int length) {
		edgeList.add(new Kanten(k1, k2, length));
	}

	public void writeToKnotNames(int index, String name) {
		knotNames[index] = name;
	}

	public int getKnotPosInList(Knoten k) {
		for (int i = 0; i < knotList.size(); i++) {
			if (k == knotList.get(i))
				return i;
		}
		return -1;
	}

	public void writeLineToAdjacencyMatrix(int x, int y, int length) {
		if (y < x) {
			int t = y;
			y = x;
			x = t;
		}
		adjacencyMatrix[x][y] = true;
		this.length[x][y] = length;
	}

	public int getAmountKnots() {
		return knotList.size();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String newPath) {
		path = newPath;
	}

	public int[][] getAdjacencyMatrix() {
		int[][] temp = new int[getAmountKnots() + 1][getAmountKnots() + 1];
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp.length; j++) {
				temp[i][j] = -1;
			}
		}

		for (Kanten k : edgeList) {
			int a = getKnotPosInList(k.k1);
			int b = getKnotPosInList(k.k2);
			temp[a + 1][b + 1] = k.länge;
			temp[b + 1][a + 1] = k.länge;
		}

		return temp;
	}

}
