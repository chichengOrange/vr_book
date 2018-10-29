package com.onway.web.controller.result;

/**
 * 微信支付回调结果集
 * 
 * @author guangdong.li
 * @version $Id: GiftDetailQueryResult.java, v 0.1 24 Feb 2016 17:08:21 guangdong.li Exp $
 */
@SuppressWarnings("rawtypes")
public class WePayCallBackResult extends JsonPageResult {

    public WePayCallBackResult(boolean bizSucc, String errCode, String message) {
        super(bizSucc, errCode, message);
    }

    /** serialVersionUID */
    private static final long serialVersionUID = -7488421673757995649L;

    private String            return_code;

    /**
     * Getter method for property <tt>return_code</tt>.
     * 
     * @return property value of return_code
     */
    public String getReturn_code() {
        return return_code;
    }

    /**
     * Setter method for property <tt>return_code</tt>.
     * 
     * @param return_code value to be assigned to property return_code
     */
    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }
}
