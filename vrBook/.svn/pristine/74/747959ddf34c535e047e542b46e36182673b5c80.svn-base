package com.onway.utils;

import com.onway.common.lang.StringUtils;

/**
 * 
* <p>Title: NumUtil</p>  
* <p>Description: 数字处理</p>  
* @author yugang.ni  
* @date 2018年6月29日  上午11:43:34
 */
public class NumUtil {
	
	public static void main(String[] args) {
		System.out.println(getHidePhone("1570680465"));
	}
	
	/**
	 * 手机号码隐藏
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
	 * 身份证隐藏
	 * @param certNo
	 * @return
	 */
	public static String getHideCard(String certNo) {
		if(StringUtils.isBlank(certNo))
			return "";
		return certNo.substring(0, 6) + "****" + certNo.substring(14, certNo.length());
	}
}
