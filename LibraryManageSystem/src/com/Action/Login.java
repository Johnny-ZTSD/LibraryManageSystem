package com.Action;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.Dao.SQL;
import com.Entity.Borrower;
import com.Entity.User;
import com.Service.Format;
//import com.Service.MainPanel;
import com.Service.Logic;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.DataOutput;
import java.awt.event.MouseEvent;

public class Login extends JFrame {

	private JLayeredPane contentPane;    //内容面板
	private JTextField text_userName;    //用户名文本域
	private JPasswordField text_password;    //密码文本域
	private JTextField text_validCode;   //验证码文本域
	private JLabel label_UIname;         //界面名称：用户登录
	private JButton btn_Login;           //登录按钮
	private JButton btn_register;        //注册按钮
	private ImageIcon img_Background;    //背景图片
	private JLabel label_Background;     //背景
	private ValidCode vCode = new ValidCode();             //验证码模块
    private String [] inputInfo = new String[3]; //存放面板输入的信息
    private ElmentOfSystem eSys= new ElmentOfSystem();     //系统小组件 
    
	/**
	 * Create the frame.
	 */
public Login() {
		showPanel();
		login_ActionLisener();
//		setBackgroundImg();  //设置背景图片
 }

public void ClearPanel(){
	  this.text_userName.setText(null);
	  this.text_password.setText(null);
	  this.text_validCode.setText(null);
}

public String[] getTextFeild(){
    	String [] textsPanel = new String[3];
     	textsPanel[0] = text_userName.getText().trim(); 
     	textsPanel[1] = new String(text_password.getPassword());   //易错：char[]转型String
     	textsPanel[2] = text_validCode.getText().trim();
      	return textsPanel;
}
/**
 * 格式检验方法
 * @param texts [0]账号    [1]密码
 * @return 
 *   if userName error -> 1
 *   if password error -> 2
 *   if 都正确  -> 0  
 */
public int LoginFormat(String texts[]){
        
	    int flag = 0;
	    
	    Format format = new Format();               //用于登录格式检验
        if((!format.IsAllEnOrAllNumber(texts[0]))||texts[0].length()!=18)
           flag = 1;
        if((!format.IsAllNumber(texts[1]))||texts[1].length()>20)
           flag =  2;
        
        return flag;
}


public boolean IsVaildCode(String string){
	return (vCode.getCode().equalsIgnoreCase(string) )?true:false; 
}

public void login_ActionLisener(){
	 
	btn_register.addActionListener(new ActionListener() {   //注册按钮
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "抱歉，暂未开放此服务！");
		}
	});
	
	btn_Login.addActionListener(new ActionListener() {      //登录按钮
		public void actionPerformed(ActionEvent e) {
			inputInfo = getTextFeild();      //获取面板输入信息
		    int login_format = LoginFormat(inputInfo); 
		    Logic lg = new Logic();       //SQL的登录（逻辑）检验
			if(IsVaildCode(inputInfo[2])){	           
			if(login_format == 0){
				 int existAccount = lg.IsExistAccount(inputInfo[0],inputInfo[1]);
		         if(existAccount > 10){
		        	 
		        	 dispose();
		        	 JOptionPane.showMessageDialog(null,"登录中...请稍等...");
		        
		        	 if(existAccount == 11){
		            	 MainPanelOfReader  mp = new MainPanelOfReader(inputInfo[0]);       //切换到主界面----->借阅者
						 System.out.println(Borrower.getBorrower(inputInfo[0]).getE_mail());
		        	     mp.setVisible(true);
		        	 }
		        	 else if(existAccount == 12){
		        		 MainPanelOfAdmin  mp = new MainPanelOfAdmin(inputInfo[0]);         //切换到主界面------>管理员
		        	     mp.setVisible(true);
		        	 }
		         }
		         else if(existAccount > 0){
		        	     JOptionPane.showMessageDialog(null,"用户名或密码输入错误，请重新输入！"); 
//		        	     ClearPanel(); 
		         }
		         else if(existAccount==0){
		        	     JOptionPane.showMessageDialog(null,"用户名或密码输入错误！"); 
		        	     ClearPanel(); 
		         }
		    }
			else if(login_format == 1 )
				JOptionPane.showMessageDialog(null,"用户名格式错误！");
			else if(login_format ==2 )
				JOptionPane.showMessageDialog(null,"密码格式错误！");				
		  }
		  else
			  JOptionPane.showMessageDialog(null,"验证码错误！");
		}
	});
}

public void setBackgroundImg(){
	 ((JPanel) this.getContentPane()).setOpaque(false);  
	 img_Background = new ImageIcon(eSys.getPathofSystem()+"\\login.jpg"); // 添加图片  
	 label_Background = new JLabel( img_Background); 
	 this.getLayeredPane().add( label_Background, new Integer(Integer.MIN_VALUE));  
//     label_Background.setBounds(0, 0, img_Background.getIconWidth(), img_Background.getIconHeight()); 
	 label_Background.setBounds(0, 0,400, 200); 
}

public void showPanel(){
	this.setTitle("图书管理信息系统");
	this.setIconImage(eSys.getIconOfSystem().getImage());  //设置登陆logo
	this.setResizable(false);    //窗口大小不可编辑
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //默认X为退出关闭
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸 
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(screenSize.width/3,screenSize.height/4, 450, 300);  
	contentPane = new JLayeredPane();
	contentPane.setBackground(Color.WHITE);
	contentPane.setBorder(null);
	contentPane.setToolTipText("");
	contentPane.setLayout(null);
	setContentPane(contentPane);
	
	
	JLabel label_userName = new JLabel("\u8D26\u53F7\uFF1A");
	label_userName.setBounds(102, 72, 54, 15);
	contentPane.add(label_userName);
	
	JLabel label_password = new JLabel("\u5BC6\u7801\uFF1A ");
	label_password.setBounds(102, 113, 54, 15);
	contentPane.add(label_password);
	
	JLabel label_validCode = new JLabel("\u9A8C\u8BC1\u7801\uFF1A");
	label_validCode.setBounds(91, 151, 54, 15);
	contentPane.add(label_validCode);
	
	text_userName = new JTextField();
	text_userName.setBounds(166, 69, 117, 21);
	contentPane.add(text_userName);
	text_userName.setColumns(20);
	
	text_password = new JPasswordField();
	text_password.setBounds(165, 110, 118, 21);
	contentPane.add(text_password);
	text_password.setColumns(10);
	
	text_validCode = new JTextField();
	text_validCode.setBounds(166, 148, 117, 21);
	contentPane.add(text_validCode);
	text_validCode.setColumns(10);
	
	label_UIname = new JLabel("\u7528\u6237\u767B\u5F55");
	label_UIname.setFont(new java.awt.Font("Dialog",1,18));  //1 加粗；0正常  .18号字体
	label_UIname.setBounds(180, 10, 79, 39);
	contentPane.add(label_UIname);
	
	final JEditorPane forgetPane = new JEditorPane();
	forgetPane.addMouseMotionListener(new MouseMotionAdapter() {
		@Override
		public void mouseMoved(MouseEvent e) {
		forgetPane.setForeground(Color.RED);
		}
	});
	forgetPane.setForeground(Color.BLUE);
	forgetPane.setEditable(false);
	forgetPane.setText("\u5FD8\u8BB0\u5BC6\u7801\uFF1F");
	forgetPane.setBounds(230, 179, 106, 21);
	contentPane.add(forgetPane);
	
	btn_Login = new JButton("\u767B\u9646(Q)");
	
	btn_Login.setBounds(119, 219, 94, 25);
	btn_Login.setMnemonic('Q');
	contentPane.add(btn_Login);
	
	btn_register = new JButton("\u6CE8\u518C(W)");
	btn_register.setBounds(223, 219, 94, 25);
	btn_Login.setMnemonic('W');
	contentPane.add(btn_register);
	String forgetPwLink = "#";
	
	vCode.setLocation(298, 144);
    
	
//	vCode.setSize(94, 24);
//	vCode.setLocation(300, 146);
//	String x = null;
//	vCode.getX(x);
//	this.text_userName.setText(x);
//	vCode.setSize(94, 25);
//	vCode.setLocation(310, 151);
//	vCode.setBounds(240,190,80,40);
	contentPane.add(vCode);
	
	this.setVisible(true);
}


/**
 * Launch the application.
 */
public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				Login frame = new Login();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}


}
