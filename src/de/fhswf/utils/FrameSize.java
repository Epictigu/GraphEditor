package de.fhswf.utils;

public enum FrameSize {

	Small(30, 550, 500),
	Medium(45, 800, 750),
	Large(60, 1050, 1000);
	
	public int maxKnoten, width, height;
	
	private FrameSize(int maxKnoten, int width, int height) {
		this.maxKnoten = maxKnoten;
		this.width = width;
		this.height = height;
	}
	
}
