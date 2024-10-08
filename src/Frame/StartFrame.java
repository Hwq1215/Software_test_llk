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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton XiuXianButton = new JButton("\u4F11\u95F2\u6A21\u5F0F");
		XiuXianButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainJFrame frame = new MainJFrame();
				frame.initUI(2);
				
			}
		});
		XiuXianButton.setBounds(203, 93, 126, 43);
		contentPane.add(XiuXianButton);
		
		JButton DaoJiShiButton = new JButton("\u5012\u8BA1\u65F6\u6A21\u5F0F");
		DaoJiShiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainJFrame frame = new MainJFrame();
				frame.initUI(1);
			}
		});
		DaoJiShiButton.setBounds(203, 187, 126, 43);
		contentPane.add(DaoJiShiButton);
	}
}
