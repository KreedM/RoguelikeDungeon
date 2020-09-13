//Simple data type representing the characteristics of simple rectangles

public class Rectangle {
	private int x, y, width, height;
	private Point center;
	
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		center = new Point(x + width / 2, y + height / 2); //Type contains static coordinates
	}
	
	//Helper methods
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Point getCenter() {
		return center;
	}
}
