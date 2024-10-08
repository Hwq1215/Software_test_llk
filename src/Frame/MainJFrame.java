package Frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import Base.Config;
import Base.Utils;

import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import Base.Utils;
import Frame.LLKListener;
public class MainJFrame extends JFrame implements Config{
	private JPanel contentPane;
	private JButton startButton;
	private JButton endButton;
	private PlayJPanel playJPanel;
	public static JLabel visableLabel;
	private LLKListener lis;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainJFrame frame = new MainJFrame();
					frame.initUI(1);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainJFrame() {
		this.setTitle("连连看");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 300, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		startButton = new JButton();
		startButton.setFont(new Font("����", Font.PLAIN, 16));
		startButton.setBounds(368, 582, 110, 50);
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				initUI(0);
			}
		});
		startButton.setText("\u5F00\u59CB");
		endButton = new JButton();
		endButton.setFont(new Font("����", Font.PLAIN, 16));
		endButton.setBounds(488, 582, 110, 50);
		
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		endButton.setText("\u7ED3\u675F");
		contentPane.setLayout(null);
		getContentPane().add(startButton);
		getContentPane().add(endButton);
		

		JLabel marksLabel = new JLabel("\u4F60\u7684\u5206\u6570:");
		marksLabel.setFont(new Font("����", Font.PLAIN, 16));
		marksLabel.setHorizontalAlignment(SwingConstants.CENTER);
		marksLabel.setBounds(10, 582, 96, 50);
		contentPane.add(marksLabel);
		
		visableLabel = new JLabel("0");
		visableLabel.setFont(new Font("����", Font.PLAIN, 16));
		visableLabel.setBounds(116, 582, 45, 50);
		contentPane.add(visableLabel);
		
		JButton btnNewButton = new JButton("\u63D0\u793A");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				giveAction();
			}
		});
		btnNewButton.setFont(new Font("����", Font.PLAIN, 16));
		btnNewButton.setBounds(608, 582, 104, 50);
		contentPane.add(btnNewButton);
	}
	//1�����ʱ��2�������ʱ
	public void initUI(Integer enable) {
		Utils.initPicData();
		playJPanel = new PlayJPanel();
		playJPanel.setBounds(X0, Y0, 760,460 );
		contentPane.add(playJPanel);
		this.setResizable(false);
		this.setVisible(true);
		Graphics2D g = (Graphics2D) playJPanel.getGraphics();
		// ������������
		lis = new LLKListener(g);
		playJPanel.update(g);
		// �����������������
		playJPanel.addMouseListener(lis);
		LLKListener.marks = 0;
		visableLabel.setText(LLKListener.marks+"");
		if(enable==1) {
			TimePanel timePanel = new TimePanel(500000);
			this.add(timePanel);
		}
	} 
	
	public void giveAction() {
		Graphics2D g = (Graphics2D) playJPanel.getGraphics();
		PlayJPanel.giveAct(g);
		LLKListener.marks -= 10;
		visableLabel.setText(""+ LLKListener.marks);
	}
}
