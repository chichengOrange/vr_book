package com.onway.core.service.localcache.enums;

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * 系统参数key的枚举
 * 
 */
public enum SysConfigCacheKeyEnum implements EnumBase {

	// -----------------------支付相关
	MENU_SERVERURL("MENU_SERVERURL", "微信菜单重定向IP地址"),

	WE_PAY_PARTNER_ID("WE_PAY_PARTNER_ID", "微信公众号支付Partner ID"),

	WE_PAY_APP_SECRET_ID("WE_PAY_APP_SECRET_ID", "微信公众号支付APP Secret"),

	WE_PAY_MCH_ID("WE_PAY_MCH_ID", "微信支付MCH_ID"),

	WE_APP_PAY_APP_ID("WE_APP_PAY_APP_ID", "微信APP支付appid"),

	WE_APP_PAY_PAY_MCH_ID("WE_APP_PAY_PAY_MCH_ID", "微信APP支付MCH_ID"),

	WE_APP_PAY_APP_SECRET_ID("WE_APP_PAY_APP_SECRET_ID", "微信APP支付APP Secret"),

	ALIPAY_PARTNER("ALIPAY_PARTNER", "支付宝支付partner"),

	ALIPAY_SELLER("ALIPAY_SELLER", "支付宝支付seller"),

	ALIPAY_PRIVATE_KEY("ALIPAY_PRIVATE_KEY", "支付宝支付privateKey"),

	WECHAT_PAY_CALL_BACK_URL("WECHAT_PAY_CALL_BACK_URL", "微信支付回调地址"),

	WECHAT_PAY_CALL_AUTH_URL("WECHAT_PAY_CALL_AUTH_URL", "微信支付授权地址"),
	
	ACCESS_TOKEN("ACCESS_TOKEN", "ACCESS_TOKEN标识"),
	
	WECHAT_INDEX_URL("WECHAT_INDEX_URL","首页链接地址"),
	
	/**【等级】普通用户等级评定周期*/
	LEVEL_USER_DEVOTE_TIME("LEVEL_USER_DEVOTE_TIME","【等级】普通用户等级评定周期"),
	/**【等级】门店等级评定周期*/
	LEVEL_SHOP_DEVOTE_TIME("LEVEL_SHOP_DEVOTE_TIME","【等级】门店等级评定周期"),
	/**【等级】代理商等级评定周期*/
	LEVEL_AGENT_DEVOTE_TIME("LEVEL_AGENT_DEVOTE_TIME","【等级】代理商等级评定周期"),
	/**【等级】服务商等级评定周期*/
	LEVEL_SERVICE_DEVOTE_TIME("LEVEL_SERVICE_DEVOTE_TIME","【等级】服务商等级评定周期"),
	
	/** 【提现】胡币兑换现金比例（1胡币 对应 value 现金）*/
	HU_WITHDRAW_RATE("HU_WITHDRAW_RATE","【提现】胡币兑换现金比例（1胡币 对应 value 现金）"),
	
	;
	/** 枚举码 */
	private String code;

	/** 描述信息 */
	private String message;

	private SysConfigCacheKeyEnum(String code, String message) {
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
	 * @param code
	 *            枚举编号
	 * @return
	 */
	public static SysConfigCacheKeyEnum getByCode(String code) {

		for (SysConfigCacheKeyEnum param : values()) {
			if (StringUtils.equals(param.getCode(), code)) {
				return param;
			}
		}

		return null;
	}
}
