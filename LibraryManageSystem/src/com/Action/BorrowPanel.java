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
import com.Service.Operation;

import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;
import java.awt.event.ActionEvent;


/***
 * 借阅者借阅面板
 * @author Zen Johnny
 * @since 2016-12-22
 */
public class BorrowPanel extends JFrame {   //implements TableCellRenderer

      private JTable table;                                    //表格容器
      private JButton btn_sure;                                //确定借阅按钮
      private JButton btn_cancel;                              //取消借阅按钮
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
	  private ArrayList<BorrowRecord> borrowRecords = new ArrayList<BorrowRecord>();  //借阅记录
	  private ArrayList<Book> ScanneredBooks = new ArrayList<Book>(); //扫描到的书本    
	  private int NumOfborrowedBook = 0;                       //已经借阅且目前未归还的书籍数目
	  private int maxNumOfBooks = 0;                            //最高借阅书数
	  private SQL sql = new SQL();
	  private String startDate;
	  private String endDate;
	  private int num_clikedSure = 0;           //记录点击确认借阅按钮次数  防止多次记录到数据库
	  private 	int num_ScanedBook = 0;         //扫描到的书本数目,此处可能产生负值  仍属于正常情况
	  
	  private String[] headers = { "序号", "书名", "借阅日期","应还日期","借阅状态","备注" };
	  private int rows = 11; 
	  private int  columns = headers.length;             //序号     书名  借阅日期     应还日期      
	  
/**
 * 构造方法
 * @author Zen Johnny
 * @since 2016-12-22
 */
public BorrowPanel() {
    InitPanel();
    panel_ActionLisenner();
}
public BorrowPanel(String idNo) {
    this();
    borrower = sql.getBorrower(idNo);
    
    borrowRecords = sql.searchBorrowRecords(borrower.getIdNo(), "idNo","0","IsRevert");
    
    NumOfborrowedBook = this.borrowRecords.size();
    
    maxNumOfBooks = borrower.getMaxCount();
    
    ShowInfoOfPanel();
}
public BorrowPanel(Borrower borrower){
	this();
	borrowRecords = sql.searchBorrowRecords(borrower.getIdNo(), "idNo","0", "IsRevert");
	maxNumOfBooks = borrower.getMaxCount();
	ShowInfoOfPanel();
}
/********************************其他方法*****************/

public void panel_ActionLisenner(){
	
    btn_sure.addActionListener(new ActionListener() {      //确认借阅按钮
    	
    	public void actionPerformed(ActionEvent e) {
    		num_clikedSure++;               //记录点击确认借阅按钮次数  防止多次记录到数据库
    		if(num_clikedSure<=1){
    		   	
    			if(	num_ScanedBook>0 )    //扫描到的书本
    			   { 
    				
    			      CreateBorrowRecords(); 		    	    
    				  JOptionPane.showMessageDialog(null,"借阅成功！");	 
    				  RefreshInfoOfBorrowedBooks();
    				  
    				}
    			else 
    				JOptionPane.showMessageDialog(null, "没有扫描到书籍，未能完成借阅.");   //返回为null的时候，不能借阅
    			
    		    
    		}
    		else
    			JOptionPane.showMessageDialog(null,"请勿重复借阅!");	
//    	      Operation.createBorrowRecord(CreateBorrowRecords());   //保存扫描到的书本信息到数据库
    	}
    });
   btn_cancel.addActionListener(new ActionListener() {      //取消借阅按钮
  	    public void actionPerformed(ActionEvent e) {
  	    	dispose();
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
}

public void initDateLabel(){
	startDate = date_Current.get(Calendar.YEAR) +"-"+
            (date_Current.get(Calendar.MONTH)+1)+"-"+
            date_Current.get(Calendar.DAY_OF_MONTH)+"";

    String year = date_Current.get(Calendar.YEAR)+""; 
    int y = 0;
    if((date_Current.get(Calendar.MONTH)+3)>12){
	   y = Integer.parseInt(year)+1;
       year = y+"";
    }
//JOptionPane.showMessageDialog(null, y);

   endDate =  year+"-"+
          (date_Current.get(Calendar.MONTH)+3)%12+"-"+
          date_Current.get(Calendar.DAY_OF_MONTH)+"";
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
	
	int nowCount = borrowRecords.size();//获取借阅者借书（未还）的总数
//	JOptionPane.showMessageDialog(null, "nowCount:"+nowCount);
	this.label_currentCanNum.setText("当前可借阅本数: "+ (borrower.getMaxCount()-nowCount)); 
	sql.UpdateBorrower(String.valueOf(nowCount), "nowCount", borrower.getIdNo());
	//同时更新数据库---借阅者（用户）的借书数目
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
	       model.setValueAt( borrowRecord.getStartDate(), i, 2);
	       model.setValueAt(borrowRecord.getEndDate(), i,3);
	       model.setValueAt( borrowRecord.isRevert()==true?"可借阅":"尚未归还",i, 4);
	       model.setValueAt("----", i,5);
	   
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
	initDateLabel();
    ShowInfoOfBorrowedBooks();   //显示已经借阅的书籍信息
	ScannedInfoOfBook();
	initInfoLabels();
}

public void InitPanel(){
	borrower = new Borrower();
	
	startDate = date_Current.get(Calendar.YEAR) +"-"+
	            (date_Current.get(Calendar.MONTH)+1)+"-"+
	            date_Current.get(Calendar.DAY_OF_MONTH)+"";
    
	String year = date_Current.get(Calendar.YEAR)+""; 
	int y = 0;
	if((date_Current.get(Calendar.MONTH)+3)>12){
		y = Integer.parseInt(year)+1;
	    year = y+"";
	}
//	JOptionPane.showMessageDialog(null, y);
	
    endDate =  year+"-"+
              (date_Current.get(Calendar.MONTH)+3)%12+"-"+
              date_Current.get(Calendar.DAY_OF_MONTH)+"";

	
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸 
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(screenSize.width/3,screenSize.height/4, 575, 408);  
	this.setIconImage(eSys.getIconOfSystem().getImage());  //设置系统logo
    setResizable(false); 
    setTitle("\u56FE\u4E66\u7BA1\u7406\u4E2D\u5FC3");    
    getContentPane().setLayout(null);
    
    /*面板----主显示标签*/
    label_main = new JLabel("借  阅 信 息");       //借阅-label
    label_main.setFont(new Font("黑体", Font.BOLD, 17));
    label_main.setBounds(226, 10, 112, 32);
    getContentPane().add(label_main);
    
    /*表格初始化*/
    Object[][] cellData = new Object[rows][columns];

    DefaultTableModel model = new DefaultTableModel(cellData, headers) {
      public boolean isCellEditable(int row, int column) {
        return false;                           //表格不可编辑
      }
    };
    table = new JTable(model);
    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    table.setForeground(Color.BLACK);
    table.setShowGrid(true);
    table.setPreferredScrollableViewportSize(getSize());   
    table.setBounds(10, 84, 549, 225); 
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
    table.getColumnModel().getColumn(0).setPreferredWidth((int)(widthAll * 0.15));    //获取表格的列
    table.getColumnModel().getColumn(1).setPreferredWidth((int)(widthAll*0.3));
    table.getColumnModel().getColumn(2).setPreferredWidth((int)(widthAll*0.15));    //获取表格的列 
    table.getColumnModel().getColumn(3).setPreferredWidth((int)(widthAll*0.15));    //获取表格的列
    table.getColumnModel().getColumn(4).setPreferredWidth((int)(widthAll*0.125));
    table.getColumnModel().getColumn(5).setPreferredWidth((int)(widthAll*0.125));
    getContentPane().add(table);
      
    /*返回按钮*/ 
    btn_return = new JButton("返回");
    btn_return.setBounds(311, 346, 93, 23);
    getContentPane().add(btn_return);
      
      btn_exit = new JButton("退出");
      btn_exit.setBounds(450, 346, 93, 23);
      getContentPane().add(btn_exit);
      
      label_currentTime = new JLabel("当前时间：");
      label_currentTime.setBounds(383, 45, 68, 29);
      getContentPane().add(label_currentTime);
      
      label_Text_currentTime = new JLabel("1970-1-1");
      label_Text_currentTime.setFont(new Font("宋体", Font.PLAIN, 14));
      label_Text_currentTime.setBounds(444, 47, 125, 25);
      getContentPane().add(label_Text_currentTime);
      
      label_userNo = new JLabel("用户：");
      label_userNo.setBounds(10, 48, 61, 23);
      getContentPane().add(label_userNo);
      
      label_Text_userNo = new JLabel();
      label_Text_userNo.setFont(new Font("宋体", Font.PLAIN, 14));
      label_Text_userNo.setBounds(61, 45, 136, 29);
      getContentPane().add(label_Text_userNo);
      
      btn_cancel = new JButton("取消借阅");
      btn_cancel.setBounds(174, 346, 93, 23);
      getContentPane().add(btn_cancel);
      
      btn_sure = new JButton("确定借阅");
      btn_sure.setBounds(35, 346, 93, 23);
      getContentPane().add(btn_sure);
      
      label_currentCanNum = new JLabel("当前可借阅本数：");
      label_currentCanNum.setBounds(10, 319, 149, 15);
      getContentPane().add(label_currentCanNum);
      
      label_maxCanNum = new JLabel("最高可借本数：");
      label_maxCanNum.setBounds(394, 319, 165, 15);
      getContentPane().add(label_maxCanNum);
}

//  public static void main(String args[]){
//	  BorrowPanel bp = new BorrowPanel("112233445566778899");
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