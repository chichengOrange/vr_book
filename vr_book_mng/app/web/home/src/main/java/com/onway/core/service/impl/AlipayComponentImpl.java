package com.onway.core.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;

/**
 * 支付宝支付接口实现类
 * 
 * @author jiaming.zhu
 * @version $Id: Alipay.java, v 0.1 2017年3月6日 上午11:23:19 zjm Exp $
 */
@Component("AlipayComponent")
public class AlipayComponentImpl{
    
    @Resource
    private SysConfigCacheManager       sysConfigCacheManager;
    private static final String FORMAT     = "json";
    private static final String CHARSET    = "GBK";
    private static final String SIGN_TYPE  = "RSA2";
    private static final String PAYEE_TYPE = "ALIPAY_LOGONID";

    /**
     * 单笔转账
     * 
     * @param orderNo
     * @param amount
     * @param account
     * @return
     * @throws AlipayApiException 
     */
    public AlipayFundTransToaccountTransferResponse pay(String orderNo, String amount,
                                                        String account, String realName) throws AlipayApiException {
    /*    AlipayClient alipayClient = new DefaultAlipayClient(
        		sysConfigCacheManager.getConfigValue("FREEBUY_ALIPAY_URL"),
        		sysConfigCacheManager.getConfigValue("ALIPAY_APPID"),
        		sysConfigCacheManager.getConfigValue("ALIPAY_PRIVATE_KEY"),FORMAT, CHARSET,
        		"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApBvY1G5x0/U2hw1RSNpEkNHSEShYIx9twjYVISYqY7gIXGZxKJCD11AcyVWaaRwJDIvEnDJ31yg1lN8VOJxyMKAKn2gZANmmT2UyjMlwXd/Kp209KafMBW36YmsBSK2eaFazSirU8pdiKkTsteCeU8BAyn6VROmaKp56sOzGwagcbu77IoloB6t3rjU1RNSwyvBegTodqm/T+iMYwzjl8idupqOE/K+fGPWgAJ/1FM4CzpKfGq/DWSSL/rTCfyDoI5ufpyDksQOLlo6E7/PHeft77RIhqgDpTU87E4t8s+7Zf5XZ5/uYV6XkTPsFZff4MuOsCPBA8sMm6bSCXvfwdQIDAQAB"
        		,SIGN_TYPE); */
        AlipayClient alipayClient = new DefaultAlipayClient(
        		sysConfigCacheManager.getConfigValue("FREEBUY_ALIPAY_URL"),
        		sysConfigCacheManager.getConfigValue("ALIPAY_APPID"),
        		sysConfigCacheManager.getConfigValue("ALIPAY_PRIVATE_KEY"),FORMAT, CHARSET,
        		sysConfigCacheManager.getConfigValue("ALIPAY_PUBLIC_KEY")
        		,SIGN_TYPE);
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent("{" + "\"out_biz_no\":\"" + orderNo 
                                + "\"," + "\"payee_type\":\""+ PAYEE_TYPE 
                                + "\"," + "\"payee_account\":\"" + account + "\","
                                + "\"payee_real_name\":\"" + realName + "\"," 
                                + "\"amount\":\""+ amount + "\"," 
                                + "\"remark\":\"提现\""
                                + "}");
        AlipayFundTransToaccountTransferResponse response = null;
        response = alipayClient.execute(request);
        return response;
    }

    /**
     * 单笔转账查询
     * 
     * @param orderNo
     * @return
     */
    public AlipayFundTransOrderQueryResponse payQuery(String orderNo) {
        AlipayClient alipayClient = new DefaultAlipayClient(
        		
            sysConfigCacheManager.getConfigValue("FREEBUY_ALIPAY_URL"),
            sysConfigCacheManager.getConfigValue("ALIPAY_APPID"),
            sysConfigCacheManager.getConfigValue("ALIPAY_PRIVATE_KEY"),
            FORMAT, CHARSET,
            sysConfigCacheManager.getConfigValue("ALIPAY_PUBLIC_KEY"),
            SIGN_TYPE);
        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
        request.setBizContent("{" + "    \"out_biz_no\":\"" + orderNo + "\"," + "  }");
        AlipayFundTransOrderQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
            System.out.println(response);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }

}
