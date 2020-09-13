import java.util.ArrayList;

public class Leaf {
	private Leaf left, right;
	private Rectangle container, room;
	
	public Leaf(Rectangle container) {
		this.container = container;
	}
	
	public static Leaf splitLeaves(int iterations, Rectangle container) {
		Leaf leaf = new Leaf(container);

		if (iterations == 0 || container.width <= DungeonGenerator.MIN_ROOM_SIZE || container.height <= DungeonGenerator.MIN_ROOM_SIZE) 
			return leaf;
		
		Rectangle[] splitContainers = Rectangle.splitRectangles(container);
		
		leaf.left = splitLeaves(iterations - 1, splitContainers[0]);
		leaf.right = splitLeaves(iterations - 1, splitContainers[1]);
		
		return leaf;
	}
	
	public void addLeaf(ArrayList<Leaf> leaves) {
		if (left == null && right == null)
			leaves.add(this);
		else {
			left.addLeaf(leaves);
			right.addLeaf(leaves);
		}	
	}
	
	public Leaf getLeft() {
		return left;
	}
	
	public Leaf getRight() {
		return right;
	}
	
	public Rectangle getContainer() {
		return container;
	}
	
	public void setRoom(Rectangle room) {
		this.room = room;
	}
	
	public Rectangle getRoom() {
		return room;
	}
}
