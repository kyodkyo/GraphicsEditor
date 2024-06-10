package shapeTools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

import global.Constants.GDrawingPanel.EAnchor;


public abstract class GShape implements Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;

	public enum EDrawingStyle{
		e2PStyle,
		eNPStyle
	}
	
	public enum EAnchors{
		eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
		eNN(new Cursor(Cursor.N_RESIZE_CURSOR)),
		eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)),
		eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
		eEE(new Cursor(Cursor.E_RESIZE_CURSOR)),
		eSW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
		eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
		eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)),
		eRR(new Cursor(Cursor.HAND_CURSOR)),
		eMM(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		private Cursor cursor;
		
		private EAnchors(Cursor cursor) {
			this.setCursor(cursor);
		}

		public Cursor getCursor() {
			return cursor;
		}

		public void setCursor(Cursor cursor) {
			this.cursor = cursor;
		}
	}
	
	public Shape shape;
	private boolean isSelected;

	private EDrawingStyle eDrawingStyle;
	
	private Ellipse2D[] anchors;
	private EAnchors selectedAnchor;
	private EAnchor eAnchor;
	
	private AffineTransform affineTransform;
	
	private Color shapeColor;
	private Color lineColor;
	private int lineThickness;
	
	protected Point p1;
	protected Point p2;
	protected Point o2;
	
	// getters & setters
	public EDrawingStyle getEDrawingStyle() {
		return this.eDrawingStyle;
	}
	
	public EAnchor getAction() {
		return this.eAnchor;
	}
	
    public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public boolean getSelected() {
		return isSelected;
	}

	public EAnchors getSelectedAnchor() {
		return selectedAnchor;
	}

	public void setSelectedAnchor(EAnchors selectedAnchor) {
		this.selectedAnchor = selectedAnchor;
	}
	
	public Color getShapeColor() {
		return shapeColor;
	}
	
	public void setShapeColor(Color color) {
		this.shapeColor = color;
	}
	
	public Color getLineColor() {
		return lineColor;
	}
	
	public void setLineColor(Color color) {
		this.lineColor = color;
	}
	
	public float getLineThickness() {
		return this.lineThickness;
	}

	public void setLineThickness(int thick) {
		if (0 < thick)
			this.lineThickness = thick;
	}
	
	// methods
    public GShape(EDrawingStyle eDrawingStyle) {
    	this.anchors = new Ellipse2D.Double[EAnchors.values().length];
    	for(EAnchors eAnchor: EAnchors.values()) {
    		this.anchors[eAnchor.ordinal()]=new Ellipse2D.Double();
    	}
    	
    	this.isSelected = false;

    	this.eDrawingStyle = eDrawingStyle;
    	this.setSelectedAnchor(null);
    	this.eAnchor = null;
    	this.affineTransform = new AffineTransform();
    	this.shapeColor = Color.WHITE;
    	this.lineColor = Color.BLACK;
    	this.lineThickness = 1;
    	
    	this.p1 = new Point();
    	this.p2 = new Point();
    	this.o2 = new Point();
    }
    
    public GShape clone() {
		GShape cloned = null;
		try {
			cloned = (GShape) super.clone();
			cloned.anchors = this.anchors.clone();
			
			for(int i=0; i<this.anchors.length; i++) 
				cloned.anchors[i] = (Ellipse2D) this.anchors[i].clone();
			cloned.affineTransform=(AffineTransform) this.affineTransform.clone();
		} 
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return cloned;	
	}
    
    public EAnchor contains(int x, int y) {
    	Shape transformedShape;
    	
    	this.eAnchor = null;
    	if (this.isSelected) {
    		for(int i=0; i<EAnchors.values().length - 1; i++) {
    			transformedShape = this.affineTransform.createTransformedShape(this.anchors[i]);
    			if (transformedShape.contains(x, y)) {
    				this.setSelectedAnchor(EAnchors.values()[i]); 
    				this.eAnchor = EAnchor.eResize;
    			}
    		}
    		
    		transformedShape = this.affineTransform.createTransformedShape(this.anchors[EAnchors.eRR.ordinal()]);
    		if (transformedShape.contains(x, y)) {
    			this.eAnchor = EAnchor.eRotate;
    		}
    	}
    	transformedShape = this.affineTransform.createTransformedShape(this.shape);
    	if (transformedShape.contains(x, y)) {
    		this.eAnchor = EAnchor.eMove;
    	}
    	return this.eAnchor;
    }
    
    public void draw(Graphics2D graphics2D) {
    	graphics2D.setColor(this.shapeColor);
    	graphics2D.fill(this.affineTransform.createTransformedShape(this.shape));
    	graphics2D.setColor(this.lineColor);
    	graphics2D.setStroke(new BasicStroke(this.lineThickness, BasicStroke.CAP_ROUND, 0));
		graphics2D.draw(this.affineTransform.createTransformedShape(this.shape));

		if(this.isSelected) {
			this.drawAnchors(graphics2D);
		}
	}
    
    private void drawAnchors(Graphics2D graphics2D) {
		int wAnchor = 10;
		int hAnchor = 10;
		
		Rectangle rectangle = this.shape.getBounds();		
		int x0 = rectangle.x - wAnchor/2;
		int x1 = rectangle.x + (rectangle.width)/2 - wAnchor/2;
		int x2 = rectangle.x + rectangle.width - wAnchor/2;
		int y0 = rectangle.y - hAnchor/2;
		int y1 = rectangle.y + (rectangle.height)/2 - hAnchor/2;
		int y2 = rectangle.y + rectangle.height - hAnchor/2;
		
		this.anchors[EAnchors.eNW.ordinal()].setFrame(x0, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.eNN.ordinal()].setFrame(x0, y1, wAnchor, hAnchor);
		this.anchors[EAnchors.eNE.ordinal()].setFrame(x0, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.eWW.ordinal()].setFrame(x1, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.eEE.ordinal()].setFrame(x1, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.eSW.ordinal()].setFrame(x2, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.eSS.ordinal()].setFrame(x2, y1, wAnchor, hAnchor);
		this.anchors[EAnchors.eSE.ordinal()].setFrame(x2, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.eRR.ordinal()].setFrame(x1, y0-50 ,wAnchor, hAnchor);
	
		for(EAnchors eAnchor: EAnchors.values()) {
			graphics2D.setColor(Color.WHITE);
			Shape transformAnchor = this.affineTransform.createTransformedShape(this.anchors[eAnchor.ordinal()]);
			graphics2D.fill(transformAnchor);
			graphics2D.setColor(Color.black);
			graphics2D.draw(transformAnchor);
		}
	}

    public void prepareToTransform(Graphics2D graphics2D,int x, int y) {
		this.draw(graphics2D);
	}
	
	public void move(Graphics2D graphics2d, int dx, int dy) {
		this.draw(graphics2d);
		this.affineTransform.translate(dx, dy);
		this.draw(graphics2d);
	}
	
	public void resize(Graphics2D graphics2d, int x, int y, int px, int py) {
		this.draw(graphics2d);
		
		double width = this.shape.getBounds().getWidth();
		double height = this.shape.getBounds().getHeight();
		double dw = 0;
		double dh = 0;
		
		switch (this.selectedAnchor) {
			case eNW:
				dw = -(x - px);	
				dh = -(y - py);
				p1.setLocation(this.anchors[EAnchors.eSE.ordinal()].getCenterX(), this.anchors[EAnchors.eSE.ordinal()].getCenterY());
				break;
			case eNN:
				dw = -(x - px);	
				dh = 0; 	 
				p1.setLocation(this.anchors[EAnchors.eSS.ordinal()].getCenterX(), 0);
				break;
			case eNE:
				dw = -(x - px);	
				dh = y - py; 	
				p1.setLocation(this.anchors[EAnchors.eSW.ordinal()].getCenterX(), this.anchors[EAnchors.eSW.ordinal()].getCenterY());
				break;
			case eWW:
				dw = 0;	
				dh = -(y - py); 
				p1.setLocation(0, this.anchors[EAnchors.eEE.ordinal()].getCenterY());
				break;
			case eEE:
				dw = 0;	
				dh = y - py; 
				p1.setLocation(0, this.anchors[EAnchors.eWW.ordinal()].getCenterY());
				break;
			case eSW:
				dw = x - px;	
				dh = -(y - py); 
				p1.setLocation(this.anchors[EAnchors.eNE.ordinal()].getCenterX(), this.anchors[EAnchors.eNE.ordinal()].getCenterY());
				break;
			case eSS:
				dw = x - px;	
				dh = 0; 	
				p1.setLocation(this.anchors[EAnchors.eNN.ordinal()].getCenterX(), 0);
				break;
			case eSE:
				dw = x - px;	
				dh = y - py; 
				p1.setLocation(this.anchors[EAnchors.eNW.ordinal()].getCenterX(), this.anchors[EAnchors.eNW.ordinal()].getCenterY());
				break;
			default: 
				break;
		}
		
		double rX = 1.0, rY = 1.0;
		if(height > 0.0) 
			rY = dh / height + rY;
		
		if(width > 0.0) 
			rX = dw / width + rX;
		
		this.affineTransform.scale(rX, rY);
		this.affineTransform.translate(-dw, -dh);
		this.draw(graphics2d);
	}
	
	public void rotate(Graphics2D graphics2d, Point start, Point end) {
		this.draw(graphics2d);
		
		double cx = this.shape.getBounds().getCenterX();
		double cy = this.shape.getBounds().getCenterY();
		
		double sAngle = Math.toDegrees(Math.atan2(cx-start.x, cy-start.y));
		double eAngle = Math.toDegrees(Math.atan2(cx-end.x, cy-end.y));
		double rAngle = sAngle - eAngle;
		
		if (rAngle < 0)
			rAngle += 360;
		
		this.affineTransform.rotate(Math.toRadians(rAngle), cx, cy);
		this.draw(graphics2d);
	}

	
	public void animate(Graphics2D graphics2d, int x, int y) {
		this.draw(graphics2d);
		this.movePoint(x, y);
		this.draw(graphics2d);
	}
    
    // interface
    public abstract void addPoints(int x, int y);
    protected abstract void movePoint(int x, int y);
    public abstract void stopPoint(int x, int y);
	public void setIntermediatePoint(int x, int y) {}

}
