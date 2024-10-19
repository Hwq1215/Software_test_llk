package Frame;

import javax.swing.JPanel;

import Base.Config;
import Base.Point;

import javax.swing.ImageIcon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.GridLayout;
import java.lang.reflect.Array;
import java.util.ArrayList;

import Base.Algr;
public class PlayJPanel extends JPanel implements Config{
	/**
	 * 
	 */

	/**
	 * Create the panel.
	 */
	public PlayJPanel() {
		this.setBounds(10, 10, 760, 460);
		setLayout(new GridLayout(1, 0, 0, 0)); 
		System.out.println(this.getGraphics());
	}
	
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		for(int i=0;i<ROWS;i++) {
			for(int j=0;j<COLS;j++) {
				ImageIcon imageIcon = ICONS[i][j];
				if(imageIcon != null) {
					g.drawImage(imageIcon.getImage(), X0 + SIZE * j, Y0 + SIZE * i, SIZE, SIZE, null);
				}
			}
		}
	}

	//提示
	public static void drawAction(Graphics2D g) {
		g.setStroke(new BasicStroke(5));
		g.setColor(Color.GREEN);
		MatchResult match = giveAct();
		int i = match.firstRow;
		int j = match.firstCol;
		int m = match.secondRow;
		int n = match.secondCol;

		// 绘制矩形
		g.drawRect(X0 + j * SIZE, Y0 + i * SIZE, SIZE, SIZE);
		g.drawRect(X0 + n * SIZE, Y0 + m * SIZE, SIZE, SIZE);

		// 绘制线条
		for (int k = 0; k < wireList.size(); k += 2) {
			Point p1 = wireList.get(k);
			Point p2 = wireList.get(k + 1);

			int x1 = X0 + p1.y * SIZE + SIZE / 2;
			int y1 = Y0 + p1.x * SIZE + SIZE / 2;
			int x2 = X0 + p2.y * SIZE + SIZE / 2;
			int y2 = Y0 + p2.x * SIZE + SIZE / 2;

			g.drawLine(x1, y1, x2, y2);
		}
		wireList.clear(); // 清理线条
		
	}


	public static MatchResult giveAct(){
		ImageIcon firstImg, secondImg;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                firstImg = ICONS[i][j];
                for (int m = 0; m < ROWS; m++) {
                    for (int n = 0; n < COLS; n++) {
                        secondImg = ICONS[m][n];
                        if (firstImg != null && secondImg != null && (i != m || j != n)) {
                            if (firstImg.toString().equals(secondImg.toString())) {
                                if (Algr.checkCol(i, j, m, n) ||
                                    Algr.checkRow(i, j, m, n) ||
                                    Algr.onePoint(i, j, m, n) ||
                                    Algr.twoPoint(i, j, m, n)) {
                                   return new MatchResult(i,j,m,n);
									}
                                }
                            }
                        }
                    }
                }
            }
			wireList.clear();
			return null;
        }

	

	public void clearGraphics() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				ICONS[i][j] = null; // 清除图像
			}
		}
		wireList.clear(); // 清除绘制线列表
		repaint(); // 重新绘制面板
	}

	public static class MatchResult {
		int firstRow, firstCol;
		int secondRow, secondCol;
	
		MatchResult(int firstRow, int firstCol, int secondRow, int secondCol) {
			this.firstRow = firstRow;
			this.firstCol = firstCol;
			this.secondRow = secondRow;
			this.secondCol = secondCol;
		}
	}
}
