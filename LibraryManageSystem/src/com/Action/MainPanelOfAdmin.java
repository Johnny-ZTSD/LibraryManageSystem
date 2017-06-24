package com.Action;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.Dao.SQL;
import com.Entity.Book;
import com.Entity.Borrower;
import com.Entity.User;
import com.Service.Format;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

/**
 * ����Ա������
 * @author Zen Johnny
 * @since 2016-12-26
 */

public class MainPanelOfAdmin extends JFrame {

	private ElmentOfSystem eSys = new ElmentOfSystem();    //ϵͳС���
    private String idNo;
    private User  Admin;
	private JLabel lblLabelpersonallogo;    //�û�ͷ��
	private JLabel label_username;
	private JLabel label_idno;
	private JLabel label_currentTime;    //��ǰʱ�� 
	private String path= "/com/Action/data/images/";           //����ͷ��logo��·��
	private SQL sql = new SQL();
	private JButton MainBtn_PersonalCenter;   //��ť---��������
	private Calendar date_Current = Calendar.getInstance();  //��ȡ��ǰʱ��       
	private JMenuItem menuItem_returnBook;      //����
	private JMenuItem menuItem_borrowBook;      //����ͼ��
	private JButton MainBtn_borrowManage;      //���Ĺ�������
	private JButton MainBtn_Exit;                 //�˳�ϵͳ
	
	/**
	 * ���췽��.
	 */
	public MainPanelOfAdmin() {
		getContentPane().setForeground(SystemColor.controlLtHighlight);
	   setFont(new Font("������", Font.PLAIN, 14));
       initPanel();
	}
	public MainPanelOfAdmin(String idNo){
		this();
		Admin = sql.getAdmin(idNo);
		this.idNo = idNo;
		initUserInfo();
		panel_ActionListener();
	}
	
	/**
	 * ��������
	 **/
	public void initUserInfo(){
//		JOptionPane.showMessageDialog(null, "����ͷ��");
		loadUserLogo();
		
//		setFont(new Font("������", Font.PLAIN, 14));
		
		label_idno.setText("�˺ţ� "+Admin.getIdNo());
		label_username.setText("������ "+Admin.getName());
		
		label_currentTime.setText("��ǰʱ�䣺   "+date_Current.get(Calendar.YEAR)+"-"+
				                  (date_Current.get(Calendar.MONTH)+1)+"-"+
				                  date_Current.get(Calendar.DAY_OF_MONTH));
		
	}
	/***
	 * ��Ϊ����
	 * @author Zen Johnny
	 * @since  2016-12-26
	 */
	public void panel_ActionListener(){
		MainBtn_PersonalCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				PersonCenterPanel newPanel = new PersonCenterPanel(Admin,2);
//				newPanel.setVisible(true);
			}
		});
		
		MainBtn_borrowManage.addActionListener(new ActionListener() {     //һ��ʽ���Ĺ���
			public void actionPerformed(ActionEvent e) {
				AdminBorrowPanel borrowPanel = new AdminBorrowPanel(Admin.getIdNo()); 
				borrowPanel.setVisible(true);
			}
		});
		
		MainBtn_Exit.addActionListener(new ActionListener() {             //�˳�ϵͳ
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		MainBtn_PersonalCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {            //�������İ�ť
				Borrower newAdmin = new Borrower(Admin);
				PersonCenterPanel panel = new PersonCenterPanel(newAdmin,2);     //��������
				panel.setVisible(true);
			}
		});
		
		menuItem_borrowBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		MainBtn_Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				JOptionPane.showMessageDialog(null,"�˳��ɹ�����ӭ�ٴ�ʹ��...");
			}
		});
		
		menuItem_returnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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

  	path_person_logo += this.Admin.getIdNo().substring(12) + "_admin.png";
   	
//  	lblLabelpersonallogo = new JLabel();
  	lblLabelpersonallogo.setToolTipText("\u7528\u6237\u5934\u50CF");
  	
//  	JOptionPane.showMessageDialog(null, path_person_logo);//---Test
  	try{
  	    
  		lblLabelpersonallogo.setIcon(new ImageIcon(MainPanelOfAdmin.class.getResource(path_person_logo)));
  	
  	}catch(Exception e){
  		JOptionPane.showMessageDialog(null, "�û�ͷ�����ʧ��.");
    	}
  }
	
  public void DeleteBook(String input_delete){
//	    String input_delete =  JOptionPane.showInputDialog(null, "��������Ҫɾ�����鱾ISBN���룺").toString().trim();
	    if(!Format.IsIsBn(input_delete)){
	    	JOptionPane.showMessageDialog(null, "��������Ϣ��ISBN���룬ɾ��ʧ�ܡ�");
	        return ;	
	    }
	    ArrayList<Book> booklist = new ArrayList<Book>();
	    booklist = SQL.searchBook(input_delete, "isbn");
	    if(!booklist.isEmpty()){
	           JOptionPane.showMessageDialog(null, "����ɹ���"); 	
	    }
	    else
	    	JOptionPane.showMessageDialog(null, "���޴��飡");
  }
  
	public void initPanel(){
		final User Admin = new User();
		
		setTitle("\u56FE\u4E66\u7BA1\u7406\u7CFB\u7EDF");
		this.setIconImage(eSys.getIconOfSystem().getImage());  //���õ�½logosetDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);                     //���ɸ��Ĵ�С
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�ĳߴ� 
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds(screenSize.width/3,screenSize.height/4, 428, 318);  
		getContentPane().setLayout(null);
		
		lblLabelpersonallogo = new JLabel();
	 	lblLabelpersonallogo.setBackground(UIManager.getColor("CheckBox.focus"));
	  	lblLabelpersonallogo.setBounds(10, 73, 131, 125);
	  	getContentPane().add(lblLabelpersonallogo);

		
		JLabel label_panelInfo = new JLabel("\u56FE\u4E66\u7BA1\u7406\u4E2D\u5FC3");
		label_panelInfo.setFont(new Font("����", Font.BOLD, 17));
		label_panelInfo.setBounds(160, 3, 119, 34);
		getContentPane().add(label_panelInfo);
		
		JLabel label = new JLabel("_______________________________________________________________________");
		label.setBounds(0, 21, 459, 15);
		getContentPane().add(label);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(SystemColor.controlLtHighlight);
		menuBar.setBackground(SystemColor.textHighlight);
		menuBar.setFont(new Font("����", Font.PLAIN, 14));
//		menuBar.setVisible(false);
		menuBar.setBounds(5, 40, 410, 20);
		getContentPane().add(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u56FE\u4E66\u7BA1\u7406");
		mnNewMenu.setBackground(SystemColor.textHighlight);
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("\u56FE\u4E66\u7BA1\u7406\u4E3B\u9875");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem menuItem = new JMenuItem("\u65B0\u589E\u56FE\u4E66");
		menuItem.addActionListener(new ActionListener() {           //����ͼ��-����¼�
			public void actionPerformed(ActionEvent e) {
				AddBookPanel addBookPanel = new AddBookPanel();
				  addBookPanel.setVisible(true);
			}
		});
		mnNewMenu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("\u5220\u9664\u56FE\u4E66");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input_delete =  JOptionPane.showInputDialog(null, "��������Ҫɾ�����鱾ISBN���룺").toString().trim();    
				DeleteBook(input_delete);
			}
		});
		mnNewMenu.add(menuItem_1);
		
		JMenuItem menuItem_5 = new JMenuItem("\u4FEE\u6539\u56FE\u4E66");
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateBookPanel updateBookPanel = new UpdateBookPanel();
				updateBookPanel.setVisible(true);
			}
		});
		mnNewMenu.add(menuItem_5);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u67E5\u8BE2\u56FE\u4E66");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		menuItem_borrowBook = new JMenuItem("\u501F\u9605\u56FE\u4E66");
		mnNewMenu.add(menuItem_borrowBook);
		
		menuItem_returnBook = new JMenuItem("\u5F52\u8FD8\u56FE\u4E66");
		mnNewMenu.add(menuItem_returnBook);
		
		JMenu menu = new JMenu("\u7528\u6237\u7BA1\u7406");
		menuBar.add(menu);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("\u65B0\u589E\u7528\u6237");
		menu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("\u67E5\u8BE2\u7528\u6237");
		menu.add(mntmNewMenuItem_3);
		
		JMenuItem menuItem_2 = new JMenuItem("\u4FEE\u6539\u7528\u6237");
		menu.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("\u6CE8\u9500\u7528\u6237");
		menu.add(menuItem_3);
		
		JMenu mnNewMenu_1 = new JMenu("\u6D88\u606F\u7BA1\u7406");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem menuItem_4 = new JMenuItem("\u53D1\u5E03\u516C\u544A");
		mnNewMenu_1.add(menuItem_4);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("\u67E5\u9605\u516C\u544A");
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenu menu_2 = new JMenu("\u8D22\u52A1\u7BA1\u7406");
		menuBar.add(menu_2);
		
		JMenuItem menuItem_6 = new JMenuItem("\u8D44\u4EA7\u6982\u51B5");
		menu_2.add(menuItem_6);
		
		JMenuItem menuItem_8 = new JMenuItem("\u96C7\u5DE5\u4FE1\u606F");
		menu_2.add(menuItem_8);
		
		JMenuItem menuItem_7 = new JMenuItem("\u5DE5\u8D44\u53D1\u653E");
		menu_2.add(menuItem_7);
		
		JMenu mnNewMenu_2 = new JMenu("\u5BF9\u5916\u4EA4\u6D41");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem menuItem_9 = new JMenuItem("\u5B66\u672F\u4EA4\u6D41");
		mnNewMenu_2.add(menuItem_9);
		
		JMenuItem menuItem_10 = new JMenuItem("\u5BF9\u5916\u4E3B\u9875");
		mnNewMenu_2.add(menuItem_10);
		
		JMenu menu_3 = new JMenu("\u4EBA\u4E8B\u7BA1\u7406");
		menuBar.add(menu_3);
		
		JMenuItem menuItem_11 = new JMenuItem("\u6743\u9650\u7BA1\u7406");
		menu_3.add(menuItem_11);
		
		JMenuItem menuItem_12 = new JMenuItem("\u4EBA\u4E8B\u8C03\u5EA6");
		menu_3.add(menuItem_12);
		
		JMenu mnNewMenu_3 = new JMenu("\u5173\u4E8E\u6211\u4EEC");
		menuBar.add(mnNewMenu_3);
		
		JMenuItem menuItem_13 = new JMenuItem("\u8054\u7CFB\u6211\u4EEC");
		mnNewMenu_3.add(menuItem_13);
		
		JMenu menu_1 = new JMenu("\u8F6F\u4EF6\u53CD\u9988");
		mnNewMenu_3.add(menu_1);
		
		label_username = new JLabel("\u59D3\u540D\uFF1A");
		label_username.setBounds(10, 240, 131, 15);
		getContentPane().add(label_username);
		
		label_idno = new JLabel("\u8D26\u53F7\uFF1A");
		label_idno.setBounds(10, 264, 213, 15);
		getContentPane().add(label_idno);
		
		MainBtn_PersonalCenter = new JButton("\u4E2A\u4EBA\u4E2D\u5FC3");
		MainBtn_PersonalCenter.setBounds(30, 208, 93, 23);
		getContentPane().add(MainBtn_PersonalCenter);
		
		label_currentTime = new JLabel("\u5F53\u524D\u65F6\u95F4\uFF1A");
		label_currentTime.setBounds(160, 63, 241, 15);
		getContentPane().add(label_currentTime);
		
		MainBtn_borrowManage = new JButton("\u4E00\u7AD9\u5F0F\u501F\u9605\u7BA1\u7406");
		MainBtn_borrowManage.setBounds(208, 117, 131, 42);
		getContentPane().add(MainBtn_borrowManage);
		
		MainBtn_Exit = new JButton("\u9000\u51FA\u7CFB\u7EDF");
		MainBtn_Exit.setBounds(208, 198, 131, 42);
		getContentPane().add(MainBtn_Exit);
	}
	
	/***
	 * getter��setter
	 * 
	 */
    public void setAdmin(User user){
    	Admin = user;
    }
	/**
	 * ������.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainPanelOfAdmin frame = new MainPanelOfAdmin("123451234512345123");
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
}
