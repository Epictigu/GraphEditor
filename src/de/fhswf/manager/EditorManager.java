package de.fhswf.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import de.fhswf.GUI;
import de.fhswf.utils.EditorButton;
import de.fhswf.utils.EditorPanel;

public class EditorManager implements ActionListener{
	
	public EditorPanel edPanel;
	private EditorButton currentSelected;
	
	public void initEditor(GUI instance) {
		edPanel = new EditorPanel();
		edPanel.setBounds(instance.getWidth() - 57, 0, 50, instance.getHeight() - 50);
		edPanel.setLayout(null);
		edPanel.setBackground(instance.k.mainColor);
		instance.add(edPanel);
		
		addEditorButton("resources/icon_knoten_select.png", true);
		addEditorButton("resources/icon_knoten.png");
		addEditorButton("resources/icon_kanten_select.png");
		addEditorButton("resources/icon_kanten.png");
	}
	
	private void addEditorButton(String iconPath) {
		addEditorButton(iconPath, false);
	}
	
	private int yMod = 0;
	private void addEditorButton(String iconPath, boolean selected) {
		try {
			EditorButton knotenButton = new EditorButton(
					new ImageIcon(ImageIO.read(getClass().getResource("..\\" + iconPath))));
			
			knotenButton.setBounds(2, yMod, 48, 48);
			knotenButton.setBorderPainted(false);
			knotenButton.setFocusPainted(false);
			knotenButton.setContentAreaFilled(false);
			knotenButton.setToolTipText("Knoten verschieben");
			knotenButton.addActionListener(this);
			knotenButton.setActionCommand("knotenSelectButton");
			
			if(selected) {
				knotenButton.setSelected(true);
				if(currentSelected != null)
					currentSelected.setSelected(false);
				currentSelected = knotenButton;
			}
			
			edPanel.add(knotenButton);
			yMod+=48;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void changeSelectedEditorButton(Object o) {
		if(currentSelected != null) {
			currentSelected.setSelected(false);
		}
		currentSelected = (EditorButton) o;
		currentSelected.setSelected(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("knotenButton")) {
			changeSelectedEditorButton(e.getSource());
		} else if(e.getActionCommand().equalsIgnoreCase("kantenButton")) {
			changeSelectedEditorButton(e.getSource());
		} else if(e.getActionCommand().equalsIgnoreCase("knotenSelectButton")) {
			changeSelectedEditorButton(e.getSource());
		} else if(e.getActionCommand().equalsIgnoreCase("kantenSelectButton")) {
			changeSelectedEditorButton(e.getSource());
		}
	}
	
}
