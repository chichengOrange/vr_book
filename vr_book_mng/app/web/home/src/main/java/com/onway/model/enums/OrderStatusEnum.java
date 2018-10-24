/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.model.enums; 

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * 订单状态  枚举
 * @author yuhang.ni
 *
 * @version OrderStatusEnum.java 2017年10月19日 下午1:42:10 yuhang.ni
 */
public enum OrderStatusEnum implements EnumBase {

	/**交易成功*/
    SUCCESS("SUCCESS","交易成功",0),
    /**交易失败*/
    FALI("FALI","交易失败",1),
    /**交易取消*/
    CANCLE("CANCLE","交易取消",2),
    /**交易进行中*/
    ING("ING","交易进行中",3),
    /**待支付*/
    WAIT("WAIT","待支付",4),
    /**未接听*/
    NO_LISTEN("NO_LISTEN","未接听",5),
    /**等待接听*/
    WAIT_LISTEN("WAIT_LISTEN","等待接听",6),
    ;

    /** 枚举码*/
    private String code;

    /** 描述信息*/
    private String message;
    
    /** 存库值  */
    private Number value;
    
    
    private OrderStatusEnum(String code, String message, Number value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Number getValue() {
		return value;
	}

	public void setValue(Number value) {
		this.value = value;
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
        return value;
    }

    /**
     * 通过枚举<code>code</code>获得枚举。
     * 
     * @param code         枚举编号
     * @return
     */
    public static OrderStatusEnum getByCode(String code) {

        for (OrderStatusEnum param : values()) {
            if (StringUtils.equals(param.getCode(), code)) {
                return param;
            }
        }
        return null;
    }
    
    /**
     * 通过枚举<code>value</code>获得枚举。
     * 
     * @param value         数据库信息
     * @return
     */
    public static OrderStatusEnum getByValue(Number value) {

        for (OrderStatusEnum param : values()) {
            if (param.getValue() == value) {
                return param;
            }
        }
        return null;
    }
}

