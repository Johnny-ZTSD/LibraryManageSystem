package com.Entity;

import java.util.Calendar;

import com.Dao.SQL;

/**
 * User Class �û���
 * @author Zen Johnny
 * @since 2016-12-15        2016-12-21 ��� ��������
 * @version 1.0
 */
public class User {
  
//	static private String nextIdNo; //��̬����Ϊ�����֤���������˺ŵ���һ������
	
//	����������ͣprivate Identifier idNo;        //֤����/�˺ţ���������ʽ���޶�ΪӢ�ĺ����ģ��淶���˺�/���֤��֤��/�˺ţ����Ժ������������չ��������ѧ����,�������ж�֤���Ƿ���ڵķ�����

	protected String idNo;        //֤����/�˺ţ���������ʽ���޶�ΪӢ�ĺ����ģ��淶���˺�/���֤��֤��/�˺ţ����Ժ������������չ��������ѧ����,�������ж�֤���Ƿ���ڵķ�����
	private String password;        //�˺�����
	private String name;            //�û�����
    private boolean sex;            //�Ա�(Ĭ�ϣ���  false ;Ů true)
    private String roleType;        //��ɫ���� ����ʦ��ѧ�����о����ͱ�������������Ա��
    private String department;      //������λ/����/רҵ/��ϵ��ifѧ����רҵ/ϵ��if��ʦ��ѧԺ/�꼶or��ϵor�칫��;��
    private String idCard;          //���֤�� ��Ĭ��Ϊ��֤����һ�£�	
    private String e_mail;          //��������--50�ַ�����
    private String telephone;       //�绰
    private int noStatus;           //�˺�ע��״̬  ��Ĭ�ϣ�һ����������1��ע�������-1�� 
	
    /**
     * ���췽��
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
		this.idCard = idNo; //-----------------------��ǰ�汾��֧�� idCard == idNo
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
	            "\nNotatus:"+ (this.noStatus==1?"��ע��":"δע��");
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
 * ����
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
        return ((sex==true)?"Ů":"��");
    }
	public void setSex(boolean sex){
		this.sex = sex;
	}
	public void setSex(String sex) {
        if(sex.contains("��")||sex.contains("Man")||sex.contains("Male"))  
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
