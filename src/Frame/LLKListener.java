package Frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Base.Algr;
import Base.Config;
import Base.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
public class LLKListener implements MouseListener,Config{
public  int marks = 0;
private Graphics2D g;
private int r1 = 0,r2 = 0,c1 = 0,c2 = 0;
private ImageIcon icon1 = null,icon2 = null;
private int count = 0;
private MainJFrame mainJFrame;
private Timer gameTimer;

public LLKListener(Graphics2D g,MainJFrame frame) {
		this.g = g;
		//设置线条的粗细
		this.g.setStroke(new BasicStroke(5));
		this.g.setColor(Color.GREEN);
		this.mainJFrame = frame;
		gameTimer = new Timer(200,e->checkGameOver());
		gameTimer.start();
	}

@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseReleased(MouseEvent e) {

	// 获得事件源对象：产生事件的对象,即为游戏面板
	JPanel panel = (JPanel) e.getSource();
	
	// 获得光标按下的位置
			int x = e.getX();
			int y = e.getY();
	 
			// 保存坐标，用于绘制提示框
			int r = (y - Y0) / SIZE;
			int c = (x - X0) / SIZE;
			int xp = X0 + c * SIZE;
			int yp = Y0 + r * SIZE;
			//将这个点添加进提示框点的队列中
			showBoradList.add(new Point(xp, yp));
			
			//在点击的点所在的图片位置绘制矩形
			g.drawRect(xp, yp, SIZE, SIZE);
		if(count == 0) {
			this.icon1 = getImgLocation(x, y); 
			if(icon1==null) {
				showBoradList.clear();
				panel.repaint();
			}else {
				r1 = (y-Y0)/SIZE;
				c1 = (x-X0)/SIZE;
				System.out.println("r1:" + r1 + "c1" + c1);
				count++;
			}
		}else {
			this.icon2 = getImgLocation(x, y);
			if(icon2 == null) {
				showBoradList.clear();
				panel.repaint();
			}
			r2 = (y-Y0)/SIZE;
			c2 = (x-X0)/SIZE;
			if(this.icon1!=null && this.icon2!=null && (r1!=r2 || c1!=c2 )) {
				if( isRemovable(this.icon1, this.icon2)) {
					// 先绘制提示线
					for (int i = 0; i < wireList.size(); i += 2) {
						Point p1 = wireList.get(i);
						Point p2 = wireList.get(i + 1);
	
						// 将下标转成坐标
						int x1 = X0 + p1.y * SIZE + SIZE / 2;
						int y1 = Y0 + p1.x * SIZE + SIZE / 2;
	
						int x2 = X0 + p2.y * SIZE + SIZE / 2;
						int y2 = Y0 + p2.x * SIZE + SIZE / 2;
	
						g.drawLine(x1, y1, x2, y2);
					}
						wireList.clear();
						this.updateMarks(1);
						MainJFrame.visableLabel.setText(marks + "");
						try {
							Thread.sleep(500);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					ICONS[r1][c1] = null;
					ICONS[r2][c2] = null;
					//相同，可消除
					System.out.println("相同，可消除");
				}
				showBoradList.clear();
				panel.repaint();
				count = 0;
			}
			
		}
}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

public int updateMarks(int addmarks){	
	marks += addmarks;
	return marks;
}

public boolean isGameEnd(ImageIcon[][] icons) {
    for (int i = 0; i < ROWS; i++) {
        for (int j = 0; j < COLS; j++) {
            if (icons[i][j] != null) {
                // Check if there are still icons available for matching
                return false;
            }
        }
    }
    return true; // No icons left, game is over
}

public boolean isRemovable(ImageIcon icon1,ImageIcon icon2){
	if(icon1!=null && icon2!=null && (r1!=r2 || c1!=c2 )) {
		if(icon1.toString().equals(icon2.toString())) {
			
			if( Algr.checkCol(r1, c1, r2, c2) ||
				Algr.checkRow(r1, c1, r2, c2) ||
				Algr.onePoint(r1, c1, r2, c2) ||
				Algr.twoPoint(r1, c1, r2, c2)
				){
					return true;
				}
			}
		}
	wireList.clear();
	return false;
}

public void checkGameOver(){

	if(mainJFrame.initMode == 0 && isGameEnd(ICONS)){
		mainJFrame.handlerGameOver();
		this.gameTimer.stop();
	}

	if(mainJFrame.initMode == 1 && isGameEnd(ICONS,mainJFrame.timePanel.getRemainingTime())){
		mainJFrame.handlerGameOver();
		this.gameTimer.stop();
	}
	
}

public boolean isGameEnd(ImageIcon [][] icons,int restTime){
	if(restTime < 1e-5 || isGameEnd(icons)){
		return true;
	}
	return false;
}

private ImageIcon getImgLocation(int x,int y) {
	int x1 = SIZE, y1=SIZE;
	for(int i = 0;i<ROWS;i++) {
		for(int j = 0;j<COLS;j++) {
			x1 = X0 + SIZE*j + SIZE;
			y1 = Y0 + SIZE*i + SIZE;
			if(x<x1 && y<y1) {
				return  ICONS[i][j];
			}
		}
	}
	return null;
}

public void clearResourse(){
	this.gameTimer.stop();
	this.mainJFrame = null;
}

}
