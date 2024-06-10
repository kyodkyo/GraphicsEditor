package shapeTransformer;

import java.awt.Graphics2D;

import shapeTools.GShape;

public class GMove extends GTransformer{

	private static final long serialVersionUID = 1L;

	public GMove(GShape currentShape) {
		super(currentShape);
	}

	@Override
	protected void transform(Graphics2D graphics2d, int x, int y, int px, int py) {
		this.currentShape.move(graphics2d, x-px, y-py);
	}

}
