package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frames.GDrawingPanel;
import global.Constants;
import global.Constants.GEditMenu.EEditMenu;

public class GEditMenu extends JMenu{
	
	private static final long serialVersionUID = 1L;

	private GDrawingPanel drawingPanel;
	
	public void associate(GDrawingPanel drawingPanel) {
		this.setDrawingPanel(drawingPanel);
	}
	
	public GEditMenu(String s) {
		super(s);
		ActionHandler actionHandler = new ActionHandler();
		
		for(EEditMenu eEditMenu : EEditMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eEditMenu.getText());
			menuItem.setActionCommand(eEditMenu.getText());
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
	
	private void undo() {
		this.drawingPanel.undo();
	}
	
	public void redo() {
		this.drawingPanel.redo();
	}
	
	private void deleteOneShape() {
		this.drawingPanel.deleteOneShape();
	}
	
	private void deleteAllShape() {
		this.drawingPanel.deleteAllShape();
	}
	
	private void cutShape() {
		this.drawingPanel.cutShape();
	}
	
	private void pasteShape() {
		this.drawingPanel.pasteShape();
	}
	
	private void copyShape() {
		this.drawingPanel.copyShape();
	}
	
	private void moveToFront() {
		this.drawingPanel.moveToFront();
	}
	
	private void moveToBack() {
		this.drawingPanel.moveToBack();
	}

	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(Constants.GEditMenu.EEditMenu.eUndo.getText())) {
				undo();
			}
			else if (e.getActionCommand().equals(Constants.GEditMenu.EEditMenu.eRedo.getText())){
				redo();
			}
			else if (e.getActionCommand().equals(Constants.GEditMenu.EEditMenu.eDelete.getText())) {
				deleteOneShape();
			}
			else if (e.getActionCommand().equals(Constants.GEditMenu.EEditMenu.eDeleteAll.getText())) {
				deleteAllShape();
			}
			else if (e.getActionCommand().equals(Constants.GEditMenu.EEditMenu.eCut.getText())) {
				cutShape();
			}
			else if (e.getActionCommand().equals(Constants.GEditMenu.EEditMenu.ePaste.getText())) {
				pasteShape();
			}
			else if (e.getActionCommand().equals(Constants.GEditMenu.EEditMenu.eCopy.getText())) {
				copyShape();
			}
			else if (e.getActionCommand().equals(Constants.GEditMenu.EEditMenu.eFront.getText())) {
				moveToFront();
			}
			else if (e.getActionCommand().equals(Constants.GEditMenu.EEditMenu.eBack.getText())) {
				moveToBack();
			}
		}

	}

}
