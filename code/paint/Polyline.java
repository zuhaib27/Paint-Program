package ca.utoronto.utm.paint;

public class Polyline {
	private Point start_point;
	private Point end_point;
	
	public Polyline(Point initial_point){
		this.start_point = initial_point;
		this.end_point = new Point(start_point.getX(),start_point.getY());
	}
	
	public Point getStartpoint(){
		return this.start_point;
	}
	public Point getEndpoint(){
		return this.end_point;
	}
	public void setEndpoint(int x, int y){
		this.end_point.setX(x);
		this.end_point.setY(y);
	}
}
