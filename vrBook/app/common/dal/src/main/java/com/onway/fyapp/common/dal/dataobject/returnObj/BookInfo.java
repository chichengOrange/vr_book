package com.onway.fyapp.common.dal.dataobject.returnObj;

/**
 * 书实体类
 * @author lishuaikai
 *
 * 2018年7月11日 下午1:55:09
 */
public class BookInfo {

	private String bookId;
	
	private String bookName;
	
	private String bookImg;
	
	//出版社
	private String bookPublish;
	
	private String bookSize;
	
	private String bookSizeIos;
	
	private String nowNetwork;
	
	private String downloadUrl;
	
	private String downloadUrlIos;
	
	private String downloadCount;
	
	private String time;
	
	//是否能下载
	private String canDownload;
	
	private String price;
	
	private String createTime;

	private String author;
	
	private String heat;
	
	//经销商
	private String distributor;
	
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

	public String getBookImg() {
		return bookImg;
	}

	public void setBookImg(String bookImg) {
		this.bookImg = bookImg;
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

	public String getNowNetwork() {
		return nowNetwork;
	}

	public void setNowNetwork(String nowNetwork) {
		this.nowNetwork = nowNetwork;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAuthor() {
		return author;
	}

	public String getBookSizeIos() {
		return bookSizeIos;
	}

	public void setBookSizeIos(String bookSizeIos) {
		this.bookSizeIos = bookSizeIos;
	}

	public String getDownloadUrlIos() {
		return downloadUrlIos;
	}

	public void setDownloadUrlIos(String downloadUrlIos) {
		this.downloadUrlIos = downloadUrlIos;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getHeat() {
		return heat;
	}

	public void setHeat(String heat) {
		this.heat = heat;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
	
	
	

}
