package shapeTools;

import java.awt.Point;
import java.awt.Polygon;

public class GTriangle extends GShape {

	private static final long serialVersionUID = 1L;
	
	private int startX;
	private int startY;

	public GTriangle() {
		super(EDrawingStyle.e2PStyle);
		this.shape = new Polygon();
	}

	public GShape clone() {
		GShape cloneShape = super.clone();
		Polygon polygon = new Polygon();
		Polygon curShape = (Polygon) this.shape;
		
		for(int i=0; i<curShape.npoints; i++) {
			Point point = new Point();
			point.x = curShape.xpoints[i];
			point.y = curShape.ypoints[i];
			polygon.addPoint(point.x, point.y);
		}
		
		cloneShape.shape = polygon;
		return cloneShape;
	}
	
	@Override
	public void addPoints(int x, int y) {
		startX = x;
		startY = y;
		Polygon triangle = (Polygon) this.shape;
		
		for(int i=0; i<3; i++)
			triangle.addPoint(x, y);
	}

	@Override
	protected void movePoint(int x, int y) {
		int width = Math.abs(x - startX);
		
		if(x < startX)
			width = -width;
		
		Polygon triangle = (Polygon) this.shape;
		triangle.xpoints[0] = startX;
		triangle.ypoints[0] = y;
		
		triangle.xpoints[1] = startX + width/2;
		triangle.ypoints[1] = startY;
		
		triangle.xpoints[2] = x ;
		triangle.ypoints[2] = y;
	}

	@Override
	public void stopPoint(int x, int y) {
		
	}

}
