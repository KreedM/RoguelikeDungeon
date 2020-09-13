import java.util.ArrayList;

//Data type that generates a randomized dungeon using a BSP tree

public class DungeonGenerator {
		
		//Constants used in generation
	    private static final int MIN_ROOM_SIZE = 16;
	    private static final float SPLIT_PROBABILITY = 0.5f;
	    private static final float MIN_CONTAINER_HEIGHT_MULTIPLIER = 0.4f, MAX_CONTAINER_HEIGHT_MULTIPLIER = 0.6f;
	    private static final float MIN_CONTAINER_WIDTH_MULTIPLIER = 0.4f, MAX_CONTAINER_WIDTH_MULTIPLIER = 0.6f;
	    private static final float MIN_ROOM_HEIGHT_MULTIPLIER = 0.6f, MAX_ROOM_HEIGHT_MULTIPLIER = 0.8f;
	    private static final float MIN_ROOM_WIDTH_MULTIPLIER = 0.6f, MAX_ROOM_WIDTH_MULTIPLIER = 0.8f;
	    
	    private Leaf tree; //Starting node, the seed
	    private ArrayList<Leaf> leaves; //Arraylist of the lowest leaves
	    private boolean[][] corridors; //2D boolean array marking the places where corridors exist
	    private int size; //The square size of the entire dungeon
	    private int iterations; //# of times the BSP split will be iterated
	    
	    public DungeonGenerator(int size, int iterations) {
	    	this.size = size;
	    	this.iterations = iterations;
	    	corridors = new boolean[size][size];
	    }

	    public void generateDungeon () { //Main method to generate the dungeon
	    	leaves = new ArrayList<Leaf>();
	    	
	    	generateContainers();
	    	
	    	generateCorridors(tree);
	    	
	    	generateRoom(tree);
	    	
	    	tree.addLeaf(leaves);
	    }
	    
	    private void generateContainers() { //Initializes the tree
	        tree = Leaf.splitLeaves(iterations, new Rectangle(0, 0, size, size));
	    }
	    
	    public static Rectangle[] splitRectangles(Rectangle rect) { //Splits the container into 2 parts
			 Rectangle r1, r2; //1st part, 2nd part respectively
			 
			 if (Math.random() > SPLIT_PROBABILITY) {
				 //Horizontal cut
				 int randHeight = (int) DungeonGenerator.randomRange(rect.getHeight() * MIN_CONTAINER_HEIGHT_MULTIPLIER, rect.getHeight() * MAX_CONTAINER_HEIGHT_MULTIPLIER);
				
				 if (randHeight % 2 == 1)
					 randHeight--;
				
				 r1 = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), randHeight);
				 r2 = new Rectangle(rect.getX(), rect.getY() + r1.getHeight(), rect.getWidth(), rect.getHeight() - r1.getHeight());
			 } 
			 else {
				 //Vertical cut
				 int randWidth = (int) DungeonGenerator.randomRange(rect.getWidth() * MIN_CONTAINER_WIDTH_MULTIPLIER, rect.getWidth() * MAX_CONTAINER_WIDTH_MULTIPLIER);
				 
				 if (randWidth % 2 == 1)
					 randWidth--;
				 
				 r1 = new Rectangle(rect.getX(), rect.getY(), randWidth, rect.getHeight());
				 r2 = new Rectangle(rect.getX() + r1.getWidth(), rect.getY(), rect.getWidth() - r1.getWidth(), rect.getHeight());
			 }
			 
			 return new Rectangle[] { r1, r2 };
		}
	    
	    private void generateCorridors(Leaf leaf) { //Method connects centers of each container, storing the result into the 2D boolean array
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
	    	
	    	generateCorridors(leaf.getLeft()); //Part that recursively calls for corridor generation on all levels of the tree
	    	generateCorridors(leaf.getRight());
	    }
	    
	    public static void generateRoom(Leaf leaf) { //Generates a randomly sized room within the constraints of its container
		    if (leaf.getLeft() == null && leaf.getRight() == null) {
		    	Rectangle container = leaf.getContainer();
		    	
		        int roomW = (int) (container.getWidth() * randomRange(MIN_ROOM_WIDTH_MULTIPLIER, MAX_ROOM_WIDTH_MULTIPLIER));
		       
		        if (roomW % 2 == 1)
					roomW++;
		        
		        int roomH = (int) (container.getHeight() * randomRange(MIN_ROOM_HEIGHT_MULTIPLIER, MAX_ROOM_HEIGHT_MULTIPLIER));
		        
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
		
	    //Helper methods
	    
	    public static float randomRange(float min, float max) { //Helper method that returns a random float between min and up to max
			return (float) (min + (Math.random() * (max - min)));
		}
		
		public static int randomRange(int min, int max) { //Helper method that returns a random integer between min and up to and including max
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
	    
	    public static int getMinRoomSize() {
	    	return MIN_ROOM_SIZE;
	    }
}
