package com.Action;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.Dao.SQL;
import com.Entity.Borrower;
import com.Entity.User;
import com.Service.Format;

import java.awt.Font;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.Color;
import javax.swing.JRadioButton;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Calendar;import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class PersonCenterPanel extends JFrame {
	
	private JTextField text_username;
	private JTextField text_idNo;
	private JTextField text_Telephone;
	private JTextField text_NoStaus;
	private JTextField texte_roleType;
	private JTextField text_idCard;
	private JTextField text_email;
	private JTextField text_department;;     //ϵͳС���
	private JRadioButton radioBtn_sex_Female;   //��ѡ��  --�Ա�Ů
	private JRadioButton radioBtn_sex_Male;
	private JMenuBar menuBar;              //�˵���
	private JButton button_modify_cancel;  //�޸�����---ȡ����ť
	private JButton button_modify_sure;  //�޸�����---ȷ����ť
	private JMenu menu_exit;              //�˵�--�˳�
	private JMenuItem menuItem_exitSystem; //�˵�ѡ��--�˳�ϵͳ
	private JMenuItem menuItem_exitPage;   //�˵�ѡ��---�˳���ǰҳ��
	private JMenu menu_modify;            //�˵�----�޸�
	private JMenuItem menuItem_modify_personData;  //�˵�ѡ��---�޸ĸ�������
	private JMenuItem menuItem_modifyPassword;     //�˵�ѡ��----�޸�����
	private JMenu menu_about;              //�˵�---����
	private JMenuItem menuItem_about_us;    //�˵�ѡ��----��������
	private JLabel lblLabelpersonallogo;    //�û�ͷ��
	private JLabel label_mainTitle;
	private JButton btn_cancelInfo;          //ȷ����Ϣ�õ�-----���Ǳ�����Ϣ
	private JButton btn_sureInfo;            //ȷ����Ϣ�õ�-----���Ǳ�����Ϣ
	
	private int Useflag;         //��ʹ�ñ�ҳ�湦����������־  1-������   2 --������
	private Borrower borrower;
	private ElmentOfSystem eSys = new ElmentOfSystem(); 
	private String path= "/com/Action/data/images/";           //����ͷ��logo��·��
	private Calendar date_Current = Calendar.getInstance();  //��ȡ��ǰʱ��     
	private SQL sql = new SQL();
	private Format format = new Format();    //��ʽ������ 
	private JLabel label;
	private JLabel label_maxCount;
	private JTextField textField_maxCount;
	private JTextField textField_nowCount;
	private JTextField textField_AllCount;
	private JLabel label_borrowGrade;
	private JTextField textField_borrowGrade;
	private JLabel label_mainTitle2;
//	private String password;           //borrowerû�����룻���޸����ϵ�ʱ�򣬻Ὣ����ɾ���ˣ���¼����
	/**
	 * ���췽��
	 */
	public PersonCenterPanel() {
		getContentPane().setEnabled(false);        //���ò��ɱ༭
		getContentPane().setFont(new Font("����", Font.BOLD, 17));	
		initPanel();
		Panel_ActionLisner();
	}
	
    public PersonCenterPanel(Borrower borrower,int useFlag){
    	this();
		setTitle("\u56FE\u4E66\u7BA1\u7406\u7CFB\u7EDF");
    	this.borrower = borrower;
    	this.Useflag = useFlag; 
    	initUserInfoOfPanel();
    } 
    
//    public PersonCenterPanel(User user,int useFlag){
//    	this();
//    	this.borrower.setBorrower(user);;    //ǿ�û�ת��Ϊ�����߽�ɫ
//    	this.Useflag = useFlag; 
//    	initUserInfoOfPanel();
//    }
	/**
	 * other methods
	 * @author Zen Johnny
	 * @since 2016-12-26
	 */
	
	public void setMenuBarIsShow(boolean is){  //����menuBar�Ƿ����ʾ
		 if(is == false)
			 menuBar.setVisible(false);
		 else
			 menuBar.setVisible(true); 
	}
   //���������� -----�ⲿӦ�õĽӿ�
	public void setMainTitle(String string){
		label_mainTitle2 = new JLabel("\u786E\u8BA4\u8EAB\u4EFD\u4FE1\u606F");   //��ȷ�������Ϣ
    	label_mainTitle2.setFont(new Font("����", Font.BOLD, 17));
    	label_mainTitle2.setBounds(247, 5, 217, 22);
    	getContentPane().add(label_mainTitle2);
    	
		label_mainTitle.setVisible(false);;    //���ڱ���Ĳ���ԭ�򣬵�һ��������رգ����ڶ���
		label_mainTitle2.setText(string);    //���ڱ���Ĳ���ԭ�򣬵�һ��������رգ����ڶ���
	}
    /**
     * ���ƿɱ༭״̬
     * if flag == 0 -->��ȫ���ɱ༭
     * if flag == 1 -->�����߱༭״̬
     * if flag == 2 -->����Ա�ɱ༭״̬
     * if flag ==-1 -->ȷ�������Ϣ-����Ϣ�ã������м�������ť����ʾ
     * @param flag
     */    
    public void ControlEditable(int flag){   
    	if(flag <=0){
    		text_username.setEditable(false);
            text_idNo.setEditable(false);
    		text_Telephone.setEditable(false);
            
    		radioBtn_sex_Female.setEnabled(false);
    		radioBtn_sex_Female.setEnabled(false);
    		text_department.setEditable(false);
    		
    		text_NoStaus.setEditable(false);
    		text_email.setEditable(false);
    		
    		texte_roleType.setEditable(false);
    		text_idCard.setEditable(false);
    		
    		textField_AllCount.setEditable(false);
    		textField_maxCount.setEditable(false);
    		textField_nowCount.setEditable(false);
    		textField_borrowGrade.setEditable(false);
    		
    		button_modify_cancel.setVisible(false);   //�����Ϣʱ����ť���ɼ�
    		button_modify_sure.setVisible(false);   //�����Ϣʱ����ť���ɼ�
    		
    		if(flag ==-1){
    			btn_cancelInfo.setVisible(true);
    			btn_sureInfo.setVisible(true);
    			
    			menuBar.setVisible(false);//�˵�����ʧ
    		}
		
    	}
    	else if(flag == 1){
    		text_username.setEditable(true);     //�û������Ա༭  
            text_idNo.setEditable(false);
    		text_Telephone.setEditable(true);   //�绰���Ա༭
            
    		radioBtn_sex_Female.setEnabled(false);   //������--�Ա𲻿��Ա༭
    		radioBtn_sex_Female.setEnabled(false);
    		text_department.setEditable(false);   //��ϵ���Ա༭
    		
    		text_NoStaus.setEditable(false);       //ע��״̬�����Ա༭����Ҫ�����ߴ���
    		text_email.setEditable(true);         //�����ʼ���ַ���Ա༭
    		
    		texte_roleType.setEditable(false);     //��ɫ���Ͳ����Ա༭
    		text_idCard.setEditable(false);        //idNo��ǰ�汾��idCard��ͬ���� �����ܱ༭
    		
    		button_modify_cancel.setVisible(true);   //�޸���Ϣʱ����ť�ɼ�
    		button_modify_sure.setVisible(true);   //�޸���Ϣʱ����ť�ɼ�
    		
    		textField_AllCount.setEditable(false);
    		textField_maxCount.setEditable(false);
    		textField_nowCount.setEditable(false);
    		textField_borrowGrade.setEditable(false);
    	}
    	else if(flag ==2 ){
    		text_username.setEditable(true);     //�û������Ա༭  
            text_idNo.setEditable(true);
    		text_Telephone.setEditable(true);   //�绰���Ա༭
            
    		radioBtn_sex_Female.setEnabled(true);   //������--���Ա༭
    		radioBtn_sex_Female.setEnabled(true);
    		text_department.setEditable(true);   //��ϵ���Ա༭
    		
    		text_NoStaus.setEditable(true);       //ע��״̬���Ա༭����Ҫ�����ߴ���
    		text_email.setEditable(true);         //�����ʼ���ַ���Ա༭
    		
    		texte_roleType.setEditable(true);     //��ɫ���Ϳ��Ա༭
    		text_idCard.setEditable(true);        //idNo��ǰ�汾��idCard��ͬ
    		
    		textField_AllCount.setEditable(true);
    		textField_maxCount.setEditable(true);
    		textField_nowCount.setEditable(true);
    		textField_borrowGrade.setEditable(true);
    		
    		button_modify_cancel.setVisible(true);   //�޸���Ϣʱ����ť�ɼ�
    		button_modify_sure.setVisible(true);   //�޸���Ϣʱ����ť�ɼ�
    		
    	}
    	else {
			JOptionPane.showConfirmDialog(null, "����Ȩ�޷�Χ֮�ڣ�");
		}
    		
    }
 /***
  * ������
  * @since 2016-12-26
  * */   
    public void Panel_ActionLisner(){
    	menuItem_exitSystem.addActionListener(new ActionListener() {   //�˳�ϵͳѡ��
    		public void actionPerformed(ActionEvent e) {
    			JOptionPane.showMessageDialog(null, "��ӭʹ�ã��˳��ɹ���");
    			System.exit(0);
    		}
    	});
    	menuItem_exitPage.addActionListener(new ActionListener() {   //�˳���ǰҳ��ѡ��
    		public void actionPerformed(ActionEvent e) {
    			dispose();
    		}
    	});
    	
    	menuItem_modify_personData.addActionListener(new ActionListener() {   //�޸�����
    		public void actionPerformed(ActionEvent e) {
    			ControlEditable(Useflag);
    		}
    	});
    	
    	button_modify_sure.addActionListener(new ActionListener() {       //���水ť
    		public void actionPerformed(ActionEvent e) {
    			
    			String [] strs = getInfoOfPanel();     //��ȡ����������Ϣ
    			
    			int is = format.PersonCenterFormat(strs);    //��ʽ����
    			if(is == 0){
    				
    				if(Useflag == 2)   //�����ǹ���Ա�ñ�ҳ���ʱ��
     			       sql.UpdateUserElementInfo(getUser(strs));    //��ʽ������󣬽���ȡ����Ϣ��װ���û����󣬲������û���Ϣ
    			    else   //����  �����ǽ����ߣ�Ҫͬ���������idNo������
    			    {
    			    	 sql.UpdateUserElementInfo(getUser(strs));	
    			         sql.UpdateBorrower(strs[0],"name",strs[1]);
    			         sql.UpdateBorrower(strs[1],"idNo",strs[1]);
    			         sql.UpdateBorrowRecord(strs[0], "name", borrower.getIdNo(),null);  //ֻҪ������˽��ĵ��鼮 ������������Ϣ
    			         
    			    } 
    			    JOptionPane.showMessageDialog(null, "���³ɹ���");
    			
    		     	initUserInfoOfPanel();   //ˢ�½�����Ϣ
    			}
    			else   	
    				JOptionPane.showMessageDialog(null,"���ݸ�ʽ��������ϸ���飡");
    		}
    	});
    	
    	button_modify_cancel.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			    ControlEditable(0); 
    		}
    	});
    	
    	btn_sureInfo.addActionListener(new ActionListener() {           //ȷ����Ϣ----���Ǳ���
    		public void actionPerformed(ActionEvent e) {
    		        dispose();
    		}
    	});
    	
    	btn_cancelInfo.addActionListener(new ActionListener() {           //ȷ����Ϣ----���Ǳ���
    		public void actionPerformed(ActionEvent e) {
    		       dispose();     
    		}
    	});
    	menuItem_modifyPassword.addActionListener(new ActionListener() {     //�޸�����
    		public void actionPerformed(ActionEvent e) {
    			UpdatePasswordPanel updatePasswordPanel = new UpdatePasswordPanel();
    		    updatePasswordPanel.setVisible(true);
    		}
    	});
    }
    
    public String[] getInfoOfPanel(){
    	String username = text_username.getText().trim();
    	String idNo = text_idNo.getText().trim();
    	String telephone = text_Telephone.getText().trim();
    	
    	String sex = radioBtn_sex_Male.isSelected()?"��":"Ů";
    	
    	String department = text_department.getText().trim();
    	
    	String noStatus = text_NoStaus.getText().equals("��ע��")?"1":"0";   //ע��--1��else 0
    	String E_mail = text_email.getText().trim();
    	
    	String roleType = texte_roleType.getText().trim();
    	String idCard  =text_idCard.getText().trim();
    	
    	String [] strs_UserPanel = new String[]{username,idNo,telephone,sex,department,noStatus,E_mail,roleType,idCard};
    	
    	return strs_UserPanel; 
    }
    

    
    public void initUserInfoOfPanel(){
//    	borrower = sql.getBorrower(this.borrower.getIdNo());  
    	
//    	JOptionPane.showMessageDialog(null, "initUserInfoOfPanel() \n"+borrower.getName());
    	text_username.setText(this.borrower.getName());
    	text_idNo.setText(this.borrower.getIdNo());
    	text_Telephone.setText(this.borrower.getTelephone());
    	
    	boolean sex = this.borrower.getSex().equals("��")?false:true;   //��---false
    	if(sex==false)
    	   radioBtn_sex_Male.setSelected(true);
    	else
    		radioBtn_sex_Female.setSelected(true); 
    	
    	text_department.setText(this.borrower.getDepartment());
    	
    	text_NoStaus.setText(this.borrower.getNoStatus()==1?"��ע��":"δע��");  //--1--ע�᣻-1ע������δע��
    	text_email.setText(this.borrower.getE_mail());
    	
    	texte_roleType.setText(this.borrower.getRoleType());
    	text_idCard.setText(this.borrower.getIdCard());   
        
    	textField_maxCount.setText(borrower.getMaxCount()+"");
    	textField_AllCount.setText(borrower.getAllCount()+"");
    	textField_borrowGrade.setText(borrower.getBorrowGrade()+"");
    	textField_nowCount.setText(borrower.getNowCount()+"");
    	
    	loadUserLogo();    //����ͷ��
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
    	path_person_logo += this.borrower.getIdNo().substring(12);
    	if(this.Useflag==1)    //����Ϊ������
    		path_person_logo += "_borrower.png";
    	else if(this.Useflag ==2)  //����Ϊ������
    		path_person_logo += "_admin.png";
    	    	
    	lblLabelpersonallogo = new JLabel("label_personalLogo");
    	lblLabelpersonallogo.setToolTipText("\u7528\u6237\u5934\u50CF");
    	
//    	JOptionPane.showMessageDialog(null, path_person_logo);//---Test
    	try{
    	    lblLabelpersonallogo.setIcon(new ImageIcon(MainPanelOfReader.class.getResource(path_person_logo)));
    	}catch(Exception e){
    		JOptionPane.showMessageDialog(null, "�û�ͷ�����ʧ��.");
      	}
    	lblLabelpersonallogo.setBackground(UIManager.getColor("CheckBox.focus"));
    	lblLabelpersonallogo.setBounds(10, 73, 131, 125);
    	getContentPane().add(lblLabelpersonallogo);

    }
    
    public User getUser(String strings[]){  
           User newUser = new User();
        	
//           String password;           //borrowerû�����룻���޸����ϵ�ʱ�򣬻Ὣ����ɾ���ˣ���¼����-----bug
           
           newUser = sql.getUserBySql(borrower.getIdNo());
           
//           password = newUser.getPassword();
           
               newUser.setName(strings[0]);
        	   newUser.setIdNo(strings[1]);
        	   newUser.setTelephone(strings[2]);
               newUser.setSex(strings[3].equals("��")?false:true);
               newUser.setDepartment(strings[4]);
               newUser.setNoStatus(Integer.parseInt(strings[5]));
               newUser.setE_mail(strings[6]);
               newUser.setRoleType(strings[7]);
               newUser.setIdCard(strings[8]);
              
//               newUser.setPassword(password);  
               
               return newUser;
    }
    
	public void initPanel(){
		borrower = new Borrower();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�ĳߴ� 
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds((int)(screenSize.width/(3)),screenSize.height/4, 625, 388);  
		getContentPane().setLayout(null);
		setResizable(false);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    this.setIconImage(eSys.getIconOfSystem().getImage());  //����ϵͳlogo
	   	
    	JLabel label_userName = new JLabel("\u59D3\u540D\uFF1A");
    	label_userName.setBounds(151, 73, 46, 15);
    	getContentPane().add(label_userName);
    	
        label_mainTitle = new JLabel("\u4E2A\u4EBA\u4FE1\u606F");
    	label_mainTitle.setFont(new Font("����", Font.BOLD, 17));
    	label_mainTitle.setBounds(267, 24, 93, 26);
    	getContentPane().add(label_mainTitle);
    	
    	JLabel label_line = new JLabel("___________________________________________________________________________________________");
    	label_line.setForeground(SystemColor.textHighlight);
    	label_line.setFont(new Font("����", Font.BOLD, 12));
    	label_line.setBounds(-14, 49, 659, 15);
    	getContentPane().add(label_line);
    	
    	JLabel label_idNo = new JLabel("\u8D26\u53F7\uFF1A");
    	label_idNo.setBounds(151, 121, 46, 15);
    	getContentPane().add(label_idNo);
    	
    	JLabel lblNewLabel_2 = new JLabel("E-Mail\uFF1A");
    	lblNewLabel_2.setBounds(15, 254, 66, 15);
    	getContentPane().add(lblNewLabel_2);
    	
    	JLabel label_sex = new JLabel("\u6027\u522B\uFF1A");
    	label_sex.setBounds(444, 73, 46, 15);
    	getContentPane().add(label_sex);
    	
    	JLabel label_noStatus = new JLabel("\u6CE8\u518C\u72B6\u6001\uFF1A");
    	label_noStatus.setBounds(444, 115, 66, 26);
    	getContentPane().add(label_noStatus);
    	
    	JLabel label_Telephone = new JLabel("\u7535\u8BDD\uFF1A");
    	label_Telephone.setBounds(151, 166, 46, 15);
    	getContentPane().add(label_Telephone);
    	
    	JLabel label_roleType = new JLabel("\u89D2\u8272\u7C7B\u522B\uFF1A");
    	label_roleType.setBounds(444, 166, 66, 15);
    	getContentPane().add(label_roleType);
    	
    	JLabel label_dapartment = new JLabel("\u79D1   \u7CFB\uFF1A");
    	label_dapartment.setBounds(15, 219, 66, 15);
    	getContentPane().add(label_dapartment);
    	
    	menuBar = new JMenuBar();
    	menuBar.setBackground(UIManager.getColor("Button.background"));
    	menuBar.setBounds(10, 5, 125, 21);
//    	menuBar.setVisible(aFlag);
    	getContentPane().add(menuBar);
    	
    	menu_exit = new JMenu("\u9000\u51FA");
    	menuBar.add(menu_exit);
    	
    	menuItem_exitSystem = new JMenuItem("\u7BA1\u7406\u7CFB\u7EDF");
    	menu_exit.add(menuItem_exitSystem);
    	
    	menuItem_exitPage = new JMenuItem("\u5F53\u524D\u9875\u9762");
    	menu_exit.add(menuItem_exitPage);
    	
    	menu_modify = new JMenu("\u4FEE\u6539");
    	menuBar.add(menu_modify);
    	
    	menuItem_modify_personData = new JMenuItem("\u8D44\u6599");
    	menu_modify.add(menuItem_modify_personData);
    	
    	menuItem_modifyPassword = new JMenuItem("\u5BC6\u7801");
    	menu_modify.add(menuItem_modifyPassword);
    	
    	menu_about = new JMenu("\u5173\u4E8E");
    	menuBar.add(menu_about);
    	
    	menuItem_about_us = new JMenuItem("\u5173\u4E8E\u6211\u4EEC");
    	menu_about.add(menuItem_about_us);
    	
    	JLabel label_IdCard = new JLabel("\u8BC1\u4EF6\u53F7\uFF1A");
    	label_IdCard.setBounds(10, 294, 66, 15);
    	getContentPane().add(label_IdCard);
    	
    	text_username = new JTextField();
    	label_userName.setLabelFor(text_username);
    	text_username.setBounds(198, 70, 81, 21);
    	text_username.setEditable(false);
    	getContentPane().add(text_username);
    	text_username.setColumns(10);
    	
    	text_idNo = new JTextField();
    	text_idNo.setBounds(198, 118, 159, 21);
    	text_idNo.setEditable(false);
    	getContentPane().add(text_idNo);
    	text_idNo.setColumns(10);
    	
    	text_Telephone = new JTextField();
    	text_Telephone.setBounds(198, 163, 159, 21);
    	text_Telephone.setEditable(false);
    	getContentPane().add(text_Telephone);
    	text_Telephone.setColumns(10);
    	
    	radioBtn_sex_Male = new JRadioButton("\u7537");
    	radioBtn_sex_Male.setBounds(487, 69, 46, 23);
//    	radioBtn_sex_Male.setVisible(false);             //���ò��ɼ�
    	radioBtn_sex_Male.setEnabled(false);
//    	radioBtn_sex_Male.set
    	getContentPane().add(radioBtn_sex_Male);
    	
    	radioBtn_sex_Female = new JRadioButton("\u5973");
    	radioBtn_sex_Female.setBounds(535, 69, 52, 23);
//    	radioBtn_sex_FeMale.setVisible(false);               //��������
    	radioBtn_sex_Female.setEnabled(false);         
    	getContentPane().add(radioBtn_sex_Female);
    	
    	text_NoStaus = new JTextField();
    	text_NoStaus.setBounds(507, 118, 66, 21);
    	text_NoStaus.setEditable(false);
    	getContentPane().add(text_NoStaus);
    	text_NoStaus.setColumns(10);
    	
    	texte_roleType = new JTextField();
    	texte_roleType.setBounds(507, 163, 66, 21);
    	texte_roleType.setEditable(false);
    	getContentPane().add(texte_roleType);
    	texte_roleType.setColumns(10);
    	
    	text_idCard = new JTextField();
    	text_idCard.setBounds(65, 291, 173, 21);
    	text_idCard.setEditable(false);
    	getContentPane().add(text_idCard);
    	text_idCard.setColumns(10);
    	
    	text_email = new JTextField();
    	text_email.setBounds(65, 251, 173, 21);
    	text_email.setEditable(false);
    	getContentPane().add(text_email);
    	text_email.setColumns(10);
    	
    	text_department = new JTextField();
    	text_department.setBounds(65, 216, 66, 21);
    	text_department.setEditable(false);    //���ò��ɱ༭
    	getContentPane().add(text_department);
    	text_department.setColumns(10);
    	
    	button_modify_sure = new JButton("\u786E\u5B9A");
    	button_modify_sure.setBounds(59, 326, 93, 23);
    	button_modify_sure.setVisible(false);     //���ò��ɼ�
    	getContentPane().add(button_modify_sure);
    	
    	button_modify_cancel = new JButton("\u53D6\u6D88");
    	button_modify_cancel.setBounds(464, 326, 93, 23);
    	button_modify_cancel.setVisible(false);    //���ò��ɼ�
    	getContentPane().add(button_modify_cancel);
    	
    	label = new JLabel("_________________________________________________________________________________________");
    	label.setForeground(SystemColor.textHighlight);
    	label.setFont(new Font("����", Font.BOLD, 12));
    	label.setBounds(0, 191, 664, 15);
    	getContentPane().add(label);
    	
    	label_maxCount = new JLabel("\u6700\u9AD8\u501F\u9605\u672C\u6570\uFF1A");
    	label_maxCount.setBounds(260, 222, 93, 15);
    	getContentPane().add(label_maxCount);
    	
    	textField_maxCount = new JTextField();
    	textField_maxCount.setBounds(355, 216, 66, 21);
    	textField_maxCount.setEditable(false);    //���ò��ɱ༭
    	getContentPane().add(textField_maxCount);
    	textField_maxCount.setColumns(10);
    	
    	JLabel label_nowCount = new JLabel("\u5F53\u524D\u501F\u9605\u672C\u6570\uFF1A");
    	label_nowCount.setBounds(260, 254, 93, 15);
    	getContentPane().add(label_nowCount);
    	
    	textField_nowCount = new JTextField();
    	textField_nowCount.setBounds(355, 251, 66, 21);
    	textField_nowCount.setEditable(false);    //���ò��ɱ༭
    	getContentPane().add(textField_nowCount);
    	textField_nowCount.setColumns(10);
    	
    	JLabel label_AllCount = new JLabel("\u501F\u4E66\u603B\u989D\u672C\u6570\uFF1A");
    	label_AllCount.setBounds(260, 294, 91, 15);
    	getContentPane().add(label_AllCount);
    	
    	textField_AllCount = new JTextField();
    	textField_AllCount.setBounds(355, 291, 66, 21);
    	textField_AllCount.setEditable(false);    //���ò��ɱ༭
    	getContentPane().add(textField_AllCount);
    	textField_AllCount.setColumns(10);
    	
    	label_borrowGrade = new JLabel("\u501F\u9605\u7B49\u7EA7\uFF1A");
    	label_borrowGrade.setBounds(444, 219, 79, 15);
    	getContentPane().add(label_borrowGrade);
    	
    	textField_borrowGrade = new JTextField();
    	textField_borrowGrade.setBounds(507, 216, 66, 21);
    	textField_borrowGrade.setEditable(false);    //���ò��ɱ༭
    	getContentPane().add(textField_borrowGrade);
    	textField_borrowGrade.setColumns(10);
    	
    	btn_sureInfo = new JButton("\u786E\u5B9A\u8EAB\u4EFD");
    	btn_sureInfo.setBounds(186, 326, 93, 23);
    	btn_sureInfo.setVisible(false);     //���ò��ɼ�
    	getContentPane().add(btn_sureInfo);
    	
    	btn_cancelInfo = new JButton("\u4E0D\u662F\u672C\u4EBA");
    	btn_cancelInfo.setBounds(328, 326, 93, 23);
    	btn_cancelInfo.setVisible(false);     //���ò��ɼ�
    	getContentPane().add(btn_cancelInfo);
    	
	} 
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Borrower borrower = new Borrower();
//					borrower.setAllProperties("513723199608111996", "����", 1, 0, "�������", 64, 10, 2, 0, "654321", false, "ѧ��", "�������", "1125418540@qq.com", "15202843104", 1);
//					PersonCenterPanel frame = new PersonCenterPanel(borrower,1);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
}
