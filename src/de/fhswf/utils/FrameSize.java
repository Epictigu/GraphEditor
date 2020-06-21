package de.fhswf.utils;

public enum FrameSize {

	Small(30, 500, 500),
	Medium(45, 750, 750),
	Large(60, 1000, 1000);
	
	public int maxKnoten, width, height;
	
	private FrameSize(int maxKnoten, int width, int height) {
		this.maxKnoten = maxKnoten;
		this.width = width;
		this.height = height;
	}
	
}
