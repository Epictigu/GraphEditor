package de.fhswf.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.fhswf.frames.GUI;
import de.fhswf.utils.EditorButton;
import de.fhswf.utils.EditorMode;
import de.fhswf.utils.EditorPanel;

public class EditorManager implements ActionListener, ChangeListener {

	public EditorPanel edPanel;
	public JSlider slider;
	private EditorButton currentSelected;
	private GUI guiInstance;

	public void initEditor(GUI instance) {
		this.guiInstance = instance;

		edPanel = new EditorPanel();
		edPanel.setBounds(instance.getWidth() - 57, 0, 50, instance.getHeight() - 50);
		edPanel.setLayout(null);
		edPanel.setBackground(instance.k.mainColor);
		instance.add(edPanel);

		addEditorButton("resources/icon_knoten_select.png", "Knoten verschieben", "knotenSelectButton", true);
		addEditorButton("resources/icon_knoten.png", "Knoten hinzufügen", "knotenButton");
		addEditorButton("resources/icon_kanten_select.png", "Kante auswählen", "kantenSelectButton");
		addEditorButton("resources/icon_kanten.png", "Kante hinzufügen", "kantenButton");
		addEditorButton("resources/icon_knoten_zusammen.png", "Knoten zusammenlegen", "knotenZusammenButton");
		addEditorButton("resources/icon_knoten_pos.png", "Breitensuche", "knotenPosButton");
		
		slider = new JSlider(JSlider.VERTICAL, 25, 100, 65);
		slider.setMajorTickSpacing(25);
		slider.setMinorTickSpacing(5);
		slider.setBackground(guiInstance.k.mainColor);
		slider.setPaintTicks(true);
		slider.setBounds(2, yMod, 48, instance.getWidth() - yMod - 51);
		slider.setPaintLabels(true);
		slider.addChangeListener(this);
		edPanel.add(slider);
	}

	private void addEditorButton(String iconPath, String tooltip, String actionCommand) {
		addEditorButton(iconPath, tooltip, actionCommand, false);
	}

	private int yMod = 0;

	private void addEditorButton(String iconPath, String tooltip, String actionCommand, boolean selected) {
		try {
			EditorButton knotenButton = new EditorButton(
					new ImageIcon(ImageIO.read(getClass().getResource("..\\" + iconPath))));
			
			knotenButton.setBounds(2, yMod, 48, 48);
			knotenButton.setBorderPainted(false);
			knotenButton.setFocusPainted(false);
			knotenButton.setContentAreaFilled(false);
			knotenButton.setToolTipText(tooltip);
			knotenButton.addActionListener(this);
			knotenButton.setActionCommand(actionCommand);

			if (selected) {
				knotenButton.setSelected(true);
				if (currentSelected != null)
					currentSelected.setSelected(false);
				currentSelected = knotenButton;
			}

			edPanel.add(knotenButton);
			yMod += 48;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void changeSelectedEditorButton(Object o) {
		if (currentSelected != null) {
			currentSelected.setSelected(false);
		}
		currentSelected = (EditorButton) o;
		currentSelected.setSelected(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("knotenButton")) {
			changeSelectedEditorButton(e.getSource());
			guiInstance.k.eM = EditorMode.AddKnoten;
		} else if (e.getActionCommand().equalsIgnoreCase("kantenButton")) {
			changeSelectedEditorButton(e.getSource());
			guiInstance.k.eM = EditorMode.AddKante;
		} else if (e.getActionCommand().equalsIgnoreCase("knotenSelectButton")) {
			changeSelectedEditorButton(e.getSource());
			guiInstance.k.eM = EditorMode.SelectKnoten;
		} else if (e.getActionCommand().equalsIgnoreCase("kantenSelectButton")) {
			changeSelectedEditorButton(e.getSource());
			guiInstance.k.eM = EditorMode.SelectKante;
		} else if (e.getActionCommand().equalsIgnoreCase("knotenZusammenButton")) {
			changeSelectedEditorButton(e.getSource());
			guiInstance.k.eM = EditorMode.KnotenZusammen;
		} else if (e.getActionCommand().equalsIgnoreCase("knotenPosButton")) {
			changeSelectedEditorButton(e.getSource());
			guiInstance.k.bfs = null;
			guiInstance.k.eM = EditorMode.KnotenPos;
		}
		guiInstance.k.resetSelected();
	}
	

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider js = (JSlider) e.getSource();

		guiInstance.k.size = js.getValue();
		guiInstance.k.repaint();
	}

}
