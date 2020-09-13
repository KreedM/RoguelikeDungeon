import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class DungeonDrawer extends JPanel {
	private static final long serialVersionUID = 1L;
	private final int tileSize = 8;
	private int size;
	private ArrayList<Rectangle> containers, rooms;
	private boolean[][] corridors;
	
	public DungeonDrawer(int size, ArrayList<Leaf> leaves, boolean[][] corridors) {
		super();
		
		setPreferredSize(new Dimension(size * tileSize, size * tileSize));
		
		this.size = size;
		
		containers = new ArrayList<Rectangle>();
		rooms = new ArrayList<Rectangle>();
		this.corridors = corridors;
		
		for (Leaf leaf : leaves) {
			containers.add(leaf.getContainer());
			rooms.add(leaf.getRoom());
		}
		setBackground(Color.BLACK);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		/*
		g.setColor(Color.GRAY);
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				g.drawRect(i * tileSize, j * tileSize, tileSize - 1, tileSize - 1);
			}
		}
		
		g.setColor(Color.GREEN);
		
		for (Rectangle container : containers)
			g.drawRect(container.getX() * tileSize, container.getY() * tileSize, container.getWidth() * tileSize - 1, container.getHeight() * tileSize - 1);
		
		*/
		
		g.setColor(Color.LIGHT_GRAY);
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (corridors[i][j])
					g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
			}
		}
		
		g.setColor(Color.DARK_GRAY);
		
		for (Rectangle room : rooms)
			g.fillRect(room.getX() * tileSize, room.getY() * tileSize, room.getWidth() * tileSize, room.getHeight() * tileSize);
		
	}
}
