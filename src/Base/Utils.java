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
		
		// 循环所需图片个数的一半，每随机一张图片，就往队列中放两次
		// 结果是：队列中的图片总数就是所需要的图片数，保证每张图片都是偶数个
		for(int i = 0;i<COLS*ROWS/2;i++) {
			int num = rd.nextInt(picCounts);
			String choose = "imgs/" + num + ".gif";
			ImageIcon imageIcon = new ImageIcon(choose);
			iconList.add(imageIcon);
			iconList.add(imageIcon);
		}
		
		// 将队列中的图片随机取出，放入二维数组
		for(int i=0;i<ROWS;i++) {
			for(int j=0;j<COLS;j++) {
				int index = rd.nextInt(iconList.size());
				ICONS[i][j] = iconList.get(index);
				iconList.remove(index);
			}
		}
		
	}
}
