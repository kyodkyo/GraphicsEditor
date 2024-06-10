package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frames.GDrawingPanel;
import global.Constants;
import global.Constants.GFileMenu.EFileMenu;

public class GFileMenu extends JMenu {
    private static final long serialVersionUID = 1L;
    
	private GDrawingPanel drawingPanel;

    public void associate(GDrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }
    
    public GFileMenu(String s) {
        super(s);
        ActionHandler actionHandler = new ActionHandler();
        
        for(EFileMenu eFileMenu : EFileMenu.values()) {
        	JMenuItem menuItem = new JMenuItem(eFileMenu.getText());
        	menuItem.setActionCommand(eFileMenu.getText());
        	menuItem.addActionListener(actionHandler);
        	this.add(menuItem);
        }
    }

    private void open() {
		try {
			File file = new File(Constants.FILENAME.toString());
			ObjectInputStream objectInputStream = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(file)));
			
			Object object = objectInputStream.readObject();
			this.drawingPanel.setShapes(object);
			objectInputStream.close();
			this.repaint();
		} 
		catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
		} 
	}
	
	private void save() {
		try {
			File file = new File(Constants.FILENAME.toString());
			
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(file)));
			
			objectOutputStream.writeObject(this.drawingPanel.getShapes());
			objectOutputStream.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
	}
    
    private class ActionHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(Constants.GFileMenu.EFileMenu.eOpen.getText())){
                open();
            }
            else if (e.getActionCommand().equals(Constants.GFileMenu.EFileMenu.eSave.getText())){
                save();
            }
        }
    }
}
