package com.onway.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 
* <p>Title: SysUtils</p>  
* <p>Description: ϵͳ������</p>  
* @author yugang.ni  
* @date 2018��7��4��  ����1:59:57
 */
public class SysUtils {

	
	/**
	 * ��½�������ۺ������
	 */
	public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
		return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
	}

	/**
	 * ��½�ֻ�����11λ����ƥ���ʽ��ǰ��λ�̶���ʽ+��8λ������ �˷�����ǰ��λ��ʽ�У� 13+������ 15+��4�������� 18+��1��4��������
	 * 17+��9�������� 147
	 */
	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[0-8])|(18[0,2,3,5-9]))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * ����ֻ�����8λ����5|6|8|9��ͷ+7λ������
	 */
	public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^(5|6|8|9)\\d{7}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
}
