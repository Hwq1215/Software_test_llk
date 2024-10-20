package Frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import Base.Config;
import Base.Utils;


import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import Adamin.MarksAdministrator;
import Adamin.User;
import java.awt.Graphics2D;

public class MainJFrame extends JFrame implements Config{
	private JPanel contentPane;
	private JButton endButton;
	private JButton btnNewButton;
	private PlayJPanel playJPanel;
	public JLabel visableLabel;
	private LLKListener lis;
	public int initMode = 0;
	private User user;
	public TimePanel timePanel;
	private MarksAdministrator marksAdministrator;
	/**
	 * Launch the application.
	 */
	public enum InitMode{
		Countdown,
		Casual
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainJFrame frame = new MainJFrame(1,null);
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
	public MainJFrame(int initMode,User user)  {
		this.initMode = initMode;
		if(!(initMode ==1 || initMode == 0)){
			throw new IllegalArgumentException("无效的模式参数");
		}
		this.user = user;
		this.marksAdministrator = new MarksAdministrator();
		this.setTitle("连连看");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(300, 300, 800, 700);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		endButton = new JButton();
		endButton.setFont(new Font("退出", Font.PLAIN, 16));
		endButton.setBounds(488, 582, 110, 50);
		
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handlerGameOver();
			}
		});
		
		endButton.setText("\u7ED3\u675F");
		this.contentPane.setLayout(null);
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
		
		this.btnNewButton = new JButton("提示");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				giveAction();
			}
		});
		btnNewButton.setFont(new Font("����", Font.PLAIN, 16));
		btnNewButton.setBounds(608, 582, 104, 50);
		contentPane.add(btnNewButton);

		        // 添加窗口监听器
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 这里执行清理操作
				setVisible(false);
				cleanupResources();
            }
        });
	}


	//初始化UI
	public void initUI(Integer enable) {
		Utils.initPicData();
		Utils.initWireLine();
		playJPanel = new PlayJPanel();
		playJPanel.setBounds(X0, Y0, 760,460 );
		contentPane.add(playJPanel);
		this.setResizable(false);
		this.setVisible(true);
		Graphics2D g = (Graphics2D) playJPanel.getGraphics();
		// 新建监听对象
		lis = new LLKListener(g,this);
		playJPanel.update(g);

		//增加鼠标监听
		playJPanel.addMouseListener(lis);
		lis.marks = 0;
		visableLabel.setText(lis.marks+"");
		this.timePanel = new TimePanel(CountDownTime);
		if(enable==1) {
			this.add(timePanel);
		}
	} 


	//提示
	public void giveAction() {
		Graphics2D g = (Graphics2D) playJPanel.getGraphics();
		PlayJPanel.drawAction(g);
	}

	public void handlerGameOver(){
		setVisible(false);
		// 显示游戏结束框
		JFrame gameOverFrame = new JFrame("游戏结束");
		gameOverFrame.setAlwaysOnTop(true); // 设置窗口总在最上层
		JLabel gameOverLabel = new JLabel("游戏结束！您的分数是:" + lis.marks, SwingConstants.CENTER);
		gameOverFrame.add(gameOverLabel);
		gameOverFrame.setSize(300, 200);
		gameOverFrame.setLocationRelativeTo(null);
		gameOverFrame.setVisible(true);
		if(user != null){
			marksAdministrator.setUsersHistoryMarks(user.account, lis.marks);
		}
		
		JButton okButton = new JButton("确定");
		okButton.addActionListener(e -> {
			if (gameOverFrame != null) {
				gameOverFrame.setVisible(false);
			}
			
		});
	
		gameOverFrame.add(okButton, BorderLayout.SOUTH); // 将按钮添加到框架
		// 禁用按钮
		endButton.setEnabled(false);
		// 禁用提示按钮
		btnNewButton.setEnabled(false);
		// 禁用鼠标监听
		if (playJPanel != null && lis != null) {
			playJPanel.removeMouseListener(lis); // 移除鼠标监听器
		}
		cleanupResources();
	}

	public void cleanupResources() {
		// 移除鼠标监听器
		if (playJPanel != null && lis != null) {
			lis.clearResourse();
			playJPanel.removeMouseListener(lis);
		}
		
		// 停止计时器
		if (timePanel != null) {
			timePanel.stopTimer(); // 假设有 stopTimer 方法来停止计时器
		}

		// 其他资源清理操作
		if(lis != null){
			lis = null; // 解除 LLKListener 绑定
		}
		// 释放图形资源（如有）
		if (playJPanel != null) {
			playJPanel.clearGraphics(); // 假设有一个方法来清理图形
			playJPanel = null; // 解除 PlayJPanel 绑定
		}
		// 可选：其他清理操作...
		System.out.println("清理所有资源完成");
	}

}	
