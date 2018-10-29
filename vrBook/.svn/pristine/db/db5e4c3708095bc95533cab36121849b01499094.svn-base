package com.onway.model.conver;

import com.onway.fyapp.common.dal.dataobject.AdDO;
import com.onway.fyapp.common.dal.dataobject.BookDO;
import com.onway.fyapp.common.dal.dataobject.returnObj.AdInfo;
import com.onway.fyapp.common.dal.dataobject.returnObj.BookInfo;
import com.onway.utils.DateUtil;

public class AdConver {

	public static AdInfo buildAdInfo(AdDO adDO){
		if(null == adDO)
			return null;
		
		AdInfo info = new AdInfo();
		info.setTitle(adDO.getAdTitle());
		info.setDesc(adDO.getAdDesc());
		info.setContent(adDO.getAdContent());
		info.setImage(adDO.getAdPoster());
		info.setDay(adDO.getAdDay()+"");

		info.setCreateTime(DateUtil.dateToString(adDO.getGmtCreate(), DateUtil.pointFormat));
		
		return info;
		
	}
}
