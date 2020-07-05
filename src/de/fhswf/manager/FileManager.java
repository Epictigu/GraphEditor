package de.fhswf.manager;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

import de.fhswf.utils.FrameSize;
import de.fhswf.utils.Graph;
import de.fhswf.utils.KnotenType;

public class FileManager {

	public static Graph readFileScanner(String path, String pathGDIP) {
		Graph graph = new Graph(path);
		try {
			// GDI
			File fileGDI = new File(path);
			Locale loc = new Locale("de", "DE");
			Scanner scannerGDI = new Scanner(fileGDI, "UTF-8");
			scannerGDI.useLocale(loc);
			while (scannerGDI.hasNextLine()) {
				String line = scannerGDI.nextLine();
				decodeGDI(line, graph);
			}
			scannerGDI.close();

			// GDIP
			if (pathGDIP != null) {
				File fileGDIP = new File(pathGDIP);
				if (fileGDIP.exists()) {
					Scanner scannerGDIP = new Scanner(fileGDIP, "UTF-8");
					scannerGDI.useLocale(loc);

					int knotSize = Integer.parseInt(scannerGDIP.nextLine());
					graph.kSize = knotSize;
					FrameSize windowSize = FrameSize.valueOf(scannerGDIP.nextLine());
					graph.fSize = windowSize;

					int index = 0;
					while (scannerGDIP.hasNextLine()) {
						String line = scannerGDIP.nextLine();
						decodeGDIP(line, graph, index);
						index++;
					}
					scannerGDIP.close();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		graph.finalize();
		return graph;
	}

	public static void writeFile(String path, Graph graph, int size, FrameSize frameSize) {
		try {
			Writer fw = null;
//			BufferedWriter out = null;
			try {
			    fw = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8);
			} catch(Exception e) {
				e.printStackTrace();
			}
//			FileWriter fw = new FileWriter(path);
			fw.write(encodeGraph(graph));
			fw.close();

			String pathPos = path + "p";
			FileWriter fwp = new FileWriter(pathPos);
			fwp.write(writePos(graph, size, frameSize));
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

	public static String writePos(Graph g, int size, FrameSize frameSize) {
		String pos = "";
		// groeße Knoten
		pos = pos + size + "\n";
		// groeße Fenster
		pos = pos + frameSize.toString() + "\n";
		for (int i = 1; i <= g.getAmountKnots(); i++) {
			int x = g.knotList.get(i - 1).pos.x; // xPosition
			int y = g.knotList.get(i - 1).pos.y; // yPosition
			int knotSize = g.knotList.get(i - 1).size; // Knotengroeße
			String knotType = g.knotList.get(i - 1).knotType.toString(); // KnotenType
			String rgbKnot;
			try {
				rgbKnot = "" + g.knotList.get(i - 1).main.getRGB(); // KnotenFarbe
			} catch (Exception e) {
				rgbKnot = "Default";
			}
			String rgbFont;
			try {
				rgbFont = "" + g.knotList.get(i - 1).font.getRGB(); // FontFarbe
			} catch (Exception e) {
				rgbFont = "Default";
			}
			// x y KnotenGroeße KnotenType KnotenFarbe FontFarbe
			pos = pos + x + " " + y + " " + knotSize + " " + knotType + " " + rgbKnot + " " + rgbFont + "\n";
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

	private static void decodeGDI(String line, Graph graph) {
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

	private static void decodeGDIP(String line, Graph graph, int knotIndex) {
		if (!line.isEmpty()) {
			String[] array = line.split(" ");
			// X Koordinate
			int x = Integer.parseInt(array[0]);
			// Y Koordinate
			int y = Integer.parseInt(array[1]);
			graph.knotList.get(knotIndex).pos = new Point(x, y);
			// Knotengroe�e
			int sizeKnot = Integer.parseInt(array[2]);
			graph.knotList.get(knotIndex).size = sizeKnot;
			// KnotenType
			KnotenType knotenType = KnotenType.valueOf(array[3]);
			graph.knotList.get(knotIndex).knotType = knotenType;
			// Knotenfarbe
			if (!array[4].equals("Default")) {
				Color knotColor = new Color(Integer.parseInt(array[4]));
				graph.knotList.get(knotIndex).main = knotColor;
			}
			// Fontfarbe
			if (!array[5].equals("Default")) {
				Color fontColor = new Color(Integer.parseInt(array[5]));
				graph.knotList.get(knotIndex).font = fontColor;
			}
		}
	}

}
