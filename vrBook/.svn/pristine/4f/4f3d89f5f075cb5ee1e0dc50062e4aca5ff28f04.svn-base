/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.web.controller.result;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.onway.platform.common.enums.EnumBase;

/**
* <p>Title: JsonResult</p>  
* <p>Description: json默认结果集</p>  
* @author yugang.ni  
* @date 2018年6月26日  下午5:07:23
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class JsonResult implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1475348231900998033L;

    // 业务操作结果
    private boolean           bizSucc          = true;
    

    private String            errMsg           = "";

    // 错误码
    private String            errCode          = "";

    public JsonResult(boolean bizSucc) {
        this.bizSucc = bizSucc;
    }

    public JsonResult(boolean bizSucc, String errCode, String errMsg) {
        this.bizSucc = bizSucc;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
    
    public void markResult(boolean bizSucc, EnumBase resulterrCode) {
        this.bizSucc = bizSucc;
        if (resulterrCode != null) {
            this.errCode = resulterrCode.name();
            this.errMsg = resulterrCode.message();
        }
    }

    public void markResult(boolean bizSucc, String errCode, String errMsg) {
    	this.bizSucc = bizSucc;
        this.errMsg = errMsg;
        this.errCode = errCode;
    }

	public boolean isBizSucc() {
		return bizSucc;
	}


	public void setBizSucc(boolean bizSucc) {
		this.bizSucc = bizSucc;
	}


	public String getErrMsg() {
		return errMsg;
	}


	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}


	public String getErrCode() {
		return errCode;
	}


	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

}
