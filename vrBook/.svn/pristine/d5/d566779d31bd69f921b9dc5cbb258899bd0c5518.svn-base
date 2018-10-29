package com.onway.model.conver;

import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.fyapp.common.dal.dataobject.returnObj.UserInfo;
import com.onway.utils.DateUtil;

/**
 * 
 * @author Yz
 *
 */
public class UserConver {

	public static UserInfo buildUserInfo( UserDO userDO) {
		if (null == userDO)
			return null;

		UserInfo info = new UserInfo();
		info.setUsrId(userDO.getUserId());
		info.setUserName(userDO.getUserName());
		info.setHeadUrl(userDO.getHeadUrl());
		info.setCell(userDO.getCell());
		//info.setStatus(userDO.getStatus());
		info.setOpenId(userDO.getOpenId());
		info.setCreateTime(DateUtil.dateToString(userDO.getGmtCreate(), DateUtil.pointFormat));
		
		return info;
	}
}
