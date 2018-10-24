package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * 账户审核通过枚举
 * @author ASUS
 *
 */
public enum UserAccountMsgEnum implements EnumBase{
	accountmsg_adopt("accountmsg_adopt","尊敬的${rean_name}用户，您的账户审核已通过！"),
	accountmsg_reject("accountmsg_reject","尊敬的${rean_name}用户，用户，您的账户审核未通过！原因为${memo}"),
	order_adopt("order_adopt","尊敬的${rean_name}用户，您的充值审核已通过！"),
	order_reject("order_reject","尊敬的${rean_name}用户，用户，您的充值审核未通过！原因为${memo}"),
	invoice_adopt("invoice_adopt","尊敬的${rean_name}用户，您的发票审核已通过！"),
	invoice_reject("invoice_reject","尊敬的${rean_name}用户，用户，您的发票审核未通过！原因为${memo}");
	 /** 枚举编号 */
    private String code;

    /** 枚举详情 */
    private String message;

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
	/**
     * 构造方法
     * 
     * @param code         枚举编号
     * @param message      枚举详情
     */
    private UserAccountMsgEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
	 /**
     * 通过枚举<code>code</code>获得枚举。
     * @param code 枚举值
     * @return  如果不存在返回NUll<br/>如果存在返回相关值
     */
    public static final String getByCode(String code) {

        //如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (UserAccountMsgEnum item : values()) {
        	System.out.println(item.getMessage());
            if (StringUtils.equals(item.getCode(), code)) {
                return item.getMessage();
            }
        }

        return null;
    }

	@Override
	public String message() {
		// TODO Auto-generated method stub
		return message;
	}
	@Override
	public Number value() {
		// TODO Auto-generated method stub
		return null;
	}
}
