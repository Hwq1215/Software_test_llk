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


import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import java.awt.Graphics2D;

public class MainJFrame extends JFrame implements Config{
	private JPanel contentPane;
	private JButton startButton;
	private JButton endButton;
	private PlayJPanel playJPanel;
	public static JLabel visableLabel;
	private LLKListener lis;
	private int initMode = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainJFrame frame = new MainJFrame(1);
					frame.initUI(frame.initMode);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainJFrame(int initMode) {
		this.initMode = initMode;
		this.setTitle("连连看");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(300, 300, 800, 700);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		startButton = new JButton();
		startButton.setFont(new Font("开始", Font.PLAIN, 16));
		startButton.setBounds(368, 582, 110, 50);
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initUI(initMode);
			}
		});
		
		startButton.setText("\u5F00\u59CB");
		endButton = new JButton();
		endButton.setFont(new Font("结束", Font.PLAIN, 16));
		endButton.setBounds(488, 582, 110, 50);
		
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		endButton.setText("\u7ED3\u675F");
		this.contentPane.setLayout(null);
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
		
		JButton btnNewButton = new JButton("提示");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				giveAction();
			}
		});
		btnNewButton.setFont(new Font("����", Font.PLAIN, 16));
		btnNewButton.setBounds(608, 582, 104, 50);
		contentPane.add(btnNewButton);
	}

	//初始化UI
	public void initUI(Integer enable) {
		Utils.initPicData();
		playJPanel = new PlayJPanel();
		playJPanel.setBounds(X0, Y0, 760,460 );
		contentPane.add(playJPanel);
		this.setResizable(false);
		this.setVisible(true);
		Graphics2D g = (Graphics2D) playJPanel.getGraphics();
		// 新建监听对象
		lis = new LLKListener(g);
		playJPanel.update(g);

		//增加鼠标监听
		playJPanel.addMouseListener(lis);
		LLKListener.marks = 0;
		visableLabel.setText(LLKListener.marks+"");
		if(enable==1) {
			TimePanel timePanel = new TimePanel(500000);
			this.add(timePanel);
		}
	} 
	//提示
	public void giveAction() {
		Graphics2D g = (Graphics2D) playJPanel.getGraphics();
		PlayJPanel.giveAct(g);
		//LLKListener.marks -= 10;
		visableLabel.setText(""+ LLKListener.marks);
	}
}	
