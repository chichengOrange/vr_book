package com.onway.web.controller.wechatpay;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */

@Service("WeChatPayUtils")
public class WeChatPayUtils {
	public String createSign(String characterEncoding,
			SortedMap<Object, Object> parameters, String Key) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();                  //
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + Key);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding)
				.toUpperCase();
		return sign;
	}
}
