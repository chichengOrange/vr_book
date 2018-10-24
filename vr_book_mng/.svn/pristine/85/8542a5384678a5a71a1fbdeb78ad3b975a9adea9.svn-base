/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.model.enums; 

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * 消息类型 枚举
 * @author yuhang.ni
 *
 * @version MsgTypeEnum.java 2017年10月19日 下午2:10:52 yuhang.ni
 */
public enum MsgTypeEnum implements EnumBase {

	/**系统消息*/
    SYS_MSG("SYS_MSG","系统消息"),
    /**推送消息*/
    PUSH_MSG("PUSH_MSG","推送消息"),
    /**通知消息*/
    NOTICE_MSG("NOTICE_MSG","通知消息"),
    ;

    /** 枚举码*/
    private String code;

    /** 描述信息*/
    private String message;

    private MsgTypeEnum(String code, String message) {
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
    public static MsgTypeEnum getByCode(String code) {

        for (MsgTypeEnum param : values()) {
            if (StringUtils.equals(param.getCode(), code)) {
                return param;
            }
        }
        return null;
    }
}

