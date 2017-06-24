package com.Dao;

import java.awt.List;
import java.io.BufferedWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.management.Query;
import javax.swing.JOptionPane;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.Entity.Book;
import com.Entity.BorrowRecord;
import com.Entity.Borrower;
import com.Entity.Identifier;
import com.Entity.User;
import com.Service.Logic;

public class SQL  {
	
	private static Connection connection = null;
	private static ResultSet rSet = null;
	private static User user = new User();
	private static Book book = new Book();
	private static Borrower borrower = new Borrower();
	private static BorrowRecord borrowRecord = new BorrowRecord();
	
	public SQL(){
		
	}

	public static User getAdmin(String idNo){
		
		User Admin = new User();
		ArrayList<User> list = new ArrayList<User>();
		list = searchUser(idNo, "idNo");
		Iterator<User> iterator = list.iterator();
		while(iterator.hasNext()){
			Admin = new User();
			Admin = iterator.next();
//			JOptionPane.showMessageDialog(null, Admin.toString());  //Test
		}	
//		JOptionPane.showMessageDialog(null, Admin.toString());  //Test
		if(!Admin.getRoleType().equals("����Ա"))
		    Admin.clearProperties();      //������ǹ���Ա---�������
		return Admin; 
	}
	
    public static Borrower getBorrower(String idNo){
    	 Borrower newBorrower;
    	 ArrayList<Borrower> list = searchBorrower(idNo, "idNo",idNo);  
    	 Iterator<Borrower> iterator = list.iterator();
    	 if(iterator.hasNext()){
    		 newBorrower = new Borrower(); 
    		 newBorrower = iterator.next();             //����ж��ͬ�ŵ��ˣ�ֻȡ��һ���ˣ���ֹbug
    	 return newBorrower;
    	 }
    	 else
    		 return null;
    }
    /**
     *  
     * @param value1   Ĭ�ϣ�idNo
     * @param value2   Ĭ�ϣ���ǰ���ĵ��鱾---��������δ�黹�ģ�
     * @param searchType1 Ĭ�ϣ�idNo
     * @param searchType2 Ĭ�ϣ���ǰ���ĵ��鱾---��������δ�黹�ģ�
     * @return unknown
     * @see
     * ResultSet �������ָ���䵱ǰ�����еĹ�ꡣ�������걻���ڵ�һ��֮ǰ��next ����������ƶ�����һ�У�
     * ��Ϊ�÷����� ResultSet ����û����һ��ʱ���� false�����Կ����� while ѭ����ʹ�����������������      	 
     */
    public ArrayList<BorrowRecord> searchBorrowRecords(String value1,String searchType1,String value2,String searchType2){
    	 
    	 BorrowRecord newBorrowRecord = null;
	         
//    	 int kt = 1;
    	 ArrayList<BorrowRecord> list = new ArrayList<BorrowRecord>();
    	 
    	 String query = "select * from [LibSystem].[dbo].[BorrowRecord] ";
         if(searchType1!= null){
        	 query += "where "+searchType1 +" = "+value1;
             if(searchType2!=null)
            	 query += " and "+searchType2 +" = "+value2;
         }
         int k = 0;
         try{
         	
            rSet = getResultSet(query); 
            
        	while(rSet.next()){ 
             
        	newBorrowRecord = new BorrowRecord(); 	
        		
        	 newBorrowRecord.clearAllproperties();
           	 
             String idNo = rSet.getString("idNo");
             
//                JOptionPane.showMessageDialog(null, "idNo:"+idNo+" "+(++k));
        	 
             String name = rSet.getString("name");
        	 
//                JOptionPane.showMessageDialog(null, "\nname:"+name);
             
             String isbn = rSet.getString("isbn");
        	 
//                JOptionPane.showMessageDialog(null, "\nisbn:"+isbn);
             
             String bookName = rSet.getString("bookName");
        	 
//                JOptionPane.showMessageDialog(null, "\nbookName:"+bookName);
             
             boolean revert = rSet.getInt("IsRevert")==0?false:true;   //0-δ�����
        	 
//                JOptionPane.showMessageDialog(null, "\nIsRevert:"+revert);
             
             String startDate = rSet.getString("startDate");
        	 
//                JOptionPane.showMessageDialog(null, "\nstartDate:"+startDate);
             
             String endDate = rSet.getString("endDate");
        	 
//                JOptionPane.showMessageDialog(null, "\nendDate:"+endDate);
             
             newBorrowRecord.setAllProperties(idNo, isbn, bookName,name, revert, startDate, endDate);

//        	 JOptionPane.showMessageDialog(null, newBorrowRecord+"\n"+kt); 
//        	 kt++;
             
        	 list.add(newBorrowRecord);
        	} 
         }catch(SQLException e){
        	 e.printStackTrace();
         }catch(Exception e){
        	 e.printStackTrace();
         }
         
    	 return list;
    }
    
    public void UpdateUserElementInfo(User user){  //�������½����ߵ��û�������Ϣ
    	String query = "update [User] set ";
//    	query += "'"+User.getIdNo()+"',";   //��Ϊ����
    	query += " password =  "+"'"+user.getPassword()+"',";
//    	JOptionPane.showMessageDialog(null, user.getPassword());//test
    	query += " name =  "+"'"+user.getName()+"',";
    	query += " sex =  "+"'"+user.getSex()+"',";
    	query += " roleType =  "+"'"+user.getRoleType()+"',";
    	query += " department =  "+"'"+user.getDepartment()+"',";
    	query += " email =  "+"'"+user.getE_mail()+"',";
    	query += " telephone =  "+"'"+user.getTelephone()+"',";
    	query += " noStatus =  "+""+user.getNoStatus()+" ";         //���һ�� --�ո�
    	
    	query += " where idNo = " + "'" + user.getIdNo()+"'";
    	
    	try{
    		Update(query);
    	}catch(Exception e){
    		JOptionPane.showMessageDialog(null, "���������쳣��");
    	}
    }
    
    //if searchType == null -->��������      
    public static ArrayList<Book> searchBook(String value,String searchType){
         ArrayList<Book> list = new ArrayList<Book>();
         String query = "select * from [LibSystem].[dbo].[Book] ";
         if(searchType!=null)
        	 query += "where "+searchType +" = "+value;  
         try{
        	 
        	 rSet = getResultSet(query);
     		while(rSet.next()){
     			String isbn = rSet.getNString("isbn");
     			String bookName = rSet.getString("bookName");
     			String press  = rSet.getString("press");
     			String bookType = rSet.getString("bookType");
     			String author = rSet.getString("author");
     			float price = rSet.getFloat("price");
     			String publishTime = rSet.getString("publishTime");
     			int borrowGrade= rSet.getInt("borrowGrade");
     			int averageDegree  = rSet.getInt("averageDegree");
//     			String startDate = rSet.getString("startDate");
//     			String endDate = rSet.getString("endDate");
     			String idNo = rSet.getString("borrower");
     			String enLibTime = rSet.getString("endLibTime");
     			String location = rSet.getString("location");
     			String bookSummary = rSet.getString("bookSummary");
     			int MAXMONTHs = rSet.getInt("MAXMONTHs");
     			
     			boolean borrowStaus;
     			if(idNo.equals("000000000000000001"))
     				borrowStaus = false;  //false����δ�����
     			else
     				borrowStaus = true;
     			
     			book.clearProperties();
     			book.setAllProperties(isbn, bookName, press, bookType, author, price, publishTime, borrowGrade, averageDegree, idNo, borrowStaus, enLibTime, location, bookSummary);
     			list.add(book);
     		}
     		    JOptionPane.showMessageDialog(null,"SQL:searchBook:\n"+book.toString());                  //�����������
     		 
     		}catch(SQLException e){
     			e.printStackTrace();
     		}catch(Exception e){
     			JOptionPane.showMessageDialog(null,"SQL:searchUser(String no)�쳣����");
     		}
     		
        	 
         return list;
    }
   
    public static User getUserBySql(String idNo){
    	return user;
    }
       
    //if searchType == null -->��������  
	public static ArrayList<User> searchUser(String value,String searchType){
 		ArrayList<User> list = new ArrayList<User>();
 		String query = "select * from [LibSystem].[dbo].[User] ";
 		if(searchType!=null) 
 			if(!searchType.equals("noStatus"))
 			  query += "where "+searchType +" = '"+value+"'";  
 			else
 				query += " where "+ searchType +" = "+value;  
 		
		try{
		rSet = getResultSet(query);
		while(rSet.next()){
			String idNo = rSet.getString("idNo");                 //֤����/�˺ţ���������ʽ���޶�ΪӢ�ĺ����ģ��淶���˺�/���֤��֤��/�˺ţ����Ժ������������չ��������ѧ����,�������ж�֤���Ƿ���ڵķ�����
			String password = rSet.getString("password");         //�˺�����
			String name = rSet.getString("name");                 //�û�����
		    
			boolean sex = rSet.getString("sex").equals("��")?false:true;       //�Ա�(Ĭ�ϣ��� -1- false ;Ů -��-1��-true)
		    
			String roleType = rSet.getString("roleType");         //��ɫ���� ����ʦ��ѧ�����о����ͱ�������������Ա��
		    String department = rSet.getString("department");     //������λ/����/רҵ/��ϵ��ifѧ����רҵ/ϵ��if��ʦ��ѧԺ/�꼶or��ϵor�칫��;��
		    
//		    String idCard = rSet.getString("idCard");             //���֤�� ��Ĭ��Ϊ��֤����һ�£�	
		    String idCard = idNo;
		    
		    String e_mail = rSet.getString("email");              //��������
		    
		    String telephone= rSet.getString("telephone");         //�绰
		    int noStatus = rSet.getInt("noStatus");                //�˺�ע��״̬  ��Ĭ�ϣ�һ����������1��ע�������-1�� 
			
//		    User newuser = new User(idNo,password,name,sex,roleType,department,idCard,e_mail,telephone,noStatus);
		    user.clearProperties();
		    user.setIdNo(idNo);
		    user.setPassword(password);
		    user.setName(name);
		    user.setSex(sex);
		    user.setRoleType(roleType);
		    user.setDepartment(department);
		    user.setIdCard(idCard);
		    user.setE_mail(e_mail);
		    user.setTelephone(telephone);
		    user.setNoStatus(noStatus);
		    list.add(user);
		    System.out.println("public static ArrayList<User> searchUser:\n"+user.toString()); 
		}
		 System.out.println("public static ArrayList<User> searchUser:\n"+user.toString());                  //�����������
		 
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("public static ArrayList<User> searchUser(String no)�쳣����");
		}
		
		return  list;
	}
    
	//���һ��������idNo;
	public static ArrayList<Borrower> searchBorrower(String value,String searchType,String No){
		ArrayList<Borrower> list = new ArrayList<Borrower>();
 		String query = "select * from [LibSystem].[dbo].[Borrower] ";
 		
 		String query2 = "select * from [LibSystem].[dbo].[User] where idNo = ";
 		
 		ResultSet rSet2 = null;
 		
 		Borrower borrower2 = new Borrower();
 		
 		if(searchType!= null){
 			if(searchType.equals("idNo")||searchType.endsWith("name")||searchType.endsWith("majorIn"))
 		     	query += "where "+searchType +" = '"+value+"'"; 
 			else 
 				query += "where "+searchType + " = " + value; 
 		}
 	    if(No.equals(null))
 		    JOptionPane.showMessageDialog(null, "SearchBorrower:δ��дidNoֵ��");
 		else
 		    query2 += "'"+No+"'";
 		
		try{
		
			rSet = getResultSet(query);
		    rSet2 = getResultSet(query2);
			
			while(rSet.next()&&rSet2.next()){
			   
			   borrower.clearAllProperties();	
			  
			   String idNo = rSet.getString("idNo");                 //֤����/�˺ţ���������ʽ���޶�ΪӢ�ĺ����ģ��淶���˺�/���֤��֤��/�˺ţ����Ժ������������չ��������ѧ����,�������ж�֤���Ƿ���ڵķ�����
			         
//			       JOptionPane.showMessageDialog(null, "idNo:"+idNo);//----����
			   
			   String name = rSet.getString("name");                 //�û�����
		    
			   int borrowGrade = rSet.getInt("borrowGrade");         //���ĵȼ�
    
			   float fine = rSet.getFloat("fine");                    //����
	        
			   String majorIn = rSet.getString("majorIn");            //רҵ
	        
			   int allCount = rSet.getInt("allCount");                 //�����鱾����
	        
			   int maxCount = rSet.getInt("maxCount");                 //�����鱾�����Ŀ
	        
			   int nowCount = rSet.getInt("nowCount");                   //�����鱾��ǰ��
			        
//			       JOptionPane.showMessageDialog(null, "nowCount:"+nowCount);
			   
			   float deposit = rSet.getFloat("deposit");                 //Ѻ��
			   
//----------------------------------------------------------------------------------
			   String password = rSet2.getString("password");
			   
			   boolean sex = rSet2.getString("sex").equals("��")?false:true;
			   
			   String roleType = rSet2.getString("roleType");
			   
			   String department = rSet2.getString("department");
			   
			   String email = rSet2.getString("email");
			   
			   String telephone = rSet2.getString("telephone");
			   
			   int noStatus = rSet2.getInt("noStatus");
			   
//--------------------------------------------------------------------------------------			   
			   borrower.setAllProperties(idNo,name,borrowGrade, fine, majorIn, allCount, maxCount, nowCount, deposit,
					                     password,sex,roleType,department,email,telephone,noStatus);
			   System.out.println("SQL: searchBorrower:\n"+borrower.toString()); 
			   list.add(borrower);
		}
		 System.out.println("public static ArrayList<User> searchUser:\n"+user.toString());                  //�����������
		 
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("public static ArrayList<User> searchUser(String no)�쳣����");
		}
		
		return  list;      
	}
	
	public static List searchBook(){
		return new List();
	}
	
	public void UpdateBorrower(String value,String updateType,String idNo){
		String query = "update [Borrower] set ";
		if(value!=null&&updateType!=null&&idNo!=null){
			//�Ը��µ��������������ж� 
			if(updateType.equals("borrowGrade")||updateType.equals("fine")||updateType.equals("allCount")||updateType.equals("maxCount")||updateType.equals("nowCount")||updateType.equals("deposit"))
			   query += updateType+" = "+value;
		    else
			   query += updateType+" = '"+value+"'";
		   
		    query += "where idNo = '"+ idNo+"'";
		    
		    Update(query);
		}
		else JOptionPane.showMessageDialog(null, "UpdateBorrower:��Ϣ����������");
	}
	
    public boolean insertBorrowRecord(BorrowRecord borrowRecord){
    	String query = "insert into [LibSystem].[dbo].[BorrowRecord] values(";
    	
    	query += "'"+borrowRecord.getIdNo()+"',";
    	query += "'"+borrowRecord.getName()+"',";
    	query += "'"+borrowRecord.getIsbn()+"',";
    	query += "'"+borrowRecord.getBookName()+"',";
    	query += (borrowRecord.isRevert()==true?1:0)+",";  //0---û�л�--false
    	query += "'"+borrowRecord.getStartDate()+"',";
    	query += "'"+borrowRecord.getEndDate()+"');";
    	boolean FLAG = true;;
    	try{	
        
    		Insert(query);   	
        
    	}catch(Exception e){
    	   JOptionPane.showMessageDialog(null, "SQL:insertBorrowed Exception!!!");
           FLAG = false;
        }
    
        return FLAG;
    }   
	
    /**
     * idNo �� isbn ����ֻ��һ�� Ҳ��������
     * @param value
     * @param updateType
     * @param idNo
     * @param isbn
     */
    public static void UpdateBorrowRecord(String value,String updateType,String idNo,String isbn){
    	String query = "update [BorrowRecord] set ";
    	try{
    		
    	     if(value!=null&&updateType!=null){
    	    	 
    	    	 if(updateType.equals("IsRevert"))   //�Ը��µ��������������ж�
    	    		 query += updateType+" = "+value+" where ";
    	    	 else
    	    	     query += updateType +" = '"+ value +"' where ";
    	    	 
    	    	 if(isbn!=null&& idNo!=null)
    	    		 query +=  "idNo = '" + idNo+"' and isbn = '" + isbn+"'";
    	    	 else{
    	    		 if(idNo!=null)
    	    			 query +=  "idNo = '" + idNo+"'";
    	    		 else
    	    			 query +=  "isbn = '" + isbn+"'"; 
    	    	 }
    		      
    	    	 Update(query);
    	     }
    	     else 
    		      JOptionPane.showMessageDialog(null, "UpdateBorrowRecord:�����쳣����");
    	
    	}catch(Exception e){
    	    JOptionPane.showMessageDialog(null, "SQL:insertBorrowed Exception!!!");
        }
    }
    
    private static void Update(String str_SQL){
    	try{
			Statement statement = getStatement(null);   //�������ݿ����
			PreparedStatement prestmt = connection.prepareStatement(str_SQL);
			prestmt.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception w){
			w.printStackTrace();
		}  
    }
    
    public static void Update(String value,String updateType,String tableName,String str_keyValue,String str_key){
    	String query = "update ["+tableName +"] set "+updateType +" = ";
    	int flag = 0;  //�ж�updatType�Ƿ�Ϊ���ַ��ͱ�ͷ
    	if(updateType.equals("price")||updateType.equals("borrowGrade")||updateType.equals("averageDegree")||updateType.equals("MAXMONTHs")||updateType.equals("price"))
    	     	flag = -1;   //Book��
    	if(updateType.equals("name")||updateType.equals("idNo")||updateType.equals("majorIn"))
    	        flag = 0;    
    	else    flag = -1;     //Borrower��
    	if(updateType.equals("IsRevert"))
    	        flag = -1;     //BorrowRecord��
    	if(updateType.equals("noStatus"))
    		    flag = -1;    //User��
    	
    	if(flag==0)
    		query += value;          //���ַ��ͱ�ͷ
    	else
    		query +="'" + value + "'";   //�ַ��ͱ�ͷ
    	query +=" where "+str_key+" = '"+str_keyValue+"'";   //���ϸ�������
//    	JOptionPane.showMessageDialog(null, query);    //test
    	try{
			Statement statement = getStatement(null);   //�������ݿ����
			PreparedStatement prestmt = connection.prepareStatement(query);
			prestmt.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception w){
			w.printStackTrace();
		}  
    } 
    
    public static Statement getStatement(String databeseName){
		Statement statement = null;
		
		/*���ض˿ڣ���ѯSQL Serverʵ�������PID----CMD��netstat -ano*/
		String usernameSQL = "sa";
		String passwordSQL = "123456";
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //���ݿ�����
		String connectDB = "jdbc:sqlserver://localhost:1433;DatabaseName =";      //����Դ
	    
		try{
			/*���ز�ע��SQL Server��JDBC����*/
			Class.forName(JDriver);
		}catch(ClassNotFoundException e){
			System.out.println("�������ݿ�����ʧ��");
			e.printStackTrace();
		}
		try{
	       /*���������ݿ��URL*/
		   if(databeseName==null){
				connection = (Connection) DriverManager.getConnection(connectDB+"LibSystem",usernameSQL,passwordSQL);
		   }
		   else{
			   connection = DriverManager.getConnection(connectDB+databeseName,usernameSQL,passwordSQL);
		       connection.setAutoCommit(false);  //?
		       statement = connection.createStatement();    //���ӳɹ�������SQL���
		   }
		}catch(SQLException e){
			   System.out.println("�������ݿ�����ִ���쳣��");
			   e.printStackTrace();
		}
		
		return   statement;
	}
	
    public static void InsertBorrowRecord(BorrowRecord borrowRecord){
    	String query = "insert into [BorrowRecord] values(";
    	query += "'"+borrowRecord.getIdNo()+"',";
    	query += "'"+borrowRecord.getName()+"',";
    	query += "'"+borrowRecord.getIsbn()+"',";
    	query += "'"+borrowRecord.getBookName()+"',";
    	query += ""+ (borrowRecord.isRevert()==false?0:1)+",";
    	query += "'" + borrowRecord.getStartDate()+"',";
    	query += "'" + borrowRecord.getEndDate()+ "')";
  
    	JOptionPane.showMessageDialog(null, query);
        
    	Insert(query);
    }
    
	private static void Insert(String str_SQL){
		try{
			Statement statement = getStatement(null);   //�������ݿ����
			PreparedStatement prestmt = connection.prepareStatement(str_SQL);
			prestmt.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception w){
			w.printStackTrace();
		}
		
	}
	
	private static ResultSet getResultSet(String str_SQL){
		ResultSet rSet = null;
		
		try{
		Statement statement = getStatement(null);
		PreparedStatement prestmt = connection.prepareStatement(str_SQL) ; 
		rSet = prestmt.executeQuery();
		}
		catch (Exception e) {
			System.out.println("SQL��ȡ������쳣��");		}
		return rSet;
	}
	
//	public static void main(String args[]){
//		SQL sql = new SQL();
////         sql.UpdateBorrower("����", "name", "513723199608111996"); 
//         sql.getAdmin("123451234512345123");
//		sql.UpdateUserElementInfo(new User("112233445566778899","654321","����",false,"ѧ��","����ó��","112233445566778899","johnny@sina.com","1898989998",1)); 
//		sql.UpdateBorrowRecord("0", "IsRevert", "112233445566778899","879-6-524789-87-2");
		
		
//		sql.Insert("insert [LibSystem].[dbo].[BookRecord] values('513723199608111996','��̫','12345678901234567','��wawawa��',1,'2016-10-03','2016-10-23');");
		
//		BorrowRecord bRecord = new BorrowRecord();
//		bRecord.setAllProperties("000000000000000001","654-56-8-42637941","����¥�Ρ�", "001", true,"2011-05-19","2011-07-19");
//		System.out.println(bRecord.toString());
//		sql.insertBorrowRecord(bRecord);
//		
//	      BorrowRecord newBorrowRecord = new BorrowRecord();
//        ArrayList<BorrowRecord>list = sql.searchBorrowRecords("513723199608111996", "idNo", null, null);
//        Iterator<BorrowRecord> iterator = list.iterator();
//        
//        while(iterator.hasNext()){
//       	  newBorrowRecord = iterator.next();
//       	  JOptionPane.showMessageDialog(null,newBorrowRecord.toString());
//        } 
		
//	    sql.getStatement(null);
//	    sql.searchUser("513723199608111996","idNo");   //��ʽҪ��
//		sql.getBorrower("112233445566778899");
//	}
}
