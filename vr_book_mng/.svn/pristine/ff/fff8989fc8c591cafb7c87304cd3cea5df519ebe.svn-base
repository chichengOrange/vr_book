/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.model.enums; 

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * 性别  枚举
 * @author yuhang.ni
 *
 * @version AccountTypeEnum.java 2017年10月19日 下午1:36:20 yuhang.ni
 */
public enum SexEnum implements EnumBase {

	/**男*/
    MAN("1","男"),
    /**女*/
    WOMAN("2","女"),
    ;

    /** 枚举码*/
    private String code;

    /** 描述信息*/
    private String message;

    private SexEnum(String code, String message) {
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
    public static SexEnum getByCode(String code) {

        for (SexEnum param : values()) {
            if (StringUtils.equals(param.getCode(), code)) {
                return param;
            }
        }
        return null;
    }
}

