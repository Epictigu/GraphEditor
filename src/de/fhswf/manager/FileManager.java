package de.fhswf.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

import de.fhswf.utils.Graph;

public class FileManager {

	public static Graph readFileScanner(String filename) {
		Graph graph = new Graph(filename);
		try {
			File file = new File(filename);
			Locale loc = new Locale("de", "DE");
			Scanner scanner = new Scanner(file, "UTF-8");
			scanner.useLocale(loc);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				decodeLine(line, graph);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		graph.finalize();
		return graph;
	}

	public static void writeFile(String path, Graph graph, int size) {
		try {
			FileWriter fw = new FileWriter(path);
			fw.write(encodeGraph(graph));
			fw.close();

			String pathPos = path + "p";
			FileWriter fwp = new FileWriter(pathPos);
			fwp.write(writePos(graph, size));
			fwp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String encodeGraph(Graph g) {
		int[][] temp = g.getAdjacencyMatrix();

		String graph = "";
		// Graph
		graph = graph + "G " + g.getAmountKnots() + "\n";

		for (int i = 1; i <= g.getAmountKnots(); i++) {
			// Knoten
			graph = graph + "V " + i + " \"" + g.knotList.get(i - 1).knotName + "\"" + "\n";
			for (int j = 1; j <= g.getAmountKnots(); j++) {
				// Kanten
				if (temp[i][j] != -1) {
					graph = graph + "E " + i + " " + j + " " + temp[i][j] + "\n";
				}
			}
		}
		return graph;
	}

	public static String writePos(Graph g, int size) {
		String pos = "";
		pos = pos + size + "\n";
		for (int i = 1; i <= g.getAmountKnots(); i++) {
			int x = g.knotList.get(i - 1).pos.x;
			int y = g.knotList.get(i - 1).pos.y;
			pos = pos + x + " " + y + "\n";
		}

		return pos;
	}

	// Abbildung_22_3.gdi
	// 1. G = Graph Zahl = anzahl der Knoten

	// 2. V = Knoten
	// 1.Zahl = Nummer des Knotens
	// Zahl/Buchstabe = bennenung desKnotens

	// 3. E = Kante
	// 1.Zahl = Nummer des "Start Knotens"
	// 2.Zahl = Zielknoten der Kante
	// 3.Zahl = PH fuer gerichtet/ungerichtet

	private static void decodeLine(String line, Graph graph) {
		if (!line.isEmpty()) {
			String[] array = line.split(" ");

			if (array[0].equals("G")) {
				int amountKnots = Integer.parseInt(array[1]);

				graph.init(amountKnots);
			} else if (array[0].equals("V")) {
				String name = array[2];
				for (int i = 3; i < array.length; i++) {
					name = name + " " + array[i];
				}
				name = name.substring(1, name.length() - 1);

				graph.addKnoten(name);
			} else if (array[0].equals("E")) {
				graph.writeLineToAdjacencyMatrix(Integer.parseInt(array[1]), Integer.parseInt(array[2]),
						Integer.parseInt(array[3]));
			}
		}
	}

}
