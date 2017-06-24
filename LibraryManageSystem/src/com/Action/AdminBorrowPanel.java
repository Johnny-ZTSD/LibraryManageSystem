package com.Action;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Transferable;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.Dao.SQL;
import com.Entity.Book;
import com.Entity.BookScanner;
import com.Entity.BorrowRecord;
import com.Entity.Borrower;
import com.Entity.User;
import com.Service.Format;
import com.Service.Logic;
import com.Service.Operation;

import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


/***
 * �����߽������
 * @author Zen Johnny
 * @since 2016-12-22
 */
public class AdminBorrowPanel extends JFrame {   //implements TableCellRenderer

      private JTable table;                                    //�������
      private JButton btn_borrowBook;                                //ȷ�����İ�ť
      private JButton btn_ReturnBook;                              //ȡ�����İ�ť
	  private JButton btn_exit;                                //�˳���ť
	  private JButton btn_return;                              //���ذ�ť
	  private JLabel label_main;                               //��������ǩ
	  private JLabel label_currentTime;                        //��ǰʱ��---��ǩ 
	  private JLabel label_Text_currentTime;                   //��ǰʱ��---���ݱ�ǩ
	  private JLabel label_userNo;                             //�û���-----��ǩ
	  private JLabel label_Text_userNo;                        //�û���-----���ݱ�ǩ
      private JLabel label_currentCanNum;                      //��ǰ�ܽ��ĵı���
	  private JLabel label_maxCanNum;                          //��߿ɽ�����Ŀ
//      private JScrollPane tableScrollPane;                     //������
     
	  private ElmentOfSystem eSys = new ElmentOfSystem();      //ϵͳС���
	  private Calendar date_Current = Calendar.getInstance();  //��ȡ��ǰʱ��       
	  private Borrower borrower; 
	  private User Admin;                               //����Ա
	  private ArrayList<BorrowRecord> borrowRecords = new ArrayList<BorrowRecord>();  //���ļ�¼
	  private ArrayList<Book> ScanneredBooks = new ArrayList<Book>(); //ɨ�赽���鱾    
	  private int NumOfborrowedBook = 0;                       //�Ѿ�������Ŀǰδ�黹���鼮��Ŀ
	  private int maxNumOfBooks = 0;                            //��߽�������
	  private SQL sql = new SQL();
	  private String startDate;
	  private String endDate;
	  private int num_clikedSure = 0;           //��¼���ȷ�Ͻ��İ�ť����  ��ֹ��μ�¼�����ݿ�
	  private int num_ScanedBook = 0;         //ɨ�赽���鱾��Ŀ,�˴����ܲ�����ֵ  �������������
	  
	  private String[] headers = { "���", "����", "��������","Ӧ������","����״̬","��ע","ISBN" };
	  private int rows = 11; 
	  private int  columns = headers.length;             //���     ����  ��������     Ӧ������      
	  private JLabel label;
	  private JButton btn_sureIdNo;
	  private JTextField textField_inputIdNo;
	  private Format format;
	  
/**
 * ���췽��
 * @author Zen Johnny
 * @since 2016-12-22
 */
public AdminBorrowPanel() {
    InitPanel();
    panel_ActionLisenner();
}
public AdminBorrowPanel(String AdminidNo) {     //����Ա�˺�
    this();
    Admin = sql.getAdmin(AdminidNo);   //��½����Ա
}
public AdminBorrowPanel(Borrower borrower){
	this();
	borrowRecords = sql.searchBorrowRecords(borrower.getIdNo(), "idNo","0", "IsRevert");
	maxNumOfBooks = borrower.getMaxCount();
	ShowInfoOfPanel();
}
/********************************��������*****************/
public String[][] getBorrowRecords(){
	int Rows = 1;   //������ͷ
	int createColumns = 0;
	int borrowedNum;  //�Ѿ����ĵ���Ŀ
	int k =1;  //��ȡ����Ϣ��������     ---->����ȡ�ô�����¼����Ŀ
	DefaultTableModel model = (DefaultTableModel)table.getModel();
	
//	JOptionPane.showMessageDialog(null,model.getRowCount());
	
	while(!model.getValueAt(Rows, 4).equals("��δ�黹"))   //��ȡ�Ѿ����ĵ�����
	   Rows ++;
	k = Rows;
	borrowedNum = Rows; 
	while(model.getValueAt(Rows,1).toString().length()>1){   //������Ϊ��
		Rows ++;
	}
	
	k = Rows -k;
	
	String [][]strings = new String[k][6];
	
	int p = 0;
	JOptionPane.showMessageDialog(null, "����������"+k);//test
	if(this.borrower.getIdNo().length()!=18){
		JOptionPane.showMessageDialog(null, "AdminPanel:\ngetBorrowRecords():��������ϢΪ�գ�");
		System.exit(1);;
	}
	
	while(model.getValueAt(Rows,4).toString().equals("�ɽ���")){   //��Ų�Ϊ��
		  
		  //�������ݿ�˳����
		  strings[p][0] = this.borrower.getIdNo();
		  strings[p][1] = this.borrower.getName();
		  strings[p][2] = BookScanner.createISBN();
		  JOptionPane.showMessageDialog(null,model.getValueAt(p,1).toString());
		  strings[p][3] = model.getValueAt(p,1).toString();  //��ȡ����
		  strings[p][4] = "0";  //Ĭ��Ϊû�й黹
		  strings[p][5] = startDate;
		  strings[p][6] = endDate; 
		  p++;
	}
	
	return strings;
} 

/**
 * �������ļ�¼
 * ǰ��1����ʽ�Ѿ�����У��
 * ǰ��2������˳��ͳһ�������ݿ�˳��ɼ�
 * @author Zen Johnny
 * @since 2017-1-3
 * @param strings
 * @return
 */
public void CreateBorrowRecord(String[] infos){
	BorrowRecord borrowRecord  = new BorrowRecord();
    borrowRecord.setAllProperties(infos[0], infos[2], infos[3], infos[1], false, infos[5], infos[6]);
    SQL.InsertBorrowRecord(borrowRecord);
}
/**
 * �������ļ�¼
 * @author Zen Johnny
 * @since 2016-12-26
 * @param strings
 * @return
 */
public ArrayList<BorrowRecord> CreateBorrowRecords(String strings[][]){
	
	ArrayList<BorrowRecord> list = new ArrayList<BorrowRecord>();
	
	BorrowRecord newBorrowRecord = null;
	
	JOptionPane.showMessageDialog(null, strings.length); //Test
	
	for(int k = 0;k<strings.length;k++){
	  for(int i = 0;i<strings.length;i++){
		 newBorrowRecord = new BorrowRecord();
		 newBorrowRecord.setIdNo(strings[k][0]);
		 newBorrowRecord.setName(strings[k][1]);
		 newBorrowRecord.setIsbn(strings[k][2]);
		 JOptionPane.showMessageDialog(null, "strings[k][3]��"+strings[k][3]);
		 newBorrowRecord.setRevert(strings[k][3].equals("0")?false:true);   //0 û�л�--false
		 
		 newBorrowRecord.setBookName(strings[k][4]);   //bookname
		 newBorrowRecord.setStartDate(strings[k][5]);
		 newBorrowRecord.setEndDate(strings[k][6]);
		 
         list.add(newBorrowRecord);
	  }
	}
   return list;
}

/***
 * ��ȫ��������Ϣ
 * @since 2017-1-2
 * @author Zen Johnny
 */

public void clearTableModel(){
	DefaultTableModel model = (DefaultTableModel)table.getModel();
	int rows = model.getRowCount();
	int columns = model.getColumnCount();
	for(int i = 1;i< rows;i++){    
        for(int j=0;j<columns;j++){ 
        	model.setValueAt((String)"",i,j);          
        }
    }
	
}

public void Refresh(){
	clearTableModel();
	borrowRecords = sql.searchBorrowRecords(borrower.getIdNo(), "idNo","0", "IsRevert");
	maxNumOfBooks = borrower.getMaxCount();
	ShowInfoOfPanel();
}
/***
 * ����
 * @author Zen Johnny
 * @since 2017-1-2
 */

public void ReturnBookPanel(){
	   DefaultTableModel model = (DefaultTableModel)table.getModel();
	   int selected = table.getSelectedRow();
	   if(selected!=-1&&selected>0){   //��һ�У���ͷ  ��-1��ʾû��ѡ��
		   if(model.getValueAt(selected,6).toString().length()==17&&Format.IsBookName(model.getValueAt(selected,1).toString())){
		      String isbn = model.getValueAt(selected, 6).toString();
//		      JOptionPane.showMessageDialog(null, "ISBN:"+isbn);  //test
		      sql.UpdateBorrowRecord("1", "IsRevert", this.borrower.getIdNo(), isbn);
		      JOptionPane.showMessageDialog(null, "����ɹ���");
		      Refresh();
		   }
		   else
			   JOptionPane.showMessageDialog(null, "��ѡ������Ϣ����");
			   
	   }
	   else
		   JOptionPane.showMessageDialog(null,"δѡ��黹���鼮��");
}

private String[] getSelectedRows(DefaultTableModel model,int selected){
//	int flag = 0;
	String [] infos = new String[7];
	infos[0] = borrower.getIdNo();
	infos[1] = borrower.getName().trim();
	infos[2] = model.getValueAt(selected, 6).toString().trim();   //��ȡISBN��
	infos[3] = model.getValueAt(selected, 1).toString().trim();   //��ȡ����
	infos[4] = "0";   //���ù黹��Ϣ��δ�黹---:0
	infos[5] = model.getValueAt(selected, 2).toString().trim();   //��ȡʱ��
    infos[6] = model.getValueAt(selected, 3).toString().trim();   //��ȡʱ��
    
    return infos;
}

/***
 * ����
 * @author Zen Johnny
 * @since 2017-1-2
 */

public void BorrowBook(){
    DefaultTableModel model = (DefaultTableModel)table.getModel();
    int selected = table.getSelectedRow();
    if(selected!=-1&&selected>0){    //0����һ�У�-1��δѡ����
    	String infos[] = getSelectedRows(model,selected); 
    	if(Format.IsBorrowRecord(infos)==0){
    		 if(!Logic.IsBorrowed(infos[2]))
    		      CreateBorrowRecord(infos);
    		 else
    			 JOptionPane.showMessageDialog(null, "�����Ѿ����裬��δ�黹��");
    	}  
    	else
    		JOptionPane.showMessageDialog(null, "��ѡ����Ϣ��д������");
    }
    else
    	JOptionPane.showMessageDialog(null, "δѡ���У�����ʧ�ܣ�");
}

public void panel_ActionLisenner(){
	
    btn_borrowBook.addActionListener(new ActionListener() {      //ȷ�Ͻ��İ�ť
    	public void actionPerformed(ActionEvent e) {
    	    BorrowBook();
    	}
    });
   btn_ReturnBook.addActionListener(new ActionListener() {      //�黹ͼ�鰴ť
  	    public void actionPerformed(ActionEvent e) {
  	    	ReturnBookPanel();
  	   }
    }); 
  
   btn_exit.addActionListener(new ActionListener() {       //�˳���ť
	    public void actionPerformed(ActionEvent e) {
	         		System.exit(0);
	   }
	});
   btn_return.addActionListener(new ActionListener() {    //���ذ�ť
	    public void actionPerformed(ActionEvent e) {
	    		    dispose();
	   }
    });
   
   btn_sureIdNo.addActionListener(new ActionListener() {   //ȷ���˺Ű�ť
     	public void actionPerformed(ActionEvent e) {
            Refresh();    //ÿ���һ�Σ���ˢ��һ��  
    		
     		String idNo = textField_inputIdNo.getText().trim(); 
     		format = new Format();
     		if(format.IsAccount(idNo) == 0){  //����˺Ÿ�ʽ��ȷ��
     			borrower = new Borrower();
     			borrower = sql.getBorrower(idNo);
     			
     			if(borrower!=null){
                    
     				borrower.Show(-1);   //ȷ����Ϣ
     				
     			    borrowRecords = sql.searchBorrowRecords(borrower.getIdNo(), "idNo","0","IsRevert");
     			    
     			    NumOfborrowedBook = borrowRecords.size();
     			    
     			    maxNumOfBooks = borrower.getMaxCount();
     			    
     			    ShowInfoOfPanel();
     				//-------------------------------------------------------
     			    
     			}
     			else 
     				JOptionPane.showMessageDialog(null, "�˺Ų����ڣ�");
     		} 
     	}
     });
}
public void setBorrower(String idNo){
	borrower = SQL.getBorrower(idNo);
} 
/**
 * ��ǩ��Ϣ��ʼ��
 * */
public void initInfoLabels(){
	
	label_Text_userNo.setText(borrower.getIdNo());
    
	label_Text_currentTime.setText(
    		date_Current.get(Calendar.YEAR)+"-"+
    		(date_Current.get(Calendar.MONTH)+1)+"-"+     //�·�Ҫ+1
    		date_Current.get(Calendar.DAY_OF_MONTH)+"");
	 
	this.label_currentCanNum.setText("��ǰ�ɽ��ı���: "+ (10-NumOfborrowedBook)); 
	this.label_maxCanNum.setText("��߿ɽ��ı����� "+ 10);  
}

public void ShowInfoOfBorrowedBooks(){    //��ʾ�Ѿ����ĵ��鼮��Ϣ
   	
   BorrowRecord borrowRecord = null;	
   
   DefaultTableModel model = (DefaultTableModel)table.getModel();
   
   Iterator<BorrowRecord> iterator = this.borrowRecords.iterator();
   
   int i = 0;
   
   while(iterator.hasNext()){
	   
	   i++;
	   
	   borrowRecord = new BorrowRecord();
	   
	   borrowRecord = iterator.next();
	   
//       JOptionPane.showMessageDialog(null,"���ԣ�\n"+borrowRecord.toString());
       
		   model.setValueAt( i, i, 0);
		   
	       model.setValueAt(borrowRecord.getBookName(), i,1);
//	       model.isCellEditable(i, 1);
	       model.setValueAt( borrowRecord.getStartDate(), i, 2);
//	       model.isCellEditable(i, 2);
	       model.setValueAt(borrowRecord.getEndDate(), i,3);
//	       model.isCellEditable(i, 3);
	       model.setValueAt( borrowRecord.isRevert()==true?"�ɽ���":"��δ�黹",i, 4);
//	       model.isCellEditable(i, 4);
	       model.setValueAt("----", i,5);
//	       model.isCellEditable(i, 5);
	       model.setValueAt( borrowRecord.getIsbn(),i, 6); 
//	       model.isCellEditable(i, 6);
	   borrowRecord.clearAllproperties();    
	  
    }	
   
}

public void RefreshInfoOfBorrowedBooks(){    //ˢ����ʾ�Ѿ����ĵ��鼮��Ϣ

	   this.borrowRecords = sql.searchBorrowRecords(this.borrower.getIdNo(), "idNo", "0", "IsRevert");
	   this.label_currentCanNum.setText("��ǰ�ɽ��ı���: "+ (10-borrowRecords.size())); 
	   this.label_maxCanNum.setText("��߿ɽ��ı����� "+ this.borrower.getMaxCount());   
	   ShowInfoOfBorrowedBooks();
}
/**
 * ����������Ϣ
 * */
  public ArrayList<BorrowRecord> CreateBorrowRecords(){
	  ArrayList<BorrowRecord> list = new ArrayList<BorrowRecord>();
	  int count = num_ScanedBook;              //��ȡɨ����ɨ�赽���鱾 
	  
	  String idNo = this.borrower.getIdNo();
	  String name = this.borrower.getName();
	  BorrowRecord newBorrowRecord = new BorrowRecord();
	  Book newBook = new Book();
	  
	  for(int i=1;i<=count;i++){
			    newBorrowRecord.clearAllproperties();
				newBook.clearProperties();
		  
				newBook = ScanneredBooks.get(i-1);
		  
				String isbn = newBook.getIsbn();
				String bookName = newBook.getBookName();
				boolean revert =  newBook.isBorrowStaus();    //------������¼��ʱ����Ҫ������������Ϊ�Ѿ��������   
				//false--δ�����----BorrowStatus    false----revert----û�й黹--->�Ѿ����
		  	  
				newBorrowRecord.setAllProperties(idNo, isbn, bookName, name, revert, startDate, endDate);
				list.add(newBorrowRecord);
				sql.insertBorrowRecord(newBorrowRecord);
//				JOptionPane.showMessageDialog(null, newBorrowRecord.toString());   //����
		}  
        return list;
  }

/***
 * ģ���鼮ɨ���նˣ�ģ�����ɨ�����Ϣ��¼�벢��ʾ	
 * @author Zen Johnny
 * @since 2016-12-23
 */
public void ScannedInfoOfBook(){    //ģ��ɨ������� ������Ϣ

	Random random = new Random();   
//	num_ScanedBook;            //��ɨ���鱾����Ŀ��ģ��״̬�£����������
	int num;
	num = random.nextInt(10); 
//	JOptionPane.showMessageDialog(null, 10+"\n"+this.NumOfborrowedBook);//----Test
	
//	JOptionPane.showMessageDialog(null, "δ���鱾��Ŀ��"+this.NumOfborrowedBook);//Test
	
    while(num>(10-this.NumOfborrowedBook)||num<=0){
    	num = random.nextInt(10);   
    }
    //this.NumOfborrowedBook   δ������Ŀ
    //num��������һ��С�ڿɽ��������Ŀ��ȥ����δ������Ŀ�Ĳ�����ֵ��������ɨ�赽���鱾�����ֵ������ 
    num_ScanedBook = num - this.NumOfborrowedBook;	  //�˴����ܲ�����ֵ  ��������
    
//    JOptionPane.showMessageDialog(null,"����ʹ��------ɨ�赽���鱾����"+(num_ScanedBook));//Test
    
    BookScanner bScanner = new BookScanner();
    Book bk;
    ScanneredBooks = bScanner.ScanedBook(num_ScanedBook);
    
//    JOptionPane.showMessageDialog(null,"����ʹ��------�������鱾��Ŀ��"+(ScanneredBooks.size()));//����4��
    
//    JOptionPane.showMessageDialog(null,"����ʹ��------list:"+(list.isEmpty()==false?"��Ϊ��":"��"));  //--------------down 
    
    Iterator<Book> iterator = ScanneredBooks.iterator(); 
    
    DefaultTableModel model = (DefaultTableModel)table.getModel();
    for(int k = 0;k<columns;k++)
   	model.setValueAt((String)headers[k], 0, k);     //��ͷ
    while(iterator.hasNext()){
 
      bk = iterator.next();
          
//      JOptionPane.showMessageDialog(null,"public void initInfoOfBook(\n"+bk.toString());//--------Test
      
      for(int i = 0 ;i< num_ScanedBook ;i++){   //+1��Ϊ�˱�����ͷ�� 
        
    	model.setValueAt(NumOfborrowedBook+1+i, NumOfborrowedBook+1+i, 0);
        model.setValueAt(bk.getBookName(), NumOfborrowedBook+1+i, 1);
        
        model.setValueAt(startDate, NumOfborrowedBook+1+i, 2);
        model.setValueAt(endDate, NumOfborrowedBook+1+i, 3);
        model.setValueAt(bk.isBorrowStaus()==false?"�ɽ���":"��δ�黹", NumOfborrowedBook+1+i, 4);
        model.setValueAt("----",NumOfborrowedBook+1+i, 5);
      }
    
    }
}
/**
 * ���Table����Ϣ��ʾ
 * 
 * */
public void ShowInfoOfPanel(){

    ShowInfoOfBorrowedBooks();   //��ʾ�Ѿ����ĵ��鼮��Ϣ
//	ScannedInfoOfBook();
	initInfoLabels();
}

public void InitPanel(){
	borrower = new Borrower();
	Admin = new User();
	
	startDate = date_Current.get(Calendar.YEAR) +"-"+
	            (date_Current.get(Calendar.MONTH)+1)+"-"+
	            date_Current.get(Calendar.DAY_OF_MONTH)+"";
    
	String year = date_Current.get(Calendar.YEAR)+""; 
	int y;
	if((date_Current.get(Calendar.MONTH)+3)>12){
		y = Integer.parseInt(year+1+""); 
	    year = y+"";
	}
	
    endDate =  year+"-"+
              (date_Current.get(Calendar.MONTH)+3)%12+"-"+
              date_Current.get(Calendar.DAY_OF_MONTH)+"";

	
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�ĳߴ� 
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(screenSize.width/3,screenSize.height/4, 583, 461);  
	this.setIconImage(eSys.getIconOfSystem().getImage());  //����ϵͳlogo
//    setResizable(false); 
    setTitle("\u56FE\u4E66\u7BA1\u7406\u4E2D\u5FC3");    
    getContentPane().setLayout(null);
    
    /*���----����ʾ��ǩ*/
    label_main = new JLabel("\u501F  \u9605  \u7BA1  \u7406");       //����-label
    label_main.setFont(new Font("����", Font.BOLD, 17));
    label_main.setBounds(204, 10, 141, 32);
    getContentPane().add(label_main);
    
    /*����ʼ��*/
    Object[][] cellData = new Object[rows][columns];

    
    DefaultTableModel model = new DefaultTableModel(cellData, headers);
    
    table = new JTable(model);
//    table.addFocusListener(new FocusAdapter() {
//    	@Override
//    	public void focusGained(FocusEvent e) {
////    		JOptionPane.showMessageDialog(null, "���");
//    	}
//    });
    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    table.setForeground(Color.BLACK);
    table.setShowGrid(true);
    table.setPreferredScrollableViewportSize(getSize());   
    table.setBounds(20, 125, 549, 225); 
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
    table.getColumnModel().getColumn(0).setPreferredWidth((int)(widthAll * 0.08));    //��ȡ������
    table.getColumnModel().getColumn(1).setPreferredWidth((int)(widthAll*0.2));
    table.getColumnModel().getColumn(2).setPreferredWidth((int)(widthAll*0.123));    //��ȡ������ 
    table.getColumnModel().getColumn(3).setPreferredWidth((int)(widthAll*0.123));    //��ȡ������
    table.getColumnModel().getColumn(4).setPreferredWidth((int)(widthAll*0.123));
    table.getColumnModel().getColumn(5).setPreferredWidth((int)(widthAll*0.04));
    table.getColumnModel().getColumn(6).setPreferredWidth((int)(widthAll*0.24));
    getContentPane().add(table);
      
    /*���ذ�ť*/ 
    btn_return = new JButton("\u8FD4\u56DE\u7CFB\u7EDF");
    btn_return.setBounds(311, 385, 93, 23);
    getContentPane().add(btn_return);
      
      btn_exit = new JButton("\u9000\u51FA\u7CFB\u7EDF");
      btn_exit.setBounds(438, 385, 93, 23);
      getContentPane().add(btn_exit);
      
      label_currentTime = new JLabel("��ǰʱ�䣺");
      label_currentTime.setBounds(381, 86, 68, 29);
      getContentPane().add(label_currentTime);
      
      label_Text_currentTime = new JLabel("1970-1-1");
      label_Text_currentTime.setFont(new Font("����", Font.PLAIN, 14));
      label_Text_currentTime.setBounds(444, 90, 125, 25);
      getContentPane().add(label_Text_currentTime);
      
      label_userNo = new JLabel("�û���");
      label_userNo.setBounds(10, 92, 61, 23);
      getContentPane().add(label_userNo);
      
      label_Text_userNo = new JLabel();
      label_Text_userNo.setFont(new Font("����", Font.PLAIN, 14));
      label_Text_userNo.setBounds(54, 86, 136, 29);
      getContentPane().add(label_Text_userNo);
      
      btn_ReturnBook = new JButton("\u5F52\u8FD8\u56FE\u4E66");
      btn_ReturnBook.setBounds(51, 385, 93, 23);
      getContentPane().add(btn_ReturnBook);
      
      btn_borrowBook = new JButton("\u501F\u9605\u56FE\u4E66");
      btn_borrowBook.setBounds(182, 385, 93, 23);
      getContentPane().add(btn_borrowBook);
      
      label_currentCanNum = new JLabel("��ǰ�ɽ��ı�����");
      label_currentCanNum.setBounds(10, 360, 149, 15);
      getContentPane().add(label_currentCanNum);
      
      label_maxCanNum = new JLabel("��߿ɽ豾����");
      label_maxCanNum.setBounds(383, 360, 165, 15);
      getContentPane().add(label_maxCanNum);
      
      label = new JLabel("______________________________________________________________________________________________________________________");
      label.setBounds(-29, 27, 726, 15);
      getContentPane().add(label);
      
      btn_sureIdNo = new JButton("\u786E\u8BA4\u8D26\u53F7");
      btn_sureIdNo.setBounds(466, 60, 93, 23);
      getContentPane().add(btn_sureIdNo);
      
      textField_inputIdNo = new JTextField();
      textField_inputIdNo.setBounds(10, 61, 432, 21);
      getContentPane().add(textField_inputIdNo);
      textField_inputIdNo.setColumns(10);
}

//  public static void main(String args[]){
//	  AdminBorrowPanel bp = new AdminBorrowPanel("123451234512345123");
//	  bp.setVisible(true);
//  }
}

//table.getTableHeader().setBackground(new Color(90, 128, 20));

//JTableHeader tableH = table.getTableHeader();     //���ñ�ͷ
//tableH.setBackground(new Color(100, 0, 200)); 
//tableH.setForeground(new Color(0, 0, 0));
//DefaultTableModel tableModel = (DefaultTableModel) table.getModel();    //�������
//tableModel.addRow(new Object[]{"sitinspring", "35", "Boss"});
//table.invalidate();                                        //���±��
//table.getTableHeader().setVisible(false);                   //���ر�ͷ
//tableScrollPane = new JScrollPane();
//tableScrollPane.setViewportView(table);   
//getContentPane().add(tableScrollPane); 
//JScrollPane scroll = new JScrollPane(table);
//scroll.getViewport().setBackground(new Color(255, 255, 255)); 
//getContentPane().add(scroll);