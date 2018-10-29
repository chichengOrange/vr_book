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
	 * 发送验证码
	 * @param cell
	 * @return
	 */
	public JsonQueryResult<String> sendVerifyCode(String cell);
	
	
	/**
	 * 校验验证码
	 * @param cell
	 * @param checkCode
	 * @return
	 */
	public JsonResult verifyCode(String cell,String checkCode);
	
	
	/**
	 * 开通成功，发送通知短信
	 * @param cardNo
	 * @param psw
	 * @return
	 */
	public JsonResult sendNotifications(String cell ,String cardNo, String psw);
	
}
