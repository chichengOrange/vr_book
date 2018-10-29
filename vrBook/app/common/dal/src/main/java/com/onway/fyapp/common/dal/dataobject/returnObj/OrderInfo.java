package com.onway.fyapp.common.dal.dataobject.returnObj;

/**
 * 
 * @author lishuaikai
 *
 * 2018年7月31日 下午2:33:31
 */
public class OrderInfo {

	private String userId;
	
	private String bookId;
	
	private String orderId;
	
	private String status;
	
	private String amount;

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
