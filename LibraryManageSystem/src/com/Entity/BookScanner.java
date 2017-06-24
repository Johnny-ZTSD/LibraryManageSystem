package com.Entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * 书本扫描器 【模拟】
 * @author Zen Johnny
 * @since 2016-12-23
 */

public class BookScanner {
	private static int counts;  
    private static Book[] books;
    private static Borrower borrower;
    private static String isbn= "978-7-302-09834-8";
	private int randomNumber = 5;         //产生isbn号码的随机数部分的长度为5
    
    public BookScanner(){
    	 init();
    } 
    
	public BookScanner(Borrower borrower){
		 this();
		 this.borrower = borrower;
	}
	
    private static void init(){
    	counts = 10;
		books = new Book[counts];
        Book newbk = new Book();
        borrower = new Borrower();     	
    }
	public ArrayList<Book> ScanedBook(int num){
    	ArrayList<Book> list = new ArrayList<Book>();   	
    	for(int i = 0 ;i<num;i++)
    		  list.add(this.getRandomBook());
    	return list;
    }
	
	public static String createISBN(){
	    int length = 5;
		Random random = new Random();
		String isbn = "978-7-03-016786-6";
		String str_random = "";              //10-14位
			for(int i=0;i<=length;i++)
			     str_random += random.nextInt(10);
			String now_isbn = isbn.substring(0, 9) + str_random + isbn.substring(15); 
		return isbn; 
	}
	
	private Book getRandomBook(){
		
		Random random = new Random();
        String str_random = "";              //10-14位
		for(int i=0;i<=randomNumber;i++)
		     str_random += random.nextInt(10);
		String now_isbn = isbn.substring(0, 9) + str_random + isbn.substring(15); 
		
//	    JOptionPane.showMessageDialog(null, "now_isbn:"+now_isbn);   //测试
		
	    String bookName = "《西游记》";
		String press="华夏文艺出版社";
		String bookType = "魔幻小说";
		String author = "罗贯中";
		float price = 78.9f;
		String publishTime = "1999-12-31";
		int borrowGrade = 1;
		int averageDegree = 1;
		
//		String startDate = "2016-12-03";
	    
//		String oldMonth = startDate.substring(5, 6);
//		String endDate = "2017-02-03";
	    String borrower = "0000000000000001";   //借阅者账号
		boolean borrowStaus = false;        //false为未被借出
		String enLibTime = "2010-12-2";
		String location = "新馆-三楼-A区-11排-4层";
		String bookSummary = "无";
       
		Book book = new Book(now_isbn, bookName, press, bookType, author, price, 
				              publishTime, borrowGrade, averageDegree, borrower, borrowStaus, enLibTime, 
				              location, bookSummary); 
//		JOptionPane.showMessageDialog(null, "Test:"+book.toString());   //测试
		
		return book;
	} 

		
//	public static void main(String args[]){
//		BookScanner bScanner = new BookScanner();
////		JOptionPane.showMessageDialog(null,"sss" );
////		JOptionPane.showMessageDialog(null, "Test:"+bScanner.getRandomBook().toString());
////		bScanner.ScanedBook(5);
//	}
}

//System.out.println("rtyujhgfdsxdcv");
//Book newbk = new Book("978-7-302-09834-8","罗贯中",); 
//
////System.out.println(newbk);
////for(int i = 0;i<counts;i++){
////	newbk.clearProperties();
//	newbk.setAuthor("罗贯中");
//	newbk.setAverageDegree(new int[]{1,1,1});
//	newbk.setBookComments(null);
//	newbk.setBookName("《西游记》");
//
//    newbk.setBookType("魔幻小说");
//    newbk.setBorrower("王琛");
//    newbk.setBorrowGrade(1);
//    newbk.setBorrowRecords(null);
//    newbk.setBorrowStaus(false);
//    newbk.setBookSummary("白话文小说");
//    
//
//    
//    Calendar calc = Calendar.getInstance();
//    calc.set(Calendar.YEAR, 1760);
//    calc.set(Calendar.MONTH, 8);
//    calc.set(Calendar.DAY_OF_MONTH, 23);
//    newbk.setEndDate(calc); 
//    
//    newbk.setEnLibTime(Calendar.getInstance());
//    newbk.setIsbn("978-7-302-09834-8");
//    newbk.setLocation("新馆-三楼-A区-11排-4层");
//    newbk.setPress("华夏文艺出版社");
//    newbk.setPublishTime(new Date(1999-12-31));
//    
//    calc.set(Calendar.YEAR, 2012);
//    calc.set(Calendar.MONTH, 9);
//    calc.set(Calendar.DAY_OF_MONTH, 10);
//    newbk.setStartDate(calc);		    
//    newbk.setPrice((float)89.0);
//	System.out.println(newbk.getPrice());
////}
//System.out.println( newbk.toString() );
