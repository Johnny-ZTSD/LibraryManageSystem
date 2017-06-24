package com.Action;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.Dao.SQL;
import com.Entity.Borrower;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class MainPanelOfReader extends JFrame {

/***********************属性***************************/
    /**
	 * 
	 * 版本
	 * */
	private static final long serialVersionUID = 1L;
	
	private String path= "/com/Action/data/images/";           //个人头像logo的路径
	private JLabel lblLabelpersonallogo ;    //用户头像
	private ElmentOfSystem eSys = new ElmentOfSystem();    //系统小组件
    private Borrower borrower;          //借阅者
    private String idNo;                //用户号             
//    private JLabel label_panelInfo;
    private JButton btn_borrow;         //借书按钮
    private JButton btn_revert;         //还书按钮
    private JButton btn_personalCenter; //个人中心       
    private JTextField text_name;
    private JTextField text_account;
    private JButton btn_withdraw;       //退出按钮 
    private SQL sql = new SQL();
    private JButton btn_refresh;        //刷新按钮
    
/****************************构造方法********************/	
	public MainPanelOfReader() {	
	   Init();                      //面板初始化
//	   SetUI();                      //美化界面 
	}
	
    public MainPanelOfReader(String idNo){
    	this();
    	this.borrower = new Borrower();
    	this.borrower = sql.getBorrower(idNo);
    	this.idNo = idNo;
    	initInfo();
    	loadUserLogo();  //加载用户头像 	
    	user_ActionLisener();        //用户监听
    }
/**************************其他方法**********************/	
     
    public void user_ActionLisener(){
    	btn_borrow.addActionListener(new ActionListener() { //借书按钮
    		public void actionPerformed(ActionEvent e) {
    			BorrowPanel bPanel =  new BorrowPanel(idNo);
    		    bPanel.setVisible(true);			
    		}
    	});
    	
    	btn_revert.addActionListener(new ActionListener() {   //还书按钮
    		public void actionPerformed(ActionEvent e) {
    			ReturnBookPanel returnBookPanel = new ReturnBookPanel(borrower);
    			returnBookPanel.setVisible(true);
    		}
    	});
    
    	btn_personalCenter.addActionListener(new ActionListener() { //个人中心
    		public void actionPerformed(ActionEvent e) {
    			   PersonCenterPanel personCenterPanel = new PersonCenterPanel(borrower,1);
                   personCenterPanel.setVisible(true); 
    		}
    	});
    	
    	btn_withdraw.addActionListener(new ActionListener() {      //退出按钮
    		public void actionPerformed(ActionEvent e) {
    		    System.exit(0);
    		}
    	});
    	
    	btn_refresh.addActionListener(new ActionListener() {     //刷新
    		public void actionPerformed(ActionEvent e) {
    			borrower =sql.getBorrower(borrower.getIdNo());
    			initInfo();
    		}
    	});
    }
    
    /***
	   * 加载用户定制头像   
	   * 路径格式：png
	   * 头像取名 ：用户账号后6位
	   * @author Zen Johnny
	   * @since 2016-12-26
	   *  注意 ：每舒心一次，图片具体路径就会更换，所以具体路径不能加载在全局变量path上，会报错 
	   * */ 
  public void loadUserLogo(){
 	    
  	String path_person_logo = path;
  	path_person_logo += this.borrower.getIdNo().substring(12)+"_borrower.png";
  	
//  	JOptionPane.showMessageDialog(null,path_person_logo);//test
  	
//  	lblLabelpersonallogo = new JLabel("label_personalLogo");
  	lblLabelpersonallogo.setToolTipText("\u7528\u6237\u5934\u50CF");
  	
//  	JOptionPane.showMessageDialog(null, path_person_logo);//---Test
  	try{
  	    lblLabelpersonallogo.setIcon(new ImageIcon(MainPanelOfReader.class.getResource(path_person_logo)));
//  	    JOptionPane.showMessageDialog(null,"loadUserLogo():测试成功！");
  	}catch(Exception e){
  		JOptionPane.showMessageDialog(null, "用户头像加载失败.");
    	}
  	lblLabelpersonallogo.setBackground(UIManager.getColor("CheckBox.focus"));
  	lblLabelpersonallogo.setBounds(190, 39, 130, 130);
  	getContentPane().add(lblLabelpersonallogo);
  	 
  }
    
    public void initInfo(){
    	text_name.setText(this.borrower.getName());
    	text_name.setToolTipText(this.borrower.getName());
    	text_account.setText(this.borrower.getIdNo().substring(0,8)+"...");
    	text_account.setToolTipText(this.borrower.getIdNo());
    }
    
    public void Init(){
    	borrower = new Borrower();
    	idNo = null;
    	
    	this.setIconImage(eSys.getIconOfSystem().getImage());  //设置登陆logosetDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	getContentPane().setLayout(null);
    	setResizable(false);                     //不可更改大小
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸 
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds(screenSize.width/3,screenSize.height/4, 428, 309);  
//    	setTitle("");
//		setTitle("");
//		setTitle("\u56FE\u4E66\u7BA1\u7406\u4E2D\u5FC3 \t \u5F53\u524D\u7528\u6237\uFF1A" + this.borrower.getName());
        GregorianCalendar gCalendar = new GregorianCalendar();
		setTitle("\u56FE\u4E66\u7BA1\u7406\u4E2D\u5FC3"); 
//		gCalendar.get(Calendar.YEAR)+"年"+ 
//        gCalendar.get(Calendar.MONTH) +"月"+ 
//        gCalendar.get(Calendar.DAY_OF_MONTH) +"日    "+ 
//        gCalendar.get(Calendar.HOUR_OF_DAY)+"："+
//        gCalendar.get(Calendar.SECOND);      
		
//    	label_panelInfo = new JLabel("\u56FE\u4E66\u501F\u9605\u7BA1\u7406\u4E2D\u5FC3");
//    	label_panelInfo.setFont(new Font("黑体", Font.BOLD, 17));
//    	label_panelInfo.setBounds(140, 10, 157, 27);
//    	getContentPane().add(label_panelInfo);
    	
    	btn_borrow = new JButton("\u501F\u4E66");
    	btn_borrow.setBounds(31, 39, 93, 36);
    	getContentPane().add(btn_borrow);
    	
    	btn_revert = new JButton("\u8FD8\u4E66");
    	btn_revert.setBounds(31, 86, 93, 36);
    	getContentPane().add(btn_revert);
    	
    	btn_personalCenter = new JButton("\u4E2A\u4EBA\u4E2D\u5FC3");
    	btn_personalCenter.setBounds(31, 132, 93, 36);
    	getContentPane().add(btn_personalCenter);
    	
    	lblLabelpersonallogo = new JLabel("label_personalLogo");
    	lblLabelpersonallogo.setToolTipText("\u7528\u6237\u5934\u50CF");
//    	lblLabelpersonallogo.setIcon(new ImageIcon(MainPanelOfReader.class.getResource("/com/Action/data/images/logo2.png")));
//    	lblLabelpersonallogo.setBackground(UIManager.getColor("CheckBox.focus"));
    	lblLabelpersonallogo.setBounds(190, 39, 130, 130);
    	getContentPane().add(lblLabelpersonallogo);
    	
    	JLabel label_name = new JLabel("\u59D3\u540D\uFF1A");
    	label_name.setBounds(190, 195, 54, 15);
    	getContentPane().add(label_name);
    	
    	text_name = new JTextField();
    	text_name.setEditable(false);
    	text_name.setBounds(237, 192, 84, 21);
    	getContentPane().add(text_name);
    	text_name.setColumns(10);
    	text_name.setText("曾太");
    	
    	JLabel label_account = new JLabel("\u8D26\u53F7\uFF1A");
    	label_account.setBounds(190, 230, 54, 15);
    	getContentPane().add(label_account);
    	
    	text_account = new JTextField();
    	text_account.setText("325435767");
    	text_account.setEditable(false);
    	text_account.setBounds(237, 227, 84, 21);
    	getContentPane().add(text_account);
    	text_account.setColumns(10);
    	
    	btn_withdraw = new JButton("\u9000\u51FA");
    	btn_withdraw.setBounds(31, 178, 93, 36);
    	getContentPane().add(btn_withdraw);
    	
    	btn_refresh = new JButton("\u5237\u65B0");
    	btn_refresh.setBounds(31, 230, 93, 36);
    	getContentPane().add(btn_refresh);
		
    } 
    /***********************部分属性的方法***********************/	
	public Borrower getBorrower() {
		return borrower;
	}
	public void setBorrower(Borrower borrower) {    //作为面向外界对象初始化本对象的接口
		this.borrower = borrower;
		this.idNo = borrower.getIdNo();
	}
    /**
     * 主函数	
     * @param args
     */
//    	public static void main(String[] args) {
//    		EventQueue.invokeLater(new Runnable() {
//    			public void run() {
//    				try {
//    					MainPanelOfReader frame = new MainPanelOfReader("513723199608111996");
//    					frame.setVisible(true);
//    				} catch (Exception e) {
//    					e.printStackTrace();
//    				}
//    			}
//    		});
//    	}  
}
