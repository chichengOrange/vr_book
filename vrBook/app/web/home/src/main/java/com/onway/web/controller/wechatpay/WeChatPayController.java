package com.onway.web.controller.wechatpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.onway.common.lang.StringUtils;



import com.onway.core.service.code.CodeGenerateComponent;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.fyapp.common.dal.daointerface.OrderDAO;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.dataobject.OrderDO;
import com.onway.fyapp.common.dal.dataobject.returnObj.WeixinInfo;
import com.onway.model.enums.OrderStatusEnum;
import com.onway.utils.ParamsUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonQueryResult;
import com.onway.web.controller.result.WePayCallBackResult;




@Controller
public class WeChatPayController extends AbstractController {

	/** logger */
	private static final Logger logger = Logger
			.getLogger(WeChatPayController.class);

	static String APPID = "wx87df6a0d893deecc";
	static String SECRET = "0a5f83197f1eb92ba247335aee32451f";

	@Resource
	private SysConfigCacheManager sysConfigCacheManager;
	@Resource
    private CodeGenerateComponent codeGenerateComponent;
	@Resource
	protected WeChatPayUtils wechatpayutils;
	@Resource
	protected getXMLNode getxmlnode;
	@Resource
	protected MD5Util md5util;
	@Resource
	private OrderDAO orderDAO; 
	@Resource
	private UserDAO userDAO;


	/** 微信支付回调 */
	@RequestMapping("/WePayCallBack.do")
	@ResponseBody
	public Object WePayCallBack(HttpServletRequest request, ModelMap modelMap)
			throws Exception {

		  WePayCallBackResult result = new WePayCallBackResult(true, "", "");
	      //如果支付成功，返回的xml
		  String successXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
	                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml>";
		  //订单�?
		  String payOrderNo = "";
		  //业务结果
		  String resultCode = "";
		  StringBuffer sb = new StringBuffer();
		  try {
			  // �?】读取xml数据
			  BufferedReader br = new BufferedReader(new InputStreamReader(
							(ServletInputStream) request.getInputStream()));
			  String line=null;
			  while((line = br.readLine()) != null){
				  sb.append(line);
			  }
			  logger.error(MessageFormat.format("微信支付回调,订单号{0},订单支付结果{1},微信报文{2}", new Object[] { "",
						resultCode, sb.toString() }));
			  String appId=sysConfigCacheManager.getConfigValue("WE_PAY_APP_ID");
			  String mchId=sysConfigCacheManager.getConfigValue("WE_PAY_MCH_ID");
			  String appKey=sysConfigCacheManager.getConfigValue("WE_PAY_KEY");
			  
			  ParamsUtil paramsUtil = new ParamsUtil();
			  //付款银行
			  String bank_type = StringUtils.substringBetween(
					 paramsUtil.getValue("bank_type", sb.toString()), "<![CDATA[", "]]>");
			  //现金支付金额
			  String cash_fee = StringUtils.substringBetween(
					 paramsUtil.getValue("cash_fee", sb.toString()), "<![CDATA[", "]]>");
			  //货币种类
			  String fee_type = StringUtils.substringBetween(
						paramsUtil.getValue("fee_type", sb.toString()),"<![CDATA[", "]]>");
			  //是否关注公众账号
			  String is_subscribe = StringUtils.substringBetween(
						paramsUtil.getValue("is_subscribe", sb.toString()),"<![CDATA[", "]]>");
			  //用户标识
			  String openid = StringUtils.substringBetween(
						paramsUtil.getValue("openid", sb.toString()), "<![CDATA[","]]>");
			  //商户订单�?
			  String out_trade_no = StringUtils.substringBetween(
						paramsUtil.getValue("out_trade_no", sb.toString()),"<![CDATA[", "]]>");
			  //业务结果
			  String result_code = StringUtils.substringBetween(
						paramsUtil.getValue("result_code", sb.toString()),"<![CDATA[", "]]>");
			  //返回状�?�?
			  String return_code = StringUtils.substringBetween(
						paramsUtil.getValue("return_code", sb.toString()),"<![CDATA[", "]]>");
			  //支付完成时间
			  String time_end = StringUtils.substringBetween(
						paramsUtil.getValue("time_end", sb.toString()),"<![CDATA[", "]]>");
			  //订单金额
			  String total_fee = paramsUtil.getValue("total_fee", sb.toString());
			  //交易类型
			  String trade_type = StringUtils.substringBetween(
						paramsUtil.getValue("trade_type", sb.toString()),"<![CDATA[", "]]>");
			  //微信支付订单�?
			  String transaction_id = StringUtils.substringBetween(
						paramsUtil.getValue("transaction_id", sb.toString()),"<![CDATA[", "]]>");
			  //随机字符�?
			  String nonce_str = StringUtils.substringBetween(
						paramsUtil.getValue("nonce_str", sb.toString()),"<![CDATA[", "]]>");
			  //签名
			  String callBackSign = StringUtils.substringBetween(
						paramsUtil.getValue("sign", sb.toString()), "<![CDATA[","]]>");	
			  
			  Map<String, String> params = new HashMap<String, String>();
			  params.put("appid", appId);
			  params.put("mch_id", mchId);
			  params.put("bank_type", bank_type);
			  params.put("cash_fee", cash_fee);
			  params.put("fee_type", fee_type);
			  params.put("is_subscribe", is_subscribe);
			  params.put("openid", openid);
			  params.put("out_trade_no", out_trade_no);
			  params.put("result_code", result_code);
			  params.put("return_code", return_code);
			  params.put("time_end", time_end);
			  params.put("total_fee", total_fee);
			  params.put("trade_type", trade_type);
			  params.put("transaction_id", transaction_id);
			  params.put("nonce_str", nonce_str);
			  String sign = PaymentKit.createSign(params, appKey);
			  
			  if(!callBackSign.equals(sign)){
				  logger.error(MessageFormat.format("微信支付回调结果攻击：订单号：{0},支付结果：{1}", new Object[]{out_trade_no,params}));
			  }
			  
			  String returnCode = paramsUtil.getValue("return_code", sb.toString());
	          if (StringUtils.contains(returnCode, "SUCCESS")) {
	              resultCode = paramsUtil.getValue("result_code", sb.toString());
	          }
	          if (StringUtils.contains(resultCode, "SUCCESS")) {
	              result.setReturn_code("SUCCESS");
	          } else {
	              result.setReturn_code("FAIL");
	          }
	          payOrderNo=out_trade_no;
	          logger.error(MessageFormat.format("微信支付结果单：订单号：{0},支付结果：{1}", new Object[]{payOrderNo,resultCode}));
	          
	          //订单状态
	          String status = StringUtils.equals("SUCCESS", result.getReturn_code())?OrderStatusEnum.SUCCESS.getCode():OrderStatusEnum
	        		  .FAIL.getCode();
	          //查询订单，判断金额是否一致?
	          OrderDO orderDO = orderDAO.queryOrderByItem(payOrderNo, null, null, null);
	          
	          if (!(Long.valueOf(total_fee) == orderDO.getPrice().getCent())) {
	              logger.error(MessageFormat.format("微信支付回调结果:订单号：{0},支付结果：{1},{2}",
	                  new Object[] { payOrderNo, total_fee, orderDO.getPrice().getCent() }));
	              return null;
	          }
	          
	          //修改订单状态
	         int update =  orderDAO.updateStatusByOrderId(status, payOrderNo);
	         if(update > 0){
	        	 return successXml; 
	         }
	         return null;
	          
		  } catch (Exception e) {
				// TODO: handle exception
			  logger.error(MessageFormat.format("微信支付回调异常,订单号{0},支付结果{1},微信报文{2}",
		                new Object[] { payOrderNo, resultCode, sb.toString() }));
		            result.setBizSucc(false);
		            result.setErrMsg("服务异常");
			}
		  
		  logger.error(MessageFormat.format("微信支付回调结果:订单号{0},支付结果{1},订状态修改失败",
		            new Object[] { payOrderNo, resultCode }));

		return "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"

		         + "<return_msg><![CDATA[FAIL]]></return_msg>" + "</xml>";
	}

	/**
	 * 微信支付下单幂等
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@RequestMapping("/paymentByWeixin.do")
	@ResponseBody
	public Object unifiedorder(final HttpServletRequest request,
			final ModelMap modelMap,final HttpServletResponse response) throws ClientProtocolException,
			IOException {
		JsonQueryResult<WeixinInfo> result = new JsonQueryResult<WeixinInfo>(true);
		
		// 用户ID
		String userId = request.getParameter("userId");
		
		String orderId = request.getParameter("orderId");
		String totalFee = orderDAO.queryOrderByItem(orderId, null, null, null).getPrice().toString();


		// 微信
		String appid = sysConfigCacheManager.getConfigValue("WE_PAY_APP_ID");
		
		// 商户ID
		String mch_id = sysConfigCacheManager.getConfigValue("WE_PAY_MCH_ID");

		// 回调地址
		String notify_url = sysConfigCacheManager
				.getConfigValue("WE_NOTIFY_URL");


		totalFee = Double.parseDouble(totalFee)*100+"";
		if (StringUtils.contains(totalFee, ".")) {
			totalFee = totalFee.substring(0, totalFee.indexOf("."));
		}

		// 用户标识
		String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
		String nonce_str = UUID.randomUUID().toString().trim()
				.replaceAll("-", "");
		
		String out_trade_no = orderId;
		String trade_type = "MWEB";
		// 商户Key
		String Key = sysConfigCacheManager.getConfigValue("WE_PAY_KEY");
		String body = "antcard";
		String spbill_create_ip = getIpAddr(request);
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		// 商户ID
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str", nonce_str);
		parameters.put("body", body);
		parameters.put("out_trade_no", out_trade_no);
		parameters.put("total_fee", totalFee);
		parameters.put("spbill_create_ip", spbill_create_ip);
		parameters.put("notify_url", notify_url);
		parameters.put("trade_type", trade_type);
		

		String mysign = wechatpayutils.createSign("UTF-8", parameters, Key);

		HttpPost post = new HttpPost(
				"https://api.mch.weixin.qq.com/pay/unifiedorder");
		String responseContent = null;
		String xml = "<xml>";
		xml += "<appid>" + appid + "</appid>";
		xml += "<body>" + body + "</body>";
		xml += "<mch_id>" + mch_id + "</mch_id>";
		xml += "<nonce_str>" + nonce_str + "</nonce_str>";
		xml += "<notify_url>" + notify_url + "</notify_url>";
		xml += "<out_trade_no>" + out_trade_no + "</out_trade_no>";
		xml += "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>";
		xml += "<total_fee>" + totalFee + "</total_fee>";
		xml += "<trade_type>" + trade_type + "</trade_type>";
		xml += "<sign>" + mysign + "</sign>";
		xml += "</xml>"; 	

		post.setEntity(new StringEntity(xml, "UTF-8"));
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse execute = httpClient.execute(post);
		HttpEntity entity = execute.getEntity();
		if (entity != null) {
			responseContent = EntityUtils.toString(entity, "utf-8");
		}
		execute.close();
		httpClient.close();

		Map<?, ?> resultmap = getxmlnode.readXML(responseContent);
		System.out.println("prepay_id:" + resultmap.get("prepay_id"));
		String prepay_id = (String) resultmap.get("prepay_id");

		String prepay_sign_string = "appId=" + appid + "&nonceStr=" + nonce_str
				+ "&package=prepay_id=" + prepay_id + "&signType=MD5"
				+ "&timeStamp=" + timeStamp + "&key=" + Key;
		String prepay_sign = MD5Util.MD5Encode(prepay_sign_string, "UTF-8")
				.toUpperCase();
		WeixinInfo info = new WeixinInfo();
		info.setAppId(appid);
		info.setTimeStamp(timeStamp);
		info.setNonceStr(nonce_str);
		info.setPaySign(prepay_sign);
		info.setPrepayId(prepay_id);
		info.setWepackage("prepay_id=" + prepay_id);
		info.setSignType("MD5");

		result.setObj(info);
		
		return result;
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
