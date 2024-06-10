package frames;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constants.EShapeButtons;

public class GShapeToolBar extends JToolBar{

	private static final long serialVersionUID = 1L;
	
	private GDrawingPanel drawingPanel;
	
	public GShapeToolBar() {
		ShapeActionHandler shapeActionHandler = new ShapeActionHandler();
		
		ButtonGroup buttonGroup = new ButtonGroup();
		for (EShapeButtons eShapeButtons : EShapeButtons.values()){
			ImageIcon shapeIcon = resizeIcon(eShapeButtons.getImage(), 30, 30);
			JRadioButton button = new JRadioButton(shapeIcon);
            button.addActionListener(shapeActionHandler);
            button.setActionCommand(eShapeButtons.toString());
            add(button);
            buttonGroup.add(button);
        }
	}

	public void initialize() {
		JRadioButton defaultButton = (JRadioButton)(this.getComponent(EShapeButtons.eLine.ordinal()));
		defaultButton.doClick();
	}
	
	public ImageIcon resizeIcon(String str, int x, int y) {
		ImageIcon icon = new ImageIcon(str);
		Image image = icon.getImage();
		Image resizedImage = image.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImage);
	}

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	private void setShapeTool(EShapeButtons eShapeButton) {
		drawingPanel.setShapeTool(eShapeButton.getShapeTool());
	}
	
	private class ShapeActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			EShapeButtons eShapeButton = EShapeButtons.valueOf(e.getActionCommand());
			setShapeTool(eShapeButton);
		}
	}
}
