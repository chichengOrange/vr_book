/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.web.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;

import com.onway.common.lang.StringUtils;
import com.onway.core.service.code.CodeGenerateComponent;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.model.constants.ParamErrorException;
import com.onway.model.enums.ErrorCode;
import com.onway.platform.common.base.PageQueryResult;
import com.onway.platform.common.configration.ConfigrationFactory;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.template.ServiceTemplate;
import com.onway.utils.RegexpUtils;

/**
 * ����������
 * 
 * @author guangdong.li
 * @version $Id: AbstractController.java, v 0.1 17 Feb 2016 11:18:55
 *          guangdong.li Exp $
 */
public class AbstractController {
	/**  */
	private static final Pattern SPLIT_PATTERN = Pattern.compile("_");

	/** logger */
	public static final Logger logger = Logger
			.getLogger(AbstractController.class);

	protected static String PAGE_NUM_STR = "pageNum";

	protected static String PAGE_SIZE_STR = "pageSize";

	protected static int DEFAULT_PAGE_SIZE = 10;

	protected static int DEFAULT_PAGE_NUM = 1;

	protected static final String CURRENTPAGE = "currentPage";

	protected static final String OFFSET = "offset";

	protected static final String LIMIT = "limit";

	protected static final String PAGE_FLAG = "page";

	protected static final String TOKEN_ERROR = "token����ȷ";

	protected static final String USER_ID = "userId";

	protected static final String TOKEN = "token";

	protected static final String APP_TYPE = "appType";

	protected static final String VERSION = "version";

	protected static final String SIGN_T = "sign_t";

	protected static final String SIGN = "sign";

	protected static final String PAGE_NUM = "pageNum";

	protected static final String TIME = "stime";

	protected static final String CHECK_CODE = "checkCode";

	protected static final String SIGN_SEED = "onway888888";

	protected static final String PROD_CODE = "prodCode";

	protected static final int PAGE_NUM_DIGIT = 1;

	protected static final int PAGE_SIZE_DIGIT = 10;

	protected static final String TRANSCODING_ERROR = "���뷽ʽת���쳣";

	protected static final String UPLOAD_SUCCESS = "�ϴ��ɹ�";

	protected static final String UPLOAD_ERROR = "�ϴ��쳣";

	protected DecimalFormat dfZero = new DecimalFormat("0");

	protected DecimalFormat dfDigit = new DecimalFormat("0.00");

	private static final String PAGE_NO = "pageNo";

	private static final String PAGE_SIZE = "pageSize";

	protected static final String TOTALITEMS = "totalItems";

	protected static final String RESULTLIST = "resultList";

	protected static final String TOTALPAGES = "totalPages";
	
	/**
	 * �ϴ�·��
	 */
	protected static final String HEAD_URL_FILE = ConfigrationFactory
			.getConfigration().getPropertyValue("head_url_file");

	/**
	 * ���ӵ�ַ
	 */
	protected static final String HEAD_URL_PATH = ConfigrationFactory
			.getConfigration().getPropertyValue("head_url_path");

	/**************************** ���е�Client ********************************/
	
	@Resource
	protected ServiceTemplate serviceTemplate;
	
	@Resource
	protected SysConfigCacheManager sysConfigCacheManager;
	
	@Resource
	protected CodeGenerateComponent codeGenerateComponent;
	
	
	
	
	
	/**************************** ���е�DAO ********************************/

	// @Resource
	// protected SmsSender SmsSender;
	/**
	 * ��ȡҳ��
	 * 
	 * @param request
	 * @return
	 */
	public int adjustPageNo(HttpServletRequest request) {
		String pageNo = request.getParameter(PAGE_NO);
		if (pageNo == null || !NumberUtils.isDigits(pageNo))
			return PAGE_NUM_DIGIT;
		return Integer.parseInt(pageNo) < 1 ? PAGE_NUM_DIGIT : Integer
				.parseInt(pageNo);
	}

	/**
	 * ��ȡÿҳ��ʾ����
	 * 
	 * @param request
	 * @return
	 */
	public int adjustPageSize(HttpServletRequest request) {
		String pageSize = request.getParameter(PAGE_SIZE);
		if (pageSize == null || !NumberUtils.isDigits(pageSize))
			return PAGE_SIZE_DIGIT;
		return Integer.parseInt(pageSize) < 1 ? PAGE_SIZE_DIGIT : Integer
				.parseInt(pageSize);
	}

	/**
	 * ת��Double
	 * 
	 * @param d
	 * @return
	 */
	public Double adjustDouble(String d) {
		if (d == null)
			return null;
		if (NumberUtils.isNumber(d)) {
			return Double.valueOf(d);
		}
		return null;
	}

	/**
	 * ��ȡ��ҳ����
	 * 
	 * @param request
	 */
	protected Map<String, Integer> getPageData(final HttpServletRequest request) {

		String page = request.getParameter(CURRENTPAGE);
		String pageSize = request.getParameter(PAGE_SIZE_STR);

		// ��ҳҳ��
		int pagesize = StringUtils.hasLength(pageSize)
				&& StringUtils.isNumeric(pageSize) ? Integer.parseInt(pageSize)
				: DEFAULT_PAGE_SIZE;

		int currentPage = 1;
		int offset = 0;

		if (StringUtils.hasLength(page) && StringUtils.isNumeric(page)) {
			currentPage = Integer.parseInt(page);
			offset = (currentPage - 1) * pagesize;
		}

		Map<String, Integer> pageMap = new HashMap<String, Integer>();
		pageMap.put(PAGE_SIZE_STR, pagesize);
		pageMap.put(CURRENTPAGE, currentPage);
		pageMap.put(OFFSET, offset);
		pageMap.put(LIMIT, (currentPage) * pagesize);
		return pageMap;
	}

	/**
	 * �Եõ�ֵ�� Null ���ַ��� ����
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	protected String getParameterCheck(final HttpServletRequest request,
			String key) {
		String Value = request.getParameter(key);
		if (null == Value || Value.equals("")) {
			Value = null;
		}
		return Value;
	}

	/**
	 * ����ҳ��
	 * 
	 * @param totalItems
	 * @return
	 */
	protected int calculatePage(int totalItems, int pagesize) {
		int totalPages = 0;
		if (0 != totalItems && totalItems > pagesize) {
			totalPages = 0 == totalItems % pagesize ? totalItems / pagesize
					: totalItems / pagesize + 1;
		} else if (0 != totalItems && totalItems <= pagesize) {
			totalPages = 1;
		}
		return totalPages;
	}

	/**
	 * ��ȡ�ͻ��˷���ip��ַ
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = (String) request.getParameter("loginIP"); // ����PC������IP��¼
		if (StringUtils.isBlank(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (StringUtils.isBlank(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (StringUtils.isBlank(ip) || ip.length() == 0
				|| "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || ip.length() == 0
				|| "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || ip.length() == 0
				|| "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	protected String getUserId(HttpServletRequest request) {
		String key = "AdD53fE9BCB5E6Db";
		String sign = request.getHeader("Sign");

		if (StringUtils.isEmpty(sign)) {
			sign = request.getParameter("Sign");
		}

		if (StringUtils.isEmpty(sign)) {
			return "";
		}

		// hellomyson_8201409090003422_1370****031_acabd0a7c1f943b5a5f1ec2b50b8adc9
		String text = decode(sign, key);
		String[] arrays = SPLIT_PATTERN.split(text, 4);

		if (arrays == null | arrays.length != 4) {
			return "";
		}

		String userId = arrays[1];

		if (StringUtils.isBlank(userId)) {
			return "";
		}
		return userId;
	}

	/**
	 * AES����
	 * 
	 * @param sign
	 *            ���ܴ�
	 * @param key
	 *            ��Կ
	 * @return
	 */
	public String decode(String sign, String key) {
		if (StringUtils.isEmpty(sign)) {
			return "";
		}
		try {
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, keyspec);
			byte[] b = cipher.doFinal(org.apache.commons.codec.binary.Base64
					.decodeBase64(sign));
			return new String(b);
		} catch (Exception e) {
			logger.error("", e);
		}
		return "";
	}

	public String encode(String sign, String key) {
		if (StringUtils.isEmpty(sign)) {
			return "";
		}
		try {
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = sign.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength
						+ (blockSize - (plaintextLength % blockSize));
			}
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			cipher.init(Cipher.ENCRYPT_MODE, keyspec);
			byte[] b = cipher.doFinal(plaintext);
			return org.apache.commons.codec.binary.Base64.encodeBase64String(b);
		} catch (Exception e) {
			logger.error("", e);
		}
		return "";
	}

	/**
	 * 
	 * �Ƿ�С��ָ���汾 �������ݣ�
	 * 
	 * @param currentVersion
	 * @param oldVersion
	 * @param platform
	 * @return
	 */
	public boolean isSupport(String currentVersion, String oldVersion) {
		try {
			return compareVersion(currentVersion, oldVersion) < 0;
		} catch (Exception e) {
			logger.error("APP�汾���¼����ж�", e);
		}
		return false;
	}

	/**
	 * �Ƚϰ汾�ߵͣ�version1 &lt; version2 ����-1 �����ڷ���0�����ڷ���1
	 * 
	 * @param version1
	 * @param version2
	 * @return
	 * @throws Exception
	 */
	public static int compareVersion(String version1, String version2) {
		if (version1 == null || version2 == null) {
			throw new ParamErrorException("�汾�Ÿ�ʽ����");
		}
		String[] versionArray1 = version1.split("\\.");
		String[] versionArray2 = version2.split("\\.");
		int idx = 0;
		int minLength = Math.min(versionArray1.length, versionArray2.length);// ȡ��С����ֵ
		int diff = 0;
		while (idx < minLength&& (diff = versionArray1[idx].length()- versionArray2[idx].length()) == 0&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {// �ٱȽ��ַ�
			
			
			++idx;
		}
		// ����Ѿ��ֳ���С����ֱ�ӷ��أ����δ�ֳ���С�����ٱȽ�λ�������Ӱ汾��Ϊ��
		diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
		return diff;
		    
	}

	public static int compareVersion2(String version1, String version2) {
		if (version1 == null || version2 == null) {
			throw new ParamErrorException("�汾�Ÿ�ʽ����");
		}
		BigDecimal v1 = new BigDecimal(version1);
		BigDecimal v2 = new BigDecimal(version2);
		

		return v1.compareTo(v2);
		    
	}
	protected Object doQueryData(HttpServletRequest request, Object[] args) {
		return null;
	}

	protected Map<String, Object> returnMapPostTreatment(
			Map<String, Object> resultMap, HttpServletRequest request,
			PageQueryResult<T> result) {
		return null;
	}
	
	
	
	 protected void cellVerify(String cell) throws BaseRuntimeException {

	        if (StringUtils.isEmpty(cell)
	            || !RegexpUtils.isHardRegexpValidate(cell, RegexpUtils.CELL_REGEXP)) {
	            throw new BaseRuntimeException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "�ֻ������ʽ����ȷ");
	        }

	    }

	    protected void checkCodeVerify(String checkCode) throws BaseRuntimeException {

	        if (StringUtils.isEmpty(checkCode)
	            || !RegexpUtils.isHardRegexpValidate(checkCode, RegexpUtils.CHECK_CODE)) {
	            throw new BaseRuntimeException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "��֤���ʽ����ȷ");
	        }

	    }
	    public String getMapValue(Map<String , Object> map, String key){
			
			String str = map.get(key) == null ? "" : map.get(key)+"";
			return str;
		}
}
