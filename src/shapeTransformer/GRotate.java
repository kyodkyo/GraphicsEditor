package shapeTransformer;

import java.awt.Graphics2D;

import shapeTools.GShape;

public class GRotate extends GTransformer {

	private static final long serialVersionUID = 1L;

	public GRotate(GShape currentShape) {
		super(currentShape);
	}

	@Override
	protected void transform(Graphics2D graphics2d, int x, int y, int px, int py) {
		start.x = px;
		start.y = py;
		end.x = x;
		end.y = y;
		
		this.currentShape.rotate(graphics2d, start, end);
	}

}
