package com.onway.model.enums;

import com.onway.platform.common.enums.EnumBase;

/**
 * 用户账户认证状态 枚举
 * @author jiayong.mao
 */
public enum UserAccountAuthEnum implements EnumBase {

	NOAUTH("NOAUTH", "未认证", 0),

	PASSAUTH("PASSAUTH", "已认证", 1),

	FAILED("FAILED", "审核不通过", 2),

	AUTHING("AUTHING", "审核中", 3),
    
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

    private UserAccountAuthEnum(String code, String message, int value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }
    
    public static UserAccountAuthEnum getBannerTypeByValue(int value) {
        for (UserAccountAuthEnum enumObj : UserAccountAuthEnum.values()) {
            if (enumObj.value == value)
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
