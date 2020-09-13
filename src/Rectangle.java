
public class Rectangle {
	int x, y, width, height;
	Point center;
	
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		center = new Point(x + width / 2, y + height / 2);
	}
	
	public static Rectangle[] splitRectangles(Rectangle rect) {
		 Rectangle r1, r2;
		 
		 if (Math.random() > 0.5) {
			 //Vertical
			 int randHeight = (int) randomRange(rect.height * 0.4f, rect.height * 0.6f);
			 if (randHeight % 2 == 1)
				 randHeight--;
			 r1 = new Rectangle(rect.x, rect.y, rect.width, randHeight);
			 r2 = new Rectangle(rect.x, rect.y + r1.height, rect.width, rect.height - r1.height);
		 } 
		 else {
			 //Horizontal
			 int randWidth = (int) randomRange(rect.width * 0.4f, rect.width * 0.6f);
			 if (randWidth % 2 == 1)
				 randWidth--;
			 r1 = new Rectangle(rect.x, rect.y, randWidth, rect.height);
			 r2 = new Rectangle(rect.x + r1.width, rect.y, rect.width - r1.width, rect.height);
		 }
		 
		 return new Rectangle[] { r1, r2 };
	}
	
	public Point getCenter() {
		return center;
	}
	
	public static float randomRange(float min, float max) { //Float or double doesn't matter
		return (float) (min + (Math.random() * (max - min)));
	}
	
	public static int randomRange(int min, int max) { //Float or double doesn't matter
		if (min > max)
			return (int) (max + (Math.random() * (min - max + 1)));
		return (int) (min + (Math.random() * (max - min + 1)));
	}
}
