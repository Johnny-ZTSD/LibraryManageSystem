package com.Service;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import com.Dao.SQL;
import com.Entity.Book;
import com.Entity.BorrowRecord;
import com.Entity.User;

public class Logic {
	private static SQL sql;
	public Logic(){
		sql = new SQL();
	}
	/***
	 * |检阅该书是否已经被借
	 * @param isbn
	 * @return true:被借
	 */
	public static boolean IsBorrowed(String isbn){
		if(Format.IsIsBn(isbn)){
			ArrayList<Book> booksList = new ArrayList<Book>();
			booksList = SQL.searchBook(isbn, "isbn");
			Iterator<Book> iterator = booksList.iterator();
			Book book = null;
			while(iterator.hasNext()){
				book = new Book();
				book = iterator.next();
				JOptionPane.showMessageDialog(null, book.toString());   //test
				if(book.getBorrower().equals("000000000000000001")){   //系统账号为000000000000000001   
					return true;
				}
			}
		}
		else
			JOptionPane.showMessageDialog(null, "非ISBN编码！");
		
		return true;
	}
	
	public static User getUser(String no,String pasd){
		   ArrayList<User>	li = SQL.searchUser(no,"idNo");
		   int k = 0;
		   int type = 0;                               //用户类型标志
		   User user = null;
		   Iterator<User> iterator = li.iterator();  
		   if(iterator.hasNext()) {
			   user = iterator.next();                //取第一个账号对象
		       k++;
		   }
		   if(k==1)
		       return user;
		   else return null;
	} 
	
    public static int IsExistAccount(String no,String pasd){
       System.out.println("账号："+no+"\t密码："+pasd);	
	   
       ArrayList<User>	li = SQL.searchUser(no,"idNo");
	   int k = 0;
	   int type = 0;                               //用户类型标志
	   User user = null;
	   Iterator<User> iterator = li.iterator();  
	   if(iterator.hasNext()) {
		   user = iterator.next();                //取第一个账号对象
	       k++;
	   }
	   if(k==1){                                 //假如账号存在
		   if(user.getPassword().equals(pasd)){
	       
			   type += 10;                             //如果密码正确：type+10   else type == 0 
	       }
	   }
	   
	   while(iterator.hasNext()){
		   iterator.next();
		   k++;                                         //计数
	   }
	   
	   if(user.getRoleType().equals("管理员") && k!=0) 
		    type += 2;                               //管理员 type+2  else user+1
	   else if(!user.getRoleType().equals("管理员") && k!=0)
		    type += 1;
	   else 
		    type += 0;                                //账号不存在,没有在系统注册
    	return type;
	};
}
