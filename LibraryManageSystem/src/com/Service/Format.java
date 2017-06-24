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
 * ��ʽ�� 
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
	 * �������ݿ�˳��ƥ���
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
	 * 1  ���Ȳ�����
	 * 2 ���Ǵ����� 
	 * 0  ��ʽ����  
	 * @param string
	 * @return
	 */

	public static int IsAccount(String string){
		if(string.length()!=18){
		    JOptionPane.showMessageDialog(null, "�˺Ÿ�ʽ������18λ�ĳ��ȣ�");	
		    return 1;
		}
		else if(!IsAllNumber(string)){
		    JOptionPane.showMessageDialog(null, "�˺�ֻ�������֣�");	
		    return 2;
		}
		else return 0;
	}
	 //����С����֮���Ƿ�������
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
     *  ��֤�����ʽ
     *  1.����'@'��+����3λ'@'������������׺
     *  2.��������ų��Ȳ��ô���50
     *  3.'@'��ǰ����Ϊ���ֺ�Ӣ��
     *  4.'@'����Ҳ��Ϊ���ֺ�Ӣ��
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
    public static boolean IsPasswordFormat(String string){     //��ֹsqlע�룬�޶�ֻ��Ϊ���ֺ�Ӣ����ĸ
    	if(string.length()>=18)  
    		return false;
    	return Pattern.matches("[a-zA-Z0-9]+", string);   
    }
    
    /***
     * ������������ݸ�ʽ
     * 0 ��ȫ����
     * 1 �û����쳣
     * 2 �˺��쳣 -----�������
     * 3 �绰�����쳣
     * 4 �Ա��쳣----һ�㲻�����
     * 5 ��ϵ�쳣
     * 6 ע��״̬�쳣
     * 7 Email�쳣
     * 8 ��ɫ�����쳣 ----һ�㲻�����
     * 9 ֤�����쳣----�������
     * 
     *  ������ֶ���쳣�������������Ϊflag��־ ���� 
     * @return
     */
    
    public int PersonCenterFormat(String strs[]){
      String[] userInfo = strs;
      int flag = 0;     //Ĭ��Ϊ��0--��ʽ��ȫ����
      
//      JOptionPane.showMessageDialog(null, "������"+userInfo[0]+" "+flag);
      if(!IsChOrEn(userInfo[0])||userInfo[0].length()>=12){
          flag = 1;
          JOptionPane.showMessageDialog(null, "������ʽ����\n1.���������Ļ�Ӣ��.\n���Ȳ��ó���12���ַ�.");
      }
//      JOptionPane.showMessageDialog(null, "�˺ţ�"+userInfo[1]+" "+flag);
      if(!IsAllNumber(userInfo[1])||userInfo[1].length()!=18){
    	  flag = 2;  
    	  JOptionPane.showMessageDialog(null, "�˺Ÿ�ʽ����:\n1.����������\n2.����ֻ��Ϊ18λ.\n���֤��X��Ĭ��Ϊ0����.");  
      }
//      JOptionPane.showMessageDialog(null, "�绰��"+userInfo[2]+" "+flag);
      if(!IsAllNumber(userInfo[2])||userInfo[2].length()!=11){
          flag = 3;
          JOptionPane.showMessageDialog(null, "�绰�����ʽ����\n1.����������.\n2.���볤��11λ.");
      }
//      JOptionPane.showMessageDialog(null, "�Ա�"+userInfo[3]+" "+flag);
      if(!(userInfo[3].equals("��")||userInfo[3].equals("Ů")))//
          flag = 4;
      if(userInfo[3].length()>=2)   //�Ա�---����
    	  flag = 4;
      
      if(flag ==4){
    	  JOptionPane.showMessageDialog(null, "�Ա�ĸ�ʽ����\nֻ����д���� ���� Ů");
      }
      
//      JOptionPane.showMessageDialog(null, "��ϵ��"+userInfo[4]+" "+flag);
      if(!IsCh(userInfo[4])||userInfo[4].length() >= 20){
    	  flag = 5;
    	  JOptionPane.showMessageDialog(null, "��ϵ��ʽ����\n1.�ַ����Ȳ��ô���20");
      }
//      JOptionPane.showMessageDialog(null, "ע��״̬��"+userInfo[5]+" "+flag);
      if(userInfo[5].length()>=3||Integer.parseInt(userInfo[5])>5){           //ע��״̬�쳣����
          flag = 6;   
          JOptionPane.showMessageDialog(null, "ע��״̬��ʽ����\n1.����������.\n2.0����ע������δע��;1������ע�� ");
      }
//      JOptionPane.showMessageDialog(null, "E-mail��"+userInfo[6]+" "+flag);
      if(!IsEmail(userInfo[6])){          //���������ʽ����
          flag = 7;
          JOptionPane.showMessageDialog(null, "E-mail��ʽ����:\n1.������Ӣ�ĺ������Լ�'@'��'.'�ַ�");
      }
//      JOptionPane.showMessageDialog(null, "��ɫ���"+userInfo[7]+" "+flag);
      if(!IsChOrEn(userInfo[7])||userInfo[7].length()>=10){         //��ɫ���ͼ���
    	  flag = 8;
          JOptionPane.showMessageDialog(null,"��ɫ���ʹ���\n1.ֻ��Ϊ���Ļ���Ӣ��2.�ַ����Ȳ��ô���10�ַ�");
      }
//      JOptionPane.showMessageDialog(null, "֤���ţ�"+userInfo[8]+" "+flag);
      if(!IsAllNumber(userInfo[8])){       //֤���ż�������Ϊ���֣���ǰ�汾��֧�����֤�ţ�
          flag = 9; 
          JOptionPane.showMessageDialog(null, "֤���Ÿ�ʽ����\n1.����������.\n2.���Ƚ�����18λ.\n3.����ĸ��Ĭ��Ϊ0��д.");
      }
      //--------------------------
      return flag;
    }
    
//    public static void main(String args[]){
//    	Format format = new Format();
////    	System.out.println(format.IsName("��̫"));
//    	
//    	String [] strings= new String[]{"513723199608111996","��̫","978-7-302-24416-5","���ݽṹ[C++��](��2��)","0","2016-09-31","2016-09-31"};
////    	System.out.println(format.IsBookName("���ݽṹ[C++��](��2��)"));
//    	System.out.println(format. IsName("�"));
//    }
}

/**
 * Pattern��ƥ�䣩��� Matcher��ƥ�������ࣺ
 *   Pattern�ࣺ
 *     p.compile(String strRegex);   //�򵥹���ģʽ�´���һ��������ʽ�����ַ���strRegex��������ʼ��
 *     
 *     p.pattern();                  //����������ʽstrRegex
 *     
 *     p.split(CharSequence input);  //���ڷָ��ַ�����������һ������������ʽ��Χ�ڵ�String []
 *     //�˷������� String.split(String regex) ����֮ͬ��
 *     Eg.
 *         Pattern p=Pattern.compile("\\d+");  //or  "[0-9.]" 
 *         String[] str=p.split("�ҵ�QQ��:456456�ҵĵ绰��:0532214�ҵ�������:aaa@aaa.com"); 
 *         ���:str[0]="�ҵ�QQ��:" str[1]="�ҵĵ绰��:" str[2]="�ҵ�������:aaa@aaa.com"
 *     
 *     Pattern.matches(String regex,CharSequence input)  //��̬���������ڿ���ƥ���ַ�������ƥ��ȫ���ַ���
 *     Eg.
 *         Pattern p=Pattern.matches("\\d+"��"356457765");   
 *         //return true �������ַ�������ƥ��,ֻ�������ַ�����ƥ���˲ŷ���true 
 *         
 *           ���Բ��Ϊ��
 *           Pattern p=Pattern.compile("\\d+"); 
 *           Matcher m=p.matcher("22bb23");  //��Pattern.comile(regex)�ĳ�ʼ����ͬ����matcher(str)��ʼ��
 *           m.matches();                    //return false  ����ͬ�� 
 *     
 *     Matcher�ࣺ      
 *     Mathcer.start()/ Matcher.end()/ Matcher.group()
 *   
 *     ��ʹ��matches(),lookingAt(),find()ִ��ƥ�������,�Ϳ��������������������õ�����ϸ����Ϣ:
 *     start()����ƥ�䵽�����ַ������ַ����е�����λ��. 
 *     end()����ƥ�䵽�����ַ��������һ���ַ����ַ����е�����λ��. 
 *     group()����ƥ�䵽�����ַ���  
 *     groupCount()���ڷ����ж�����
 * */
