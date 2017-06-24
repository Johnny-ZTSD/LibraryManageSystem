package com.Entity;

import java.util.Calendar;
import java.util.Date;

/**
 * ֤���� 
 * @author Zen Johnny 
 * @since 2016-12-15
 * @version 1.0  
 * */

public class Identifier {
   String no;             //֤���ţ���������ʽ���޶�ΪӢ�ĺ����ģ�Ĭ�Ϲ淶�����֤��Ψһ֤���˺ţ�������ѧ�����˺ţ�
   String type;           //֤�����ͣ�����ʱ�����ޣ�����Ĭ��Ϊ�����֤ IdCard��
   int enMonths;          //֤��������Ч������
   Calendar invaildDate;  //ʧЧ��ʼʱ��
   Calendar enDate;       //��Ч��ʼʱ��
   /**
    * Construction Methods
    * @param no
    * @param type
    */
   public Identifier(String no, String type,int enMonths,Calendar enDate) {
	super();
	no = no;
	type = type;
	this.enDate = enDate;
	this.enDate = enDate;
	
	Calendar calendar  = this.enDate;
	calendar.add(Calendar.MONTH,this.enMonths);
	this.invaildDate = calendar; 
}
   
   public Identifier() {
		super();
		no = null;
		type = null;
		this.enDate = null;
		this.invaildDate = null;
		this.enDate = null;
}
   
   /**
    * Others' Methods
    */
   
public String getNo() {
	return no;
}

public void setNo(String no) {
	this.no = no;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}
public Calendar getEnDate() {
	return enDate;
}
public void setEnDate(Calendar cale) {
	this.enDate = cale;
}

public Calendar getInvaildDate(Calendar cale) {
	return invaildDate;
}
/**
 * ����ʱ��
 * �������õ�ʧЧʱ��Ȱ���������ʱ��Ҫ���ô�ʧЧʱ��Ͱ������õ�cale��������true�����򣬲������ò�����FALSE
 * @param cale
 * @return
 */
public boolean setInvaildDate(Calendar cale) {
	Calendar calendar = cale;
	calendar.add(Calendar.MONTH,-this.enMonths);
	int comp = calendar.compareTo(this.enDate);
	if(comp>0){
	  this.invaildDate = cale;	
	  return true;	
	}else
		return false;     
}
/**
 * �ж��Ƿ����
 * �жϵ��ڴ��ε����ڵ�ʱ��֤�����Ƿ��Ѿ����ڡ�
 * @param cale
 * @return boolean
 */
public boolean IsEffective(Calendar cale){
    if(cale.compareTo(invaildDate)<=0)
    	return true;
    else
    	return false;
}

}
