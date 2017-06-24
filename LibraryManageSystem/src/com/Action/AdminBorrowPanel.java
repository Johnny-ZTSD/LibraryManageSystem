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
 * 借阅者借阅面板
 * @author Zen Johnny
 * @since 2016-12-22
 */
public class AdminBorrowPanel extends JFrame {   //implements TableCellRenderer

      private JTable table;                                    //表格容器
      private JButton btn_borrowBook;                                //确定借阅按钮
      private JButton btn_ReturnBook;                              //取消借阅按钮
	  private JButton btn_exit;                                //退出按钮
	  private JButton btn_return;                              //返回按钮
	  private JLabel label_main;                               //界面主标签
	  private JLabel label_currentTime;                        //当前时间---标签 
	  private JLabel label_Text_currentTime;                   //当前时间---内容标签
	  private JLabel label_userNo;                             //用户名-----标签
	  private JLabel label_Text_userNo;                        //用户名-----内容标签
      private JLabel label_currentCanNum;                      //当前能借阅的本数
	  private JLabel label_maxCanNum;                          //最高可借阅数目
//      private JScrollPane tableScrollPane;                     //滚动条
     
	  private ElmentOfSystem eSys = new ElmentOfSystem();      //系统小组件
	  private Calendar date_Current = Calendar.getInstance();  //获取当前时间       
	  private Borrower borrower; 
	  private User Admin;                               //管理员
	  private ArrayList<BorrowRecord> borrowRecords = new ArrayList<BorrowRecord>();  //借阅记录
	  private ArrayList<Book> ScanneredBooks = new ArrayList<Book>(); //扫描到的书本    
	  private int NumOfborrowedBook = 0;                       //已经借阅且目前未归还的书籍数目
	  private int maxNumOfBooks = 0;                            //最高借阅书数
	  private SQL sql = new SQL();
	  private String startDate;
	  private String endDate;
	  private int num_clikedSure = 0;           //记录点击确认借阅按钮次数  防止多次记录到数据库
	  private int num_ScanedBook = 0;         //扫描到的书本数目,此处可能产生负值  仍属于正常情况
	  
	  private String[] headers = { "序号", "书名", "借阅日期","应还日期","借阅状态","备注","ISBN" };
	  private int rows = 11; 
	  private int  columns = headers.length;             //序号     书名  借阅日期     应还日期      
	  private JLabel label;
	  private JButton btn_sureIdNo;
	  private JTextField textField_inputIdNo;
	  private Format format;
	  
/**
 * 构造方法
 * @author Zen Johnny
 * @since 2016-12-22
 */
public AdminBorrowPanel() {
    InitPanel();
    panel_ActionLisenner();
}
public AdminBorrowPanel(String AdminidNo) {     //管理员账号
    this();
    Admin = sql.getAdmin(AdminidNo);   //登陆管理员
}
public AdminBorrowPanel(Borrower borrower){
	this();
	borrowRecords = sql.searchBorrowRecords(borrower.getIdNo(), "idNo","0", "IsRevert");
	maxNumOfBooks = borrower.getMaxCount();
	ShowInfoOfPanel();
}
/********************************其他方法*****************/
public String[][] getBorrowRecords(){
	int Rows = 1;   //跳过表头
	int createColumns = 0;
	int borrowedNum;  //已经借阅的数目
	int k =1;  //获取有信息的总行数     ---->最终取得创建记录的数目
	DefaultTableModel model = (DefaultTableModel)table.getModel();
	
//	JOptionPane.showMessageDialog(null,model.getRowCount());
	
	while(!model.getValueAt(Rows, 4).equals("尚未归还"))   //获取已经借阅的行数
	   Rows ++;
	k = Rows;
	borrowedNum = Rows; 
	while(model.getValueAt(Rows,1).toString().length()>1){   //书名不为空
		Rows ++;
	}
	
	k = Rows -k;
	
	String [][]strings = new String[k][6];
	
	int p = 0;
	JOptionPane.showMessageDialog(null, "借书行数："+k);//test
	if(this.borrower.getIdNo().length()!=18){
		JOptionPane.showMessageDialog(null, "AdminPanel:\ngetBorrowRecords():借阅者信息为空！");
		System.exit(1);;
	}
	
	while(model.getValueAt(Rows,4).toString().equals("可借阅")){   //序号不为空
		  
		  //依照数据库顺序存放
		  strings[p][0] = this.borrower.getIdNo();
		  strings[p][1] = this.borrower.getName();
		  strings[p][2] = BookScanner.createISBN();
		  JOptionPane.showMessageDialog(null,model.getValueAt(p,1).toString());
		  strings[p][3] = model.getValueAt(p,1).toString();  //获取书名
		  strings[p][4] = "0";  //默认为没有归还
		  strings[p][5] = startDate;
		  strings[p][6] = endDate; 
		  p++;
	}
	
	return strings;
} 

/**
 * 创建借阅记录
 * 前提1：格式已经经过校验
 * 前提2：数据顺序统一按照数据库顺序采集
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
 * 创建借阅记录
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
		 JOptionPane.showMessageDialog(null, "strings[k][3]："+strings[k][3]);
		 newBorrowRecord.setRevert(strings[k][3].equals("0")?false:true);   //0 没有还--false
		 
		 newBorrowRecord.setBookName(strings[k][4]);   //bookname
		 newBorrowRecord.setStartDate(strings[k][5]);
		 newBorrowRecord.setEndDate(strings[k][6]);
		 
         list.add(newBorrowRecord);
	  }
	}
   return list;
}

/***
 * 完全清理表格信息
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
 * 还书
 * @author Zen Johnny
 * @since 2017-1-2
 */

public void ReturnBookPanel(){
	   DefaultTableModel model = (DefaultTableModel)table.getModel();
	   int selected = table.getSelectedRow();
	   if(selected!=-1&&selected>0){   //第一行：表头  ；-1表示没有选中
		   if(model.getValueAt(selected,6).toString().length()==17&&Format.IsBookName(model.getValueAt(selected,1).toString())){
		      String isbn = model.getValueAt(selected, 6).toString();
//		      JOptionPane.showMessageDialog(null, "ISBN:"+isbn);  //test
		      sql.UpdateBorrowRecord("1", "IsRevert", this.borrower.getIdNo(), isbn);
		      JOptionPane.showMessageDialog(null, "还书成功！");
		      Refresh();
		   }
		   else
			   JOptionPane.showMessageDialog(null, "所选择行信息有误！");
			   
	   }
	   else
		   JOptionPane.showMessageDialog(null,"未选择归还的书籍！");
}

private String[] getSelectedRows(DefaultTableModel model,int selected){
//	int flag = 0;
	String [] infos = new String[7];
	infos[0] = borrower.getIdNo();
	infos[1] = borrower.getName().trim();
	infos[2] = model.getValueAt(selected, 6).toString().trim();   //获取ISBN号
	infos[3] = model.getValueAt(selected, 1).toString().trim();   //获取书名
	infos[4] = "0";   //设置归还信息：未归还---:0
	infos[5] = model.getValueAt(selected, 2).toString().trim();   //获取时间
    infos[6] = model.getValueAt(selected, 3).toString().trim();   //获取时间
    
    return infos;
}

/***
 * 借书
 * @author Zen Johnny
 * @since 2017-1-2
 */

public void BorrowBook(){
    DefaultTableModel model = (DefaultTableModel)table.getModel();
    int selected = table.getSelectedRow();
    if(selected!=-1&&selected>0){    //0：第一行；-1：未选中行
    	String infos[] = getSelectedRows(model,selected); 
    	if(Format.IsBorrowRecord(infos)==0){
    		 if(!Logic.IsBorrowed(infos[2]))
    		      CreateBorrowRecord(infos);
    		 else
    			 JOptionPane.showMessageDialog(null, "本书已经被借，尚未归还！");
    	}  
    	else
    		JOptionPane.showMessageDialog(null, "所选行信息填写不完整");
    }
    else
    	JOptionPane.showMessageDialog(null, "未选中行，借阅失败！");
}

public void panel_ActionLisenner(){
	
    btn_borrowBook.addActionListener(new ActionListener() {      //确认借阅按钮
    	public void actionPerformed(ActionEvent e) {
    	    BorrowBook();
    	}
    });
   btn_ReturnBook.addActionListener(new ActionListener() {      //归还图书按钮
  	    public void actionPerformed(ActionEvent e) {
  	    	ReturnBookPanel();
  	   }
    }); 
  
   btn_exit.addActionListener(new ActionListener() {       //退出按钮
	    public void actionPerformed(ActionEvent e) {
	         		System.exit(0);
	   }
	});
   btn_return.addActionListener(new ActionListener() {    //返回按钮
	    public void actionPerformed(ActionEvent e) {
	    		    dispose();
	   }
    });
   
   btn_sureIdNo.addActionListener(new ActionListener() {   //确认账号按钮
     	public void actionPerformed(ActionEvent e) {
            Refresh();    //每点击一次，就刷新一次  
    		
     		String idNo = textField_inputIdNo.getText().trim(); 
     		format = new Format();
     		if(format.IsAccount(idNo) == 0){  //如果账号格式正确。
     			borrower = new Borrower();
     			borrower = sql.getBorrower(idNo);
     			
     			if(borrower!=null){
                    
     				borrower.Show(-1);   //确认信息
     				
     			    borrowRecords = sql.searchBorrowRecords(borrower.getIdNo(), "idNo","0","IsRevert");
     			    
     			    NumOfborrowedBook = borrowRecords.size();
     			    
     			    maxNumOfBooks = borrower.getMaxCount();
     			    
     			    ShowInfoOfPanel();
     				//-------------------------------------------------------
     			    
     			}
     			else 
     				JOptionPane.showMessageDialog(null, "账号不存在！");
     		} 
     	}
     });
}
public void setBorrower(String idNo){
	borrower = SQL.getBorrower(idNo);
} 
/**
 * 标签信息初始化
 * */
public void initInfoLabels(){
	
	label_Text_userNo.setText(borrower.getIdNo());
    
	label_Text_currentTime.setText(
    		date_Current.get(Calendar.YEAR)+"-"+
    		(date_Current.get(Calendar.MONTH)+1)+"-"+     //月份要+1
    		date_Current.get(Calendar.DAY_OF_MONTH)+"");
	 
	this.label_currentCanNum.setText("当前可借阅本数: "+ (10-NumOfborrowedBook)); 
	this.label_maxCanNum.setText("最高可借阅本数： "+ 10);  
}

public void ShowInfoOfBorrowedBooks(){    //显示已经借阅的书籍信息
   	
   BorrowRecord borrowRecord = null;	
   
   DefaultTableModel model = (DefaultTableModel)table.getModel();
   
   Iterator<BorrowRecord> iterator = this.borrowRecords.iterator();
   
   int i = 0;
   
   while(iterator.hasNext()){
	   
	   i++;
	   
	   borrowRecord = new BorrowRecord();
	   
	   borrowRecord = iterator.next();
	   
//       JOptionPane.showMessageDialog(null,"测试：\n"+borrowRecord.toString());
       
		   model.setValueAt( i, i, 0);
		   
	       model.setValueAt(borrowRecord.getBookName(), i,1);
//	       model.isCellEditable(i, 1);
	       model.setValueAt( borrowRecord.getStartDate(), i, 2);
//	       model.isCellEditable(i, 2);
	       model.setValueAt(borrowRecord.getEndDate(), i,3);
//	       model.isCellEditable(i, 3);
	       model.setValueAt( borrowRecord.isRevert()==true?"可借阅":"尚未归还",i, 4);
//	       model.isCellEditable(i, 4);
	       model.setValueAt("----", i,5);
//	       model.isCellEditable(i, 5);
	       model.setValueAt( borrowRecord.getIsbn(),i, 6); 
//	       model.isCellEditable(i, 6);
	   borrowRecord.clearAllproperties();    
	  
    }	
   
}

public void RefreshInfoOfBorrowedBooks(){    //刷新显示已经借阅的书籍信息

	   this.borrowRecords = sql.searchBorrowRecords(this.borrower.getIdNo(), "idNo", "0", "IsRevert");
	   this.label_currentCanNum.setText("当前可借阅本数: "+ (10-borrowRecords.size())); 
	   this.label_maxCanNum.setText("最高可借阅本数： "+ this.borrower.getMaxCount());   
	   ShowInfoOfBorrowedBooks();
}
/**
 * 创建借阅信息
 * */
  public ArrayList<BorrowRecord> CreateBorrowRecords(){
	  ArrayList<BorrowRecord> list = new ArrayList<BorrowRecord>();
	  int count = num_ScanedBook;              //获取扫描器扫描到的书本 
	  
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
				boolean revert =  newBook.isBorrowStaus();    //------创建记录的时候，需要将本参数更改为已经借出！！   
				//false--未被借出----BorrowStatus    false----revert----没有归还--->已经借出
		  	  
				newBorrowRecord.setAllProperties(idNo, isbn, bookName, name, revert, startDate, endDate);
				list.add(newBorrowRecord);
				sql.insertBorrowRecord(newBorrowRecord);
//				JOptionPane.showMessageDialog(null, newBorrowRecord.toString());   //测试
		}  
        return list;
  }

/***
 * 模拟书籍扫描终端：模拟完成扫描的信息的录入并显示	
 * @author Zen Johnny
 * @since 2016-12-23
 */
public void ScannedInfoOfBook(){    //模拟扫描产生的 借书信息

	Random random = new Random();   
//	num_ScanedBook;            //被扫描书本的数目（模拟状态下：随机产生）
	int num;
	num = random.nextInt(10); 
//	JOptionPane.showMessageDialog(null, 10+"\n"+this.NumOfborrowedBook);//----Test
	
//	JOptionPane.showMessageDialog(null, "未还书本数目："+this.NumOfborrowedBook);//Test
	
    while(num>(10-this.NumOfborrowedBook)||num<=0){
    	num = random.nextInt(10);   
    }
    //this.NumOfborrowedBook   未还书数目
    //num产生的是一个小于可借最大书数目减去读者未还书数目的差的随机值；并不是扫描到的书本的随机值！！！ 
    num_ScanedBook = num - this.NumOfborrowedBook;	  //此处可能产生负值  是正常的
    
//    JOptionPane.showMessageDialog(null,"测试使用------扫描到的书本数："+(num_ScanedBook));//Test
    
    BookScanner bScanner = new BookScanner();
    Book bk;
    ScanneredBooks = bScanner.ScanedBook(num_ScanedBook);
    
//    JOptionPane.showMessageDialog(null,"测试使用------产生的书本数目："+(ScanneredBooks.size()));//产生4本
    
//    JOptionPane.showMessageDialog(null,"测试使用------list:"+(list.isEmpty()==false?"不为空":"空"));  //--------------down 
    
    Iterator<Book> iterator = ScanneredBooks.iterator(); 
    
    DefaultTableModel model = (DefaultTableModel)table.getModel();
    for(int k = 0;k<columns;k++)
   	model.setValueAt((String)headers[k], 0, k);     //表头
    while(iterator.hasNext()){
 
      bk = iterator.next();
          
//      JOptionPane.showMessageDialog(null,"public void initInfoOfBook(\n"+bk.toString());//--------Test
      
      for(int i = 0 ;i< num_ScanedBook ;i++){   //+1是为了保留表头栏 
        
    	model.setValueAt(NumOfborrowedBook+1+i, NumOfborrowedBook+1+i, 0);
        model.setValueAt(bk.getBookName(), NumOfborrowedBook+1+i, 1);
        
        model.setValueAt(startDate, NumOfborrowedBook+1+i, 2);
        model.setValueAt(endDate, NumOfborrowedBook+1+i, 3);
        model.setValueAt(bk.isBorrowStaus()==false?"可借阅":"尚未归还", NumOfborrowedBook+1+i, 4);
        model.setValueAt("----",NumOfborrowedBook+1+i, 5);
      }
    
    }
}
/**
 * 完成Table的信息显示
 * 
 * */
public void ShowInfoOfPanel(){

    ShowInfoOfBorrowedBooks();   //显示已经借阅的书籍信息
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

	
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸 
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(screenSize.width/3,screenSize.height/4, 583, 461);  
	this.setIconImage(eSys.getIconOfSystem().getImage());  //设置系统logo
//    setResizable(false); 
    setTitle("\u56FE\u4E66\u7BA1\u7406\u4E2D\u5FC3");    
    getContentPane().setLayout(null);
    
    /*面板----主显示标签*/
    label_main = new JLabel("\u501F  \u9605  \u7BA1  \u7406");       //借阅-label
    label_main.setFont(new Font("黑体", Font.BOLD, 17));
    label_main.setBounds(204, 10, 141, 32);
    getContentPane().add(label_main);
    
    /*表格初始化*/
    Object[][] cellData = new Object[rows][columns];

    
    DefaultTableModel model = new DefaultTableModel(cellData, headers);
    
    table = new JTable(model);
//    table.addFocusListener(new FocusAdapter() {
//    	@Override
//    	public void focusGained(FocusEvent e) {
////    		JOptionPane.showMessageDialog(null, "你好");
//    	}
//    });
    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    table.setForeground(Color.BLACK);
    table.setShowGrid(true);
    table.setPreferredScrollableViewportSize(getSize());   
    table.setBounds(20, 125, 549, 225); 
    for(int k = 0;k<columns;k++)
    	 model.setValueAt((String)headers[k], 0, k);     //表头
    for(int i = 1;i< rows;i++){    
        for(int j=0;j<columns;j++){ 
        	model.setValueAt((String)"",i,j);          
        }
    }
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  //设置不可更改容器组件尺寸
    table.setRowHeight(20);                           //设置行高
    int widthAll =table.getWidth();
    table.getColumnModel().getColumn(0).setPreferredWidth((int)(widthAll * 0.08));    //获取表格的列
    table.getColumnModel().getColumn(1).setPreferredWidth((int)(widthAll*0.2));
    table.getColumnModel().getColumn(2).setPreferredWidth((int)(widthAll*0.123));    //获取表格的列 
    table.getColumnModel().getColumn(3).setPreferredWidth((int)(widthAll*0.123));    //获取表格的列
    table.getColumnModel().getColumn(4).setPreferredWidth((int)(widthAll*0.123));
    table.getColumnModel().getColumn(5).setPreferredWidth((int)(widthAll*0.04));
    table.getColumnModel().getColumn(6).setPreferredWidth((int)(widthAll*0.24));
    getContentPane().add(table);
      
    /*返回按钮*/ 
    btn_return = new JButton("\u8FD4\u56DE\u7CFB\u7EDF");
    btn_return.setBounds(311, 385, 93, 23);
    getContentPane().add(btn_return);
      
      btn_exit = new JButton("\u9000\u51FA\u7CFB\u7EDF");
      btn_exit.setBounds(438, 385, 93, 23);
      getContentPane().add(btn_exit);
      
      label_currentTime = new JLabel("当前时间：");
      label_currentTime.setBounds(381, 86, 68, 29);
      getContentPane().add(label_currentTime);
      
      label_Text_currentTime = new JLabel("1970-1-1");
      label_Text_currentTime.setFont(new Font("宋体", Font.PLAIN, 14));
      label_Text_currentTime.setBounds(444, 90, 125, 25);
      getContentPane().add(label_Text_currentTime);
      
      label_userNo = new JLabel("用户：");
      label_userNo.setBounds(10, 92, 61, 23);
      getContentPane().add(label_userNo);
      
      label_Text_userNo = new JLabel();
      label_Text_userNo.setFont(new Font("宋体", Font.PLAIN, 14));
      label_Text_userNo.setBounds(54, 86, 136, 29);
      getContentPane().add(label_Text_userNo);
      
      btn_ReturnBook = new JButton("\u5F52\u8FD8\u56FE\u4E66");
      btn_ReturnBook.setBounds(51, 385, 93, 23);
      getContentPane().add(btn_ReturnBook);
      
      btn_borrowBook = new JButton("\u501F\u9605\u56FE\u4E66");
      btn_borrowBook.setBounds(182, 385, 93, 23);
      getContentPane().add(btn_borrowBook);
      
      label_currentCanNum = new JLabel("当前可借阅本数：");
      label_currentCanNum.setBounds(10, 360, 149, 15);
      getContentPane().add(label_currentCanNum);
      
      label_maxCanNum = new JLabel("最高可借本数：");
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

//JTableHeader tableH = table.getTableHeader();     //设置表头
//tableH.setBackground(new Color(100, 0, 200)); 
//tableH.setForeground(new Color(0, 0, 0));
//DefaultTableModel tableModel = (DefaultTableModel) table.getModel();    //添加行数
//tableModel.addRow(new Object[]{"sitinspring", "35", "Boss"});
//table.invalidate();                                        //更新表格
//table.getTableHeader().setVisible(false);                   //隐藏表头
//tableScrollPane = new JScrollPane();
//tableScrollPane.setViewportView(table);   
//getContentPane().add(tableScrollPane); 
//JScrollPane scroll = new JScrollPane(table);
//scroll.getViewport().setBackground(new Color(255, 255, 255)); 
//getContentPane().add(scroll);