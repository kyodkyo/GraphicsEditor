package frames;

import javax.swing.JFrame;

public class GMain {
	
	public GMain() { 
		
	}
	
	private static GMainFrame mainFrame;
	
    public static void main(String[] args) {
        mainFrame = new GMainFrame();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.initialize();
    }
}
