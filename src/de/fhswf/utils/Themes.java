package de.fhswf.utils;

import java.awt.Color;

public enum Themes {
	
	def("Default Color Theme", new Color(51, 51, 51), Color.WHITE, Color.BLACK, Color.RED),
	dark("Dark Color Theme", Color.BLACK, new Color(100, 100, 100), Color.BLACK, Color.RED),
	wonderland("Wonderland Color Theme", new Color(43, 45, 66), new Color(239, 35, 60), new Color(237, 242, 244), Color.WHITE),
	ice("Ice Color Theme", new Color(13, 27, 42), new Color(119, 141, 169), new Color(27, 38, 59), new Color(65, 90, 119)),
	classic("Classic Color Theme", new Color(94, 100, 114), new Color(174, 217, 224), new Color(250, 243, 221), new Color(255, 166, 158)),
	custom("Custom Color Theme", new Color(51, 51, 51), Color.WHITE, Color.BLACK, Color.RED);
	
	public String buttonText;
	public Color backgroundColor;
	public Color mainColor;
	public Color fontColor;
	public Color overlappingColor;
	
	private Themes(String buttonText, Color backgroundColor, Color mainColor, Color fontColor, Color overlappingColor) {
		this.buttonText = buttonText;
		this.backgroundColor = backgroundColor;
		this.mainColor = mainColor;
		this.fontColor = fontColor;
		this.overlappingColor = overlappingColor;
	}
	
}
