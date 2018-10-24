package com.onway.model.enums;

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * 短信记录状态 枚举
 * @author jiayong.mao
 */
public enum SmsRecordStateEnum implements EnumBase {

	INIT("INIT", "待审核", 0),

	FAILED("FAILED", "审核驳回", 1),

	WAIT_SEND("WAIT_SEND", "待发送", 2),

	SENDING("SENDING", "发送中", 3),
    
	SUCCESS("SUCCESS", "发送完毕", 4),
    
	CANCELED("CANCELED", "已取消", 5),
    
    ;
	
	private String code;

    private String message;

    private int    value;
	
	@Override
	public String message() {
		return message;
	}

	@Override
	public Number value() {
		return value;
	}

    private SmsRecordStateEnum(String code, String message, int value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }
    
    public static SmsRecordStateEnum getBannerTypeByCode(String name) {
        for (SmsRecordStateEnum enumObj : SmsRecordStateEnum.values()) {
            if (StringUtils.equals(enumObj.name(), name))
                return enumObj;
        }
        return null;
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
