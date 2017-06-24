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
	 * |���ĸ����Ƿ��Ѿ�����
	 * @param isbn
	 * @return true:����
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
				if(book.getBorrower().equals("000000000000000001")){   //ϵͳ�˺�Ϊ000000000000000001   
					return true;
				}
			}
		}
		else
			JOptionPane.showMessageDialog(null, "��ISBN���룡");
		
		return true;
	}
	
	public static User getUser(String no,String pasd){
		   ArrayList<User>	li = SQL.searchUser(no,"idNo");
		   int k = 0;
		   int type = 0;                               //�û����ͱ�־
		   User user = null;
		   Iterator<User> iterator = li.iterator();  
		   if(iterator.hasNext()) {
			   user = iterator.next();                //ȡ��һ���˺Ŷ���
		       k++;
		   }
		   if(k==1)
		       return user;
		   else return null;
	} 
	
    public static int IsExistAccount(String no,String pasd){
       System.out.println("�˺ţ�"+no+"\t���룺"+pasd);	
	   
       ArrayList<User>	li = SQL.searchUser(no,"idNo");
	   int k = 0;
	   int type = 0;                               //�û����ͱ�־
	   User user = null;
	   Iterator<User> iterator = li.iterator();  
	   if(iterator.hasNext()) {
		   user = iterator.next();                //ȡ��һ���˺Ŷ���
	       k++;
	   }
	   if(k==1){                                 //�����˺Ŵ���
		   if(user.getPassword().equals(pasd)){
	       
			   type += 10;                             //���������ȷ��type+10   else type == 0 
	       }
	   }
	   
	   while(iterator.hasNext()){
		   iterator.next();
		   k++;                                         //����
	   }
	   
	   if(user.getRoleType().equals("����Ա") && k!=0) 
		    type += 2;                               //����Ա type+2  else user+1
	   else if(!user.getRoleType().equals("����Ա") && k!=0)
		    type += 1;
	   else 
		    type += 0;                                //�˺Ų�����,û����ϵͳע��
    	return type;
	};
}
