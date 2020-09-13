import java.util.ArrayList;

//Data type representing a leaf in a BSP tree

public class Leaf {
	private Leaf left, right;
	private Rectangle container, room;
	
	public Leaf(Rectangle container) {
		this.container = container;
	}
	
	public static Leaf splitLeaves(int iterations, Rectangle container) { //Static method used to populate the tree
		Leaf leaf = new Leaf(container);

		//Prevents splitting if size is already too small
		if (iterations == 0 || container.getWidth() <= DungeonGenerator.getMinRoomSize() || container.getHeight() <= DungeonGenerator.getMinRoomSize()) 
			return leaf;
		
		Rectangle[] splitContainers = DungeonGenerator.splitRectangles(container);
		
		leaf.left = splitLeaves(iterations - 1, splitContainers[0]);
		leaf.right = splitLeaves(iterations - 1, splitContainers[1]);
		
		return leaf;
	}
	
	public void addLeaf(ArrayList<Leaf> leaves) { //Adds itself to a list if it is the lowest in the tree, meaning it doesn't have a right or left
		if (left == null && right == null)
			leaves.add(this);
		else {
			left.addLeaf(leaves);
			right.addLeaf(leaves);
		}	
	}
	
	//Helper methods
	
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
