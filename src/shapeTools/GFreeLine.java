package shapeTools;

import java.awt.Shape;
import java.awt.geom.Path2D;

public class GFreeLine extends GShape{

	private static final long serialVersionUID = 1L;

	public GFreeLine() {
		super(EDrawingStyle.e2PStyle);
		this.shape = new Path2D.Double();
	}

	public GShape clone() {
		GShape cloneShape = super.clone();
		cloneShape.shape = (Shape) ((Path2D.Double)(this.shape)).clone();
		return cloneShape;
	}
	
	@Override
	public void addPoints(int x, int y) {
		Path2D path = (Path2D) this.shape;
		path.moveTo(x, y);
	}

	@Override
	protected void movePoint(int x, int y) {
		Path2D path = (Path2D) this.shape;
		path.lineTo(x, y);
	}

	@Override
	public void stopPoint(int x, int y) {
		
	}

}
