package com.Service;

import java.text.ParseException;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JOptionPane;

import com.Action.ReturnBookPanel;
/**
 * 格式类 
 * @author Zen Johnny
 * @since 2016-12-15
 */
public class Format {
    public Format() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean IsIsBn(String string){
		boolean flag =  Pattern.matches("[\u2010-[\\d]+]+",string);
//		System.out.println(flag);
		if(flag==true){
			if(string.length()!=17)
				flag =false;
		}
//		
//		Pattern pattern = Pattern.compile("[0-9]");
//		Matcher mtMatcher = pattern.matcher(string);
//		return mtMatcher.matches();
		return flag;
	}

	public static boolean IsName(String string){
		boolean flag = Pattern.matches("[\u4e00-\u9fa5a-zA-Z]+",string);
		if(flag==true){
			if(string.length()>=12)
				flag =false;
		}
		return flag;
	} 
	
	public static boolean IsBookName(String string){
//		boolean flag = Pattern.matches("[\u4e00-\u9fa5a-zA-Z\[\]\(\)\+\-]+",string);
		boolean flag = true;
		if(flag==true){
			if(string.length()>=40)
				flag =false;
		}
		return flag;
	}
	
	public static boolean IsDateString(String string){
		boolean flag = Pattern.matches("[\u2010-[\\d]+]+",string);
		System.out.println(flag);
		if(flag==true){
			if(string.length()!=10)
				flag =false;
		}
		return flag;
	}
	/***
	 * 依照数据库顺序匹配的
	 * @param strings
	 * @return
	 */
	public static int IsBorrowRecord(String strings[]){
		int flag = 0;
		if(IsAccount(strings[0])!=0)
			flag = 1;
//		JOptionPane.showMessageDialog(null, flag+"\n"+strings[0]);
		if(!IsName(strings[1]))
			flag = 2;
//		JOptionPane.showMessageDialog(null, flag+"\n"+strings[1]);
		if(!IsIsBn(strings[2]))
			flag = 3;
//		JOptionPane.showMessageDialog(null, flag+"\n"+strings[2]);
		if(!IsBookName(strings[3]))
			flag = 4;
//		JOptionPane.showMessageDialog(null, flag+"\n"+strings[3]);
		if(!IsAllNumber(strings[4]))
			flag = 5;
//		JOptionPane.showMessageDialog(null, flag);
		if(!IsDateString(strings[5]))
			flag = 6;
//		JOptionPane.showMessageDialog(null, flag);
		if(!IsDateString(strings[6]))
			flag = 7;
//		JOptionPane.showMessageDialog(null, flag);
		
		return flag;
	}
	
	
	/**
	 * 1  长度不满足
	 * 2 不是纯数字 
	 * 0  格式正常  
	 * @param string
	 * @return
	 */

	public static int IsAccount(String string){
		if(string.length()!=18){
		    JOptionPane.showMessageDialog(null, "账号格式不满足18位的长度！");	
		    return 1;
		}
		else if(!IsAllNumber(string)){
		    JOptionPane.showMessageDialog(null, "账号只能是数字！");	
		    return 2;
		}
		else return 0;
	}
	 //除了小数点之外是否都是数字
	public static boolean IsAllNumber(String string){   
		
		return Pattern.matches("[0-9.]+",string);
}
    public static boolean IsAllEn(String string){
		
		return Pattern.matches("[a-zA-Z]+",string);
}   
public static boolean IsAllEnOrAllNumber(String string){
		
		return Pattern.matches("[0-9a-zA-Z]+",string);
}
    public static boolean IsCh(String string){
		
		return Pattern.matches("[\u4e00-\u9fa5]+",string);
}
    public static boolean IsChOrEn(String string){
    	return Pattern.matches("[\u4e00-\u9fa5a-zA-Z]+",string);

    }
    
    
    /**
     *  验证邮箱格式
     *  1.满足'@'号+至少3位'@'符后面的邮箱后缀
     *  2.满足邮箱号长度不得大于50
     *  3.'@'符前，均为数字和英文
     *  4.'@'符后，也均为数字和英文
     * */
    public static boolean IsEmail(String string){
    	boolean flag = false;
        try{
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(string);
                flag = matcher.matches();
                
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }  
    public static boolean IsPasswordFormat(String string){     //防止sql注入，限定只能为数字和英文字母
    	if(string.length()>=18)  
    		return false;
    	return Pattern.matches("[a-zA-Z0-9]+", string);   
    }
    
    /***
     * 检验输入的数据格式
     * 0 完全正常
     * 1 用户名异常
     * 2 账号异常 -----不会出现
     * 3 电话号码异常
     * 4 性别异常----一般不会出现
     * 5 科系异常
     * 6 注册状态异常
     * 7 Email异常
     * 8 角色类型异常 ----一般不会出现
     * 9 证件号异常----不会出现
     * 
     *  如果出现多个异常；以数字最大者为flag标志 返回 
     * @return
     */
    
    public int PersonCenterFormat(String strs[]){
      String[] userInfo = strs;
      int flag = 0;     //默认为：0--格式完全正常
      
//      JOptionPane.showMessageDialog(null, "姓名："+userInfo[0]+" "+flag);
      if(!IsChOrEn(userInfo[0])||userInfo[0].length()>=12){
          flag = 1;
          JOptionPane.showMessageDialog(null, "姓名格式错误：\n1.仅限于中文或英文.\n长度不得超过12的字符.");
      }
//      JOptionPane.showMessageDialog(null, "账号："+userInfo[1]+" "+flag);
      if(!IsAllNumber(userInfo[1])||userInfo[1].length()!=18){
    	  flag = 2;  
    	  JOptionPane.showMessageDialog(null, "账号格式错误:\n1.仅限于数字\n2.长度只能为18位.\n身份证含X的默认为0代替.");  
      }
//      JOptionPane.showMessageDialog(null, "电话："+userInfo[2]+" "+flag);
      if(!IsAllNumber(userInfo[2])||userInfo[2].length()!=11){
          flag = 3;
          JOptionPane.showMessageDialog(null, "电话号码格式错误：\n1.仅限于数字.\n2.号码长度11位.");
      }
//      JOptionPane.showMessageDialog(null, "性别："+userInfo[3]+" "+flag);
      if(!(userInfo[3].equals("男")||userInfo[3].equals("女")))//
          flag = 4;
      if(userInfo[3].length()>=2)   //性别---长度
    	  flag = 4;
      
      if(flag ==4){
    	  JOptionPane.showMessageDialog(null, "性别的格式错误：\n只能填写：男 或者 女");
      }
      
//      JOptionPane.showMessageDialog(null, "科系："+userInfo[4]+" "+flag);
      if(!IsCh(userInfo[4])||userInfo[4].length() >= 20){
    	  flag = 5;
    	  JOptionPane.showMessageDialog(null, "科系格式错误：\n1.字符长度不得大于20");
      }
//      JOptionPane.showMessageDialog(null, "注册状态："+userInfo[5]+" "+flag);
      if(userInfo[5].length()>=3||Integer.parseInt(userInfo[5])>5){           //注册状态异常检验
          flag = 6;   
          JOptionPane.showMessageDialog(null, "注册状态格式错误：\n1.仅限于数字.\n2.0代表注销或者未注册;1代表已注册 ");
      }
//      JOptionPane.showMessageDialog(null, "E-mail："+userInfo[6]+" "+flag);
      if(!IsEmail(userInfo[6])){          //电子邮箱格式检验
          flag = 7;
          JOptionPane.showMessageDialog(null, "E-mail格式错误:\n1.仅限于英文和数字以及'@'和'.'字符");
      }
//      JOptionPane.showMessageDialog(null, "角色类别："+userInfo[7]+" "+flag);
      if(!IsChOrEn(userInfo[7])||userInfo[7].length()>=10){         //角色类型检验
    	  flag = 8;
          JOptionPane.showMessageDialog(null,"角色类型错误：\n1.只能为中文或者英文2.字符长度不得大于10字符");
      }
//      JOptionPane.showMessageDialog(null, "证件号："+userInfo[8]+" "+flag);
      if(!IsAllNumber(userInfo[8])){       //证件号检验必须均为数字（当前版本仅支持身份证号）
          flag = 9; 
          JOptionPane.showMessageDialog(null, "证件号格式错误：\n1.仅限于数字.\n2.长度仅限于18位.\n3.含字母的默认为0填写.");
      }
      //--------------------------
      return flag;
    }
    
//    public static void main(String args[]){
//    	Format format = new Format();
////    	System.out.println(format.IsName("曾太"));
//    	
//    	String [] strings= new String[]{"513723199608111996","曾太","978-7-302-24416-5","数据结构[C++版](第2版)","0","2016-09-31","2016-09-31"};
////    	System.out.println(format.IsBookName("数据结构[C++版](第2版)"));
//    	System.out.println(format. IsName("杨华"));
//    }
}

/**
 * Pattern（匹配）类和 Matcher（匹配器）类：
 *   Pattern类：
 *     p.compile(String strRegex);   //简单工厂模式下创建一个正则表达式，以字符串strRegex作参数初始化
 *     
 *     p.pattern();                  //返回正则表达式strRegex
 *     
 *     p.split(CharSequence input);  //用于分割字符串，并返回一个符合正则表达式范围内的String []
 *     //此法可能与 String.split(String regex) 有类同之处
 *     Eg.
 *         Pattern p=Pattern.compile("\\d+");  //or  "[0-9.]" 
 *         String[] str=p.split("我的QQ是:456456我的电话是:0532214我的邮箱是:aaa@aaa.com"); 
 *         结果:str[0]="我的QQ是:" str[1]="我的电话是:" str[2]="我的邮箱是:aaa@aaa.com"
 *     
 *     Pattern.matches(String regex,CharSequence input)  //静态方法，用于快速匹配字符串，且匹配全部字符串
 *     Eg.
 *         Pattern p=Pattern.matches("\\d+"，"356457765");   
 *         //return true 对整个字符串进行匹配,只有整个字符串都匹配了才返回true 
 *         
 *           可以拆分为：
 *           Pattern p=Pattern.compile("\\d+"); 
 *           Matcher m=p.matcher("22bb23");  //与Pattern.comile(regex)的初始化类同，以matcher(str)初始化
 *           m.matches();                    //return false  与上同理 
 *     
 *     Matcher类：      
 *     Mathcer.start()/ Matcher.end()/ Matcher.group()
 *   
 *     当使用matches(),lookingAt(),find()执行匹配操作后,就可以利用以上三个方法得到更详细的信息:
 *     start()返回匹配到的子字符串在字符串中的索引位置. 
 *     end()返回匹配到的子字符串的最后一个字符在字符串中的索引位置. 
 *     group()返回匹配到的子字符串  
 *     groupCount()用于返回有多少组
 * */
