package frames;

import javax.swing.JMenuBar;

import global.Constants;
import menus.GChangeMenu;
import menus.GEditMenu;
import menus.GFileMenu;

public class GMenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	
	private GFileMenu fileMenu;
	private GEditMenu editMenu;
	private GChangeMenu changeMenu;
	
	public GMenuBar() {
		this.fileMenu = new GFileMenu(Constants.GMenuBar.EMenu.eFile.getText());
        this.add(this.fileMenu);
    
        this.editMenu = new GEditMenu(Constants.GMenuBar.EMenu.eEdit.getText());
        this.add(this.editMenu);
        
        this.changeMenu = new GChangeMenu(Constants.GMenuBar.EMenu.eChange.getText());
        this.add(this.changeMenu);
	}
	
	public void associate(GDrawingPanel drawingPanel) {
		this.fileMenu.associate(drawingPanel);
		this.editMenu.associate(drawingPanel);
		this.changeMenu.associate(drawingPanel);
	}
	
	public void initialize() {
		
	}
}
