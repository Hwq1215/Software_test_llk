package Frame;

import javax.swing.JPanel;
import Base.Config;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
public class TimePanel extends JPanel implements Config{

	/**
	 * Create the panel.
	 */
	public TimePanel(int ms) {
		this.setBounds(X0,Y0 +ROWS*SIZE +50,300,80);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("����", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 300, 80);
		add(lblNewLabel);
		
		final int count = ms / 1000;
		Timer timer = new Timer();
		lblNewLabel.setText("剩余" + count + "s");
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i = count;i>=0;i--) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lblNewLabel.setText("剩余" + i + "s");
				}
				JFrame act = new JFrame("ʱ�䵽��");
				JLabel jct = new JLabel("ʱ�䵽��");
				act.add(jct);
				act.setVisible(true);
			}
		}, 10);
		
	}
}
