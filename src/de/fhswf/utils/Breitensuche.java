package de.fhswf.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Breitensuche {

	private static Map<Knoten, Integer> bsV;
	private static int stage;
	private static  Map<Integer, List<List<Kanten>>> adjM;
	
	public static Map<Knoten, Integer> BFS(Graph g, Knoten start) {
		stage = 0;
		bsV = new HashMap<Knoten, Integer>();
		adjM = new HashMap<Integer, List<List<Kanten>>>();
		
		for(Knoten k : g.knotList)
			bsV.put(k, -1);
		
		bsV.put(start, 0);
		List<Kanten> adj = g.getAdjacentKanten(start);
		List<List<Kanten>> adjL = new ArrayList<List<Kanten>>();
		adjL.add(adj);
		adjM.put(0, adjL);
		
		while(adjM.get(stage) != null) {
			List<List<Kanten>> nextList = new ArrayList<List<Kanten>>();
			List<List<Kanten>> adjLC = adjM.get(stage);
			for(List<Kanten> list : adjLC) {
				for(Kanten k : list) {
					Knoten kn = null;
					if(bsV.get(k.k1) == -1) kn = k.k1;
						else if(bsV.get(k.k2) == -1) kn = k.k2;
					if(kn != null && bsV.get(kn) == -1) {
						bsV.put(kn, stage + 1);
						nextList.add(g.getAdjacentKanten(kn));
					}
				}
			}
			if(!nextList.isEmpty()) adjM.put(stage + 1, nextList);
			adjM.remove(stage);
			stage++;
		}
		return bsV;
	}
	
	

}
