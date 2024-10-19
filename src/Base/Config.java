package Base;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public interface Config {
	public static final int X0 = 20;
	public static final int Y0 = 20;
	
	public static final int picCounts = 9;
	
	public static int SIZE = 40;
	
	public static int COLS = 18;
	public static int ROWS = 10;
	
	public static ImageIcon ICONS[][] = new ImageIcon[ROWS][COLS];
	
	public ArrayList<Point> wireList = new ArrayList<Point>(); 
	
	public ArrayList<Point> showBoradList = new ArrayList<Point>();

	
}
