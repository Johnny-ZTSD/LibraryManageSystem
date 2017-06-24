package com.Action;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JButton;

public class AddBookPanel extends JFrame {

	private ElmentOfSystem eSys= new ElmentOfSystem();     //系统小组件 
	private JTextField text_isbn;
	private JTextField text_bookName;
	private JTextField text_press;
	private JTextField text_bookType;
	private JTextField text_author;
	private JTextField text_prive;
	private JTextField text_publishTime;
	private JTextField text_borrowGrade;
	private JTextField text_averageDergee;
	private JTextField text_borrower;
	private JTextField text_enLibTime;
	private JTextField text_location;
	private JTextField text_summary;
	
	/**
	 * Create the frame.
	 * @author Zen Johnny
	 * @since 2017-01-04
	 */
	public AddBookPanel() {
		initPanel();
	}
	
    public void initPanel(){
    	this.setTitle("图书管理信息系统");
    	this.setIconImage(eSys.getIconOfSystem().getImage());  //设置登陆logo
    	this.setResizable(false);    //窗口大小不可编辑
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //默认X为退出关闭
    	getContentPane().setLayout(null);
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸 
    	setBounds(screenSize.width/3,screenSize.height/4, 450, 402);  
//    	setContentPane(contentPane);
    	
    	JLabel label_main = new JLabel("\u65B0\u589E\u56FE\u4E66");
    	label_main.setFont(new Font("黑体", Font.BOLD, 17));
    	label_main.setBounds(187, 10, 74, 21);
    	getContentPane().add(label_main);
    	
    	JLabel label_isbn = new JLabel("ISBN\uFF1A");
    	label_isbn.setFont(new Font("黑体", Font.PLAIN, 12));
    	label_isbn.setBounds(37, 44, 54, 15);
    	getContentPane().add(label_isbn);
    	
    	text_isbn = new JTextField();
    	text_isbn.setBounds(76, 41, 298, 21);
    	getContentPane().add(text_isbn);
    	text_isbn.setColumns(10);
    	
    	JLabel label_bookName = new JLabel("\u4E66\u540D\uFF1A");
    	label_bookName.setFont(new Font("黑体", Font.PLAIN, 12));
    	label_bookName.setBounds(37, 85, 54, 15);
    	getContentPane().add(label_bookName);
    	
    	text_bookName = new JTextField();
    	text_bookName.setBounds(76, 82, 298, 21);
    	getContentPane().add(text_bookName);
    	text_bookName.setColumns(10);
    	
    	JLabel label_press = new JLabel("\u51FA\u7248\u793E\uFF1A");
    	label_press.setFont(new Font("黑体", Font.PLAIN, 12));
    	label_press.setBounds(25, 123, 54, 15);
    	getContentPane().add(label_press);
    	
    	text_press = new JTextField();
    	text_press.setBounds(76, 120, 88, 21);
    	getContentPane().add(text_press);
    	text_press.setColumns(10);
    	
    	JLabel label_bookType = new JLabel("\u4E66\u672C\u7C7B\u578B\uFF1A");
    	label_bookType.setFont(new Font("黑体", Font.PLAIN, 12));
    	label_bookType.setBounds(10, 163, 66, 15);
    	getContentPane().add(label_bookType);
    	
    	text_bookType = new JTextField();
    	text_bookType.setBounds(76, 160, 88, 21);
    	getContentPane().add(text_bookType);
    	text_bookType.setColumns(10);
    	
    	JLabel label_author = new JLabel("\u7B2C\u4E00\u4F5C\u8005\uFF1A");
    	label_author.setFont(new Font("黑体", Font.PLAIN, 12));
    	label_author.setBounds(10, 206, 66, 15);
    	getContentPane().add(label_author);
    	
    	text_author = new JTextField();
    	text_author.setBounds(78, 203, 88, 21);
    	getContentPane().add(text_author);
    	text_author.setColumns(10);
    	
    	JLabel label_price = new JLabel("\u4EF7\u683C\uFF1A");
    	label_price.setFont(new Font("黑体", Font.PLAIN, 12));
    	label_price.setBounds(37, 242, 54, 15);
    	getContentPane().add(label_price);
    	
    	text_prive = new JTextField();
    	text_prive.setBounds(76, 239, 88, 21);
    	getContentPane().add(text_prive);
    	text_prive.setColumns(10);
    	
    	text_publishTime = new JTextField();
    	text_publishTime.setBounds(285, 120, 89, 21);
    	getContentPane().add(text_publishTime);
    	text_publishTime.setColumns(10);
    	
    	JLabel label_publishTime = new JLabel("\u51FA\u7248\u65F6\u95F4\uFF1A");
    	label_publishTime.setFont(new Font("黑体", Font.PLAIN, 12));
    	label_publishTime.setBounds(220, 123, 66, 15);
    	getContentPane().add(label_publishTime);
    	
    	JLabel label_borrowGrade = new JLabel("\u501F\u9605\u7B49\u7EA7\uFF1A");
    	label_borrowGrade.setFont(new Font("黑体", Font.PLAIN, 12));
    	label_borrowGrade.setBounds(218, 163, 68, 15);
    	getContentPane().add(label_borrowGrade);
    	
    	text_borrowGrade = new JTextField();
    	text_borrowGrade.setBounds(285, 160, 89, 21);
    	getContentPane().add(text_borrowGrade);
    	text_borrowGrade.setColumns(10);
    	
    	JLabel label_averageDergee = new JLabel("\u5E73\u5747\u8BC4\u7EA7\uFF1A");
    	label_averageDergee.setFont(new Font("黑体", Font.PLAIN, 12));
    	label_averageDergee.setBounds(219, 206, 67, 15);
    	getContentPane().add(label_averageDergee);
    	
    	text_averageDergee = new JTextField();
    	text_averageDergee.setBounds(285, 200, 89, 21);
    	getContentPane().add(text_averageDergee);
    	text_averageDergee.setColumns(10);
    	
    	JLabel label_borrower = new JLabel("\u501F\u9605\u8005\uFF1A");
    	label_borrower.setFont(new Font("黑体", Font.PLAIN, 12));
    	label_borrower.setBounds(232, 242, 54, 15);
    	getContentPane().add(label_borrower);
    	
    	text_borrower = new JTextField();
    	text_borrower.setBounds(285, 239, 89, 21);
    	getContentPane().add(text_borrower);
    	text_borrower.setColumns(10);
    	
    	JLabel label_enLibTime = new JLabel("\u5165\u9986\u65F6\u95F4\uFF1A");
    	label_enLibTime.setBounds(10, 275, 60, 15);
    	getContentPane().add(label_enLibTime);
    	
    	text_enLibTime = new JTextField();
    	text_enLibTime.setBounds(76, 272, 88, 21);
    	getContentPane().add(text_enLibTime);
    	text_enLibTime.setColumns(10);
    	
    	JLabel label_location = new JLabel("\u4F4D\u7F6E\uFF1A");
    	label_location.setBounds(242, 275, 54, 15);
    	getContentPane().add(label_location);
    	
    	text_location = new JTextField();
    	text_location.setBounds(285, 272, 89, 21);
    	getContentPane().add(text_location);
    	text_location.setColumns(10);
    	
    	JLabel label_sumary = new JLabel("\u5185\u5BB9\u6458\u8981\uFF1A");
    	label_sumary.setBounds(10, 311, 69, 15);
    	getContentPane().add(label_sumary);
    	
    	text_summary = new JTextField();
    	text_summary.setBounds(75, 308, 299, 21);
    	getContentPane().add(text_summary);
    	text_summary.setColumns(10);
    	
    	JButton btn_sure = new JButton("\u786E\u5B9A");
    	btn_sure.setBounds(85, 339, 93, 23);
    	getContentPane().add(btn_sure);
    	
    	JButton btn_cancel = new JButton("\u53D6\u6D88");
    	btn_cancel.setBounds(268, 339, 93, 23);
    	getContentPane().add(btn_cancel);
    }
    
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AddBookPanel frame = new AddBookPanel();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
}
