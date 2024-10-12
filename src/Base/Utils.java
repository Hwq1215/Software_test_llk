package Base;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Utils implements Config{
	public Utils() {
		
	}
	public static void initPicData() {
		ArrayList<ImageIcon> iconList= new ArrayList<ImageIcon>();
		Random rd = new Random();
		
		for(int i = 0;i<COLS*ROWS/2;i++) {
			int num = rd.nextInt(picCounts);
			String choose = "imgs/" + num + ".jpeg";
			ImageIcon imageIcon = new ImageIcon(choose);
			iconList.add(imageIcon);
			iconList.add(imageIcon);
		}
		
		
		for(int i=0;i<ROWS;i++) {
			for(int j=0;j<COLS;j++) {
				int index = rd.nextInt(iconList.size());
				ICONS[i][j] = iconList.get(index);
				iconList.remove(index);
			}
		}
		
	}
}
