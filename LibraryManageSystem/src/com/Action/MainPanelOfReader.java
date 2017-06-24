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

/***********************����***************************/
    /**
	 * 
	 * �汾
	 * */
	private static final long serialVersionUID = 1L;
	
	private String path= "/com/Action/data/images/";           //����ͷ��logo��·��
	private JLabel lblLabelpersonallogo ;    //�û�ͷ��
	private ElmentOfSystem eSys = new ElmentOfSystem();    //ϵͳС���
    private Borrower borrower;          //������
    private String idNo;                //�û���             
//    private JLabel label_panelInfo;
    private JButton btn_borrow;         //���鰴ť
    private JButton btn_revert;         //���鰴ť
    private JButton btn_personalCenter; //��������       
    private JTextField text_name;
    private JTextField text_account;
    private JButton btn_withdraw;       //�˳���ť 
    private SQL sql = new SQL();
    private JButton btn_refresh;        //ˢ�°�ť
    
/****************************���췽��********************/	
	public MainPanelOfReader() {	
	   Init();                      //����ʼ��
//	   SetUI();                      //�������� 
	}
	
    public MainPanelOfReader(String idNo){
    	this();
    	this.borrower = new Borrower();
    	this.borrower = sql.getBorrower(idNo);
    	this.idNo = idNo;
    	initInfo();
    	loadUserLogo();  //�����û�ͷ�� 	
    	user_ActionLisener();        //�û�����
    }
/**************************��������**********************/	
     
    public void user_ActionLisener(){
    	btn_borrow.addActionListener(new ActionListener() { //���鰴ť
    		public void actionPerformed(ActionEvent e) {
    			BorrowPanel bPanel =  new BorrowPanel(idNo);
    		    bPanel.setVisible(true);			
    		}
    	});
    	
    	btn_revert.addActionListener(new ActionListener() {   //���鰴ť
    		public void actionPerformed(ActionEvent e) {
    			ReturnBookPanel returnBookPanel = new ReturnBookPanel(borrower);
    			returnBookPanel.setVisible(true);
    		}
    	});
    
    	btn_personalCenter.addActionListener(new ActionListener() { //��������
    		public void actionPerformed(ActionEvent e) {
    			   PersonCenterPanel personCenterPanel = new PersonCenterPanel(borrower,1);
                   personCenterPanel.setVisible(true); 
    		}
    	});
    	
    	btn_withdraw.addActionListener(new ActionListener() {      //�˳���ť
    		public void actionPerformed(ActionEvent e) {
    		    System.exit(0);
    		}
    	});
    	
    	btn_refresh.addActionListener(new ActionListener() {     //ˢ��
    		public void actionPerformed(ActionEvent e) {
    			borrower =sql.getBorrower(borrower.getIdNo());
    			initInfo();
    		}
    	});
    }
    
    /***
	   * �����û�����ͷ��   
	   * ·����ʽ��png
	   * ͷ��ȡ�� ���û��˺ź�6λ
	   * @author Zen Johnny
	   * @since 2016-12-26
	   *  ע�� ��ÿ����һ�Σ�ͼƬ����·���ͻ���������Ծ���·�����ܼ�����ȫ�ֱ���path�ϣ��ᱨ�� 
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
//  	    JOptionPane.showMessageDialog(null,"loadUserLogo():���Գɹ���");
  	}catch(Exception e){
  		JOptionPane.showMessageDialog(null, "�û�ͷ�����ʧ��.");
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
    	
    	this.setIconImage(eSys.getIconOfSystem().getImage());  //���õ�½logosetDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	getContentPane().setLayout(null);
    	setResizable(false);                     //���ɸ��Ĵ�С
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�ĳߴ� 
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds(screenSize.width/3,screenSize.height/4, 428, 309);  
//    	setTitle("");
//		setTitle("");
//		setTitle("\u56FE\u4E66\u7BA1\u7406\u4E2D\u5FC3 \t \u5F53\u524D\u7528\u6237\uFF1A" + this.borrower.getName());
        GregorianCalendar gCalendar = new GregorianCalendar();
		setTitle("\u56FE\u4E66\u7BA1\u7406\u4E2D\u5FC3"); 
//		gCalendar.get(Calendar.YEAR)+"��"+ 
//        gCalendar.get(Calendar.MONTH) +"��"+ 
//        gCalendar.get(Calendar.DAY_OF_MONTH) +"��    "+ 
//        gCalendar.get(Calendar.HOUR_OF_DAY)+"��"+
//        gCalendar.get(Calendar.SECOND);      
		
//    	label_panelInfo = new JLabel("\u56FE\u4E66\u501F\u9605\u7BA1\u7406\u4E2D\u5FC3");
//    	label_panelInfo.setFont(new Font("����", Font.BOLD, 17));
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
    	text_name.setText("��̫");
    	
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
    /***********************�������Եķ���***********************/	
	public Borrower getBorrower() {
		return borrower;
	}
	public void setBorrower(Borrower borrower) {    //��Ϊ�����������ʼ��������Ľӿ�
		this.borrower = borrower;
		this.idNo = borrower.getIdNo();
	}
    /**
     * ������	
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
