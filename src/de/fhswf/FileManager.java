package de.fhswf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

	public static Graph readFileBufferdReader(String filename) throws IOException {
		Graph graph = new Graph(filename);
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			String line;
			do {
				line = br.readLine();
				decodeLine(line, graph);
			} while (line != null);
		} finally {
			br.close();
		}
		return graph;
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
				graph.writeLineToAdjacencyMatrix(Integer.parseInt(array[1]),
						Integer.parseInt(array[2]),
						Integer.parseInt(array[3]));
			}
		}
	}

}
