package com.onway.utils.emoji;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.onway.common.lang.StringUtils;

/**
 * ��̨����emoji��� ����
 * 
 * @author yuhang.ni
 *
 * @version EmojiUtil.java 2017��12��1�� ����3:31:37 yuhang.ni
 */
public class EmojiUtil {
	/**
	 * ����ת�������ֱ�Ӵ���utf-8 ȡ����������ǰ�˵�ʱ�����emojiRecovery2 ����
	 * 
	 * @Description ���ַ����е�emoji����ת���ɿ�����utf-8�ַ������ݿ��б���ĸ�ʽ������ռ4���ֽڣ���Ҫutf8mb4�ַ�����
	 * @param str
	 *            ��ת���ַ���
	 * @return ת�����ַ���
	 * @throws UnsupportedEncodingException
	 *             exception
	 */
	public static String emojiConvert1(String str) {
		if (StringUtils.isBlank(str))
			return str;

		try {
			String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(str);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				matcher.appendReplacement(sb,
						"[[" + URLEncoder.encode(matcher.group(1), "UTF-8")
								+ "]]");
			}
			matcher.appendTail(sb);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Description ��ԭutf8���ݿ��б���ĺ�ת����emoji������ַ���
	 * @param str
	 *            ת������ַ���
	 * @return ת��ǰ���ַ���
	 * @throws UnsupportedEncodingException
	 *             exception
	 */
	public static String emojiRecovery2(String str) {
		if (StringUtils.isBlank(str))
			return str;
		try {
			String patternString = "\\[\\[(.*?)\\]\\]";
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(str);

			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				matcher.appendReplacement(sb,
						URLDecoder.decode(matcher.group(1), "UTF-8"));
			}
			matcher.appendTail(sb);
			return sb.toString();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
}