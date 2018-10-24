package com.onway.fyapp.common.dal.vo;

import java.util.Date;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

public class UserBookVO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7391334392579246502L;
	
	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>USER_ID</tt>.
	 */
	private String userId;

	/**
	 * This property corresponds to db column <tt>BOOK_ID</tt>.
	 */
	private String bookId;

	/**
	 * This property corresponds to db column <tt>DELETE_FLG</tt>.
	 */
	private String deleteFlg;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;
	
	/**
	 * This property corresponds to db column <tt>BOOK_NAME</tt>.
	 */
	private String bookName;

	/**
	 * This property corresponds to db column <tt>BOOK_IMG</tt>.
	 */
	private String bookImg;

	/**
	 * This property corresponds to db column <tt>BOOK_AUTHOR</tt>.
	 */
	private String bookAuthor;

	/**
	 * This property corresponds to db column <tt>BOOK_PUBLISH</tt>.
	 */
	private String bookPublish;

	/**
	 * This property corresponds to db column <tt>BOOK_SIZE</tt>.
	 */
	private String bookSize;

	/**
	 * This property corresponds to db column <tt>BOOK_HEAT</tt>.
	 */
	private String bookHeat;

	/**
	 * This property corresponds to db column <tt>DOWNLOAD_URL</tt>.
	 */
	private String downloadUrl;

	/**
	 * This property corresponds to db column <tt>DOWNLOAD_COUNT</tt>.
	 */
	private String downloadCount;

	/**
	 * This property corresponds to db column <tt>CAN_DOWNLOAD</tt>.
	 */
	private String canDownload;

	/**
	 * This property corresponds to db column <tt>PRICE</tt>.
	 */
	private String price;

	/**
	 * This property corresponds to db column <tt>DISTRIBUTOR</tt>.
	 */
	private String distributor;
	
	/**
	 * This property corresponds to db column <tt>USER_NAME</tt>.
	 */
	private String userName;
	
	/**
	 * This property corresponds to db column <tt>CELL</tt>.
	 */
	private String cell;
	/**
	 * This property corresponds to db column <tt>LOCAL_FLG</tt>.
	 */
	private String localFlg;
	
	
	
	



	public String getLocalFlg() {
        return localFlg;
    }



    public void setLocalFlg(String localFlg) {
        this.localFlg = localFlg;
    }



    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getBookId() {
		return bookId;
	}



	public void setBookId(String bookId) {
		this.bookId = bookId;
	}



	public String getDeleteFlg() {
		return deleteFlg;
	}



	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}



	public Date getGmtCreate() {
		return gmtCreate;
	}



	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}



	public Date getGmtModified() {
		return gmtModified;
	}



	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}



	public String getBookName() {
		return bookName;
	}



	public void setBookName(String bookName) {
		this.bookName = bookName;
	}



	public String getBookImg() {
		return bookImg;
	}



	public void setBookImg(String bookImg) {
		this.bookImg = bookImg;
	}



	public String getBookAuthor() {
		return bookAuthor;
	}



	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}



	public String getBookPublish() {
		return bookPublish;
	}



	public void setBookPublish(String bookPublish) {
		this.bookPublish = bookPublish;
	}



	public String getBookSize() {
		return bookSize;
	}



	public void setBookSize(String bookSize) {
		this.bookSize = bookSize;
	}



	public String getBookHeat() {
		return bookHeat;
	}



	public void setBookHeat(String bookHeat) {
		this.bookHeat = bookHeat;
	}



	public String getDownloadUrl() {
		return downloadUrl;
	}



	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}



	public String getDownloadCount() {
		return downloadCount;
	}



	public void setDownloadCount(String downloadCount) {
		this.downloadCount = downloadCount;
	}



	public String getCanDownload() {
		return canDownload;
	}



	public void setCanDownload(String canDownload) {
		this.canDownload = canDownload;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}



	public String getDistributor() {
		return distributor;
	}



	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getCell() {
		return cell;
	}



	public void setCell(String cell) {
		this.cell = cell;
	}


	


}
