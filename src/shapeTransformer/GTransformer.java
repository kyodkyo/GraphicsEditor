package shapeTransformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;

import shapeTools.GShape;

public abstract class GTransformer implements Serializable, Cloneable{

	private static final long serialVersionUID = 1L;
	
	protected GShape currentShape;
	protected int px, py;
	protected Point start;
	protected Point end;
	
	public GTransformer(GShape currentShape) {
		this.currentShape = currentShape;
		this.start = new Point();
		this.end = new Point();
	}

	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.px=x;
		this.py=y;
		this.start.setLocation(x,y);
	}
	
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		this.transform(graphics2d, x, y, px, py);
		this.px=x;
		this.py=y;
		this.start.setLocation(x,y);
	}
	
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
		this.px=x;
		this.py=y;
	}
	
	public void continueTransforming(Graphics2D graphics2d, int x, int y) {
		this.px=x;
		this.py=y;
	}
	
	protected abstract void transform(Graphics2D graphics2d, int x, int y, int px, int py);

}
