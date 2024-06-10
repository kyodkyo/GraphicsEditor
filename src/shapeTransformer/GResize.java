package shapeTransformer;

import java.awt.Graphics2D;

import shapeTools.GShape;

public class GResize extends GTransformer {
	
	private static final long serialVersionUID = 1L;

	public GResize(GShape currentShape) {
		super(currentShape);
	}

	@Override
	protected void transform(Graphics2D graphics2d, int x, int y, int px, int py) {
		this.currentShape.resize(graphics2d, x, y, px, py);
	}

}
