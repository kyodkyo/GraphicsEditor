package shapeTools;

import java.awt.Rectangle;
import java.awt.Shape;

public class GRectangle extends GShape{
	
	private static final long serialVersionUID = 1L;

	public GRectangle() {
		super(EDrawingStyle.e2PStyle);
		this.shape = new Rectangle();
	}
	
	public GShape clone() {
		GShape cloneShape = super.clone();
		cloneShape.shape = (Shape)((Rectangle)(this.shape)).clone();
		return cloneShape;
	}
	
	@Override
	public void addPoints(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setLocation(x,y);
		rectangle.setSize(0,0);
	}
	
	@Override
	public void stopPoint(int x, int y) {
	}
	
	@Override
	public void movePoint(int x, int y) { 
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setSize(x-rectangle.x, y-rectangle.y);
	}
}
