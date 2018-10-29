package com.onway.utils;

import com.onway.common.lang.StringUtils;

/**
 * 
* <p>Title: NumUtil</p>  
* <p>Description: ���ִ���</p>  
* @author yugang.ni  
* @date 2018��6��29��  ����11:43:34
 */
public class NumUtil {
	
	public static void main(String[] args) {
		System.out.println(getHidePhone("1570680465"));
	}
	
	/**
	 * �ֻ���������
	 * 
	 * @param cell
	 * @return
	 */
	public static String getHidePhone(String cell) {
		if(StringUtils.isBlank(cell) || cell.length() < 11)
			return cell;
		return cell.substring(0, 3) + "****" + cell.substring(7, cell.length());
	}

	/**
	 * ���֤����
	 * @param certNo
	 * @return
	 */
	public static String getHideCard(String certNo) {
		if(StringUtils.isBlank(certNo))
			return "";
		return certNo.substring(0, 6) + "****" + certNo.substring(14, certNo.length());
	}
}
