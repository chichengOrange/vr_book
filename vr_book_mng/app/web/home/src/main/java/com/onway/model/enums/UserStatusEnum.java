/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.model.enums; 

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * 用户账号状态  枚举
 * @author yuhang.ni
 *
 * @version AccountTypeEnum.java 2017年10月19日 下午1:36:20 yuhang.ni
 */
public enum UserStatusEnum implements EnumBase {

	/**账号正常*/
	ENABLE("ENABLE","账号正常"),
    /**该账号已被系统冻结*/
	FREEZED("FREEZED","该账号已被系统冻结"),
    /**该账号已注销*/
	DISABLED("DISABLED","该账号已注销"),
	/**系统黑名单用户*/
	BLACK("BLACK","系统黑名单")
    ;

    /** 枚举码*/
    private String code;

    /** 描述信息*/
    private String message;

    private UserStatusEnum(String code, String message) {
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
    public static UserStatusEnum getByCode(String code) {

        for (UserStatusEnum param : values()) {
            if (StringUtils.equals(param.getCode(), code)) {
                return param;
            }
        }
        return null;
    }
}

