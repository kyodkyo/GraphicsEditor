package shapeEditTools;

import java.util.Vector;

import shapeTools.GShape;

public class GUndoRedoShape {
	
	public final static int MAXSIZE = 10;
	private int index;
	private int top;
	private int bottom;
	private Vector<Vector<GShape>> undoRedoShapes;
	
	public GUndoRedoShape() {
		this.index = -1;
		this.top = -1;
		this.bottom = 0;
		this.undoRedoShapes = new Vector<Vector<GShape>>();
		
		for(int i=0; i<MAXSIZE; i++) 
			this.undoRedoShapes.add(new Vector<GShape>());
	}
	
	public void push(Vector<GShape> shapes){
		this.index += 1; 
		this.undoRedoShapes.set(this.index % MAXSIZE, shapes);
		this.top = this.index;
	}
	
	public Vector<GShape> undo(){ 
		if(this.index - this.bottom - 1 < 0)
			return null;
		
		index -= 1;
		return this.undoRedoShapes.get(this.index % MAXSIZE);
	}
	
	public Vector<GShape> redo(){
		if(this.top - this.index - 1 < 0) 
			return null;
		
		index += 1;
		return this.undoRedoShapes.get(this.index % MAXSIZE);
	}
	
	public void clear() {
		this.index = -1;
		this.top = -1;
		this.bottom = 0;
		this.undoRedoShapes = new Vector<Vector<GShape>>();
		
		for(int i=0; i<MAXSIZE; i++)
			this.undoRedoShapes.add(new Vector<GShape>());
	}
	
	
	@SuppressWarnings("unchecked")
	public Vector<GShape> deepCopy(Vector<GShape> shapes){
		Vector<GShape> cloneShapes = (Vector<GShape>) shapes.clone();
		for(int i=0; i<shapes.size(); i++) 
			cloneShapes.set(i, shapes.get(i).clone());
		return cloneShapes;
	}
}
