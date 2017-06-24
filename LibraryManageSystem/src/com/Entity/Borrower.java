package com.Entity;

import java.util.List;

import javax.swing.JOptionPane;

import org.omg.PortableInterceptor.USER_EXCEPTION;

import com.Action.PersonCenterPanel;
import com.Dao.SQL;

/**
 * �������� 
 * @author Zen Johnny
 * @since 2016-12-15
 * @version 1.0 
 */
public class Borrower extends User{
/**
 * ���ݿ�� ����
 * ������������ԣ�idNo �� name
 * @author Zen Johnny
 * @sinc 2016-12-24
 * */
   private int borrowGrade;   //���ĵȼ�
   private float fine;        //������
   private String majorIn;    //����רҵ/����
   private int allCount;      //�ۼƽ�������Ŀ���Դ��������˺������� 
   private int maxCount;      //��������Ŀ
   private int nowCount;      //��ǰ�ڽ���״̬������Ŀ   ------ǰ̨����  
   private float deposit;     //Ѻ��
   
   private List<BorrowRecord> nowBooks;  //��ǰ���ĵ��鼮 ��keyΪ������֤���ţ�valueΪֵ
 
/**
 * ���췽��
 * @since 2016-12-22   
 */
    public Borrower(){
	super();
	this.borrowGrade = 0;
	this.fine = 0;
	this.majorIn = super.getDepartment();     //����������߿�ϵ�ص�
    this.allCount = 0;
    this.maxCount = 0;
    this.nowCount = 0;
    this.deposit = 0;
   } 
    
    public Borrower(User user){      //�������캯��
       setBorrower(user);
    }
 /**
  * ��������  
  * @param identifier
  * @return Borrower
  * @since 2016-12-25
  */
    
    /**
     * Userת������
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
    	this.majorIn = user.getDepartment();     //����������߿�ϵ�ص�
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
        this.setNoStatus(0);                      //�˺���ע��״̬
    }
    
    public void setAllProperties(String idNo,String name,int borrowGrade,float fine,String majorIn,int allCount,int maxCount,int nowCount,float deposit,
    		                     String password,boolean sex,String roleType,String department,String email,String telephone,int noStatus){
    	setIdNo(idNo);
    	setName(name);
    	
    	this.borrowGrade = borrowGrade;
    	this.fine = fine;
    	this.majorIn = department;     //------------����������߿�ϵ�ص�
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
        
        setIdCard(idNo);//---------------��ǰ�汾��֧��  idNo == idCard
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
	public static Borrower getBorrower(){  //����ģʽ-----����һ���½�����
 	   return new Borrower();
    }
    
    public static Borrower getBorrower(String No){  //����ģʽ-----ͨ���˺Ż�ȡ������
       return SQL.getBorrower(No);
    }
    
 /***
  * get��set����
  * @author Zen Johnny
  * @since 2016-12-22
  *
  ***/
    
    public int Show(int btnFlag){
       if(this.idNo.length()==18){    //�ó����ж��Ƿ񴴽��˱��û�	
    	   
    	   PersonCenterPanel personCenterPanel = new PersonCenterPanel(this,1);
    	   personCenterPanel.setMainTitle("ȷ�������Ϣ");
    	   personCenterPanel.ControlEditable(btnFlag);   //-1  ȷ����Ϣ��
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
