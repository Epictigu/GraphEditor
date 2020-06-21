package de.fhswf;

import java.util.ArrayList;
import java.util.List;

import de.fhswf.utils.FrameSize;
import de.fhswf.utils.Graph;

public class Main {
	
	public static List<GUI> guiList = new ArrayList<GUI>();
	
	public static void main(String[] args) {
		openNewFrame(null);
	}
	
	public static void openNewFrame(Graph g) {
		openNewFrame(g, FrameSize.Small);
	}
	
	public static void openNewFrame(Graph g, FrameSize size) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				guiList.add(new GUI(g, size));
			}
		};
		
		new Thread(r).start();
	}

}
