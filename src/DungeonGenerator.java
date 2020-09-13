import java.util.ArrayList;

public class DungeonGenerator {
	    public static final int MIN_ROOM_SIZE = 16;
	    private Leaf tree;
	    private ArrayList<Leaf> leaves;
	    private boolean[][] corridors;
	    private int size;
	    private int iterations;
	    
	    public DungeonGenerator(int size, int iterations) {
	    	this.size = size;
	    	this.iterations = iterations;
	    	corridors = new boolean[size][size];
	    }

	    public void generateDungeon () {
	    	leaves = new ArrayList<Leaf>();
	    	
	    	generateContainers();
	    	
	    	generateCorridors(tree);
	    	
	    	generateRoom(tree);
	    	
	    	tree.addLeaf(leaves);
	    }
	    
	    private void generateContainers() {
	        tree = Leaf.splitLeaves(iterations, new Rectangle(0, 0, size, size));
	    }
	    
	    private void generateCorridors(Leaf leaf) {
	    	if (leaf.getLeft() == null && leaf.getRight() == null)
				return;
	    	
	    	Point leftCenter = leaf.getLeft().getContainer().getCenter();
	    	Point rightCenter = leaf.getRight().getContainer().getCenter();

	    	if (rightCenter.getX() - leftCenter.getX() != 0) {
	    		int x = leftCenter.getX();
	    		while (rightCenter.getX() - x != 0) {
		    		corridors[x][leftCenter.getY()] = true;	 
	    			x++;
	    		}
	    	}
	    	else {
	    		int y = leftCenter.getY();
	    		while (rightCenter.getY() - y != 0) {
		    		corridors[leftCenter.getX()][y] = true;	
	    			y++;
	    		}
			}
	    	
	    	generateCorridors(leaf.getLeft());
	    	generateCorridors(leaf.getRight());
	    }
	    
	    public static void generateRoom(Leaf leaf) {
		    // should create rooms for leafs
		    if (leaf.getLeft() == null && leaf.getRight() == null) {
		    	Rectangle container = leaf.getContainer();
		    	
		        int roomW = (int) (container.getWidth() * randomRange(0.6f, 0.8f));
		       
		        if (roomW % 2 == 1)
					roomW++;
		        
		        int roomH = (int) (container.getHeight() * randomRange(0.6f, 0.8f));
		        
		        if (roomH % 2 == 1)
					roomH++;
		        
		        Point center = container.getCenter();
		        
		        int x = randomRange(Math.max(container.getX(), center.getX() - roomW + 1),
		        Math.min(container.getX() + container.getWidth() - roomW, center.getX()));
		        
		        int y = randomRange(Math.max(container.getY(), center.getY() - roomH + 1),
		        Math.min(container.getY() + container.getHeight() - roomH, center.getY()));
		        
		        leaf.setRoom(new Rectangle(x, y, roomW, roomH));
		    } 
		    else {
		        generateRoom(leaf.getLeft());
		        generateRoom(leaf.getRight());
		    }
		}
	    
		public static Rectangle[] splitRectangles(Rectangle rect) {
			 Rectangle r1, r2;
			 
			 if (Math.random() > 0.5) {
				 //Vertical
				 int randHeight = (int) DungeonGenerator.randomRange(rect.getHeight() * 0.4f, rect.getHeight() * 0.6f);
				 if (randHeight % 2 == 1)
					 randHeight--;
				 r1 = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), randHeight);
				 r2 = new Rectangle(rect.getX(), rect.getY() + r1.getHeight(), rect.getWidth(), rect.getHeight() - r1.getHeight());
			 } 
			 else {
				 //Horizontal
				 int randWidth = (int) DungeonGenerator.randomRange(rect.getWidth() * 0.4f, rect.getWidth() * 0.6f);
				 if (randWidth % 2 == 1)
					 randWidth--;
				 r1 = new Rectangle(rect.getX(), rect.getY(), randWidth, rect.getHeight());
				 r2 = new Rectangle(rect.getX() + r1.getWidth(), rect.getY(), rect.getWidth() - r1.getWidth(), rect.getHeight());
			 }
			 
			 return new Rectangle[] { r1, r2 };
		}
	    
	    public static float randomRange(float min, float max) {
			return (float) (min + (Math.random() * (max - min)));
		}
		
		public static int randomRange(int min, int max) { 
			if (min > max)
				return (int) (max + (Math.random() * (min - max + 1)));
			return (int) (min + (Math.random() * (max - min + 1)));
		}
	    
	    public ArrayList<Leaf> getLeaves() {
	    	return leaves;
	    }
	    
	    public boolean[][] getCorridors() {
	    	return corridors;
	    }
	    
	    public int getSize() {
	    	return size;
	    }
}
