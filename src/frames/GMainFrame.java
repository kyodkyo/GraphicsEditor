package frames;

import java.awt.*;

import javax.swing.*;

import global.Constants;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private GMenuBar menuBar;
	private GShapeToolBar shapeToolBar;
	private GDrawingPanel drawingPanel;

	public GMainFrame(){
		this.setTitle("Graphics Editor");
		this.setSize(Constants.GMainFrame.WiDTH, Constants.GMainFrame.HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        LayoutManager layoutManager = new BorderLayout();
        this.setLayout(layoutManager);
        
        this.menuBar = new GMenuBar();
        this.setJMenuBar(this.menuBar);
        
        this.shapeToolBar = new GShapeToolBar();
        this.add(this.shapeToolBar, BorderLayout.NORTH);

        this.drawingPanel = new GDrawingPanel();
    	this.add(this.drawingPanel, BorderLayout.CENTER);
        
        this.menuBar.associate(this.drawingPanel);
        this.shapeToolBar.associate(this.drawingPanel);
        
    }
	
	public void initialize() {
		this.menuBar.initialize();
		this.shapeToolBar.initialize();
		this.drawingPanel.initialize();
	}
	
}
