package com.onway.core.service.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.onway.common.lang.HttpUtils;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.SmsComponent;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.fyapp.common.dal.daointerface.SmDAO;
import com.onway.fyapp.common.dal.dataobject.SmDO;
import com.onway.platform.common.configration.ConfigrationFactory;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.utils.SmsResult;
import com.onway.web.controller.result.JsonQueryResult;
import com.onway.web.controller.result.JsonResult;

@Service
public class SmsComponentImpl implements SmsComponent{

	private static final Logger logger = Logger.getLogger(SmsComponentImpl.class);
	
	@Resource
	private SmDAO smDAO;
	
	@Resource
	private SysConfigCacheManager sysConfigCacheManager;
	
	@Override
	public JsonQueryResult<String> sendVerifyCode(String cell) {
		// TODO Auto-generated method stub
		
    	String APIKEY = sysConfigCacheManager.getConfigValue("SMS_APIKEY");
    	String APIACCOUNT =sysConfigCacheManager.getConfigValue("SMS_ACCOUNT");
    	String APIURL = sysConfigCacheManager.getConfigValue("SMS_URL");
    	
    	JsonQueryResult<String> result = new JsonQueryResult<String>(true);
		String content = "";
		try {
			//测试环境
			if(!ConfigrationFactory.getConfigration().isProd()){
				SmDO smDO = new SmDO();
				smDO.setCell(cell);
				smDO.setVerifyCode("000000");
				smDO.setSmsStatus("SUCC");
				smDAO.insert(smDO);
				result.setObj("000000");
			}else{
			
				//判断发送间隔
				SmDO smDO2 = smDAO.selectSmsBycell(cell);
				if(smDO2 != null){
				if (new Date().getTime() - smDAO.selectSmsBycell(cell).getGmtCreate().getTime() <= 60000) {
					result.setBizSucc(false);
					result.setErrMsg("发送频率过快！");
					return result;
				}
				}
				String verifyCode = getVerifyCode(6);
				content = "尊敬的用户您好，您的短信验证码为：" + verifyCode + "（五分钟有效）";
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("apiKey", APIKEY);
				paramsMap.put("apiAccount", APIACCOUNT);
				paramsMap.put("cell", cell);
				paramsMap.put("content", content);
				paramsMap.put("style", "FRONT");
				logger.error("发送内容"+verifyCode);
				
				result.setObj(verifyCode);
				String returnStr="";
				try {
					returnStr = HttpUtils.executePostMethod(APIURL, "utf-8", paramsMap);
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.error( MessageFormat.format("短信发送结果：cell{0}，returnStr：{1}", new Object[]{cell,returnStr}));
				
				SmDO smDO = new SmDO();
	            smDO.setCell(cell);
	            smDO.setVerifyCode(verifyCode);
	            smDO.setMemo(returnStr);

	            if (StringUtils.isBlank(returnStr)) {
	                throw new BaseRuntimeException("返回结果为空");
	            }
	            
	            SmsResult smsResult = JSON.parseObject(StringUtils.trim(returnStr), SmsResult.class);
	            String smsStatus = null;
	            if(smsResult.getCode().equals("SUCCESS")){
	            	smsStatus = "SUCC";
	            }else{
	            	smsStatus = "FAIL";
	                result.setBizSucc(false);
	                result.setErrMsg(" 发送验证码失败，请稍后再试 ");
	            }
	            
	            
	            smDO.setSmsStatus(smsStatus);
	            smDAO.insert(smDO);
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
		return result;
	}

	
	@Override
	public JsonResult verifyCode(String cell, String checkCode) {
		// TODO Auto-generated method stub
		JsonResult result = new JsonResult(true);
		 if (!ConfigrationFactory.getConfigration().isProd()) {
	            return result;
	        }
	         SmDO smsDO = smDAO.selectSmsBycell(cell);

	        //1.1
	        if (StringUtils.isBlank(checkCode)) {
	            result.setBizSucc(false);
	            result.setErrMsg("请输入验证码");
	            return result;
	        }
	        //1.2没有发送过验证码
	        if (null == smsDO) {
	            result.setBizSucc(false);
	            result.setErrMsg("未发送过验证码,或发送验证码失败");
	            return result;
	        }
	        //1.3验证码时效性
	        if (new Date().getTime() - smsDO.getGmtCreate().getTime() >= 300000) { //5分钟
	            result.setBizSucc(false);
	            result.setErrMsg("验证码失效");
	            return result;
	        }

	        //1.4验证码比对
	        if (!checkCode.equals(smsDO.getVerifyCode())) {
	            result.setBizSucc(false);
	            result.setErrMsg("验证码错误");
	            return result;
	        }
	        return result;
	}
	
	@Override
	public JsonResult sendNotifications(String cell, String cardNo, String psw) {
		// TODO Auto-generated method stub
		String APIKEY = sysConfigCacheManager.getConfigValue("SMS_APIKEY");
    	String APIACCOUNT =sysConfigCacheManager.getConfigValue("SMS_ACCOUNT");
    	String APIURL = sysConfigCacheManager.getConfigValue("SMS_URL");
    	String short_url = sysConfigCacheManager.getConfigValue("SHORT_URL");
		JsonResult result = new JsonResult(true);
		String content = "";
		try {
				content = "尊敬的用户，恭喜您成为金卡会员，您的卡号：" + cardNo + "，密码："+ psw +"，登录链接："+short_url+"，请保管好您的卡号密码！";
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("apiKey", APIKEY);
				paramsMap.put("apiAccount", APIACCOUNT);
				paramsMap.put("cell", cell);
				paramsMap.put("content", content);
				paramsMap.put("style", "FRONT");
				
				String returnStr="";
				try {
					returnStr = HttpUtils.executePostMethod(APIURL, "utf-8", paramsMap);
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

	            if (StringUtils.isBlank(returnStr)) {
	                throw new BaseRuntimeException("返回结果为空");
	            }
	            
	            SmsResult smsResult = JSON.parseObject(StringUtils.trim(returnStr), SmsResult.class);
	            String smsStatus = null;
	            if(smsResult.getCode().equals("SUCCESS")){
	            	smsStatus = "SUCC";
	            }else{
	            	smsStatus = "FAIL";
	                result.setBizSucc(false);
	                logger.error( MessageFormat.format("通知短信发送失败：{0}", new Object[]{returnStr}));
	            }

			
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
		return result;
	}
	
	public String getVerifyCode(int length) {
        if (length < 1 || length > 10) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= length; i++) {
            int rand = new Random().nextInt(10);
            sb.append(rand);
        }
        return sb.toString();
    }


	public SmDAO getsmDAO() {
		return smDAO;
	}


	public void setsmDAO(SmDAO smDAO) {
		this.smDAO = smDAO;
	}


	public SysConfigCacheManager getSysConfigCacheManager() {
		return sysConfigCacheManager;
	}


	public void setSysConfigCacheManager(SysConfigCacheManager sysConfigCacheManager) {
		this.sysConfigCacheManager = sysConfigCacheManager;
	}


	
	
}
