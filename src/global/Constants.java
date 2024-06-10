package global;

import shapeTools.GFreeLine;
import shapeTools.GLine;
import shapeTools.GOval;
import shapeTools.GPolygon;
import shapeTools.GRectangle;
import shapeTools.GShape;
import shapeTools.GStar;
import shapeTools.GTriangle;

public class Constants {
	public final static String FILENAME = "output";
	
	public enum EShapeButtons {
		eLine("line", new GLine(), "images/line.jpeg"),
		eFreeLine("freeline", new GFreeLine(), "images/freeline.jpeg"),
		eRectangle("rectangle", new GRectangle(), "images/rectangle.jpeg"),
	    eOval("oval", new GOval(), "images/oval.jpeg"),
	    eTriangle("triangle", new GTriangle(), "images/triangle.jpeg"),
		ePolygon("polygon", new GPolygon(), "images/polygon.jpeg"),
		eStar("star", new GStar(), "images/star.jpeg");
	
	    private String text;
	    private GShape shapeTool;
	    private String image;
	
	    private EShapeButtons(String text, GShape shapeTool, String image){
	        this.text = text;
	        this.shapeTool = shapeTool;
	        this.image = image;	    
        }
	    
	    public String getText() {
	        return this.text;
	    }
	
	    public GShape getShapeTool() {
	        return this.shapeTool;
	    }
	    
	    public String getImage() {
	    	return this.image;
	    }
	}
	
	public static class GMainFrame {
		public final static int WiDTH = 800;
		public final static int HEIGHT = 1200;
	}
	
	public static class GMenuBar{
		public enum EMenu{
			eFile("파일"),
			eEdit("편집"),
			eChange("변경");
			
			private String text;
			
			private EMenu(String text) {
				this.text = text;
			}
			
			public String getText() {
				return this.text;			
			}
		}
	}
	
	public static class GDrawingPanel{
		public enum EDrawingState{
			eIdle,
			e2PState,
			eNPState,
			eTransformation;
		}
		
		public enum EAnchor{
			eMove,
			eResize,
			eRotate
		}
		
		public enum ETransformation{
			eDraw,
			eMove,
			eResize,
			eRotate
		}
	}
	
	public static class GFileMenu{
		public enum EFileMenu{
			eOpen("Open"),
			eSave("Save");
			
			private String text;
			
			private EFileMenu(String text) {
				this.text = text;
			}
			
			public String getText() {
				return this.text;
			}
		}
	}
	
	public static class GEditMenu{
		public enum EEditMenu{
			eUndo("Undo"),
			eRedo("Redo"),
			eDelete("Delete"),
			eDeleteAll("Delete All"),
			eCut("Cut"),
			ePaste("Paste"),
			eCopy("Copy"),
			eFront("Front"),
			eBack("Back");
			
			private String text;
			
			private EEditMenu(String text) {
				this.text = text;
			}
			
			public String getText() {
				return this.text;
			}
		}
	}
	
	public static class GChangeMenu{
		public enum EChangeMenu{
			eShapeColor("ShapeColor"),
			eLineColor("LineColor"),
			eLineThickness("LineThickness");
			
			private String text;
			
			private EChangeMenu(String text) {
				this.text = text;
			}
			
			public String getText() {
				return this.text;
			}
		}
	}
}
