package com.Entity;

import java.util.Calendar;

/**
 * 借书记录
 * 每借书一次就新产生一个记录
 * @author Zen Johnny
 *
 */
public class BorrowRecord {
   private String idNo;
   private String isbn;         //ISBN号 
   private String bookName;     //书名
   private String name;          //借阅者名字
   private boolean revert;       //是否归还（默认：false 没有归还--0）
   private String startDate; //借阅时间
   private String endDate;   //归还时间

 
   /**
    * 构造
    * */
   public BorrowRecord(String idNo,String isbn, String bookName, String name, boolean revert, String startDate, String endDate) {
		super();
		this.idNo = idNo;
		this.isbn = isbn;
		this.bookName = bookName;
		this.name = name;
		this.revert = revert;
		this.startDate = startDate;
		this.endDate = endDate;
	}


public BorrowRecord() {
	super();
}
   /**
    * other methods
    */
public void clearAllproperties() {
	
	this.idNo = null;
	this.isbn = null;
	this.bookName = null;
	this.name = null;
	this.revert = false;
	this.startDate = null;
	this.endDate = null;
}

public void setAllProperties(String idNo,String isbn, String bookName, String name, boolean revert, String startDate, String endDate) {
	this.idNo = idNo;
	this.isbn = isbn;
	this.bookName = bookName;
	this.name = name;
	this.revert = revert;
	this.startDate = startDate;
	this.endDate = endDate;
}

@Override
public String toString() {
	return "BorrowRecord :"+ 
           "\n[idNo=" + idNo + 
		   "\n isbn=" + isbn + 
		   "\n bookName=" + bookName + 
		   "\n name=" + name + 
		   "\n revert="+ revert + 
		   "\n startDate=" + startDate + 
		   "\n endDate=" + endDate + "]";
}

public String getIsbn() {
	return isbn;
}


public String getIdNo() {
	return idNo;
}


public void setIdNo(String idNo) {
	this.idNo = idNo;
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


public String getName() {
	return name;
}


public void setName(String name) {
	this.name = name;
}


public boolean isRevert() {
	return revert;
}


public void setRevert(boolean revert) {
	this.revert = revert;
}


public String getStartDate() {
	return startDate;
}


public void setStartDate(String startDate) {
	this.startDate = startDate;
}


public String getEndDate() {
	return endDate;
}


public void setEndDate(String endDate) {
	this.endDate = endDate;
}

}
