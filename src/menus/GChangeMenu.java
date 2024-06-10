package menus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import frames.GDrawingPanel;
import global.Constants;
import global.Constants.GChangeMenu.EChangeMenu;

public class GChangeMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	
	private GDrawingPanel drawingPanel;
	
	public void associate(GDrawingPanel drawingPanel) {
		this.setDrawingPanel(drawingPanel);
	}
	
	public GChangeMenu(String s) {
		super(s);
		ActionHandler actionHandler = new ActionHandler();
		
		for(EChangeMenu eChangeMenu : EChangeMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eChangeMenu.getText());
			menuItem.setActionCommand(eChangeMenu.getText());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
	}
	
	public GDrawingPanel getDrawingPanel() {
		return drawingPanel;
	}

	public void setDrawingPanel(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	private void changeLineColor() {
		Color color = JColorChooser.showDialog(this, "테두리 색상", Color.black);
		if(color == null)
			color = Color.black;
		this.drawingPanel.setLineColor(color);
	}
	
	private void changeShapeColor() {
		Color color = JColorChooser.showDialog(this, "채우기 색상", Color.white);
		if(color == null) 
			color = Color.white;
		this.drawingPanel.setShapeColor(color);
	}
	
	private void changeLineThickness() {
		String[] thick = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
		Object selected = JOptionPane.showInputDialog(null, "두께를 고르세요", "굵기", JOptionPane.QUESTION_MESSAGE, null, thick, thick[0]);
		
		int n = Integer.parseInt((String) selected);
		if(selected == null)
			this.drawingPanel.setLineThickness(1);
		else
			this.drawingPanel.setLineThickness(n);
		
	}
	
	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(Constants.GChangeMenu.EChangeMenu.eShapeColor.getText())) {
				changeShapeColor();
			}
			else if (e.getActionCommand().equals(Constants.GChangeMenu.EChangeMenu.eLineColor.getText())){
				changeLineColor();
			}
			else if (e.getActionCommand().equals(Constants.GChangeMenu.EChangeMenu.eLineThickness.getText())) {
				changeLineThickness();
			}
		}
		
	}
}
