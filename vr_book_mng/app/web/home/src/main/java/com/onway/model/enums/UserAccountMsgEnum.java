package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * �˻����ͨ��ö��
 * @author ASUS
 *
 */
public enum UserAccountMsgEnum implements EnumBase{
	accountmsg_adopt("accountmsg_adopt","�𾴵�${rean_name}�û��������˻������ͨ����"),
	accountmsg_reject("accountmsg_reject","�𾴵�${rean_name}�û����û��������˻����δͨ����ԭ��Ϊ${memo}"),
	order_adopt("order_adopt","�𾴵�${rean_name}�û������ĳ�ֵ�����ͨ����"),
	order_reject("order_reject","�𾴵�${rean_name}�û����û������ĳ�ֵ���δͨ����ԭ��Ϊ${memo}"),
	invoice_adopt("invoice_adopt","�𾴵�${rean_name}�û������ķ�Ʊ�����ͨ����"),
	invoice_reject("invoice_reject","�𾴵�${rean_name}�û����û������ķ�Ʊ���δͨ����ԭ��Ϊ${memo}");
	 /** ö�ٱ�� */
    private String code;

    /** ö������ */
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
     * ���췽��
     * 
     * @param code         ö�ٱ��
     * @param message      ö������
     */
    private UserAccountMsgEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
	 /**
     * ͨ��ö��<code>code</code>���ö�١�
     * @param code ö��ֵ
     * @return  ��������ڷ���NUll<br/>������ڷ������ֵ
     */
    public static final String getByCode(String code) {

        //���ΪNUll���� NUll
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
