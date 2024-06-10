package shapeTools;

import java.awt.*;
import java.awt.geom.Line2D;

public class GLine extends GShape{
	
	private static final long serialVersionUID = 1L;

	public GLine() {
		super(EDrawingStyle.e2PStyle);
		this.shape = new Line2D.Double();
	}

	public GShape clone() {
		GShape cloneShape = super.clone();
		cloneShape.shape = (Shape)((Line2D.Double)(this.shape)).clone();
		return cloneShape;
	}
	
	@Override
	public void addPoints(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(x, y, x, y);
	}
	
	@Override
	public void stopPoint(int x, int y) {
	}
	
	@Override
	public void movePoint(int x, int y) { 
		Line2D line = (Line2D) this.shape;
		line.setLine(line.getX1(), line.getY1(), x, y);
	}
	
}
