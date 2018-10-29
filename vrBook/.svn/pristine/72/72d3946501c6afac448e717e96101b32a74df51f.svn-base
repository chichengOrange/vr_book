package com.onway.task;

import com.alibaba.fastjson.JSON;
import com.onway.common.lang.HttpUtils;
import com.onway.core.service.localcache.enums.SysConfigCacheKeyEnum;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.fyapp.common.dal.daointerface.SysConfigDAO;
import com.onway.utils.TokenInfo;


public class TokenAccessTask extends AbstractTask {

	private SysConfigCacheManager sysConfigCacheManager;
 
	private SysConfigDAO sysConfigDAO;
	
	public SysConfigDAO getSysConfigDAO() {
		return sysConfigDAO;
	}

	public void setSysConfigDAO(SysConfigDAO sysConfigDAO) {
		this.sysConfigDAO = sysConfigDAO;
	}

	@Override
	protected boolean canProcess() {
		return true;
	}

	 //设置定时任务，每小时进行一次获取access_token
	@Override
	protected void process() {
		try {
			String appid = sysConfigCacheManager
					.getConfigValue(SysConfigCacheKeyEnum.WE_APP_PAY_APP_ID);
			String appKey = sysConfigCacheManager
					.getConfigValue(SysConfigCacheKeyEnum.WE_PAY_APP_SECRET_ID);

			String authUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ appid + "&secret=" + appKey;
			String resultStr = HttpUtils.executeGetMethod(authUrl);
			
			TokenInfo tokenInfo = JSON.parseObject(resultStr, TokenInfo.class);
			
			//插入数据库
			int update=sysConfigDAO.updateSYSValue(tokenInfo.getAccess_token(), SysConfigCacheKeyEnum.ACCESS_TOKEN.getCode());
			
			if(update>0){
				sysConfigCacheManager.reload();
			}

		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public void setSysConfigCacheManager(
			SysConfigCacheManager sysConfigCacheManager) {
		this.sysConfigCacheManager = sysConfigCacheManager;
	}

}
