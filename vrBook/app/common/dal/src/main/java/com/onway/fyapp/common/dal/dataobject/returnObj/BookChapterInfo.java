package com.onway.fyapp.common.dal.dataobject.returnObj;

import java.util.List;

/**
 * 
 * @author lishuaikai
 *
 * 2018年7月20日 下午2:46:18
 */
public class BookChapterInfo {

	private String bookId;
	
	private String bookName;
	
	private List<ChapterInfo> chapterList;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public List<ChapterInfo> getChapterList() {
		return chapterList;
	}

	public void setChapterList(List<ChapterInfo> chapterList) {
		this.chapterList = chapterList;
	}
	
	
}
