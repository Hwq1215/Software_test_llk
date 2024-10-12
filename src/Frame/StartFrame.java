package Frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFrame frame = new StartFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartFrame() {	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 577, 384);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.contentPane.setLayout(null);
		
		JButton XiuXianButton = new JButton("休闲模式");
		XiuXianButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainJFrame frame = chooseMode(2);
				frame.initUI(2);
			}
		});
		XiuXianButton.setBounds(203, 93, 126, 43);
		this.contentPane.add(XiuXianButton);
		
		JButton DaoJiShiButton = new JButton("倒计时模式");
		DaoJiShiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainJFrame frame = chooseMode(1);
				frame.initUI(1);
			}
		});
		DaoJiShiButton.setBounds(203, 187, 126, 43);
		this.contentPane.add(DaoJiShiButton);
	}

	public MainJFrame chooseMode(int initmode ){
		return new MainJFrame(initmode);
	}
}