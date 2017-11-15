package ca.utoronto.utm.paint;

public class Square{
	private Point origin;
	private Point anchor;
	private int dimension;

	public int getDimension() {
		return dimension;
	}

	public Square(Point origin){
			//origin is top left of rectangle
			this.setOrigin(origin);
			//anchor is the first clicked point when making rectangle
			this.anchor = new Point(origin.getX(),origin.getY());
		}

	public void setEnd(Point end_point) {
		int end_x = end_point.getX();
		int end_y = end_point.getY();
		int anchor_x = anchor.getX();
		int anchor_y = anchor.getY();
				
	    
	    int width = Math.abs(end_x-anchor_x);
	    int height = Math.abs(end_y-anchor_y);
	    
	    this.dimension = Math.min(width,height);
	    int x_location;
	    int y_location;
	    
	    if(end_x>=anchor_x && end_y>=anchor_y){
	    	x_location = anchor_x;
	    	y_location = anchor_y;
	    	}
	    else if(end_x>=anchor_x && end_y<=anchor_y){
	    	x_location = anchor_x;
	    	y_location = anchor_y - this.dimension;
	    }
	    else if(end_x<=anchor_x && end_y>=anchor_y){
	    	x_location = anchor_x - this.dimension;
	    	y_location = anchor_y;
	    }
	    else{
	    	x_location = anchor_x-this.dimension;
	    	y_location = anchor_y-this.dimension;
	    }
	    this.origin.setX(x_location);
	    this.origin.setY(y_location);	
	}


	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	}


