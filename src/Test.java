import javax.swing.JFrame;

public class Test {
	public static void main(String[] args) {
		DungeonGenerator dungeon = new DungeonGenerator(86, 4);
		dungeon.generateDungeon();
		
		JFrame frame = new JFrame();
		frame.setTitle("Dungeon Drawer");
		frame.setContentPane(new DungeonDrawer(dungeon.getSize(), dungeon.getLeaves(), dungeon.getCorridors()));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
