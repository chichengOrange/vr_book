package com.onway.web.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.onway.common.lang.ArrayUtils;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.code.CodeGenerateComponent;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.fyapp.common.dal.daointerface.AdDAO;
import com.onway.fyapp.common.dal.daointerface.BookChapterDAO;
import com.onway.fyapp.common.dal.daointerface.BookDAO;
import com.onway.fyapp.common.dal.daointerface.BookHandleDAO;
import com.onway.fyapp.common.dal.daointerface.BookSectionDAO;
import com.onway.fyapp.common.dal.daointerface.BookUserDAO;
import com.onway.fyapp.common.dal.daointerface.BookVersionDAO;
import com.onway.fyapp.common.dal.daointerface.FunctionsLogDAO;
import com.onway.fyapp.common.dal.daointerface.OntimeDAO;
import com.onway.fyapp.common.dal.daointerface.OrderDAO;
import com.onway.fyapp.common.dal.daointerface.PhoneDAO;
import com.onway.fyapp.common.dal.daointerface.SearchDAO;
import com.onway.fyapp.common.dal.daointerface.SmsLogDAO;
import com.onway.fyapp.common.dal.daointerface.SysConfigDAO;
import com.onway.fyapp.common.dal.daointerface.SysMenuDAO;
import com.onway.fyapp.common.dal.daointerface.SysRoleDAO;
import com.onway.fyapp.common.dal.daointerface.SysRoleUserDAO;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.daointerface.UserLoginLogDAO;
import com.onway.fyapp.common.dal.daointerface.UserProtocolDAO;
import com.onway.fyapp.common.dal.daointerface.UserVesionDAO;
import com.onway.fyapp.common.dal.daointerface.VersionDAO;
import com.onway.platform.common.service.template.ServiceTemplate;
import com.onway.web.template.ControllerTemplate;

/**
 * 控制器基类
 * @author Administrator
 *
 */
public class AbstractController {
	/**  */
	private static final Pattern SPLIT_PATTERN = Pattern.compile("_");

	/** logger */
	public static final Logger logger = Logger.getLogger(AbstractController.class);

	protected static String PAGE_NUM_STR = "pageNum";

	protected static String PAGE_SIZE_STR = "pageSize";

	protected static int DEFAULT_PAGE_SIZE = 10;

	protected static int DEFAULT_PAGE_NUM = 1;

	protected static final String CURRENTPAGE = "currentPage";

	protected static final String OFFSET = "offset";

	protected static final String LIMIT = "limit";

	protected static final String PAGE_FLAG = "page";

	protected static final String TOKEN_ERROR = "token不正确";

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

	protected static final String TRANSCODING_ERROR = "编码方式转型异常";

	protected static final String UPLOAD_SUCCESS = "上传成功";

	protected static final String UPLOAD_ERROR = "上传异常";

	protected DecimalFormat dfZero = new DecimalFormat("0");

	protected DecimalFormat dfDigit = new DecimalFormat("0.00");

	private static final String PAGE_NO = "pageNo";

	private static final String PAGE_SIZE = "pageSize";

	protected static final String TOTALITEMS = "totalItems";

	protected static final String RESULTLIST = "resultList";

	protected static final String TOTALPAGES = "totalPages";

	/****************************所有的Component********************************/
	
	@Resource
	protected SysConfigCacheManager sysConfigCacheManager;
	
	@Resource
    protected ControllerTemplate controllerTemplate;

    @Resource
    protected ServiceTemplate serviceTemplate;
    
	@Resource
	public CodeGenerateComponent codeGenerateComponent;

	/**************************** 所有的DAO ********************************/
	
	@Resource
	protected AdDAO adDAO;
	
	@Resource
	protected SysRoleDAO sysRoleDAO;
	
	@Resource
	protected SysMenuDAO sysMenuDAO;
	
	@Resource
	protected SysRoleUserDAO sysRoleUserDAO;

	@Resource
	protected SysConfigDAO sysConfigDAO;
	
	@Resource
	protected BookDAO BookDAO;
	
	@Resource
	protected  OrderDAO OrderDAO;

	@Resource
	protected  UserDAO UserDAO;
	
	@Resource
	protected  BookVersionDAO BookVersionDAO;
	
	@Resource
	protected  BookUserDAO bookUserDAO;

	@Resource
	protected  BookChapterDAO BookChapterDAO;
	
	@Resource
	protected  BookSectionDAO BookSectionDAO;
	
	@Resource
	protected  UserProtocolDAO UserProtocolDAO;
	
	@Resource
	protected  PhoneDAO PhoneDAO;
	
	@Resource
	protected  OntimeDAO OntimeDAO;
	
	@Resource
	protected  UserLoginLogDAO UserLoginLogDAO;
	
	@Resource
	protected  UserVesionDAO UserVesionDAO;
	
	@Resource
	protected  BookHandleDAO BookHandleDAO;
	
	@Resource
	protected  FunctionsLogDAO FunctionsLogDAO;
	
	@Resource
	protected  SearchDAO SearchDAO;
	
	@Resource
	protected  SmsLogDAO SmsLogDAO;
	
	
	@Resource
	protected  VersionDAO versionDAO;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**************************** 部分工具 ********************************/
	
	/**
	 * 获取页码
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
	
		public int getTimeLengthShow(Date beginDate, Date endDate, String remaind) {
			long abs = Math.abs(endDate.getTime() - beginDate.getTime());
			
			int timeLength = (int) (abs / (1000 * 60));
			
			int other = (int) abs % (1000 * 60);
			if (other > 0 && remaind.equals("1"))
				timeLength = timeLength + 1;
			return timeLength;
		}
		
	
		public static String getRandomNumberChar(int n) {
			String numberChar = "0123456789";
			StringBuffer sb = new StringBuffer();
			Random random = new Random();
			for(int i = 0; i < n; i++) {
				sb.append( numberChar.charAt( random.nextInt( numberChar.length() ) ) );
			}
			return sb.toString();
		}
		
	
	public int adjustPageSize(HttpServletRequest request) {
		String pageSize = request.getParameter(PAGE_SIZE);
		if (pageSize == null || !NumberUtils.isDigits(pageSize))
			return PAGE_SIZE_DIGIT;
		return Integer.parseInt(pageSize) < 1 ? PAGE_SIZE_DIGIT : Integer
				.parseInt(pageSize);
	}
	/**
	 * email校验
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		String regex = "^\\w+[-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$ ";
		return check(email, regex);
	}
	/**
	 * 邮政编码
	 * @param postCode
	 * @return
	 */
	public static boolean isPostCode(String postCode) {
		String regex = "[1-9]\\d{5}";
		return check(postCode, regex);
	}
	/**
	 * 手机号检验
	 * @param cellphone
	 * @return
	 */
	public static boolean checkCellphone(String cellphone) {
		String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
		return check(cellphone, regex);
	}

	/**
	 * 固定电话检验
	 * @param telephone
	 * @return
	 */
	public static boolean checkTelephone(String telephone) {
		String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
		return check(telephone, regex);
	}

	static boolean flag = false;
	static String regex = "";

	public static boolean check(String str, String regex) {
		try {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	/**
	 * 字符串转double
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
	 * 获取分页数据
	 * @param request
	 * @return
	 */
	protected Map<String, Integer> getPageData(final HttpServletRequest request) {

		String page = request.getParameter(CURRENTPAGE);
		String pageSize = request.getParameter(PAGE_SIZE_STR);

		
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
	 * 取参数
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
	 * 计算分页总页数
	 * @param totalItems
	 * @param pagesize
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
	  * 上传文件
	  * @param image
	  * @param absolutePath
	  * @return
	  * @throws Exception
	  */
     public File uploadFile(MultipartFile image, String absolutePath) throws Exception {

         File userAuthImage = new File(absolutePath);
         if (image == null || ArrayUtils.isEmpty(image.getBytes())) {
         }
         FileUtils.writeByteArrayToFile(userAuthImage, image.getBytes());
         
         return userAuthImage;
     }
     /**
      * 删除文件  可以删除磁盘文件
      * @param fileurl
      */
     public void deletefile(String fileurl){
    	 File file  = new File(fileurl);
    	 if(file.exists() && file.isFile()){
			try {
				file.delete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e,e);
			}
    	 }	
     }
     /**
      * 更新文件
      * @param image
      * @param absolutePath
      * @param oldurl
      * @throws Exception
      */
      public void uploadFile(MultipartFile image, String absolutePath,String oldurl) throws Exception {
    	 //先删除
    	  deletefile(oldurl);
    	  //再上传
          File userAuthImage = new File(absolutePath);
          if (image == null || ArrayUtils.isEmpty(image.getBytes())) {
          }
          FileUtils.writeByteArrayToFile(userAuthImage, image.getBytes());
      }
    
	/**
	 * IP地址工具
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = (String) request.getParameter("loginIP"); 
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
	 * AES解密
	 * @param sign
	 * @param key
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
	 * 计算时间
	 * @param i
	 * @return
	 */
	public String getstringhistorydata(int i){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE,i);
		Date time=cal.getTime();
		String da = new SimpleDateFormat("yyyy-MM-dd").format(time);
		return da;
	}

 
	/**
	 * 生成width位数的随机数
	 * @param width
	 * @return
	 */
	public static String getRandom(int width) {
		String random = "";
		for (int i = 0; i < width; i++) {
			random = random + (int) Math.floor(Math.random() * 10);
		}
		return random;
	}
	
	/**
	 * 是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 生成uuid
	 * @return
	 */
	public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString(); 
        String uuidStr=str.replace("-", "");
        return uuidStr;
      }
}
