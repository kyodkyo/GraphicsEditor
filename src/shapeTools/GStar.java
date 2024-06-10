package shapeTools;

import java.awt.Point;
import java.awt.Polygon;

public class GStar extends GShape{

	private static final long serialVersionUID = 1L;
	
	private int startX;
	private int startY;
	
	public GStar() {
		super(EDrawingStyle.eNPStyle);
		this.shape = new Polygon();
	}
	
	public GShape clone() {
		GShape cloneShape = super.clone();
		Polygon polygon = (Polygon)new Polygon();
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
		Polygon star = (Polygon) this.shape;
		for(int i=0; i<8; i++) {
			star.addPoint(x, y);
		}
	}

	@Override
	public void stopPoint(int x, int y) {
		
	}

	@Override
	protected void movePoint(int x, int y) {
		int width = (int) Math.abs(x - this.startX);
		int height = (int) Math.abs(y - this.startY);
		
		int x1 = startX + (int)width * 3 / 8;
		int x2 = startX + (int)width / 2;
		int x3 = startX + (int)width * 5 / 8;

		int y1 = startY + (int)height * 3 / 8;
		int y2 = startY + (int)height / 2;
		int y3 = startY + (int)height * 5 / 8;
		
		Polygon star = (Polygon) this.shape;
		star.xpoints[0] = startX;
		star.ypoints[0] = y2;
		
		star.xpoints[1] = x1;
		star.ypoints[1] = y1;
		
		star.xpoints[2] = x2;
		star.ypoints[2] = startY;
		
		star.xpoints[3] = x3;
		star.ypoints[3] = y1;
		
		star.xpoints[4] = x;
		star.ypoints[4] = y2;
		
		star.xpoints[5] = x3;
		star.ypoints[5] = y3;
		
		star.xpoints[6] = x2;
		star.ypoints[6] = y;
		
		star.xpoints[7] = x1;
		star.ypoints[7] = y3;
	}
}
