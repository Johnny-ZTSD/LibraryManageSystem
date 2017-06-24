package com.Entity;

import java.util.Calendar;
import java.util.Date;

/**
 * 证件类 
 * @author Zen Johnny 
 * @since 2016-12-15
 * @version 1.0  
 * */

public class Identifier {
   String no;             //证件号（主键，格式：限定为英文和中文；默认规范：身份证作唯一证件账号，不考虑学号作账号）
   String type;           //证件类型（开发时间有限，仅仅默认为：身份证 IdCard）
   int enMonths;          //证件持续生效的月数
   Calendar invaildDate;  //失效开始时间
   Calendar enDate;       //生效开始时间
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
 * 设置时间
 * 假如设置的失效时间比按照月数的时间要来得大，失效时间就按照设置的cale来并返回true；否则，不予设置并返回FALSE
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
 * 判断是否过期
 * 判断当期传参的日期的时候，证件号是否已经过期。
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
