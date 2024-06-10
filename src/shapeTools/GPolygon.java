package shapeTools;

import java.awt.Point;
import java.awt.Polygon;

public class GPolygon extends GShape{
	
	private static final long serialVersionUID = 1L;
	
	public GPolygon() {
		super(EDrawingStyle.eNPStyle);
		this.shape = new Polygon();
	}

	public GShape clone() {
		GShape cloneShape = super.clone();
		Polygon polygon = ((Polygon)new Polygon());
		Polygon curShape = (Polygon) this.shape;
		
		for(int i=0; i<curShape.npoints; i++) {
			Point np = new Point();
			np.x = curShape.xpoints[i];
			np.y = curShape.ypoints[i];
			polygon.addPoint(np.x, np.y);
		}
		cloneShape.shape= polygon;
		return cloneShape;
	}
	
	@Override
	public void addPoints(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.addPoint(x, y);
		polygon.addPoint(x, y);
	}
	
	public void setIntermediatePoint(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.addPoint(x, y);
	}
	
	@Override
	public void stopPoint(int x, int y) {
	}
	
	@Override
	public void movePoint(int x, int y) { 
		Polygon polygon = (Polygon) this.shape;
		polygon.xpoints[polygon.npoints-1]=x;
		polygon.ypoints[polygon.npoints-1]=y;
	}
	
//	@Override
//	public void drag(Graphics graphics) {
//		Graphics2D graphics2D = (Graphics2D) graphics;
//		graphics2D.setXORMode(graphics2D.getBackground());
//		
//		graphics2D.drawPolygon(xPoints, yPoints, nPoints);
//	}
//
//	@Override
//	public void draw(Graphics graphics) {
//		graphics.drawPolygon(xPoints, yPoints, nPoints);
//	}

}
