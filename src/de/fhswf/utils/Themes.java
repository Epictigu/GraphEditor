package de.fhswf.utils;

import java.awt.Color;

public enum Themes {
	
	def("Default", new Color(51, 51, 51), Color.WHITE, Color.BLACK, Color.RED, new Color(72, 72, 72)),
	dark("Dark", Color.BLACK, new Color(100, 100, 100), Color.BLACK, Color.RED, new Color(72, 72, 72)),
	wonderland("Wonderland", new Color(43, 45, 66), new Color(239, 35, 60), new Color(237, 242, 244), Color.WHITE, new Color(72, 72, 72)),
	ice("Night", new Color(13, 27, 42), new Color(119, 141, 169), new Color(27, 38, 59), new Color(65, 90, 119), new Color(2, 62, 138)),
	classic("Classic", new Color(94, 100, 114), new Color(220, 47, 2), new Color(250, 243, 221), new Color(255, 166, 158), new Color(72, 72, 72));
	
	public String buttonText;
	public Color backgroundColor;
	public Color mainColor;
	public Color fontColor;
	public Color overlappingColor;
	public Color gridColor;
	
	private Themes(String buttonText, Color backgroundColor, Color mainColor, Color fontColor, Color overlappingColor, Color gridColor) {
		this.buttonText = buttonText;
		this.backgroundColor = backgroundColor;
		this.mainColor = mainColor;
		this.fontColor = fontColor;
		this.overlappingColor = overlappingColor;
		this.gridColor = gridColor;
	}
	
}
