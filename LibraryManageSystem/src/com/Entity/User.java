package com.Entity;

import java.util.Calendar;

import com.Dao.SQL;

/**
 * User Class 用户类
 * @author Zen Johnny
 * @since 2016-12-15        2016-12-21 添加 密码属性
 * @version 1.0
 */
public class User {
  
//	static private String nextIdNo; //静态变量为无身份证者申请者账号的下一个数字
	
//	此属性先暂停private Identifier idNo;        //证件号/账号（主键，格式：限定为英文和中文；规范：账号/身份证作证件/账号，但以后可以做类型拓展，不考虑学号作,其内有判断证件是否过期的方法）

	protected String idNo;        //证件号/账号（主键，格式：限定为英文和中文；规范：账号/身份证作证件/账号，但以后可以做类型拓展，不考虑学号作,其内有判断证件是否过期的方法）
	private String password;        //账号密码
	private String name;            //用户姓名
    private boolean sex;            //性别(默认：男  false ;女 true)
    private String roleType;        //角色类型 （老师，学生（研究生和本科生），管理员）
    private String department;      //所属单位/部门/专业/科系（if学生：专业/系别；if老师：学院/年级or科系or办公室;）
    private String idCard;          //身份证号 （默认为与证件号一致）	
    private String e_mail;          //电子邮箱--50字符以内
    private String telephone;       //电话
    private int noStatus;           //账号注册状态  （默认：一旦申请后就是1；注销后就是-1） 
	
    /**
     * 构造方法
     * @param idNo
     * @param name
     * @param sex
     * @param roleType
     * @param department
     * @param idCard
     * @param e_mail
     * @param telephone
     * @param noStatus
     */
    public User(String idNo,String password, String name, boolean sex, String roleType, String department,String idCard,
			String e_mail, String telephone,int noStatus) {
		super();
//		this.idNo = new Identifier(idNo,type,enMonths,enDate);
		this.idNo = idNo;
		this.password = password;
		this.name = name;
		this.sex = sex;
		this.roleType = roleType;
		this.department = department;
		this.idCard = idNo; //-----------------------当前版本仅支持 idCard == idNo
		this.e_mail = e_mail;
		this.telephone = telephone;
        this.noStatus = noStatus; 
//		if(this.idNo.no.length()<=1)
//		      this.noStatus = false;
//		else
//			 this.noStatus = true;
	}
    
    public User(){
    	super();
        this.idNo = null;
		this.password = null;
		this.name = null;
		this.sex = false;
		this.roleType = null;
		this.department = null;
		this.idCard = null;
		this.e_mail = null;
		this.telephone = null;
		this.noStatus = 1;
    }
    /**
     * other methods
     * @return
     */
    public void setUser(User user){
		User.getUser(user.getIdNo().toString());
    }
	public static User getUser(String idNo){
		new SQL();
		return SQL.getUserBySql(idNo);
	}
	public String toString(){
		return 
				"User:"+
				"\nIdNo:"+this.idNo+
				"\nPassword:"+this.password + 
				"\nName:"+ this.name+ 
				"\nSex:"+ this.getSex()+ 
	            "\nroleType:"+this.roleType + 
	            "\nDepartment:"+ this.department + 
	            "\nE-mail:"+ this.e_mail+ 
	            "\nTelephone:"+ this.telephone+ 
	            "\nNotatus:"+ (this.noStatus==1?"已注册":"未注册");
	}
	public void clearProperties(){
		this.idNo = null;
		this.password = null;
		this.name = null;
		this.sex = false;
		this.roleType = null;
		this.department = null;
		this.idCard = null;
		this.e_mail = null;
		this.telephone = null;
		this.noStatus = 1;
	}

//    public void setIdNo(final String idNo){
//    	this.idNo = new Identifier(){
//    		@Override
//    		public void setNo(String no) {
//    			// TODO Auto-generated method stub
//    			super.setNo(idNo);
//    		}
//    	}; 
//    }
/**
 * 密码
 * @since 2016-12-21	
 * @return
 */ public String getIdNo() {
		return idNo;
	}
    public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
        return ((sex==true)?"女":"男");
    }
	public void setSex(boolean sex){
		this.sex = sex;
	}
	public void setSex(String sex) {
        if(sex.contains("男")||sex.contains("Man")||sex.contains("Male"))  
            this.sex = false;
        else this.sex = true;
    }
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getNoStatus() {
		return noStatus;
	}
	public void setNoStatus(int noStatus) {
		this.noStatus = noStatus;
	}
    
}
