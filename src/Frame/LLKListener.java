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

public class LLKListener implements MouseListener,Config{
public static int marks = 0;
private Graphics2D g;
private int r1 = 0,r2 = 0,c1 = 0,c2 = 0;
private ImageIcon icon1 = null,icon2 = null;
private int count = 0;

public LLKListener(Graphics2D g) {
		this.g = g;
		// ���������Ĵ�ϸ
		this.g.setStroke(new BasicStroke(5));
		this.g.setColor(Color.GREEN);
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

	// ����¼�Դ���󣺲����¼��Ķ���,��Ϊ��Ϸ���
	JPanel panel = (JPanel) e.getSource();
	
	// ��ù�갴�µ�λ��
			int x = e.getX();
			int y = e.getY();
	 
			// �������꣬���ڻ�����ʾ��
			int r = (y - Y0) / SIZE;
			int c = (x - X0) / SIZE;
			int xp = X0 + c * SIZE;
			int yp = Y0 + r * SIZE;
			//���������ӽ���ʾ���Ķ�����
			showBoradList.add(new Point(xp, yp));
			
			//�ڵ���ĵ����ڵ�ͼƬλ�û��ƾ���
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
				if( this.icon1.toString().equals(this.icon2.toString())) {
					
					if( Algr.checkCol(r1, c1, r2, c2) ||
						Algr.checkRow(r1, c1, r2, c2) ||
						Algr.onePoint(r1, c1, r2, c2) ||
						Algr.twoPoint(r1, c1, r2, c2)
						){
						// �Ȼ�����ʾ��
						for (int i = 0; i < wireList.size(); i += 2) {
							Point p1 = wireList.get(i);
							Point p2 = wireList.get(i + 1);
	 
							// ���±�ת������
							int x1 = X0 + p1.y * SIZE + SIZE / 2;
							int y1 = Y0 + p1.x * SIZE + SIZE / 2;
	 
							int x2 = X0 + p2.y * SIZE + SIZE / 2;
							int y2 = Y0 + p2.x * SIZE + SIZE / 2;
	 
							g.drawLine(x1, y1, x2, y2);
						}
							wireList.clear();
							marks++;
							MainJFrame.visableLabel.setText(marks + "");
							if(marks == COLS * ROWS/2) {
								MainJFrame.visableLabel.setText("��Ϸ����������ʼ���¿�ʼ");
							}
							try {
								Thread.sleep(500);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						ICONS[r1][c1] = null;
						ICONS[r2][c2] = null;
						System.out.println("��ͬ��������");
					}
					
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
}
