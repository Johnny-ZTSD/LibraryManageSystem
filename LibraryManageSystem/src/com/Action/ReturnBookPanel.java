package com.Action;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.lang.model.element.Element;
import javax.naming.InitialContext;
import javax.security.auth.Refreshable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.Dao.SQL;
import com.Entity.BorrowRecord;
import com.Entity.Borrower;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;
import java.awt.event.ActionEvent;

public class ReturnBookPanel extends JFrame {
    
	private ElmentOfSystem eSys;     //�Զ���ϵͳС��� 
	private JTable table;
	private JLabel label_main;
	private JLabel label_userNo;
	private JLabel label_currentTime;
	private JLabel label_NumOfShouldReturn;
	private JLabel label_fine;
	private JButton btn_sure;
	private JButton btn_return;
	private JButton btn_exit;

	private String[] headers = { "���", "����", "��������","Ӧ������","����״̬","��ע" };
	private int rows = 11; 
	private int  columns = headers.length;             //���     ����  ��������     Ӧ������      
	
	private Calendar date_Current = Calendar.getInstance();  //��ȡ��ǰʱ�� 
	private ArrayList<BorrowRecord> borrowRecords = new ArrayList<BorrowRecord>();  //���ļ�¼�������ߵ�ǰ���еģ�
	private ArrayList<BorrowRecord> ScannedRecords = new ArrayList<BorrowRecord>();  //׼���黹���鱾��¼��һ���֣�
	private int NumOfBorrowed;                         //�ѽ��ĵ�δ�黹���鱾����
	private SQL sql = new SQL();
	private String nowDate;
	private String endDate;    
	private Borrower borrower;
	private int num_clikedSure = 0;           //��¼���ȷ�Ϲ黹��ť����  ��ֹ��μ�¼�����ݿ�  
	
	/**
	 * ���췽��.
	 */
	public ReturnBookPanel() {
		InitPanel();
	}
	
    public ReturnBookPanel(Borrower borrower){
    	this();
    	this.borrower = borrower;
    	InitInfoOfTable();
    	InitInfoOfLabel();
    	panel_ActionLisener();
    } 
	
	/***
	 * ��������
	 */
    public void Refresh(){
//    	borrowRecords = sql.searchBorrowRecords(this.borrower.getIdNo(), "idNo", "0", "IsRevert");
    	this.NumOfBorrowed = borrowRecords.size();
    	initTableModel();    //������
    	ReturnInfoOfTable();   //�ոջ������Ϣ
    	InitInfoOfLabel();
    }
    
    public void ReturnInfoOfTable(){
    	BorrowRecord borrowRecord = null;	
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		Iterator<BorrowRecord> iterator = this.borrowRecords.iterator();
        int k = 1;    //�ӵڶ��п�ʼ��ʾ���ļ�¼��Ϣ 
//        int NumScanedOfBooks = 0;   //׼���黹���鱾����Ŀ����ɨ�赽���鱾����
        
        Random random = new Random();     
//		NumScanedOfBooks = random.nextInt(borrowRecords.size());    //ģ��
        
        while(iterator.hasNext()){   //ģ��:ȡ����δ�黹���鱾�е�ǰNumScanedOfBooks��		   	   
			   borrowRecord = iterator.next();       
				   model.setValueAt( k, k, 0);
			       model.setValueAt(borrowRecord.getBookName(), k,1);
			       model.setValueAt( borrowRecord.getStartDate(), k, 2);
			       model.setValueAt(borrowRecord.getEndDate(), k,3);
			       model.setValueAt( borrowRecord.isRevert()==true?"�ѹ黹":"��δ�黹", k, 4);
			       model.setValueAt("----", k,5);		   
			   borrowRecord.clearAllproperties();    
			   k++;	   
		    }
        NumOfBorrowed = this.borrower.getMaxCount() - (k-1);
    }
    
    public void ReturnBook(){
    	String isbn = "";
    	int i = 0;
    	
    	while(i<ScannedRecords.size()){
    	   isbn = ScannedRecords.get(i).getIsbn();
//    	   JOptionPane.showMessageDialog(null, );
    	   JOptionPane.showMessageDialog(null, "�����ߣ�\n"+this.borrower+"\nISBN:"+isbn);
    	   ScannedRecords.get(i).setRevert(true);   //����Ϊ�Ѿ����� 
    	   JOptionPane.showMessageDialog(null,ScannedRecords.get(i).toString());  //test
    	   SQL.UpdateBorrowRecord("1", "IsRevert",this.borrower.getIdNo(),isbn);
           i++;
    	}
    }
    
    public void panel_ActionLisener(){
		btn_sure.addActionListener(new ActionListener() {       //ȷ�Ϲ黹
			public void actionPerformed(ActionEvent e) {
				num_clikedSure++;               //��¼���ȷ�Ͻ��İ�ť����  ��ֹ��μ�¼�����ݿ�
	    		if(num_clikedSure<=1){
//	    			Refresh();
	    			ReturnBook();
					JOptionPane.showMessageDialog(null, "����ɹ���");
//					Refresh();
	    		}
	    		else
	    			JOptionPane.showMessageDialog(null,"�����ظ��黹!");	
				
			}
		});
//		btn_cancel.addActionListener(new ActionListener() {     //ȡ���黹
//			public void actionPerformed(ActionEvent e) {
//				Refresh();
//				setTitle("��ǰ������Ϣ");
//			}
//		});
		
		btn_return.addActionListener(new ActionListener() {      //����
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btn_exit.addActionListener(new ActionListener() {         //�˳�-����ϵͳ
			public void actionPerformed(ActionEvent e) {
			     System.exit(0);  //�����˳�
			}
		});
		
    }
    
    public void InitInfoOfLabel(){
    	this.label_userNo.setText("�û���    " + this.borrower.getIdNo());
        this.label_currentTime.setText("��ǰʱ�䣺    "+this.nowDate);
       
        this.NumOfBorrowed = borrowRecords.size();
        this.label_NumOfShouldReturn.setText("���黹�鱾��  "+this.NumOfBorrowed+"��");
        
        this.label_fine.setText("����  "+0+"Ԫ");
    }
    
	public void InitInfoOfTable(){
		
		borrowRecords = sql.searchBorrowRecords(this.borrower.getIdNo(), "idNo", "0", "IsRevert");
		
		BorrowRecord borrowRecord = null;	
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		Iterator<BorrowRecord> iterator = this.borrowRecords.iterator();
        int k = 1;    //�ӵڶ��п�ʼ��ʾ���ļ�¼��Ϣ 
        int NumScanedOfBooks = 0;   //׼���黹���鱾����Ŀ����ɨ�赽���鱾����
        
        Random random = new Random();     
		NumScanedOfBooks = random.nextInt(borrowRecords.size());    //ģ��
        
        while(iterator.hasNext() && k <= NumScanedOfBooks){   //ģ��:ȡ����δ�黹���鱾�е�ǰNumScanedOfBooks��
			   
			   
			   borrowRecord = iterator.next();
			   
//	JOptionPane.showMessageDialog(null, borrowRecord.toString());   //����
			   
			   BorrowRecord newBorrowRecord = new BorrowRecord();
			   /***
			    * �������´�������!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			    */
			   newBorrowRecord.setAllProperties(borrowRecord.getIdNo(), borrowRecord.getIsbn(),borrowRecord.getBookName(), borrowRecord.getName(), borrowRecord.isRevert(), borrowRecord.getStartDate(), borrowRecord.getEndDate());
			   
//			   JOptionPane.showMessageDialog(null, "�鱾��Ϣ��"+newBorrowRecord.toString()); 
			   
			   ScannedRecords.add(newBorrowRecord);   //��ӵ�Ҫ�黹���鱾��¼��
			   
//		       JOptionPane.showMessageDialog(null,"���ԣ�\n"+borrowRecord.toString());
		       
				   model.setValueAt( k, k, 0);
			       model.setValueAt(borrowRecord.getBookName(), k,1);
			       model.setValueAt( borrowRecord.getStartDate(), k, 2);
			       model.setValueAt(borrowRecord.getEndDate(), k,3);
			       model.setValueAt( borrowRecord.isRevert()==true?"�ѹ黹":"��δ�黹", k, 4);
			       model.setValueAt("----", k,5);
			   
			   borrowRecord.clearAllproperties();    
			   k++;
			   
		    }	
//        
//        JOptionPane.showMessageDialog(null,"Test:"+ScannedRecords.size());     
//        for(int i=0;i<ScannedRecords.size();i++){
//        	JOptionPane.showMessageDialog(null, ScannedRecords.get(i).toString());;
//        }
	}
    
	public void initTableModel(){
		Object[][] cellData = new Object[rows][columns];

		DefaultTableModel model = new DefaultTableModel(cellData, headers) {
		      public boolean isCellEditable(int row, int column) {
		        return false;                           //��񲻿ɱ༭
		      }
		    };
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setForeground(Color.BLACK);
		table.setShowGrid(true);
		table.setPreferredScrollableViewportSize(getSize());   
		table.setBounds(10, 84, 557, 225); 
		for(int k = 0;k<columns;k++)
		 	 model.setValueAt((String)headers[k], 0, k);     //��ͷ
		for(int i = 1;i< rows;i++){    
		    for(int j=0;j<columns;j++){ 
		    	model.setValueAt((String)"",i,j);          
		    }
		 }
		 table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  //���ò��ɸ�����������ߴ�
		 table.setRowHeight(20);                           //�����и�
		 int widthAll =table.getWidth();
		 table.getColumnModel().getColumn(0).setPreferredWidth((int)(widthAll*0.15));    //��ȡ������
		 table.getColumnModel().getColumn(1).setPreferredWidth((int)(widthAll*0.3));
		 table.getColumnModel().getColumn(2).setPreferredWidth((int)(widthAll*0.15));    //��ȡ������ 
		 table.getColumnModel().getColumn(3).setPreferredWidth((int)(widthAll*0.15));    //��ȡ������
		 table.getColumnModel().getColumn(4).setPreferredWidth((int)(widthAll*0.125));
		 table.getColumnModel().getColumn(5).setPreferredWidth((int)(widthAll*0.125));
		 getContentPane().add(table);
	}
	
	public void InitPanel(){
		borrower = new Borrower();
		nowDate  = date_Current.get(Calendar.YEAR)+"-"+
				   (date_Current.get(Calendar.MONTH)+1)+"-"+
				   date_Current.get(Calendar.DAY_OF_MONTH);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�ĳߴ� 
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds((int)(screenSize.width/(3.5)),screenSize.height/4, 583, 408);  
		getContentPane().setLayout(null);
		setResizable(false);
		getContentPane().setLayout(null);
		setTitle("\u56FE\u4E66\u7BA1\u7406\u4E2D\u5FC3");
		this.setIconImage(eSys.getIconOfSystem().getImage());     //���ù���ϵͳlogo
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);               //�رհ�ť��Ĭ�ϲ��� 
		
		
		label_main = new JLabel("\u8FD8\u4E66\u4FE1\u606F");
		label_main.setFont(new Font("����", Font.BOLD, 17));
		label_main.setBounds(234, 10, 85, 39);
		getContentPane().add(label_main);

        initTableModel(); 
		
		label_userNo = new JLabel("�û���");
		label_userNo.setBounds(10, 48, 188, 23);
		getContentPane().add(label_userNo);
		
		label_currentTime = new JLabel("��ǰʱ�䣺");
		label_currentTime.setBounds(383, 48, 184, 29);
		getContentPane().add(label_currentTime);
		
		btn_return = new JButton("\u8FD4\u56DE");
		btn_return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_return.setBounds(226, 346, 93, 23);
		getContentPane().add(btn_return);
		
		btn_exit = new JButton("\u9000\u51FA");
		btn_exit.setBounds(391, 346, 93, 23);
		getContentPane().add(btn_exit);
		
		btn_sure = new JButton("\u5F52\u8FD8\u786E\u8BA4");
		btn_sure.setBounds(72, 346, 93, 23);
		getContentPane().add(btn_sure);
		
		label_NumOfShouldReturn = new JLabel("\u5F85\u5F52\u8FD8\u4E66\u672C\uFF1A");
		label_NumOfShouldReturn.setBounds(10, 321, 123, 15);
		getContentPane().add(label_NumOfShouldReturn);
		
		label_fine = new JLabel("\u7F5A\u91D1\uFF1A");
		label_fine.setBounds(476, 319, 91, 15);
		getContentPane().add(label_fine);
		
	}
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ReturnBookPanel frame = new ReturnBookPanel();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
}
