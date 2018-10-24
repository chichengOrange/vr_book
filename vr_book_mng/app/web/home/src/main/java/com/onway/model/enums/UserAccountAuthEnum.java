package com.onway.model.enums;

import com.onway.platform.common.enums.EnumBase;

/**
 * �û��˻���֤״̬ ö��
 * @author jiayong.mao
 */
public enum UserAccountAuthEnum implements EnumBase {

	NOAUTH("NOAUTH", "δ��֤", 0),

	PASSAUTH("PASSAUTH", "����֤", 1),

	FAILED("FAILED", "��˲�ͨ��", 2),

	AUTHING("AUTHING", "�����", 3),
    
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
