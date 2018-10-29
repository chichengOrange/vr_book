package com.onway.core.service;

import com.onway.web.controller.result.JsonQueryResult;
import com.onway.web.controller.result.JsonResult;

/**
 * 
 * @author Yz
 *
 */
public interface SmsComponent {
	
	/**
	 * ������֤��
	 * @param cell
	 * @return
	 */
	public JsonQueryResult<String> sendVerifyCode(String cell);
	
	
	/**
	 * У����֤��
	 * @param cell
	 * @param checkCode
	 * @return
	 */
	public JsonResult verifyCode(String cell,String checkCode);
	
	
	/**
	 * ��ͨ�ɹ�������֪ͨ����
	 * @param cardNo
	 * @param psw
	 * @return
	 */
	public JsonResult sendNotifications(String cell ,String cardNo, String psw);
	
}
