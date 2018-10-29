/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.web.controller.result;

import org.codehaus.jackson.map.annotate.JsonSerialize;



/**
 * 
* <p>Title: CountResult</p>  
* <p>Description: 数量结果集</p>  
* @author yugang.ni  
* @date 2018年6月27日  下午6:13:07
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CountResult extends JsonResult{

	private static final long serialVersionUID = -5664768750199011376L;
	
	public CountResult(boolean bizSucc) {
		super(bizSucc);
	}
	
	public CountResult(boolean bizSucc, String errCode, String errMsg) {
		super(bizSucc,errCode,errMsg);
	}

	private int count = 0;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
}
