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
		if(!Admin.getRoleType().equals("管理员"))
		    Admin.clearProperties();      //如果不是管理员---清空属性
		return Admin; 
	}
	
    public static Borrower getBorrower(String idNo){
    	 Borrower newBorrower;
    	 ArrayList<Borrower> list = searchBorrower(idNo, "idNo",idNo);  
    	 Iterator<Borrower> iterator = list.iterator();
    	 if(iterator.hasNext()){
    		 newBorrower = new Borrower(); 
    		 newBorrower = iterator.next();             //如果有多个同号的人，只取第一个人，防止bug
    	 return newBorrower;
    	 }
    	 else
    		 return null;
    }
    /**
     *  
     * @param value1   默认：idNo
     * @param value2   默认：当前借阅的书本---参数（还未归还的）
     * @param searchType1 默认：idNo
     * @param searchType2 默认：当前借阅的书本---参数（还未归还的）
     * @return unknown
     * @see
     * ResultSet 对象具有指向其当前数据行的光标。最初，光标被置于第一行之前。next 方法将光标移动到下一行；
     * 因为该方法在 ResultSet 对象没有下一行时返回 false，所以可以在 while 循环中使用它来迭代结果集。      	 
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
             
             boolean revert = rSet.getInt("IsRevert")==0?false:true;   //0-未被借出
        	 
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
    
    public void UpdateUserElementInfo(User user){  //仅仅更新借阅者的用户基本信息
    	String query = "update [User] set ";
//    	query += "'"+User.getIdNo()+"',";   //作为主键
    	query += " password =  "+"'"+user.getPassword()+"',";
//    	JOptionPane.showMessageDialog(null, user.getPassword());//test
    	query += " name =  "+"'"+user.getName()+"',";
    	query += " sex =  "+"'"+user.getSex()+"',";
    	query += " roleType =  "+"'"+user.getRoleType()+"',";
    	query += " department =  "+"'"+user.getDepartment()+"',";
    	query += " email =  "+"'"+user.getE_mail()+"',";
    	query += " telephone =  "+"'"+user.getTelephone()+"',";
    	query += " noStatus =  "+""+user.getNoStatus()+" ";         //最后一个 --空格
    	
    	query += " where idNo = " + "'" + user.getIdNo()+"'";
    	
    	try{
    		Update(query);
    	}catch(Exception e){
    		JOptionPane.showMessageDialog(null, "更新数据异常！");
    	}
    }
    
    //if searchType == null -->搜索所有      
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
     				borrowStaus = false;  //false代表未被借出
     			else
     				borrowStaus = true;
     			
     			book.clearProperties();
     			book.setAllProperties(isbn, bookName, press, bookType, author, price, publishTime, borrowGrade, averageDegree, idNo, borrowStaus, enLibTime, location, bookSummary);
     			list.add(book);
     		}
     		    JOptionPane.showMessageDialog(null,"SQL:searchBook:\n"+book.toString());                  //测试数据输出
     		 
     		}catch(SQLException e){
     			e.printStackTrace();
     		}catch(Exception e){
     			JOptionPane.showMessageDialog(null,"SQL:searchUser(String no)异常！！");
     		}
     		
        	 
         return list;
    }
   
    public static User getUserBySql(String idNo){
    	return user;
    }
       
    //if searchType == null -->搜索所有  
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
			String idNo = rSet.getString("idNo");                 //证件号/账号（主键，格式：限定为英文和中文；规范：账号/身份证作证件/账号，但以后可以做类型拓展，不考虑学号作,其内有判断证件是否过期的方法）
			String password = rSet.getString("password");         //账号密码
			String name = rSet.getString("name");                 //用户姓名
		    
			boolean sex = rSet.getString("sex").equals("男")?false:true;       //性别(默认：男 -1- false ;女 -（-1）-true)
		    
			String roleType = rSet.getString("roleType");         //角色类型 （老师，学生（研究生和本科生），管理员）
		    String department = rSet.getString("department");     //所属单位/部门/专业/科系（if学生：专业/系别；if老师：学院/年级or科系or办公室;）
		    
//		    String idCard = rSet.getString("idCard");             //身份证号 （默认为与证件号一致）	
		    String idCard = idNo;
		    
		    String e_mail = rSet.getString("email");              //电子邮箱
		    
		    String telephone= rSet.getString("telephone");         //电话
		    int noStatus = rSet.getInt("noStatus");                //账号注册状态  （默认：一旦申请后就是1；注销后就是-1） 
			
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
		 System.out.println("public static ArrayList<User> searchUser:\n"+user.toString());                  //测试数据输出
		 
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("public static ArrayList<User> searchUser(String no)异常！！");
		}
		
		return  list;
	}
    
	//最后一个必须填idNo;
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
 		    JOptionPane.showMessageDialog(null, "SearchBorrower:未填写idNo值！");
 		else
 		    query2 += "'"+No+"'";
 		
		try{
		
			rSet = getResultSet(query);
		    rSet2 = getResultSet(query2);
			
			while(rSet.next()&&rSet2.next()){
			   
			   borrower.clearAllProperties();	
			  
			   String idNo = rSet.getString("idNo");                 //证件号/账号（主键，格式：限定为英文和中文；规范：账号/身份证作证件/账号，但以后可以做类型拓展，不考虑学号作,其内有判断证件是否过期的方法）
			         
//			       JOptionPane.showMessageDialog(null, "idNo:"+idNo);//----测试
			   
			   String name = rSet.getString("name");                 //用户姓名
		    
			   int borrowGrade = rSet.getInt("borrowGrade");         //借阅等级
    
			   float fine = rSet.getFloat("fine");                    //罚金
	        
			   String majorIn = rSet.getString("majorIn");            //专业
	        
			   int allCount = rSet.getInt("allCount");                 //借阅书本总数
	        
			   int maxCount = rSet.getInt("maxCount");                 //借阅书本最大数目
	        
			   int nowCount = rSet.getInt("nowCount");                   //借阅书本当前数
			        
//			       JOptionPane.showMessageDialog(null, "nowCount:"+nowCount);
			   
			   float deposit = rSet.getFloat("deposit");                 //押金
			   
//----------------------------------------------------------------------------------
			   String password = rSet2.getString("password");
			   
			   boolean sex = rSet2.getString("sex").equals("男")?false:true;
			   
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
		 System.out.println("public static ArrayList<User> searchUser:\n"+user.toString());                  //测试数据输出
		 
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("public static ArrayList<User> searchUser(String no)异常！！");
		}
		
		return  list;      
	}
	
	public static List searchBook(){
		return new List();
	}
	
	public void UpdateBorrower(String value,String updateType,String idNo){
		String query = "update [Borrower] set ";
		if(value!=null&&updateType!=null&&idNo!=null){
			//对更新的数据类型做出判断 
			if(updateType.equals("borrowGrade")||updateType.equals("fine")||updateType.equals("allCount")||updateType.equals("maxCount")||updateType.equals("nowCount")||updateType.equals("deposit"))
			   query += updateType+" = "+value;
		    else
			   query += updateType+" = '"+value+"'";
		   
		    query += "where idNo = '"+ idNo+"'";
		    
		    Update(query);
		}
		else JOptionPane.showMessageDialog(null, "UpdateBorrower:信息不完整！！");
	}
	
    public boolean insertBorrowRecord(BorrowRecord borrowRecord){
    	String query = "insert into [LibSystem].[dbo].[BorrowRecord] values(";
    	
    	query += "'"+borrowRecord.getIdNo()+"',";
    	query += "'"+borrowRecord.getName()+"',";
    	query += "'"+borrowRecord.getIsbn()+"',";
    	query += "'"+borrowRecord.getBookName()+"',";
    	query += (borrowRecord.isRevert()==true?1:0)+",";  //0---没有还--false
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
     * idNo 或 isbn 可以只填一个 也可以两个
     * @param value
     * @param updateType
     * @param idNo
     * @param isbn
     */
    public static void UpdateBorrowRecord(String value,String updateType,String idNo,String isbn){
    	String query = "update [BorrowRecord] set ";
    	try{
    		
    	     if(value!=null&&updateType!=null){
    	    	 
    	    	 if(updateType.equals("IsRevert"))   //对更新的数据类型做出判断
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
    		      JOptionPane.showMessageDialog(null, "UpdateBorrowRecord:参数异常！！");
    	
    	}catch(Exception e){
    	    JOptionPane.showMessageDialog(null, "SQL:insertBorrowed Exception!!!");
        }
    }
    
    private static void Update(String str_SQL){
    	try{
			Statement statement = getStatement(null);   //创建数据库语句
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
    	int flag = 0;  //判断updatType是否为非字符型表头
    	if(updateType.equals("price")||updateType.equals("borrowGrade")||updateType.equals("averageDegree")||updateType.equals("MAXMONTHs")||updateType.equals("price"))
    	     	flag = -1;   //Book表
    	if(updateType.equals("name")||updateType.equals("idNo")||updateType.equals("majorIn"))
    	        flag = 0;    
    	else    flag = -1;     //Borrower表
    	if(updateType.equals("IsRevert"))
    	        flag = -1;     //BorrowRecord表
    	if(updateType.equals("noStatus"))
    		    flag = -1;    //User表
    	
    	if(flag==0)
    		query += value;          //非字符型表头
    	else
    		query +="'" + value + "'";   //字符型表头
    	query +=" where "+str_key+" = '"+str_keyValue+"'";   //加上更新条件
//    	JOptionPane.showMessageDialog(null, query);    //test
    	try{
			Statement statement = getStatement(null);   //创建数据库语句
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
		
		/*本地端口：查询SQL Server实例服务的PID----CMD：netstat -ano*/
		String usernameSQL = "sa";
		String passwordSQL = "123456";
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //数据库引擎
		String connectDB = "jdbc:sqlserver://localhost:1433;DatabaseName =";      //数据源
	    
		try{
			/*加载并注册SQL Server的JDBC驱动*/
			Class.forName(JDriver);
		}catch(ClassNotFoundException e){
			System.out.println("加载数据库引擎失败");
			e.printStackTrace();
		}
		try{
	       /*建立到数据库的URL*/
		   if(databeseName==null){
				connection = (Connection) DriverManager.getConnection(connectDB+"LibSystem",usernameSQL,passwordSQL);
		   }
		   else{
			   connection = DriverManager.getConnection(connectDB+databeseName,usernameSQL,passwordSQL);
		       connection.setAutoCommit(false);  //?
		       statement = connection.createStatement();    //连接成功！创建SQL语句
		   }
		}catch(SQLException e){
			   System.out.println("连接数据库命令执行异常！");
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
			Statement statement = getStatement(null);   //创建数据库语句
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
			System.out.println("SQL获取结果集异常！");		}
		return rSet;
	}
	
//	public static void main(String args[]){
//		SQL sql = new SQL();
////         sql.UpdateBorrower("吴淞", "name", "513723199608111996"); 
//         sql.getAdmin("123451234512345123");
//		sql.UpdateUserElementInfo(new User("112233445566778899","654321","李逵",false,"学生","对外贸易","112233445566778899","johnny@sina.com","1898989998",1)); 
//		sql.UpdateBorrowRecord("0", "IsRevert", "112233445566778899","879-6-524789-87-2");
		
		
//		sql.Insert("insert [LibSystem].[dbo].[BookRecord] values('513723199608111996','曾太','12345678901234567','《wawawa》',1,'2016-10-03','2016-10-23');");
		
//		BorrowRecord bRecord = new BorrowRecord();
//		bRecord.setAllProperties("000000000000000001","654-56-8-42637941","《红楼梦》", "001", true,"2011-05-19","2011-07-19");
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
//	    sql.searchUser("513723199608111996","idNo");   //格式要对
//		sql.getBorrower("112233445566778899");
//	}
}
