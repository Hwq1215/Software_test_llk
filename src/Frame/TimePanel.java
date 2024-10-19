package Frame;

import javax.swing.JPanel;
import Base.Config;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

public class TimePanel extends JPanel implements Config {
    public static int remainingTime;
	private Timer timer; 
    public TimePanel(int ms) {
        this.setBounds(X0, Y0 + ROWS * SIZE + 50, 300, 80);
        setLayout(null);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 30));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(0, 0, 300, 80);
        add(lblNewLabel);

        remainingTime = ms / 1000;
        lblNewLabel.setText( + remainingTime + "s");
        
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int i = remainingTime; i >= 0; i--) {
                    remainingTime = i; // Update remaining time
                    lblNewLabel.setText( + i + "s");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 10);
    }

    // Getter method to access remaining time
    public int getRemainingTime() {
        return remainingTime;
    }

	public void stopTimer() {
        if (timer != null) {
            timer.cancel(); // Cancel the timer
            timer.purge(); // Remove all canceled tasks from the timer's task queue
            timer = null; // Optional: clear the reference
        }
    }
}
