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

	private JLayeredPane contentPane;    //�������
	private JTextField text_userName;    //�û����ı���
	private JPasswordField text_password;    //�����ı���
	private JTextField text_validCode;   //��֤���ı���
	private JLabel label_UIname;         //�������ƣ��û���¼
	private JButton btn_Login;           //��¼��ť
	private JButton btn_register;        //ע�ᰴť
	private ImageIcon img_Background;    //����ͼƬ
	private JLabel label_Background;     //����
	private ValidCode vCode = new ValidCode();             //��֤��ģ��
    private String [] inputInfo = new String[3]; //�������������Ϣ
    private ElmentOfSystem eSys= new ElmentOfSystem();     //ϵͳС��� 
    
	/**
	 * Create the frame.
	 */
public Login() {
		showPanel();
		login_ActionLisener();
//		setBackgroundImg();  //���ñ���ͼƬ
 }

public void ClearPanel(){
	  this.text_userName.setText(null);
	  this.text_password.setText(null);
	  this.text_validCode.setText(null);
}

public String[] getTextFeild(){
    	String [] textsPanel = new String[3];
     	textsPanel[0] = text_userName.getText().trim(); 
     	textsPanel[1] = new String(text_password.getPassword());   //�״�char[]ת��String
     	textsPanel[2] = text_validCode.getText().trim();
      	return textsPanel;
}
/**
 * ��ʽ���鷽��
 * @param texts [0]�˺�    [1]����
 * @return 
 *   if userName error -> 1
 *   if password error -> 2
 *   if ����ȷ  -> 0  
 */
public int LoginFormat(String texts[]){
        
	    int flag = 0;
	    
	    Format format = new Format();               //���ڵ�¼��ʽ����
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
	 
	btn_register.addActionListener(new ActionListener() {   //ע�ᰴť
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "��Ǹ����δ���Ŵ˷���");
		}
	});
	
	btn_Login.addActionListener(new ActionListener() {      //��¼��ť
		public void actionPerformed(ActionEvent e) {
			inputInfo = getTextFeild();      //��ȡ���������Ϣ
		    int login_format = LoginFormat(inputInfo); 
		    Logic lg = new Logic();       //SQL�ĵ�¼���߼�������
			if(IsVaildCode(inputInfo[2])){	           
			if(login_format == 0){
				 int existAccount = lg.IsExistAccount(inputInfo[0],inputInfo[1]);
		         if(existAccount > 10){
		        	 
		        	 dispose();
		        	 JOptionPane.showMessageDialog(null,"��¼��...���Ե�...");
		        
		        	 if(existAccount == 11){
		            	 MainPanelOfReader  mp = new MainPanelOfReader(inputInfo[0]);       //�л���������----->������
						 System.out.println(Borrower.getBorrower(inputInfo[0]).getE_mail());
		        	     mp.setVisible(true);
		        	 }
		        	 else if(existAccount == 12){
		        		 MainPanelOfAdmin  mp = new MainPanelOfAdmin(inputInfo[0]);         //�л���������------>����Ա
		        	     mp.setVisible(true);
		        	 }
		         }
		         else if(existAccount > 0){
		        	     JOptionPane.showMessageDialog(null,"�û�������������������������룡"); 
//		        	     ClearPanel(); 
		         }
		         else if(existAccount==0){
		        	     JOptionPane.showMessageDialog(null,"�û����������������"); 
		        	     ClearPanel(); 
		         }
		    }
			else if(login_format == 1 )
				JOptionPane.showMessageDialog(null,"�û�����ʽ����");
			else if(login_format ==2 )
				JOptionPane.showMessageDialog(null,"�����ʽ����");				
		  }
		  else
			  JOptionPane.showMessageDialog(null,"��֤�����");
		}
	});
}

public void setBackgroundImg(){
	 ((JPanel) this.getContentPane()).setOpaque(false);  
	 img_Background = new ImageIcon(eSys.getPathofSystem()+"\\login.jpg"); // ���ͼƬ  
	 label_Background = new JLabel( img_Background); 
	 this.getLayeredPane().add( label_Background, new Integer(Integer.MIN_VALUE));  
//     label_Background.setBounds(0, 0, img_Background.getIconWidth(), img_Background.getIconHeight()); 
	 label_Background.setBounds(0, 0,400, 200); 
}

public void showPanel(){
	this.setTitle("ͼ�������Ϣϵͳ");
	this.setIconImage(eSys.getIconOfSystem().getImage());  //���õ�½logo
	this.setResizable(false);    //���ڴ�С���ɱ༭
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Ĭ��XΪ�˳��ر�
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�ĳߴ� 
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
	label_UIname.setFont(new java.awt.Font("Dialog",1,18));  //1 �Ӵ֣�0����  .18������
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
