 package com.onway.model.conver;


import com.onway.fyapp.common.dal.dataobject.BookVersionDO;
import com.onway.fyapp.common.dal.dataobject.returnObj.BookVersionInfo;
import com.onway.utils.DateUtil;

/**
 * 
 * @author lishuaikai
 *
 * 2018年7月19日 上午10:11:34
 */
public class BookVersionConver {
	public static BookVersionInfo buildBookInfo(BookVersionDO bookVersionDO){
		if(null == bookVersionDO)
			return null;
		
		BookVersionInfo info = new BookVersionInfo();
		info.setBookId(bookVersionDO.getBookId());
		info.setChangeLog(bookVersionDO.getChangeLog());
		info.setMemo(bookVersionDO.getMemo());
        info.setReviseNum(bookVersionDO.getReviseNum());

		info.setCreateTime(DateUtil.dateToString(bookVersionDO.getGmtCreate(), DateUtil.pointFormat));
		
		return info;
		
	}
}
