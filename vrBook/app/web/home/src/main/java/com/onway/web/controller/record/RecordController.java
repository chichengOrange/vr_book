package com.onway.web.controller.record;

import java.text.MessageFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.daointerface.BookHandleDAO;
import com.onway.fyapp.common.dal.daointerface.DownloadLogDAO;
import com.onway.fyapp.common.dal.daointerface.FunctionsLogDAO;
import com.onway.fyapp.common.dal.daointerface.FunctionsPointLogDAO;
import com.onway.fyapp.common.dal.daointerface.ModelLogDAO;
import com.onway.fyapp.common.dal.daointerface.OntimeDAO;
import com.onway.fyapp.common.dal.daointerface.PhoneDAO;
import com.onway.fyapp.common.dal.daointerface.SearchDAO;
import com.onway.fyapp.common.dal.daointerface.SmsLogDAO;
import com.onway.fyapp.common.dal.daointerface.UserLoginLogDAO;
import com.onway.fyapp.common.dal.daointerface.UserProtocolDAO;
import com.onway.fyapp.common.dal.daointerface.UserVesionDAO;
import com.onway.fyapp.common.dal.dataobject.BookHandleDO;
import com.onway.fyapp.common.dal.dataobject.DownloadLogDO;
import com.onway.fyapp.common.dal.dataobject.FunctionLogDO;
import com.onway.fyapp.common.dal.dataobject.FunctionsLogDO;
import com.onway.fyapp.common.dal.dataobject.FunctionsPointLogDO;
import com.onway.fyapp.common.dal.dataobject.ModelLogDO;
import com.onway.fyapp.common.dal.dataobject.OntimeDO;
import com.onway.fyapp.common.dal.dataobject.PhoneDO;
import com.onway.fyapp.common.dal.dataobject.SearchDO;
import com.onway.fyapp.common.dal.dataobject.SmsLogDO;
import com.onway.fyapp.common.dal.dataobject.UserLoginLogDO;
import com.onway.fyapp.common.dal.dataobject.UserProtocolDO;
import com.onway.fyapp.common.dal.dataobject.UserVesionDO;
import com.onway.model.enums.ErrorCode;
import com.onway.platform.common.enums.BaseResultCodeEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;

/**
 * 数据统计相关
 * @author lishuaikai
 *
 * 2018年7月23日 下午12:51:37
 */
@Controller
public class RecordController extends AbstractController{

	@Resource
	private SearchDAO searchDAO;
	
	@Resource
	private UserVesionDAO userVesionDAO;
	
	@Resource
	private PhoneDAO phoneDAO;
	
	@Resource
	private OntimeDAO ontimeDAO;
	
	@Resource
	private UserProtocolDAO userProtocolDAO;
	
	@Resource
	private BookHandleDAO bookHandleDAO;
	
	@Resource
	private UserLoginLogDAO userLoginLogDAO;
	
	@Resource
	private FunctionsLogDAO functionsLogDAO;
	
	@Resource
	private DownloadLogDAO downloadLogDAO;
	
	@Resource
	private FunctionsPointLogDAO functionsPointLogDAO;
	
	@Resource
	private ModelLogDAO modelLogDAO;
	
	@Resource
	private SmsLogDAO smsLogDAO;
	/**
	 * 搜索数据统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchRecord.do")
	@ResponseBody
	public Object searchRecord(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId = request.getParameter("userId");
		String keyWords = request.getParameter("keyWords");
		String type = request.getParameter("type");
		try {
			AssertUtil.notBlank(keyWords, "关键字不能为空");
			AssertUtil.notBlank(type, "关键字不能为空");
			SearchDO searchDO = new SearchDO();
			searchDO.setKeyWords(keyWords);
			searchDO.setType(type);
			searchDO.setUserId(userId);
			int insert = searchDAO.insert(searchDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * 用户app版本统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "userAppRecord.do")
	@ResponseBody
	public Object userAppRecord(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId = request.getParameter("userId");
		String appVersion = request.getParameter("appVersion");
		String imei = request.getParameter("IMEI");
		try {
			AssertUtil.notBlank(appVersion, "版本号不能为空");
			//判断之前有没有用户数据，如果有，更新版本
			UserVesionDO userVesion = userVesionDAO.queryByUserId(userId);
			if(userVesion != null){
				//更新版本
				userVesionDAO.updateVersion(appVersion, userId);
				return result;
			}
			//插入数据
			UserVesionDO userVesionDO = new UserVesionDO();
			userVesionDO.setAppVersion(appVersion);
			userVesionDO.setUserId(userId);
			userVesionDO.setImei(imei);
			int insert = userVesionDAO.insert(userVesionDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("操作失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("操作异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * 硬件，系统，隐私数据统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "systemRecord.do")
	@ResponseBody
	public Object systemRecord(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId = request.getParameter("userId");
		String phoneType = request.getParameter("phoneType");
		String systemVersion = request.getParameter("systemVersion");
		String province = request.getParameter("province");//省
		String city = request.getParameter("city");//s市
		String area = request.getParameter("area");//区
		String imei = request.getParameter("IMEI");
		try {
			PhoneDO phoneDO = new PhoneDO();
			phoneDO.setUserId(userId);
			phoneDO.setPhoneType(phoneType);
			phoneDO.setSysVersion(systemVersion);
			phoneDO.setProvince(province);
			phoneDO.setArea(area);
			phoneDO.setCity(city);
			phoneDO.setImei(imei);
			int insert = phoneDAO.insert(phoneDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * app使用数据统计，启动
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "appUse.do")
	@ResponseBody
	public Object appUseRecord(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId = request.getParameter("userId");
		String imei = request.getParameter("IMEI");
		try {
			OntimeDO ontimeDO = new OntimeDO();
			ontimeDO.setUserId(userId);
			ontimeDO.setImei(imei);
			int insert = ontimeDAO.insert(ontimeDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * app使用数据统计，退出
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "appQuit.do")
	@ResponseBody
	public Object appQuit(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId = request.getParameter("userId");
		String imei = request.getParameter("IMEI");
		String interfaces = request.getParameter("interfaces");
		try {
			OntimeDO ontimeDO = ontimeDAO.queryByUserId(userId, imei);
			
			//更新数据
			int update = ontimeDAO.updateByUserId(interfaces, ontimeDO.getId());
			if(update<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * 查看用户协议数据统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "seeProtocols.do")
	@ResponseBody
	public Object seeProtocols(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String imei = request.getParameter("IMEI");
		String type = request.getParameter("type");
		try {
			UserProtocolDO userProtocolDO = new UserProtocolDO();
			userProtocolDO.setImei(imei);
			userProtocolDO.setProtocol(type);
			int insert = userProtocolDAO.insert(userProtocolDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * 书籍使用统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bookUse.do")
	@ResponseBody
	public Object bookUse(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String bookId = request.getParameter("bookId");
		String type = request.getParameter("type");//１进入扫描界面　２扫描界面返回主界面　３每本书籍删除时
		try {
			AssertUtil.notBlank(bookId, "书籍编号不能为空");
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(type, "操作类型不能为空");
			//判断是不是第一次操作
			BookHandleDO bookHandle = bookHandleDAO.queryByUserIdAndBookIdAndType(userId, bookId, type);
			if(bookHandle != null){
				bookHandleDAO.updateByUserId(userId, bookId, type);
				return result;
			}
			BookHandleDO bookHandleDO = new BookHandleDO();
			bookHandleDO.setBookId(bookId);
			bookHandleDO.setUserId(userId);
			bookHandleDO.setHandCount(1);
			bookHandleDO.setType(type);
			int insert = bookHandleDAO.insert(bookHandleDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * 用户登录方式统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "userLoginLog.do")
	@ResponseBody
	public Object userLoginLog(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String type = request.getParameter("type");//'0 注册后登陆，1 记忆状态登陆，2 账号密码登陆，3 手机号密码登陆',
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(type, "操作类型不能为空");
		/*	//判断是不是第一次操作
			
			UserLoginLogDO UserLoginLog = userLoginLogDAO.queryByUserIdAndType(userId, type);
			if(UserLoginLog != null){
				userLoginLogDAO.updateByUserId(userId, type);
				return result;
			}*/
			UserLoginLogDO userLoginLogDO = new UserLoginLogDO();
			userLoginLogDO.setUserId(userId);
			userLoginLogDO.setLoginCount(1);
			userLoginLogDO.setLoginType(type);
			userLoginLogDO.setIp(getIpAddr(request));
			int insert = userLoginLogDAO.insert(userLoginLogDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * 功能按钮使用统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "functions.do")
	@ResponseBody
	public Object functions(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String function = request.getParameter("function");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			//判断是不是第一次操作
			
			FunctionsLogDO functionsLogs = functionsLogDAO.queryByUserIdAndFunction(userId, function);
			if(functionsLogs != null){
				functionsLogDAO.updateByUserId(userId, function);
				return result;
			}
			FunctionsLogDO functionsLogsDO = new FunctionsLogDO();
			functionsLogsDO.setUserId(userId);
			functionsLogsDO.setClickCount(1);
			functionsLogsDO.setFunctions(function);
			int insert = functionsLogDAO.insert(functionsLogsDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * 书籍下载相关统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bookDownLog.do")
	@ResponseBody
	public Object bookDownLog(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String bookId = request.getParameter("bookId");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(bookId, "书籍编号不能为空");
			
			DownloadLogDO downloadLogDO = new DownloadLogDO();
			downloadLogDO.setUserId(userId);
			downloadLogDO.setBookId(bookId);
			//downloadLogDO.setIsSuccess("1");
			int insert = downloadLogDAO.insert(downloadLogDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * 书籍下载时，统计界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bookDownInterface.do")
	@ResponseBody
	public Object bookDownInterface(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String bookId = request.getParameter("bookId");
		String interfaces = request.getParameter("interfaces");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(bookId, "书籍编号不能为空");
			AssertUtil.notBlank(interfaces, "界面不能为空");
			
			//查询最近一条下载记录
			DownloadLogDO downloadLog = downloadLogDAO.queryByUserIdAndBookId(userId, bookId);
			if(downloadLog != null){
				downloadLogDAO.updateByUserId(interfaces, downloadLog.getId());
				return result;
			}
			DownloadLogDO downloadLogDO = new DownloadLogDO();
			downloadLogDO.setUserId(userId);
			downloadLogDO.setBookId(bookId);
			//downloadLogDO.setIsSuccess("1");
			downloadLogDO.setInterfaces(interfaces);
			int insert = downloadLogDAO.insert(downloadLogDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * 书籍下载完成时，统计平均下载速度
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bookDownSpeed.do")
	@ResponseBody
	public Object bookDownSpeed(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String bookId = request.getParameter("bookId");
		String speed = request.getParameter("speed");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(bookId, "书籍编号不能为空");
			AssertUtil.notBlank(speed, "平均下载速度不能为空");
			
			//查询最近一条下载记录
			DownloadLogDO downloadLog = downloadLogDAO.queryByUserIdAndBookId(userId, bookId);
			if(downloadLog != null){
				downloadLogDAO.updateSpeedByUserId(speed, "0", downloadLog.getId());
				return result;
			}
			DownloadLogDO downloadLogDO = new DownloadLogDO();
			downloadLogDO.setUserId(userId);
			downloadLogDO.setBookId(bookId);
			downloadLogDO.setIsSuccess("0");
			downloadLogDO.setDownloadSpeed(speed);
			int insert = downloadLogDAO.insert(downloadLogDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * 书籍下载失败
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bookDownFail.do")
	@ResponseBody
	public Object bookDownFail(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String bookId = request.getParameter("bookId");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(bookId, "书籍编号不能为空");
			
			//查询最近一条下载记录
			DownloadLogDO downloadLog = downloadLogDAO.queryByUserIdAndBookId(userId, bookId);
			if(downloadLog != null){
				downloadLogDAO.updateSpeedByUserId(downloadLog.getDownloadSpeed(), "1", downloadLog.getId());
				return result;
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * 功能点使用统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "functionsPoint.do")
	@ResponseBody
	public Object functionsPoint(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String modelId = request.getParameter("modelId");//模型ID
		String interfaces = request.getParameter("interfaces");//界面
		String type = request.getParameter("type");//0 返回扫描界面时, 1 放大操作时, 2 缩小操作时, 3 旋转操作时, 4 点击每个按钮时, 5 点开部位介绍时
		String position = request.getParameter("position");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(modelId, "模型ID不能为空");
			//判断是不是第一次操作
			if("5".equals(type)){
				interfaces = null;
			}
			FunctionsPointLogDO functionsPointLog = functionsPointLogDAO.queryByUserIdAndFunction(userId, interfaces, type, position);
			if(functionsPointLog != null){
				functionsPointLogDAO.updateByUserId(userId, interfaces, type, position);
				return result;
			}
			FunctionsPointLogDO functionsPointLogDO = new FunctionsPointLogDO();
			functionsPointLogDO.setUserId(userId);
			functionsPointLogDO.setModelId(modelId);
			functionsPointLogDO.setInterfaces(interfaces);
			functionsPointLogDO.setOperationCount(1);
			functionsPointLogDO.setOperationType(type);
			functionsPointLogDO.setPosition(position);
			int insert = functionsPointLogDAO.insert(functionsPointLogDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * 模型使用率统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "modelUse.do")
	@ResponseBody
	public Object modelUse(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String modelId = request.getParameter("modelId");
		String type = request.getParameter("type");//0 打开闪光灯，1 扫描成功， 2 进入手动选择， 3 手动选择成功
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(modelId, "书籍编号不能为空");
			AssertUtil.notBlank(type, "类型不能为空");
			
		
			ModelLogDO modelLogDO = new ModelLogDO();
			modelLogDO.setUserId(userId);
			modelLogDO.setModelId(modelId);
			modelLogDO.setType(type);
			int insert = modelLogDAO.insert(modelLogDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * 验证码有效率统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "smsLog.do")
	@ResponseBody
	public Object smsLog(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String imei = request.getParameter("IMEI");
		String cell = request.getParameter("cell");
		String interfaces = request.getParameter("interfaces");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(imei, "手机识别码不能为空");
			AssertUtil.notBlank(interfaces, "界面不能为空");
			
		
			SmsLogDO smsLogDO = new SmsLogDO();
			smsLogDO.setUserId(userId);
			smsLogDO.setCell(cell);
			smsLogDO.setImei(imei);
			smsLogDO.setInterfaces(interfaces);
			int insert = smsLogDAO.insert(smsLogDO);
			if(insert<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
}
