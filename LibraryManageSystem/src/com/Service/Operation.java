package com.Service;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import com.Dao.SQL;
import com.Entity.Book;
import com.Entity.BorrowRecord;

public class Operation {
	private static SQL sql = new SQL();
	
	public Operation() {
		sql = new SQL();
	}
	
    public static void createBorrowRecord(ArrayList<BorrowRecord> bookList){
    	  
    	   Iterator<BorrowRecord> iterator = bookList.iterator();
//    	   
    	   JOptionPane.showMessageDialog(null, "Operation Test:\n扫描到的书本数目："+bookList.size());  //正常
//    	   JOptionPane.showMessageDialog(null, "Operation Test:\n扫描到的书本数目："+); 
    	   BorrowRecord borrowRecord = new BorrowRecord();
    	   
    	   while(iterator.hasNext()){
    		   
                borrowRecord.clearAllproperties();
                borrowRecord = iterator.next();
                JOptionPane.showMessageDialog(null, "Operation Test:\n"+borrowRecord.toString());
//    		    sql.insertBorrowed(borrowRecord);      //正常
              }   
//    	BorrowRecord []borrowRecord  = new BorrowRecord[bookList.size()];
    	   
//    	for(int i = 0;i<bookList.size();i++){
//    		
//    		 JOptionPane.showMessageDialog(null, "Operation Test:\n"+bookList.get(i).toString());
//    		 sql.insertBorrowed(bookList.get(i));      //正常
//    	}
    	
    }
    
//    public static void main(String args[]){
//    	 ArrayList<BorrowRecord> list = new ArrayList<BorrowRecord>();
//    	 list.add(new BorrowRecord("000000000000000001","654-56-8-42637941","《红楼梦》", "001", true,"2011-05-19","2011-07-19"));
//    	 Operation operation = new Operation();
//    	 operation.createBorrowRecord(list);
//    }
}
