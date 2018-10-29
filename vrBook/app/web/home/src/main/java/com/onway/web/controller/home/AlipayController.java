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
	 * 支付宝APP支付
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
			AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();//创建API对应的request类
			
			//回调地址
			String notifyUrl = sysConfigCacheManager.getConfigValue("ALIPAY_NOTIFY_URL");
			alipayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址
			alipayRequest.setBizContent("{" +
			" \"out_trade_no\":\""+orderId+"\"," +
			" \"total_amount\":\""+money+"\"," +
			" \"subject\":\"zzCard\"," +
			" \"body\":\"zzCard\"" +
			" }");//填充业务参数

			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayRequest);
			if(response.isSuccess()){
			System.out.println("调用成功");
			result.setObj(response.getBody());
			} else {
			System.out.println("调用失败");
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			 LogUtil.error(logger, MessageFormat.format("支付宝支付异常,原因：{0}",
		                new Object[] { e.getMessage() }));
			 return new JsonResult(false, "", MessageFormat.format("支付失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			 LogUtil.error(logger, MessageFormat.format("支付宝支付异常,原因：{0}",
		                new Object[] { e.getMessage() }));
			 return new JsonResult(false, "", MessageFormat.format("支付异常：{0}", new Object[]{e.getMessage()}));
		}
		
		
		return result;
	}
	
	/**
	 * 支付宝回调
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
	        LogUtil.error(logger, MessageFormat.format("支付宝回调结果:充值单{0},订单支付结果{1}",
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
	            //乱码解决，这段代码在出现乱码时使用。
	            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
	            params.put(name, valueStr);
	        }
	        //  params.put("sign", request.getParameter("sign"));
	        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
	        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
	        String alipaypublicKey = sysConfigCacheManager
	            .getConfigValue("ALIPAY_PUBLIC_KEY");
	        String signType = params.get("sign_type");
	        boolean flag = AlipaySignature.rsaCheckV1(params, alipaypublicKey, "UTF-8", signType);
	        if (!flag) {
	            LogUtil.error(logger, MessageFormat.format("支付宝回调结果被攻击:支付单{0},订单支付结果{1}",
	                new Object[] { "", trade_status }));
	            return null;
	        }
	        OrderDO orderDO = orderDAO.queryOrderByItem(depositId, null, null, null);
	        Money callBackMoney = new Money(params.get("total_amount"));
	        if (!callBackMoney.equals(orderDO.getPrice())) {
	            LogUtil.error(logger, MessageFormat.format("支付宝充值被攻击，回调结果:充值单号：{0},充值支付结果：{1},{2}",
	                new Object[] { depositId, callBackMoney, orderDO.getPrice() }));
	            return null;
	        }
	        //1.1交易支付成功
	        if (StringUtils.equals("TRADE_SUCCESS", trade_status)) {
	            status = OrderStatusEnum.SUCCESS.getCode();
	        }
	        //1.2交易创建，等待买家付款  (这时给支付宝null  等买家支付后继续回调)
	        if (StringUtils.equals("WAIT_BUYER_PAY", trade_status)) {
	            return null;
	        }
	        //1.3未付款交易超时关闭，或支付完成后全额退款
	        //1.4交易结束，不可退款
	        if (StringUtils.equals("TRADE_CLOSED", trade_status)
	            || StringUtils.equals("TRADE_FINISHED", trade_status)) {
	        	 status = OrderStatusEnum.FAIL.getCode();
	        }
	        //2.修改充值相关数据 直到我方修改成功 才返回支付宝success
	        int a = orderDAO.updateStatusByOrderId(OrderStatusEnum.SUCCESS.getCode(), depositId);
	        
	        if (a > 0) {
	            LogUtil.error(logger, MessageFormat.format("支付宝回调结果:订单号{0},订单支付结果{1},订单状态修改成功",
	                new Object[] { depositId, trade_status }));
	            return success;
	        }
	        LogUtil.error(logger, MessageFormat.format("支付宝回调结果:订单号{0},订单支付结果{1},订单状态修改失败",
	            new Object[] { depositId, trade_status }));
	        return "fail";
	}
	
	/**
	 * SDK调用进行初始化
	 * 
	 * @return
	 */
	public AlipayClient getAlipayClient() {
		// 支付宝网关
		String ALIPAY_URL = 
				sysConfigCacheManager.getConfigValue("ALIPAY_URL");
	/*				SystemHelper
					.getSystemConfig("alipay_url");*/

		// APPID
		String ALIPAY_APP_ID = 
				sysConfigCacheManager.getConfigValue("ALIPAY_APP_ID");
	/*				SystemHelper
					.getSystemConfig("alipay_app_id");*/

		// APP私钥
		String APP_PRIVATE_KEY =
				sysConfigCacheManager.getConfigValue("ALIPAY_PRIVATE_KEY");
				/*SystemHelper
				.getSystemConfig("private_key");*/

		// 支付宝公钥
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
