package com.Entity;

import java.util.List;

import javax.swing.JOptionPane;

import org.omg.PortableInterceptor.USER_EXCEPTION;

import com.Action.PersonCenterPanel;
import com.Dao.SQL;

/**
 * 借阅者类 
 * @author Zen Johnny
 * @since 2016-12-15
 * @version 1.0 
 */
public class Borrower extends User{
/**
 * 数据库端 另建表
 * 额外加了两属性：idNo 和 name
 * @author Zen Johnny
 * @sinc 2016-12-24
 * */
   private int borrowGrade;   //借阅等级
   private float fine;        //罚款金额
   private String majorIn;    //所属专业/科室
   private int allCount;      //累计借阅书数目（自创建借阅账号以来） 
   private int maxCount;      //最大借阅书目
   private int nowCount;      //当前在借阅状态的书数目   ------前台处理  
   private float deposit;     //押金
   
   private List<BorrowRecord> nowBooks;  //当前借阅的书籍 ：key为借阅者证件号，value为值
 
/**
 * 构造方法
 * @since 2016-12-22   
 */
    public Borrower(){
	super();
	this.borrowGrade = 0;
	this.fine = 0;
	this.majorIn = super.getDepartment();     //部门与借阅者科系重叠
    this.allCount = 0;
    this.maxCount = 0;
    this.nowCount = 0;
    this.deposit = 0;
   } 
    
    public Borrower(User user){      //拷贝构造函数
       setBorrower(user);
    }
 /**
  * 其它方法  
  * @param identifier
  * @return Borrower
  * @since 2016-12-25
  */
    
    /**
     * User转借阅者
     */
    public void setBorrower(User user){
    	setIdNo(user.getIdNo());
    	setPassword(user.getPassword());
    	setName(user.getName());
    	setSex(user.getSex());
    	setRoleType(user.getRoleType());
    	setDepartment(user.getDepartment());
    	setIdCard(user.getIdCard());
    	setE_mail(user.getE_mail());
    	setTelephone(user.getTelephone());
    	setNoStatus(user.getNoStatus());
    	
    	this.borrowGrade = 0;
    	this.fine = 0;
    	this.majorIn = user.getDepartment();     //部门与借阅者科系重叠
    	this.allCount = 0;
    	this.maxCount = 0;
    	this.nowCount = 0;
    	this.deposit = 0;
//    	JOptionPane.showMessageDialog(null, this.toString());
    }
    public void clearAllProperties(){
    	setIdNo(null);
    	setName(null);
    	
    	this.borrowGrade = 0;
    	this.fine = 0;
    	this.majorIn = null;
        this.allCount = 0;
        this.maxCount = 0;
        this.nowCount = 0;
        this.deposit = 0;
        
        this.setPassword(null);
        this.setSex(false);
        this.setRoleType(null);
        this.setDepartment(null);
        this.setE_mail(null);
        this.setTelephone(null);
        this.setNoStatus(0);                      //账号在注销状态
    }
    
    public void setAllProperties(String idNo,String name,int borrowGrade,float fine,String majorIn,int allCount,int maxCount,int nowCount,float deposit,
    		                     String password,boolean sex,String roleType,String department,String email,String telephone,int noStatus){
    	setIdNo(idNo);
    	setName(name);
    	
    	this.borrowGrade = borrowGrade;
    	this.fine = fine;
    	this.majorIn = department;     //------------部门与借阅者科系重叠
        this.allCount = allCount;
        this.maxCount = maxCount;
        this.nowCount = nowCount;
        this.deposit = deposit;
        
        this.setPassword(password);
        this.setSex(sex);
        this.setRoleType(roleType);
        this.setDepartment(department);
        this.setE_mail(email);
        this.setTelephone(telephone);
        this.setNoStatus(noStatus);
        
        setIdCard(idNo);//---------------当前版本仅支持  idNo == idCard
    }
    
    @Override
	public String toString() {
		return "Borrower :"+
	           "\nIdNo:"+getIdNo()+
	           "\n[borrowGrade:" + borrowGrade + 
	           "\n fine:" + fine + 
	           "\n majorIn:" + majorIn + 
	           "\n allCount:"+ allCount + 
	           "\n maxCount:" + maxCount + 
	           "\n nowCount:" + nowCount + 
	           "\n deposit:" + deposit+ 
	           "\n nowBooks:" + nowBooks + 
	           "\n"+super.toString();
	}
	public static Borrower getBorrower(){  //单例模式-----创建一个新借阅者
 	   return new Borrower();
    }
    
    public static Borrower getBorrower(String No){  //单例模式-----通过账号获取借阅者
       return SQL.getBorrower(No);
    }
    
 /***
  * get和set方法
  * @author Zen Johnny
  * @since 2016-12-22
  *
  ***/
    
    public int Show(int btnFlag){
       if(this.idNo.length()==18){    //用长度判断是否创建了本用户	
    	   
    	   PersonCenterPanel personCenterPanel = new PersonCenterPanel(this,1);
    	   personCenterPanel.setMainTitle("确认身份信息");
    	   personCenterPanel.ControlEditable(btnFlag);   //-1  确认信息用
    	   personCenterPanel.setVisible(true);
    	   return 1;
       }
       else
    	   return -1;
    }
    
	public int getBorrowGrade() {
		return borrowGrade;
	}
	public void setBorrowGrade(int borrowGrade) {
		this.borrowGrade = borrowGrade;
	}
	public float getFine() {
		return fine;
	}
	public void setFine(float fine) {
		this.fine = fine;
	}
	public String getMajorIn() {
		return majorIn;
	}
	public void setMajorIn(String majorIn) {
		this.majorIn = majorIn;
	}
	
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	public int getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	public int getNowCount() {
		return nowCount;
	}
	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}
	public float getDeposit() {
		return deposit;
	}
	public void setDeposit(float deposit) {
		this.deposit = deposit;
	}
	public List<BorrowRecord> getNowBooks() {
		return nowBooks;
	}
	public void setNowBooks(List<BorrowRecord> nowBooks) {
		this.nowBooks = nowBooks;
	}
            
}
