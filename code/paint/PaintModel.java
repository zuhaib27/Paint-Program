
package ca.utoronto.utm.paint;

import java.util.ArrayList;
import java.util.Observable;

public class PaintModel extends Observable {
	private ArrayList<Point> points=new ArrayList<Point>();
	private ArrayList<Circle> circles=new ArrayList<Circle>();
	private ArrayList<Rectangle> rectangles=new ArrayList<Rectangle>();
	private ArrayList<Polyline> polylines=new ArrayList<Polyline>();
	private ArrayList<Square> squares=new ArrayList<Square>();
	public void addPoint(Point p){
		this.points.add(p);
		this.setChanged();
		this.notifyObservers();
	}
	public ArrayList<Point> getPoints(){
		return points;
	}
	public void addPolyline(Polyline pl){
		this.polylines.add(pl);
		this.setChanged();
		this.notifyObservers();
	}
	public ArrayList<Polyline> getPolylines(){
		return polylines;
	}
	public void addCircle(Circle c){
		this.circles.add(c);
		this.setChanged();
		this.notifyObservers();
	}
	public ArrayList<Circle> getCircles(){
		return this.circles;
	}
	public void addSquare(Square s){
		this.squares.add(s);
		this.setChanged();
		this.notifyObservers();
	}
	public ArrayList<Square> getSquares(){
		return this.squares;
	}
	
	public void addRectangle(Rectangle r){
		this.rectangles.add(r);
		this.setChanged();
		this.notifyObservers();
	}
	public ArrayList<Rectangle> getRectangles(){
		return this.rectangles;
	}
}

