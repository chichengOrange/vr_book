/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.model.enums; 

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.onway.model.EnumItem;
import com.onway.platform.common.enums.EnumBase;

    /**
     * 支付方式状态枚举
     * @author wenqiang.Wang
     * @version $Id: SysConfigCacheKeyEnum.java, v 0.1 2017年6月27日 下午6:10:45 wenqiang.Wang Exp $
     */
public enum PayWayEnum implements EnumBase {
	
    /**微信支付*/
	WECHATPAY("WECHATPAY","微信支付"),
    /**支付宝支付*/
	ALIPAY("ALIPAY","支付宝支付"),
	/**学分支付*/
	POINT("POINT","学分支付"),
    /**学币支付*/
	COIN("COIN","学币支付"),
    ;

    /** 枚举码*/
    private String code;

    /** 描述信息*/
    private String message;
    
   public static final List<EnumItem> list = new ArrayList<EnumItem>(2);
    
    static{
        for (PayWayEnum param : values()) {
            list.add(new EnumItem(param.getCode(), param.message()));
        }
    }


    private PayWayEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /** 
     * @see com.onway.platform.common.enums.EnumBase#message()
     */
    @Override
    public String message() {
        return message;
    }

    /** 
     * @see com.onway.platform.common.enums.EnumBase#value()
     */
    @Override
    public Number value() {
        return null;
    }

    /**
     * 通过枚举<code>code</code>获得枚举。
     * 
     * @param code         枚举编号
     * @return
     */
    public static PayWayEnum getByCode(String code) {

        for (PayWayEnum param : values()) {
            if (StringUtils.equals(param.getCode(), code)) {
                return param;
            }
        }
        return null;
    }
}

