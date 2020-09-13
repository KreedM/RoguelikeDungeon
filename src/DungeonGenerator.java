import java.util.ArrayList;

public class DungeonGenerator {
	    public static final int MIN_ROOM_SIZE = 16;

	    public int size;

	    //Range (1, 6)
	    public int iterations;

	    //Range (1, 4)
	    public static int corridorThickness = 3;
	    
	    private Leaf tree;
	    
	    private ArrayList<Leaf> leaves;
	    
	    private boolean[][] corridors;
	    
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

	    	if (rightCenter.x - leftCenter.x != 0) {
	    		int x = leftCenter.x;
	    		while (rightCenter.x - x != 0) {
	    			for (int i = 0; i < corridorThickness; i++)
		    			corridors[x][leftCenter.y + i - corridorThickness / 2] = true;	 
	    			x++;
	    		}
	    	}
	    	else {
	    		int y = leftCenter.y;
	    		while (rightCenter.y - y != 0) {
	    			for (int i = 0; i < corridorThickness; i++)
		    			corridors[leftCenter.x + i - corridorThickness / 2][y] = true;	
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
		    	
		        int roomW = (int) (container.width * Rectangle.randomRange(0.5f, 0.8f));
		       
		        if (roomW % 2 == 1)
					roomW++;
		        
		        int roomH = (int) (container.height * Rectangle.randomRange(0.5f, 1));
		        
		        if (roomH % 2 == 1)
					roomH++;
		        
		        Point center = container.getCenter();
		        
		        int x = Rectangle.randomRange(Math.max(container.x, center.x + corridorThickness / 2 - roomW + 1),
		        Math.min(container.x + container.width - roomW, center.x));
		        
		        int y = Rectangle.randomRange(Math.max(container.y, center.y + corridorThickness / 2 - roomH + 1),
		        Math.min(container.y + container.height - roomH, center.y));
		        
		        leaf.setRoom(new Rectangle(x, y, roomW, roomH));
		    } 
		    else {
		        generateRoom(leaf.getLeft());
		        generateRoom(leaf.getRight());
		    }
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
