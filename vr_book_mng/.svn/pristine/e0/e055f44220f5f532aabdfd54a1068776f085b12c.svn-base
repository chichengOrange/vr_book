/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.model.enums; 

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * 系统返回信息  枚举
 * @author yuhang.ni
 *
 * @version AccountTypeEnum.java 2017年10月19日 下午1:36:20 yuhang.ni
 */
public enum SysMsgEnum implements EnumBase {

	/**账户不存在*/
    INEXISTENCE("INEXISTENCE","账户不存在"),
    /**账号或密码错误*/
    ERROR_LOGIN("ERROR_LOGIN","账号或密码错误"),
    /**该账户已注册*/
    REGIST_AGAIN("REGIST_AGAIN","该账户已注册"),
    /**登陆成功*/
    LOGIN_SUCCESS("LOGIN_SUCCESS","登陆成功"),
    /**登陆失败*/
    LOGIN_FAIL("LOGIN_FAIL","登陆失败"),
    /**登陆成功*/
    REGIST_SUCCESS("REGIST_SUCCESS","注册成功"),
    /**登陆失败*/
    REGIST_FAIL("REGIST_FAIL","注册失败"),
    /**该手机号未注册*/
    NO_REGIST_CELL("NO_REGIST_CELL","该手机号未注册"),
    /**发送验证码成功*/
    SEND_VERCODE_SUCCESS("SEND_VERCODE_SUCCESS","发送验证码成功"),
    /**发送验证码失败*/
    SEND_VERCODE_FAIL("SEND_VERCODE_FAIL","发送验证码失败"),
    ;

    /** 枚举码*/
    private String code;

    /** 描述信息*/
    private String message;

    private SysMsgEnum(String code, String message) {
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
    public static SysMsgEnum getByCode(String code) {

        for (SysMsgEnum param : values()) {
            if (StringUtils.equals(param.getCode(), code)) {
                return param;
            }
        }
        return null;
    }
}

