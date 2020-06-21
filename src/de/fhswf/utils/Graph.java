package de.fhswf.utils;

public class Graph {
	private int amountKnots;
	private String[] knotNames;
	private boolean[][] adjacencyMatrix;
	
	private String path;
	
	
	public Graph(String path) {
		this.path = path;
	}
	

	public void init(int amountKnots) {
		this.amountKnots = amountKnots;
		knotNames = new String[amountKnots + 1];
		adjacencyMatrix = new boolean[amountKnots + 1][amountKnots + 1];
	}

	public void writeToKnotNames(int index, String name) {
		knotNames[index] = name;
	}

	public void writeLineToAdjacencyMatrix(int x, int y) {
		adjacencyMatrix[x][y] = true;
	}

	public int getAmountKnots() {
		return amountKnots;
	}

	public String[] getKnotNames() {
		return knotNames;
	}

	public boolean[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
	
	public String getPath() {
		return path;
	}
	
}
