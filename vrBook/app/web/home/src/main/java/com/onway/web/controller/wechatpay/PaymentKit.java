package com.onway.web.controller.wechatpay;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.onway.platform.common.utils.LogUtil;

public class PaymentKit {

    /** logger */
    public static final Logger  logger  = Logger.getLogger(PaymentKit.class);
    private static final String CHARSET = "UTF-8";

    public static String packageSign(Map<String, String> params, boolean urlEncoder) {

        TreeMap<String, String> sortedParams = new TreeMap<String, String>(params);

        sortedParams.remove("sign");

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Entry<String, String> param : sortedParams.entrySet()) {
            String value = param.getValue();
            if (StringUtils.isBlank(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(param.getKey()).append("=");
            if (urlEncoder) {
                try {
                    value = urlEncode(value);
                } catch (UnsupportedEncodingException e) {
                }
            }
            sb.append(value);
        }
        return sb.toString();
    }

    private static String urlEncode(String src) throws UnsupportedEncodingException {
        return URLEncoder.encode(src, CHARSET).replace("+", "%20");
    }

    public static String createSign(Map<String, String> params, String paternerKey) {
        String stringA = packageSign(params, false);
        String stringSignTemp = stringA + "&key=" + paternerKey;
//        LogUtil.info(
//            logger,
//            MessageFormat.format("微信充值回调结果stringSignTemp：{0},{1}", new Object[] { stringSignTemp,
//                    params }));
        return MD5Util.MD5Encode(stringSignTemp, "UTF-8").toUpperCase();
    }

    public static boolean verifyNotify(Map<String, String> params, String paternerKey) {
        String sign = PaymentKit.createSign(params, paternerKey);
        return sign.equals(params.get("sign"));
    }

    public static String toXml(Map<String, String> params) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        for (Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (StringUtils.isBlank(value))
                continue;
            xml.append("<").append(key).append(">");
            xml.append(entry.getValue());
            xml.append("</").append(key).append(">");
        }
        xml.append("</xml>");
        return xml.toString();
    }

//    public static Map<String, String> xmlToMap(String strXML) throws Exception {
//        try {
//            Map<String, String> data = new HashMap<String, String>();
//            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
//            org.w3c.dom.Document doc = documentBuilder.parse(stream);
//            doc.getDocumentElement().normalize();
//            NodeList nodeList = doc.getDocumentElement().getChildNodes();
//            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
//                Node node = nodeList.item(idx);
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
//                    data.put(element.getNodeName(), element.getTextContent());
//                }
//            }
//            try {
//                stream.close();
//            } catch (Exception ex) {
//                // do nothing
//            }
//            return data;
//        } catch (Exception ex) {
//
//            throw ex;
//        }

// /   }

}
