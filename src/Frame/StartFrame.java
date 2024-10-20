package Frame;


import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.undo.StateEdit;
import Adamin.User;
import java.awt.*;
import Adamin.UserAdministrator;
import Adamin.UserAdministrator.ResigterError;
public class StartFrame extends JFrame {

	private JPanel contentPane;
	private JPanel startPanel;
	private MainJFrame mainJFrame;
	public static User user;
	private JButton toggleButton = new JButton("用户登录");
	public UserAdministrator userAdministrator;
	private JButton checkupButton;
	private HistoryFrame historyFrame;
	private JButton DaoJiShiButton;
	private JButton XiuXianButton;
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
	public class StartPanel extends JPanel{
		
		StartPanel(){
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			setSize(500, 300);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new java.awt.Insets(10, 10, 10, 10);
			gbc.anchor = GridBagConstraints.CENTER;
			setLayout(new GridBagLayout());
			JLabel accountLabel = new JLabel("");
			checkupButton = new JButton("查看历史分数");
			if(user != null){
				accountLabel.setText("用户：" + user.account);
				checkupButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						historyFrame = new HistoryFrame(user.account);
						historyFrame.setVisible(true);
						historyFrame.updateMarks();
					}
				});
				this.add(accountLabel,gbc);
				gbc.gridy++;
				this.add(checkupButton,gbc);
			}  

			gbc.gridy++;
			XiuXianButton = new JButton("休闲模式");
			XiuXianButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						mainJFrame= chooseMode(0);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					mainJFrame.initUI(0);
				}
			});

			XiuXianButton.setSize(130, 45);
			this.add(XiuXianButton,gbc);

			gbc.gridy++;
			DaoJiShiButton = new JButton("倒计时模式");
			DaoJiShiButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						mainJFrame= chooseMode(1);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					mainJFrame.initUI(1);
				}
			});


			DaoJiShiButton.setSize(130, 45);
			this.add(DaoJiShiButton,gbc);


		}
	} 
	
	private void togglePanel(){
		remove(contentPane);
		if(contentPane instanceof StartPanel){
			contentPane = new UserPanel();
			toggleButton.setText("不登录，直接玩");
		}else{
			contentPane = new StartPanel();	
			toggleButton.setText("用户登录");
		}
		add(contentPane, BorderLayout.CENTER);
		revalidate();  // 通知布局管理器重新计算布局
		repaint();     // 重新绘制整个组件树
	}

	public StartFrame() {	
		this.userAdministrator = new UserAdministrator();
        // 设置关闭操作和窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);

		// 设置布局管理器为BorderLayout
		setLayout(new BorderLayout());
		
		this.startPanel = new StartPanel();
		this.contentPane = startPanel;
		// 创建按钮面板
		JPanel toggleButtonPanel = new JPanel();
		toggleButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		// 设置按钮点击事件
		toggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 登录按钮的点击事件逻辑
				togglePanel();
			}
		});

		// 将按钮添加到面板中
		toggleButtonPanel.add(toggleButton);

		// 将按钮面板放在窗口的顶部（NORTH），可以选择EAST或其他位置
		add(toggleButtonPanel, BorderLayout.LINE_END);

		// 创建并添加StartPanel到窗口的中心区域
		add(this.contentPane, BorderLayout.CENTER);

		// 设置窗口居中显示
		setLocationRelativeTo(null);

		// 显示窗口
		setVisible(true);
			
	}

	public MainJFrame chooseMode(int initmode) throws Exception{
		MainJFrame mainJFrame = null;
		if(user == null){
			mainJFrame =  new MainJFrame(initmode,null);
		}else{
			mainJFrame = new MainJFrame(initmode,user);
		}
		mainJFrame.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
			}
			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentShown(ComponentEvent e) {
				XiuXianButton.setEnabled(false);
				DaoJiShiButton.setEnabled(false);
				toggleButton.setEnabled(false);
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				XiuXianButton.setEnabled(true);
				DaoJiShiButton.setEnabled(true);
				toggleButton.setEnabled(true);
				// 获取事件源并调用dispose()
				Component source = e.getComponent();
				if (source instanceof JFrame) {
					JFrame frame = (JFrame) source;
					frame.dispose(); // 释放资源
				}
			}
		});
		return mainJFrame;
	}

	public class UserPanel extends JPanel {
		private JPanel currentPanel;
		private JButton toggleButton;
		
		public UserPanel() {
			setLayout(new GridBagLayout());
			
			// 创建 GridBagConstraints
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			
			
			// 默认显示登录面板
			currentPanel = new LoginPanel();
			add(currentPanel, gbc);
	
			// 创建右上角的切换按钮
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			toggleButton = new JButton("注册");
	
			buttonPanel.add(toggleButton);
			
			// 放置按钮到右上角
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.NORTHEAST;
			gbc.weightx = 0;
			gbc.weighty = 0;
			add(buttonPanel, gbc);
			
			// 为切换按钮添加事件
			toggleButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					togglePanels();
				}
			});
			
			// 显示窗口
			setVisible(true);
		}
	
		// 切换面板的方法
		private void togglePanels() {
			// 移除当前面板
			remove(currentPanel);
			
			// 切换面板
			if (currentPanel instanceof LoginPanel) {
				currentPanel = new ResgisterPanel();
				toggleButton.setText("已注册？去登陆");
			} else {
				currentPanel = new LoginPanel();
				toggleButton.setText("注册");
			}
			
			// 重新添加新面板并刷新布局
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			add(currentPanel, gbc);
			
			// 刷新 UI
			revalidate();
			repaint();
		}
		

		// 登录面板类
		public class LoginPanel extends JPanel {
			JTextField accountField;
			JPasswordField passwordField;
			JLabel attentionJLabel;
			LoginPanel() {
				setLayout(new GridBagLayout());
				this.accountField = new JTextField(15);
				this.passwordField =  new JPasswordField(20);
				this.attentionJLabel = new JLabel("");
				// 添加一些登录界面的组件
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 1;
				gbc.gridy = 0;
				gbc.insets = new Insets(10, 10, 10, 10);
				gbc.anchor = GridBagConstraints.CENTER;
				
				add(new JLabel("登录界面"), gbc);
				gbc.gridy++;
				gbc.gridx=0;
				add(new JLabel("账号："), gbc);
				gbc.gridx=1;
				add(this.accountField, gbc);
				gbc.gridy++;
				gbc.gridx=0;
				add(new JLabel("密码："), gbc);
				gbc.gridx=1;
				add(this.passwordField, gbc);
				gbc.gridy++;
				gbc.gridx=1;
				JButton loginComfirmButton = new JButton("登录");
				loginComfirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String account = new String(accountField.getText());
						String password = new String(passwordField.getPassword());
						if(userAdministrator.login(account, password) == null){
							attentionJLabel.setText("账号或密码错误");
						}else{
							accountField.setText("");
							passwordField.setText("");
							user = new User(account,password);
							togglePanel();
						}
					}	
				});
				add(loginComfirmButton, gbc);
				gbc.gridy++;
				add(this.attentionJLabel,gbc);
			}
		}
	
		// 注册面板类
		public class ResgisterPanel extends JPanel {
			JTextField accountField;
			JPasswordField passwordField;
			JPasswordField confirmPasswordField;
			JLabel attentionLabel;
			ResgisterPanel() {
				setLayout(new GridBagLayout());
				this.accountField = new JTextField(15);
				this.passwordField =  new JPasswordField(20);
				this.confirmPasswordField = new JPasswordField(20);
				this.attentionLabel = new JLabel("");
				this.attentionLabel.setText("");
				// 添加一些注册界面的组件
				GridBagConstraints gbc = new GridBagConstraints();
				
				gbc.gridx = 1;
				gbc.gridy = 0;
				gbc.insets = new Insets(10, 10, 10, 10);
				gbc.anchor = GridBagConstraints.CENTER;
				
				
				add(new JLabel("注册界面"), gbc);
				gbc.gridy++;
				add(new JLabel("账号应为只包含字母、数字和下划线，长度在3-13个字符之间"), gbc);
				gbc.gridy++;
				gbc.gridx=1;
				add(new JLabel("密码应只包含可见字符（不含空格），长度在7-19个字符之间"), gbc);
				gbc.gridy++;
				gbc.gridx = 0;
				add(new JLabel("账号 "), gbc);
				gbc.gridx = 1;
				add(this.accountField, gbc);
				gbc.gridy++;
				gbc.gridx = 0;
				add(new JLabel("密码 "), gbc);
				gbc.gridx = 1;
				add(this.passwordField, gbc);
				gbc.gridy++;
				gbc.gridx = 0;
				add(new JLabel("再输入一遍密码 "), gbc);
				gbc.gridx = 1;
				add(this.confirmPasswordField, gbc);
				gbc.gridy++;
				JButton reButton = new JButton("注册");
				reButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String password = new String(passwordField.getPassword());
						String confirmPassword = new String(confirmPasswordField.getPassword());
						if(password.equals(confirmPassword)){
							ResigterError error = userAdministrator.registerCheck(accountField.getText(),password);
							if(error.equals(ResigterError.No_Error)){
								attentionLabel.setText("注册成功");
							}else{
								attentionLabel.setText(error.getErrorAttention());
							}
						}else{
							attentionLabel.setText("两次密码不一致");

						}
					}
				});
				add(reButton,gbc);
				gbc.gridy = 5;
				add(this.attentionLabel,gbc);
			}
		}
	}
}