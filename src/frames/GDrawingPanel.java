package frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import global.Constants.GDrawingPanel.EAnchor;
import global.Constants.GDrawingPanel.EDrawingState;
import global.Constants.GDrawingPanel.ETransformation;
import shapeEditTools.GUndoRedoShape;
import shapeTools.GShape;
import shapeTransformer.GMove;
import shapeTransformer.GResize;
import shapeTransformer.GRotate;
import shapeTransformer.GTransformer;

import java.util.*;

public class GDrawingPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private Vector<GShape> shapes;
	private GUndoRedoShape undoRedoShape;

	private GShape shapeTool;
	private GShape currentShape;
	private GShape clonedShape;
	private GTransformer transformer;
	
	private EDrawingState eDrawingState;
	private ETransformation eTransformation;
	
	public Color shapeColor;
	public Color lineColor;
	private int LineThickness;
	
	public GDrawingPanel() {
		this.shapes = new Vector<>();
		this.undoRedoShape = new GUndoRedoShape();
		this.clonedShape = null;
		this.eDrawingState = EDrawingState.eIdle;
		
		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
	}
	
	public void initialize() {
		this.setBackground(Color.WHITE);
		this.shapes.clear();
		this.repaint();
		this.undoRedoShape.clear();
	}
	
	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		for(GShape shape : shapes) {
			shape.draw((Graphics2D)graphics);
		}
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		if (shapeTool != null) {
			shapeTool.draw((Graphics2D)graphics);
		}
	}
	
	// getters & setters
	// ---------------------------------------------------------------
	public void setShapeTool(GShape shapeTool) {
		this.shapeTool = shapeTool;
	}
	
	public Vector<GShape> getShapes() {
		return this.shapes;
	}
	
	@SuppressWarnings("unchecked")
	public void setShapes(Object object) {
		 this.shapes = (Vector<GShape>) object;
		 this.repaint();
	}
	
	public EDrawingState geteDrawingState() {
		return eDrawingState;
	}

	public void seteDrawingState(EDrawingState eDrawingState) {
		this.eDrawingState = eDrawingState;
	}

	public ETransformation geteTransformation() {
		return eTransformation;
	}

	public void seteTransformation(ETransformation eTransformation) {
		this.eTransformation = eTransformation;
	}
	
	public Color getShapeColor() {
		return this.shapeColor;
	}
	
	public void setShapeColor(Color color) {
		this.shapeColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		this.shapeTool.setShapeColor(this.shapeColor);
		if (this.currentShape != null)
			this.currentShape.setShapeColor(this.shapeColor);
		repaint();
	}
	
	public Color getLineColor() {
		return this.lineColor;
	}
	
	public void setLineColor(Color color) {
		this.lineColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		this.shapeTool.setLineColor(this.lineColor);
		if (this.currentShape != null)
			this.currentShape.setLineColor(this.lineColor);
		repaint();
	}
	
	public float getLineThickness() {
		 return this.LineThickness;
	 }

	 public void setLineThickness(int thick) {
		 this.LineThickness = thick;
		 shapeTool.setLineThickness(this.LineThickness);
		 
		 if(this.currentShape != null) 
	    	  currentShape.setLineThickness(this.LineThickness);     
		 repaint();
	 }
	
	// Drawing
	// ----------------------------------------------------------------
	private void setSelected(GShape selectedShape) {
		 for(GShape shape : this.shapes) {
			 shape.setSelected(false); 
		 }
		 this.currentShape = selectedShape;
		 this.currentShape.setSelected(true);
		 this.repaint(); 
	 }
	
	private void startDrawing(int x, int y) {
		currentShape = shapeTool.clone();
		currentShape.addPoints(x, y);
		this.repaint();
	}
	
	private void keepDrawing(int x, int y) {
		Graphics2D graphics2d = (Graphics2D) getGraphics();
		graphics2d.setXORMode(getBackground());
		currentShape.animate(graphics2d, x, y);
	}
	
	private void stopDrawing(int x, int y) {
		currentShape.stopPoint(x, y);
		this.shapes.add(this.currentShape);
		this.repaint();
		this.undoPush();
	}
	
	private void setIntermediatePoint(int x, int y) {	
		this.currentShape.setIntermediatePoint(x, y);
	}
	
	private GShape onShape(int x, int y) {
		for(GShape shape : this.shapes) {
			EAnchor eAnchor = shape.contains(x, y);
			if (eAnchor != null)
				return shape;
		}
		return null;
	}
	
	// Transforming
	// ------------------------------------------------------------------
	private void startTransforming(GShape selectedShape, int x, int y) {
		this.currentShape = selectedShape;
		EAnchor eAnchor = this.currentShape.getAction();
		
		switch(eAnchor) {
			case eMove:
				this.transformer = new GMove(this.currentShape);
				break;
			case eResize:
				this.transformer = new GResize(this.currentShape);
				break;
			case eRotate:
				this.transformer = new GRotate(this.currentShape);
				break;
			default:
				break;
		}
		
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.initTransforming(graphics2d, x, y);
	}
	
	private void keepTransforming(int x, int y) {
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.keepTransforming(graphics2d,x,y);
	}
	
	private void finishTransforming(int x, int y) {
		this.setSelected(this.currentShape);	
		this.repaint();
		this.undoPush();
	}
	
	// Editing
	// -----------------------------------------------------------------
	public void undo() {
		 Vector<GShape> undoShapes = this.undoRedoShape.undo();
		 if(undoShapes == null)
			 return;
		 this.shapes = this.undoRedoShape.deepCopy(undoShapes);
		 this.repaint();
	}
	
	public void redo() {
		Vector<GShape> redoShapes = this.undoRedoShape.redo();
		if(redoShapes == null)
			 return;
		this.shapes = this.undoRedoShape.deepCopy(redoShapes);
		this.repaint();
	}
	
	public void undoPush() {
		this.undoRedoShape.push(this.undoRedoShape.deepCopy(this.shapes));
	}
	
	public void cutShape() {
		if(this.currentShape != null) {
			this.clonedShape = this.currentShape.clone();
			this.shapes.remove(this.currentShape);
			this.undoPush();
			this.repaint();
		}
	}
	
	public void copyShape() {
		if(this.currentShape != null)
			this.clonedShape = this.currentShape.clone();
	}
	
	public void pasteShape() { 
		if(this.clonedShape!=null) {
			this.shapes.add(this.clonedShape.clone());
			this.undoPush(); 
			this.repaint();
		}
	}
	
	public void deleteOneShape() {
		if(this.currentShape!=null) {
			this.clonedShape = null;
			this.shapes.remove(this.currentShape);
			this.undoPush();
			this.repaint();
		}
	}
	
	public void deleteAllShape() {
		this.shapes.clear();
		this.undoPush();	
		this.repaint();
	}
	
	public void moveToFront() {
		Vector<GShape> temp = new Vector<GShape>();
		for (int i=shapes.size()-1; i>=0; i--) {
			if (shapes.get(i).getSelected()) {
				temp.add(shapes.get(i));
				this.shapes.remove(i);
			}
		}
		
		for (int i=temp.size()-1; i>=0; i--)
			this.shapes.add(temp.get(i));
		this.undoPush();
		this.repaint();
	}
	
	public void moveToBack() {
		Vector<GShape> temp = new Vector<GShape>();
		for (int i=shapes.size()-1; i>=0; i--) {
			if (shapes.get(i).getSelected()) {
				temp.add(shapes.get(i));
				this.shapes.remove(i);
			}
		}

		Vector<GShape> temp2 = new Vector<GShape>();
		for (int i=shapes.size()-1; i>=0; i--)
			temp2.add(shapes.get(i));

		this.shapes.clear();
		for (int i=temp.size()-1; i>=0; i--)
			this.shapes.add(temp.get(i));

		for (int i=temp2.size()-1; i>=0; i--)
			this.shapes.add(temp2.get(i));

		this.undoPush();
		this.repaint();
	}

	
	private class MouseEventHandler implements MouseListener, MouseMotionListener{
		@Override
		public void mousePressed(MouseEvent e) {
			if(eDrawingState == EDrawingState.eIdle) {
				GShape selectedShape = onShape(e.getX(), e.getY());
				
				if(selectedShape == null) { 
					if(shapeTool.getEDrawingStyle() == GShape.EDrawingStyle.e2PStyle) { 
						startDrawing(e.getX(), e.getY());
						eDrawingState = EDrawingState.e2PState;
					}
				}else {
					startTransforming(selectedShape, e.getX(), e.getY());
					eDrawingState = EDrawingState.eTransformation;
				}
			}
		}
		
		@Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 1) {
				mouse1Clicked(e);
			}
			else if (e.getClickCount() == 2) {
				mouse2Clicked(e);
			}
        }
		
		private void mouse1Clicked(MouseEvent e) {
			if(eDrawingState == EDrawingState.eIdle) {
				GShape selectedShape = onShape(e.getX(), e.getY());
				
				if(selectedShape==null) { 
					if(shapeTool.getEDrawingStyle() == GShape.EDrawingStyle.eNPStyle) {
						startDrawing(e.getX(), e.getY());
						eDrawingState = EDrawingState.eNPState;
					}
					else {
						for(GShape shape : shapes) 
							 shape.setSelected(false); 
					}
				}
				else 
					setSelected(selectedShape); 
			}
			else {
				if(shapeTool.getEDrawingStyle() == GShape.EDrawingStyle.eNPStyle)
					setIntermediatePoint(e.getX(), e.getY());
			}
			
		}
		
		private void mouse2Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPState) {
				if (shapeTool.getEDrawingStyle() == GShape.EDrawingStyle.eNPStyle) {
					stopDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.eIdle;
				}
			}
		}
        
		@Override
		public void mouseDragged(MouseEvent e) {
			if(eDrawingState == EDrawingState.e2PState) {
				if(shapeTool.getEDrawingStyle() == GShape.EDrawingStyle.e2PStyle) {
					keepDrawing(e.getX(), e.getY());
				}
			}else if(eDrawingState == EDrawingState.eTransformation) {
				keepTransforming(e.getX(), e.getY());
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PState) {
				if (shapeTool.getEDrawingStyle() == GShape.EDrawingStyle.e2PStyle) {
					stopDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.eIdle;
				}
			}
			else if (eDrawingState == EDrawingState.eTransformation) {
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPState) {
				if (shapeTool.getEDrawingStyle() == GShape.EDrawingStyle.eNPStyle) {
					keepDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPState;
				}
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

}
