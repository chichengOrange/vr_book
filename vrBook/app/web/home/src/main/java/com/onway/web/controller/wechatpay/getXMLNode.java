package com.onway.web.controller.wechatpay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service("getXMLNode")
public class getXMLNode {
	public String getXMLNodeValue(String node_name, String xml) {
		String[] tmp = xml.split("<" + node_name + ">");
		String[] _tmp = tmp[1].split("</" + node_name + ">");
		return _tmp[0];
	}

	public Map readXML(String xml) {
		Map<String, String> map = new HashMap<String, String>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement();
			List<Element> list = rootElt.elements();
			for (Element element : list) {
				map.put(element.getName(), element.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
