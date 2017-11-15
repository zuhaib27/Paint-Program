package ca.utoronto.utm.paint;

import javax.swing.*;  
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

// https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
// https://docs.oracle.com/javase/tutorial/2d/

class PaintPanel extends JPanel implements Observer, MouseMotionListener, MouseListener  {
	private int i=0;
	private PaintModel model; // slight departure from MVC, because of the way painting works
	private View view; // So we can talk to our parent or other components of the  view

	private int thickness;

	private Color color;
	private String mode; // modifies how we interpret input (could be better?)
	private Circle circle; // the circle we are building
	private Rectangle rectangle;
	private Polyline polyline;
	private Square square;

	public PaintPanel(PaintModel model, View view){
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(300,300));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.model = model;
		this.model.addObserver(this);
		
		this.thickness = thickness;
		this.view=view;

		this.view=view;
	
		this.color = Color.BLACK;

	}
	/**
	 *  View aspect of this
	 */
	public void paintComponent(Graphics g) {
		// Use g to draw on the JPanel, lookup java.awt.Graphics in
		// the javadoc to see more of what this can do for you!!
		
        super.paintComponent(g); //paint background
        Graphics2D g2d = (Graphics2D) g; // lets use the advanced api
		// setBackground (Color.blue); 


		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(this.thickness));
		
		
	
//		// Draw Lines
//		ArrayList<Point> points = this.model.getPoints();
//		for(int i=0;i<points.size()-1; i++){
//			Point p1=points.get(i);
//			Point p2=points.get(i+1);
//			g2d.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	
			
//		}

		// Draw Lines
		ArrayList<Point> points = this.model.getPoints();
		for(int i=0;i<points.size()-1; i++){
			Point p1=points.get(i);
			Point p2=points.get(i+1);
			g2d.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		}
		
		ArrayList<Polyline> polylines = this.model.getPolylines();
		for(Polyline pl:polylines){
			Point start_point = pl.getStartpoint();
			Point end_point = pl.getEndpoint();
			g2d.drawLine(start_point.getX(), start_point.getY(),
					end_point.getX(), end_point.getY());
		}
		if(this.polyline!=null){
			Point start_point = this.polyline.getStartpoint();
			Point end_point = this.polyline.getEndpoint();
			g2d.drawLine(start_point.getX(), start_point.getY(),
					end_point.getX(), end_point.getY());
		}

		// Draw Circles
		ArrayList<Circle> circles = this.model.getCircles();
		for(Circle c: circles){
			int x = c.getCentre().getX();
			int y = c.getCentre().getY();
			int radius = c.getRadius();
			if(setFill){
				g2d.fillOval(x-radius, y-radius, 2*radius, 2*radius);
			}
			else{
				g2d.drawOval(x-radius, y-radius, 2*radius, 2*radius);
			}
		}


		if(this.circle!=null){
			int circle_radius = this.circle.getRadius();
			g2d.drawOval(this.circle.getCentre().getX()-circle_radius, 
					this.circle.getCentre().getY()-circle_radius, 2*circle_radius, 2*circle_radius);
		}

		// Draw Rectangle

		ArrayList<Rectangle> rectangles = this.model.getRectangles();
		for(Rectangle r: rectangles){
			int x = r.getOrigin().getX();
			int y = r.getOrigin().getY();
			int width = r.getWidth();
			int height = r.getHeight();
		
			if(setFill){
				g2d.fillRect(x, y, width, height);
			}
			else{
				g2d.drawRect(x, y, width, height);
			}
			
		}
		if(rectangle!=null){
			g2d.drawRect(rectangle.getOrigin().getX(), rectangle.getOrigin().getY()
					, rectangle.getWidth(), rectangle.getHeight());
		}

		ArrayList<Square> squares = this.model.getSquares();
		for(Square s: squares){
			int x = s.getOrigin().getX();
			int y = s.getOrigin().getY();
			int dimensions=s.getDimension();
			if(setFill){
				g2d.fillRect(x, y, dimensions, dimensions);
			}
			else{
				g2d.drawRect(x, y, dimensions, dimensions);
			}
			}
			if(this.square!=null){
				int dimensions = this.square.getDimension();
				g2d.drawRect(this.square.getOrigin().getX(), this.square.getOrigin().getY()
							, dimensions, dimensions);
				}
			g2d.dispose();
	}

	protected int last_x;
	protected int last_y;
	/**
	 * Move to current point on panel.
	 * @param x integer value representing x coordinate on panel.
	 * @param y integer value representing y coordinate on panel.
	 */
	
	 public void moveto(int x, int y) {
		  last_x = x;
		  last_y = y;
		 }

	/** 
	  * Draw from the last point to this point, then remember new point 
	  * 
	  */
	public void lineto(int x, int y) {
		Graphics g = getGraphics(); // Get the object to draw with
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(this.thickness));
		g2d.setColor(color); // Tell it what color to use
		g2d.drawLine(last_x, last_y, x, y); // Tell it what to draw
		moveto(x, y); // Save the current point
	}

	@Override
	public void update(Observable o, Object arg) {
		// Not exactly how MVC works, but similar.
		this.repaint(); // Schedule a call to paintComponent
	}
	
	public void setThickness(int t){
		this.thickness = t;
	}
	
	/**
	 *  Controller aspect of this
	 */
	public void setMode(String mode){
		this.mode=mode;
	}
	
	// MouseMotionListener below
	@Override
	public void mouseMoved(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Circle"){
			
		}
	}

	private void setCircle(int x, int y){
		int x_gap = this.circle.getCentre().getX()-x;
		int y_gap = this.circle.getCentre().getY()-y;
		int radius = (int) Math.sqrt(Math.pow(x_gap,2) + Math.pow(y_gap,2));
		this.circle.setRadius(radius);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(this.mode=="Squiggle"){
//			this.model.addPoint(new Point(e.getX(), e.getY()));
			lineto(e.getX(), e.getY());
		} else if(this.mode=="Circle"){
			//feedback and draw "potential circle... transition"
			setCircle(e.getX(), e.getY());
			placeCircle(e.getX(), e.getY());
			this.repaint();
			
		} else if (this.mode == "Rectangle"){
			this.rectangle.setEnd(new Point(e.getX(),e.getY()));
			this.repaint();

		}
		else if(this.mode == "Polyline"){
			this.polyline.setEndpoint(e.getX(), e.getY());
			this.repaint();
		}
		else if(this.mode=="Square"){
			this.square.setEnd(new Point(e.getX(),e.getY()));
			this.repaint();
		}
		
		}


	// MouseListener below
	@Override
	public void mouseClicked(MouseEvent e) {
		if(this.mode=="Squiggle"){
			moveto(e.getX(), e.getY());
			//model.addPoint(p);
		} else if(this.mode=="Circle"){
			moveto(e.getX(), e.getY());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(this.mode=="Squiggle"){
			moveto(e.getX(), e.getY());
		} else if(this.mode=="Circle"){
			// Problematic notion of radius and centre!!
			Point centre = new Point(e.getX(), e.getY());
			int radius = 0;
			this.circle=new Circle(centre, radius);
		}
		else if(this.mode=="Rectangle"){
			this.rectangle=new Rectangle(new Point(e.getX(),e.getY()));
		}
		else if(this.mode=="Polyline"){
			this.polyline=new Polyline(new Point(e.getX(),e.getY()));
		}
		else if(this.mode=="Square"){
			this.square=new Square(new Point(e.getX(),e.getY()));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Circle"){
			if(this.circle!=null){

				setCircle(e.getX(),e.getY());
				Circle circle_reference = this.circle;
				//set circle to null so that repaint will not paint circle twice
				this.circle = null;
				this.model.addCircle(circle_reference);
			}
		} else if(this.mode == "Rectangle"){
			if (this.rectangle !=null){
				this.rectangle.setEnd(new Point(e.getX(),e.getY()));
				Rectangle rectangle_reference = this.rectangle;
				//set rectangle to null so that repaint will not paint rectangle twice.
				this.rectangle = null;
				this.model.addRectangle(rectangle_reference);

			}
		}
		else if(this.mode=="Polyline"){
			if(this.polyline!=null){
				this.polyline.setEndpoint(e.getX(), e.getY());
				Polyline polyline_reference = this.polyline;
				this.polyline = null;
				this.model.addPolyline(polyline_reference);
			}
		}
		else if(this.mode=="Square"){
			if(this.square!=null){
				this.square.setEnd(new Point(e.getX(),e.getY()));
				Square square_reference = this.square;
				this.square=null;
				this.model.addSquare(square_reference);
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Circle"){
			
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(this.mode=="Squiggle"){
		
		} else if(this.mode=="Circle"){
		}
	}
		
	public void setColour(Color c) {
		this.color = c;
	}
	
	private boolean setFill = false;
	public void setFill(boolean b) {
		setFill = b;
		
	}

	private void placeCircle(int x, int y){
		int x_gap = this.circle.getCentre().getX()-x;
		int y_gap = this.circle.getCentre().getY()-y;
		int radius = (int) Math.sqrt(Math.pow(x_gap,2) + Math.pow(y_gap,2));
		this.circle.setRadius(radius);
		this.model.addCircle(this.circle);
	}

}


