package com.Action;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.Dao.SQL;
import com.Entity.Borrower;
import com.Entity.User;
import com.Service.Format;
import com.Service.Logic;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdatePasswordPanel extends JFrame {

    private ElmentOfSystem eSys= new ElmentOfSystem();     //系统小组件 
    private JPasswordField text_oldPassword;
    private JPasswordField text_confirmPassword;
    private JPasswordField text_newPassword;
    private JTextField  text_useraccount;
    private JButton btn_sure;
    private JButton btn_reset;
    
	public UpdatePasswordPanel() {
		init();
		ActionListenner();
	}
   
	public void ActionListenner(){
		btn_sure.addActionListener(new ActionListener() {   //确认密码_____按钮
			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showMessageDialog(null,"OKOKOK");  //test
				UpdatePassword();
				
			}
		});
		
		btn_reset.addActionListener(new ActionListener() {          //重置按钮
			public void actionPerformed(ActionEvent e) {
				clearText(-1);
			}
		});
	}
	
	public int IsAccount(String idNo,String password){
		if(Format.IsAccount(idNo) != 0)               //检验账号格式
			return -1;
		if(!Format.IsPasswordFormat(password))         //检验密码格式
			return -1;
		return Logic.IsExistAccount(idNo, password);  //连接数据库检验：11借阅者   ；12管理员
	}
	
	public void clearText(int i){
		text_confirmPassword.setText("");
		text_newPassword.setText("");
		text_oldPassword.setText("");
		if(i==-1)   //全部清空
			text_useraccount.setText("");
	}
	
	public void UpdatePassword(){
	   String [] texts = new String[4];
	   texts[0] = this.text_useraccount.getText().trim(); 
//	   JOptionPane.showMessageDialog(null,"账号："+texts[0]);
	   texts[1] = new String(this.text_oldPassword.getPassword());
//	   JOptionPane.showMessageDialog(null,"旧密码："+texts[1]);
	   int k = IsAccount(texts[0],texts[1]);
//	   JOptionPane.showMessageDialog(null, "ewetasfgs");
	   if(k<10){
		   JOptionPane.showMessageDialog(null, "账号或者密码错误！");
//	       clearText(-1);
	   }
	   else{
	      texts[2] = new String(this.text_newPassword.getPassword());
//	      JOptionPane.showMessageDialog(null,"新密码："+texts[2]);
	      texts[3] = new String(this.text_confirmPassword.getPassword());
//	      JOptionPane.showMessageDialog(null,"确认密码："+texts[3]);
	      if(!Format.IsPasswordFormat(texts[2])||!Format.IsPasswordFormat(texts[3])){
	         JOptionPane.showMessageDialog(null, "密码格式不符合规范！");
//	         clearText(1);
	      }
	      else {
	         if(!texts[2].equals(texts[3])){
		        JOptionPane.showMessageDialog(null,"密码不匹配，请重新设置密码.");
//		        clearText(1);
		     }
	         else
	        	try{
	                SQL.Update(texts[2], "password", "User",texts[0],"idNo");
	                JOptionPane.showMessageDialog(null, "密码更新成功！");
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
	      }
	      
	   }
//	   return texts;
	}
	
	public void init(){
		setTitle("\u56FE\u4E66\u7BA1\u7406\u7CFB\u7EDF");
		this.setIconImage(eSys.getIconOfSystem().getImage());  //设置登陆logosetDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);                     //不可更改大小
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸 
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	setBounds(screenSize.width/3,screenSize.height/4, 428, 318);  
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u4FEE\u6539\u5BC6\u7801");
		label.setFont(new Font("Dialog", Font.BOLD, 18));
		label.setBounds(159, 10, 93, 26);
		getContentPane().add(label);
		
		JLabel label_oldPassword = new JLabel("\u539F\u5BC6\u7801\uFF1A");
		label_oldPassword.setBounds(58, 90, 54, 15);
		getContentPane().add(label_oldPassword);
		
		text_oldPassword = new JPasswordField();
		text_oldPassword.setBounds(120, 85, 215, 20);
		getContentPane().add(text_oldPassword);
		text_oldPassword.setColumns(10);
		
		JLabel label_newPassword = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
		label_newPassword.setBounds(58, 129, 54, 15);
		getContentPane().add(label_newPassword);
		
		text_confirmPassword = new JPasswordField();
		text_confirmPassword.setBounds(120, 165, 215, 20);
		getContentPane().add(text_confirmPassword);
		text_confirmPassword.setColumns(10);
		
		JLabel label_confirmPassword = new JLabel("\u786E\u8BA4\u65B0\u5BC6\u7801\uFF1A");
		label_confirmPassword.setBounds(37, 168, 78, 15);
		getContentPane().add(label_confirmPassword);
		
		text_newPassword = new JPasswordField();
		text_newPassword.setColumns(10);
		text_newPassword.setBounds(120, 125, 215, 20);
		getContentPane().add(text_newPassword);
		
		btn_sure = new JButton("\u786E\u8BA4");
		btn_sure.setBounds(63, 223, 93, 23);
		getContentPane().add(btn_sure);
		
		btn_reset = new JButton("\u91CD\u7F6E");
		btn_reset.setBounds(244, 223, 93, 23);
		getContentPane().add(btn_reset);
		
		JLabel label_useraccount = new JLabel("\u8D26\u53F7\uFF1A");
		label_useraccount.setBounds(68, 45, 54, 15);
		getContentPane().add(label_useraccount);
		
		text_useraccount = new JTextField();
		text_useraccount.setBounds(120, 45, 215, 20);
		getContentPane().add(text_useraccount);
		text_useraccount.setColumns(10);
	}
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UpdatePasswordPanel frame = new UpdatePasswordPanel();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
}
