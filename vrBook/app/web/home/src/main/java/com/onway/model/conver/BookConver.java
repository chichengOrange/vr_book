package com.onway.model.conver;

import com.onway.common.lang.Money;
import com.onway.fyapp.common.dal.dataobject.BookDO;
import com.onway.fyapp.common.dal.dataobject.returnObj.BookInfo;
import com.onway.utils.DateUtil;

/**
 * 书实体转换
 * @author lishuaikai
 *
 * 2018年7月11日 下午2:00:36
 */
public class BookConver {

	public static BookInfo buildBookInfo(BookDO bookDO){
		if(null == bookDO)
			return null;
		
		BookInfo info = new BookInfo();
		info.setBookId(bookDO.getBookId());
		info.setBookName(bookDO.getBookName());
		info.setBookImg(bookDO.getBookImg());
		info.setBookPublish(bookDO.getBookPublish());
		info.setBookSize(bookDO.getBookSize());
		info.setCanDownload(bookDO.getCanDownload()+"");
		info.setDownloadCount(bookDO.getDownloadCount()+"");
		info.setDownloadUrl(bookDO.getDownloadUrl());
		info.setPrice(bookDO.getPrice()+"");
		info.setNowNetwork(bookDO.getNowNetwork());
		info.setCreateTime(DateUtil.dateToString(bookDO.getGmtCreate(), DateUtil.pointFormat));
		info.setAuthor(bookDO.getBookAuthor());
		info.setHeat(bookDO.getBookHeat()+"");
		info.setDistributor(bookDO.getDistributor());
		info.setDownloadUrlIos(bookDO.getDownloadUrlIos());
		info.setBookSizeIos(bookDO.getBookSizeIos());
		return info;
		
	}
}
