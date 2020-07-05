package de.fhswf;

import java.util.ArrayList;
import java.util.List;

import de.fhswf.frames.GUI;
import de.fhswf.utils.FrameSize;
import de.fhswf.utils.Graph;

public class Main {
	
	public static List<GUI> guiList = new ArrayList<GUI>();

	public static void main(String[] args) {
		Graph g = new Graph("");
		g.fSize = FrameSize.Small;
		g.kSize = 65;
		openNewFrame(g);
	}

	public static void openNewFrame(Graph g) {
		openNewFrame(g, g.fSize, g.kSize);
	}

	public static void openNewFrame(Graph g, FrameSize size) {
		openNewFrame(g, size, g.kSize);
	}
	
	public static void openNewFrame(Graph g, FrameSize size, int kSize) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				guiList.add(new GUI(g, size, kSize));
			}
		};

		new Thread(r).start();
	}

}
