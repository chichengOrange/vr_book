package com.onway.core.service.localcache.enums;

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * ϵͳ����key��ö��
 * 
 */
public enum SysConfigCacheKeyEnum implements EnumBase {

	// -----------------------֧�����
	MENU_SERVERURL("MENU_SERVERURL", "΢�Ų˵��ض���IP��ַ"),

	WE_PAY_PARTNER_ID("WE_PAY_PARTNER_ID", "΢�Ź��ں�֧��Partner ID"),

	WE_PAY_APP_SECRET_ID("WE_PAY_APP_SECRET_ID", "΢�Ź��ں�֧��APP Secret"),

	WE_PAY_MCH_ID("WE_PAY_MCH_ID", "΢��֧��MCH_ID"),

	WE_APP_PAY_APP_ID("WE_APP_PAY_APP_ID", "΢��APP֧��appid"),

	WE_APP_PAY_PAY_MCH_ID("WE_APP_PAY_PAY_MCH_ID", "΢��APP֧��MCH_ID"),

	WE_APP_PAY_APP_SECRET_ID("WE_APP_PAY_APP_SECRET_ID", "΢��APP֧��APP Secret"),

	ALIPAY_PARTNER("ALIPAY_PARTNER", "֧����֧��partner"),

	ALIPAY_SELLER("ALIPAY_SELLER", "֧����֧��seller"),

	ALIPAY_PRIVATE_KEY("ALIPAY_PRIVATE_KEY", "֧����֧��privateKey"),

	WECHAT_PAY_CALL_BACK_URL("WECHAT_PAY_CALL_BACK_URL", "΢��֧���ص���ַ"),

	WECHAT_PAY_CALL_AUTH_URL("WECHAT_PAY_CALL_AUTH_URL", "΢��֧����Ȩ��ַ"),
	
	ACCESS_TOKEN("ACCESS_TOKEN", "ACCESS_TOKEN��ʶ"),
	
	WECHAT_INDEX_URL("WECHAT_INDEX_URL","��ҳ���ӵ�ַ"),
	
	/**���ȼ�����ͨ�û��ȼ���������*/
	LEVEL_USER_DEVOTE_TIME("LEVEL_USER_DEVOTE_TIME","���ȼ�����ͨ�û��ȼ���������"),
	/**���ȼ����ŵ�ȼ���������*/
	LEVEL_SHOP_DEVOTE_TIME("LEVEL_SHOP_DEVOTE_TIME","���ȼ����ŵ�ȼ���������"),
	/**���ȼ��������̵ȼ���������*/
	LEVEL_AGENT_DEVOTE_TIME("LEVEL_AGENT_DEVOTE_TIME","���ȼ��������̵ȼ���������"),
	/**���ȼ��������̵ȼ���������*/
	LEVEL_SERVICE_DEVOTE_TIME("LEVEL_SERVICE_DEVOTE_TIME","���ȼ��������̵ȼ���������"),
	
	/** �����֡����Ҷһ��ֽ������1���� ��Ӧ value �ֽ�*/
	HU_WITHDRAW_RATE("HU_WITHDRAW_RATE","�����֡����Ҷһ��ֽ������1���� ��Ӧ value �ֽ�"),
	
	;
	/** ö���� */
	private String code;

	/** ������Ϣ */
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
	 * ͨ��ö��<code>code</code>���ö�١�
	 * 
	 * @param code
	 *            ö�ٱ��
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
