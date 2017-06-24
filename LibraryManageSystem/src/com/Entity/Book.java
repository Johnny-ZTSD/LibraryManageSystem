package com.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 *图书类
 *@author Zen Johnny
 *@since 2016-12-15
 */

public class Book {   //实现图书串行化接口
  
  private String isbn;            //ISBN号   (主键)
  private String bookName;        //书名
  private String press;           //出版社
  private String bookType;        //图书类类别号 (格式要求：GB分类标准)
  private String author;          //作者
  private float  price;           //价格
  private String publishTime;     //出版时间
  private int    borrowGrade;     //藏书借阅等级
  private int    averageDegree;   //图书平均（分数）评级（范围：0-5星）                 -----需要调用图书评论数据
 
  private String borrower;        //当前借阅者  ---------idNo号
  
  private boolean borrowStaus;    //当前借阅状态   (默认:false为未被借出)  ----数据库无此属性，在前台判断
  private String enLibTime;       //入馆时间
  private String location;        //馆藏具体地址位置    
  private String bookSummary;     //内容摘要
  
  public final int MAXMONTHs = 3;  //最大借阅月数
  
  private Collection<BookComment> bookComments;  //本图书评论        集合   
  private ArrayList<BorrowRecord> borrowRecords;       //本图书借书记录 集合   
  /**
   * 所有属性-构造（初始化）
   * @since 2016-12-24
   */
  public Book(String isbn, String bookName, String press, String bookType, 
		      String author, float price,String publishTime, int borrowGrade,
		      int averageDegree, String borrower,boolean borrowStaus, 
		      String enLibTime, String location, String bookSummary) {
		super();
		this.isbn = isbn;
		this.bookName = bookName;
		this.press = press;
		this.bookType = bookType;
		this.author = author;
		this.price = price;
		this.publishTime = publishTime;
		this.borrowGrade = borrowGrade;
		this.averageDegree = averageDegree; 
		this.borrower = borrower;
		this.borrowStaus = borrowStaus;
		this.enLibTime = enLibTime;
		this.location = location;
		this.bookSummary = bookSummary;
	}

  public Book() {
	    super();
		this.isbn = null;
		this.bookName = null;
		this.press = null;
		this.bookType = null;
		this.author = null;
		this.price = 0;
		this.publishTime = null;
		this.borrowGrade = 0;
		this.averageDegree = 0;
		this.borrower = null;
		this.borrowStaus = false;
		this.enLibTime = null;
		this.location = null;
		this.bookSummary = null;
  }
  /***
   * 其他方法
   * @author Zen Johnny
   * @since 2016-12-23
   */
  public void clearProperties(){
	  this.author = null;
	  this.averageDegree = 0;
	  this.bookComments = null;
	  this.bookName =null;
	  this.bookSummary =null;
	  this.bookType =null;
	  this.borrower = null;
	  this.borrowGrade = 0;
	  this.borrowRecords = null;
	  this.borrowStaus = false;  //false未被借出
	  this.enLibTime = null;
	  this.isbn = null;
	  this.location =null;
	  this.press =null;
	  this.price = 0;
	  this.publishTime = null;
  }
  public void setAllProperties(String isbn, String bookName, String press, String bookType, 
	      String author, float price,String publishTime, int borrowGrade,
	      int averageDegree, String borrower,boolean borrowStaus, 
	      String enLibTime, String location, String bookSummary) {
	this.isbn = isbn;
	this.bookName = bookName;
	this.press = press;
	this.bookType = bookType;
	this.author = author;
	this.price = price;
	this.publishTime = publishTime;
	this.borrowGrade = borrowGrade;
	this.averageDegree = averageDegree; 
//	this.endDate = endDate;
//	this.startDate = startDate;
	this.borrower = borrower;
	this.borrowStaus = borrowStaus;
	this.enLibTime = enLibTime;
	this.location = location;
	this.bookSummary = bookSummary;
  }
  /**
   * 方法
   */
  
public String getIsbn() {
	return isbn;
}

public void setIsbn(String isbn) {
	this.isbn = isbn;
}

public String getBookName() {
	return bookName;
}

public void setBookName(String bookName) {
	this.bookName = bookName;
}

public String getPress() {
	return press;
}

public void setPress(String press) {
	this.press = press;
}

public String getBookType() {
	return bookType;
}

public void setBookType(String bookType) {
	this.bookType = bookType;
}

public String getAuthor() {
	return author;
}

public void setAuthor(String author) {
	this.author = author;
}

public float getPrice() {
	return price;
}

public void setPrice(float price) {
	this.price = price;
}

public String getPublishTime() {
	return publishTime;
}

public void setPublishTime(String publishTime) {
	this.publishTime = publishTime;
}

public int getBorrowGrade() {
	return borrowGrade;
}

public void setBorrowGrade(int borrowGrade) {
	this.borrowGrade = borrowGrade;
}

public int getAverageDegree() {
	return averageDegree;
}
/**
 * 设置图书平均评级 
 * @param int [] bookDegrees
 * @return averageDegree
 */
public void setAverageDegree(int bookDegrees[]) {
	int i = 0;
	while(i<=bookDegrees.length) 
		this.averageDegree += bookDegrees[i];
	this.averageDegree /= bookDegrees.length;
}

public void setAverageDegree(int averageDegree) {
	this.averageDegree = averageDegree;
}

//public String getEndDate() {
//	return endDate;
//}
//
//public void setEndDate(String endDate) {
//	this.endDate = endDate;
//}

//public String getStartDate() {
//	return startDate;
//}
//
//public void setStartDate(String startDate) {
//	this.startDate = startDate;
//}

public String getBorrower() {
	return borrower;
}

public void setBorrower(String borrower) {
	this.borrower = borrower;
}

public boolean isBorrowStaus() {
	return borrowStaus;
}

public void setBorrowStaus(boolean borrowStaus) {
	this.borrowStaus = borrowStaus;
}

public String getEnLibTime() {
	return enLibTime;
}

public void setEnLibTime(String enLibTime) {
	this.enLibTime = enLibTime;
}

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public String getBookSummary() {
	return bookSummary;
}

public void setBookSummary(String bookSummary) {
	this.bookSummary = bookSummary;
}
 

/** 
 * @pdGenerated default getter 
 * */
public java.util.Collection<BookComment> getBookComments() {
   if (bookComments == null)
	   bookComments = new java.util.HashSet<BookComment>();
   return bookComments;
}
@Override
public String toString() {
	return "Book :"
			+ "\n[isbn=" + isbn + 
			"\n bookName=" + bookName + 
			"\n press=" + press + 
			"\n bookType=" + bookType + 
			"\n author="+ author +
			"\n price=" + price + 
			"\n publishTime=" + publishTime + 
			"\n borrowGrade=" + borrowGrade+ 
			"\n averageDegree=" + averageDegree + 
//			"\n endDate=" + endDate + 
//			"\n startDate=" + startDate + 
			"\n borrower="+ borrower + 
			"\n borrowStaus=" + borrowStaus + 
			"\n enLibTime=" + enLibTime + 
			"\n location=" + location+ 
			"\n bookSummary=" + bookSummary + 
			"\n maxDays=" + MAXMONTHs + 
			"\n bookComments=" + bookComments+
			"\n borrowRecords=" + borrowRecords + "]";
}


//public static void main(String args[]){
//	String isbn = "978-7-302-09834-8";
//	String bookName = "《西游记》";
//	String press="华夏文艺出版社";
//	String bookType = "魔幻小说";
//	String author = "罗贯中";
//	float price = 78.9f;
//	String publishTime = "1999-12-31";
//	int borrowGrade = 1;
//	int averageDegree = 1;
//	
//	String startDate = "2016-12-3";
//    
//	String oldMonth = startDate.substring(5, 6);
//	String endDate = "2017-2-3";
//    String borrower = "王琛";
//	boolean borrowStaus = false;
//	String enLibTime = "2010-12-2";
//	String location = "新馆-三楼-A区-11排-4层";
//	String bookSummary = "无";
//			
//	Book newbk = new Book(isbn, bookName, press, bookType, author, price, 
//			              publishTime, borrowGrade, averageDegree, borrower, 
//			              borrowStaus, enLibTime,location, bookSummary);
//     System.out.println(newbk.toString());
//}


/** 
 * @pdGenerated default getter 
 * */
public ArrayList<BorrowRecord> getBorrowRecords() {
	   if (borrowRecords == null)
		   borrowRecords = new ArrayList<BorrowRecord>();
	   return borrowRecords;
}
/** 
 * @pdGenerated default iterator getter 
 * */
public java.util.Iterator getIteratorBookComments() {
   if (bookComments == null)
      bookComments = new java.util.HashSet<BookComment>();
   return bookComments.iterator();
}
/** 
 * @pdGenerated default iterator getter 
 * */
public java.util.Iterator getIteratorBorrowRecords() {
	   if (borrowRecords == null)
		   borrowRecords = new ArrayList<BorrowRecord>();
	   return borrowRecords.iterator();
	}
/** @pdGenerated default setter
  * @param newBookComments
  * */
public void setBookComments(java.util.Collection<BookComment> newBookComments) {
   removeAllBookComments();
   for (java.util.Iterator iter = newBookComments.iterator(); iter.hasNext();)
      addBookComment((BookComment)iter.next());
}
/** @pdGenerated default setter
 * @param newBorrowRecords
 * */
public void setBorrowRecords(java.util.Collection<BorrowRecord> newBorrowRecords) {
	   removeAllBorrowRecords();
	   for (java.util.Iterator iter = newBorrowRecords.iterator(); iter.hasNext();)
	      addBorrowRecord((BorrowRecord)iter.next());
	}
/** @pdGenerated default add
  * @param newBookComment 
  * */
public void addBookComment(BookComment newBookComment) {
   if (newBookComment == null)
      return;
   if (this.bookComments == null)
      this.bookComments = new java.util.HashSet<BookComment>();
   if (!this.bookComments.contains(newBookComment))
      this.bookComments.add(newBookComment);
}
/** @pdGenerated default add
 * @param newBorrowRecord 
 * */
public void addBorrowRecord(BorrowRecord newBorrowRecord) {
	   if (newBorrowRecord == null)
	      return;
	   if (this.borrowRecords == null)
	      this.borrowRecords = new java.util.ArrayList<BorrowRecord>();
	   if (!this.borrowRecords.contains(newBorrowRecord))
	      this.borrowRecords.add(newBorrowRecord);
	}
/** @pdGenerated default remove
  * @param oldBookComment
  * */
public void removeBookComment(BookComment oldBookComment) {
   if (oldBookComment == null)
      return;
   if (this.bookComments != null)
      if (this.bookComments.contains(oldBookComment))
         this.bookComments.remove(oldBookComment);
}
/** @pdGenerated default remove
 * @param oldBorrowRecord
 * */
public void removeBorrowRecord(BorrowRecord oldBorrowRecord) {
	   if (oldBorrowRecord == null)
	      return;
	   if (this.borrowRecords != null)
	      if (this.borrowRecords.contains(oldBorrowRecord))
	         this.borrowRecords.remove(oldBorrowRecord);
	}
/** @pdGenerated default removeAll 
 * */
public void removeAllBookComments() {
   if (bookComments != null)
      bookComments.clear();
}

/** @pdGenerated default removeAll 
 * */
public void removeAllBorrowRecords(){
	if (borrowRecords != null)
		borrowRecords.clear();
}
}


 