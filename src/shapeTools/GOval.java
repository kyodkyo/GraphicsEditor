package shapeTools;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class GOval extends GShape{
	
	private static final long serialVersionUID = 1L;
	
	public GOval() {
		super(EDrawingStyle.e2PStyle);
		this.shape = new Ellipse2D.Double();
	}
	
	public GShape clone() {
		GShape cloneShape = super.clone();
		cloneShape.shape= (Shape) ((Ellipse2D.Double)(this.shape)).clone();
		return cloneShape;
	}
	
	@Override
	public void addPoints(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(x, y, 0, 0);
	}
	
	@Override
	public void stopPoint(int x, int y) {
	}
	
	@Override
	public void movePoint(int x, int y) { 
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(ellipse.getX(),ellipse.getY(),x-ellipse.getX(), y-ellipse.getY());
	}
	
//	@Override
//	public void drag(Graphics graphics) {
//		Graphics2D graphics2D = (Graphics2D) graphics;
//		graphics2D.setXORMode(graphics2D.getBackground());
//		
//		double oldPowX = Math.pow(o2.x - p1.x, 2);
//		double oldPowY = Math.pow(o2.y - p1.y, 2);
//		int oldR = (int)Math.sqrt(oldPowX + oldPowY);
//
//		double newPowX = Math.pow(p2.x - p1.x, 2);
//		double newPowY = Math.pow(p2.y - p1.y, 2);
//		int newR = (int) Math.sqrt(newPowX + newPowY);
//		
//		graphics2D.drawOval(p1.x - oldR, p1.y - oldR, 2*oldR, 2*oldR);
//		graphics2D.drawOval(p1.x - newR, p1.y - newR, 2*newR, 2*newR);
//		ovalR = newR;
//	}
//
//	@Override
//	public void draw(Graphics graphics) {
//		graphics.drawOval(p1.x - ovalR, p1.y - ovalR, 2*ovalR, 2*ovalR);
//	}

}
