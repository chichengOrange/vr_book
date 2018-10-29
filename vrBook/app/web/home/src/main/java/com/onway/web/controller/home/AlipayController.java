package com.onway.web.controller.home;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;


import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.fyapp.common.dal.daointerface.OrderDAO;
import com.onway.fyapp.common.dal.dataobject.OrderDO;
import com.onway.model.enums.OrderStatusEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.utils.LogUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonQueryResult;
import com.onway.web.controller.result.JsonResult;


@Controller
public class AlipayController extends AbstractController {
	
	Logger logger = Logger.getLogger(AlipayController.class);

	@Resource
	private SysConfigCacheManager sysConfigCacheManager;

	@Resource
	private OrderDAO orderDAO;
	

	/**
	 * ֧����APP֧��
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "appAlipay.do" )
	@ResponseBody
	public Object appAlipay(HttpServletRequest request){
		JsonQueryResult<String> result = new JsonQueryResult<String>(true);
		
		try {
			String orderId = request.getParameter("orderId");
			String totalFee = orderDAO.queryOrderByItem(orderId, null, null, null).getPrice().toString();
			
			Money money = new Money(totalFee);
			AlipayClient alipayClient =getAlipayClient();
			AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();//����API��Ӧ��request��
			
			//�ص���ַ
			String notifyUrl = sysConfigCacheManager.getConfigValue("ALIPAY_NOTIFY_URL");
			alipayRequest.setNotifyUrl(notifyUrl);//�ڹ������������û�����֪ͨ��ַ
			alipayRequest.setBizContent("{" +
			" \"out_trade_no\":\""+orderId+"\"," +
			" \"total_amount\":\""+money+"\"," +
			" \"subject\":\"zzCard\"," +
			" \"body\":\"zzCard\"" +
			" }");//���ҵ�����

			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayRequest);
			if(response.isSuccess()){
			System.out.println("���óɹ�");
			result.setObj(response.getBody());
			} else {
			System.out.println("����ʧ��");
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			 LogUtil.error(logger, MessageFormat.format("֧����֧���쳣,ԭ��{0}",
		                new Object[] { e.getMessage() }));
			 return new JsonResult(false, "", MessageFormat.format("֧��ʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			 LogUtil.error(logger, MessageFormat.format("֧����֧���쳣,ԭ��{0}",
		                new Object[] { e.getMessage() }));
			 return new JsonResult(false, "", MessageFormat.format("֧���쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		
		return result;
	}
	
	/**
	 * ֧�����ص�
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "alipayCallBack.do")
	@ResponseBody
	public String alipayCallBack(HttpServletRequest request, ModelMap modelMap) throws Exception{
		 String trade_status = request.getParameter("trade_status");
	        String depositId = request.getParameter("out_trade_no");
	        LogUtil.error(logger, MessageFormat.format("֧�����ص����:��ֵ��{0},����֧�����{1}",
	            new Object[] { depositId, trade_status }));
	        String status = null;
	        String success = "success";
	        Map<String, String> params = new HashMap<String, String>();
	        Map requestParams = request.getParameterMap();
	        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
	            String name = (String) iter.next();
	            String[] values = (String[]) requestParams.get(name);
	            String valueStr = "";
	            for (int i = 0; i < values.length; i++) {
	                valueStr = (i == values.length - 1) ? valueStr + values[i]
	                    : valueStr + values[i] + ",";
	            }
	            //����������δ����ڳ�������ʱʹ�á�
	            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
	            params.put(name, valueStr);
	        }
	        //  params.put("sign", request.getParameter("sign"));
	        //�м�alipaypublickey��֧�����Ĺ�Կ����ȥopen.alipay.com��ӦӦ���²鿴��
	        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
	        String alipaypublicKey = sysConfigCacheManager
	            .getConfigValue("ALIPAY_PUBLIC_KEY");
	        String signType = params.get("sign_type");
	        boolean flag = AlipaySignature.rsaCheckV1(params, alipaypublicKey, "UTF-8", signType);
	        if (!flag) {
	            LogUtil.error(logger, MessageFormat.format("֧�����ص����������:֧����{0},����֧�����{1}",
	                new Object[] { "", trade_status }));
	            return null;
	        }
	        OrderDO orderDO = orderDAO.queryOrderByItem(depositId, null, null, null);
	        Money callBackMoney = new Money(params.get("total_amount"));
	        if (!callBackMoney.equals(orderDO.getPrice())) {
	            LogUtil.error(logger, MessageFormat.format("֧������ֵ���������ص����:��ֵ���ţ�{0},��ֵ֧�������{1},{2}",
	                new Object[] { depositId, callBackMoney, orderDO.getPrice() }));
	            return null;
	        }
	        //1.1����֧���ɹ�
	        if (StringUtils.equals("TRADE_SUCCESS", trade_status)) {
	            status = OrderStatusEnum.SUCCESS.getCode();
	        }
	        //1.2���״������ȴ���Ҹ���  (��ʱ��֧����null  �����֧��������ص�)
	        if (StringUtils.equals("WAIT_BUYER_PAY", trade_status)) {
	            return null;
	        }
	        //1.3δ����׳�ʱ�رգ���֧����ɺ�ȫ���˿�
	        //1.4���׽����������˿�
	        if (StringUtils.equals("TRADE_CLOSED", trade_status)
	            || StringUtils.equals("TRADE_FINISHED", trade_status)) {
	        	 status = OrderStatusEnum.FAIL.getCode();
	        }
	        //2.�޸ĳ�ֵ������� ֱ���ҷ��޸ĳɹ� �ŷ���֧����success
	        int a = orderDAO.updateStatusByOrderId(OrderStatusEnum.SUCCESS.getCode(), depositId);
	        
	        if (a > 0) {
	            LogUtil.error(logger, MessageFormat.format("֧�����ص����:������{0},����֧�����{1},����״̬�޸ĳɹ�",
	                new Object[] { depositId, trade_status }));
	            return success;
	        }
	        LogUtil.error(logger, MessageFormat.format("֧�����ص����:������{0},����֧�����{1},����״̬�޸�ʧ��",
	            new Object[] { depositId, trade_status }));
	        return "fail";
	}
	
	/**
	 * SDK���ý��г�ʼ��
	 * 
	 * @return
	 */
	public AlipayClient getAlipayClient() {
		// ֧��������
		String ALIPAY_URL = 
				sysConfigCacheManager.getConfigValue("ALIPAY_URL");
	/*				SystemHelper
					.getSystemConfig("alipay_url");*/

		// APPID
		String ALIPAY_APP_ID = 
				sysConfigCacheManager.getConfigValue("ALIPAY_APP_ID");
	/*				SystemHelper
					.getSystemConfig("alipay_app_id");*/

		// APP˽Կ
		String APP_PRIVATE_KEY =
				sysConfigCacheManager.getConfigValue("ALIPAY_PRIVATE_KEY");
				/*SystemHelper
				.getSystemConfig("private_key");*/

		// ֧������Կ
		String ALIPAY_PUBLIC_KEY = 
				sysConfigCacheManager.getConfigValue("ALIPAY_PUBLIC_KEY");
	/*				SystemHelper
					.getSystemConfig("alipay_public_key");*/

		AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_URL,
				ALIPAY_APP_ID, APP_PRIVATE_KEY, "json", "utf-8",
				ALIPAY_PUBLIC_KEY, "RSA2");
		return alipayClient;
	}
	 public String getRandomCode(int length) {
	        if (length < 1 || length > 10) {
	            return "";
	        }
	        StringBuffer sb = new StringBuffer();
	        for (int i = 1; i <= length; i++) {
	            int rand = new Random().nextInt(10);
	            sb.append(rand);
	        }
	    return  sb.toString();
	}
}
