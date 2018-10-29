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
 * ����ͳ�����
 * @author lishuaikai
 *
 * 2018��7��23�� ����12:51:37
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
	 * ��������ͳ��
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
			AssertUtil.notBlank(keyWords, "�ؼ��ֲ���Ϊ��");
			AssertUtil.notBlank(type, "�ؼ��ֲ���Ϊ��");
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * �û�app�汾ͳ��
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
			AssertUtil.notBlank(appVersion, "�汾�Ų���Ϊ��");
			//�ж�֮ǰ��û���û����ݣ�����У����°汾
			UserVesionDO userVesion = userVesionDAO.queryByUserId(userId);
			if(userVesion != null){
				//���°汾
				userVesionDAO.updateVersion(appVersion, userId);
				return result;
			}
			//��������
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("����ʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("�����쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * Ӳ����ϵͳ����˽����ͳ��
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
		String province = request.getParameter("province");//ʡ
		String city = request.getParameter("city");//s��
		String area = request.getParameter("area");//��
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * appʹ������ͳ�ƣ�����
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * appʹ������ͳ�ƣ��˳�
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
			
			//��������
			int update = ontimeDAO.updateByUserId(interfaces, ontimeDO.getId());
			if(update<=0){
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.OPERATION_FAIL.getCode());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * �鿴�û�Э������ͳ��
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * �鼮ʹ��ͳ��
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bookUse.do")
	@ResponseBody
	public Object bookUse(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String bookId = request.getParameter("bookId");
		String type = request.getParameter("type");//������ɨ����桡��ɨ����淵�������桡��ÿ���鼮ɾ��ʱ
		try {
			AssertUtil.notBlank(bookId, "�鼮��Ų���Ϊ��");
			AssertUtil.notBlank(userId, "�û�ID����Ϊ��");
			AssertUtil.notBlank(type, "�������Ͳ���Ϊ��");
			//�ж��ǲ��ǵ�һ�β���
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * �û���¼��ʽͳ��
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "userLoginLog.do")
	@ResponseBody
	public Object userLoginLog(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String type = request.getParameter("type");//'0 ע����½��1 ����״̬��½��2 �˺������½��3 �ֻ��������½',
		try {
			AssertUtil.notBlank(userId, "�û�ID����Ϊ��");
			AssertUtil.notBlank(type, "�������Ͳ���Ϊ��");
		/*	//�ж��ǲ��ǵ�һ�β���
			
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * ���ܰ�ťʹ��ͳ��
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
			AssertUtil.notBlank(userId, "�û�ID����Ϊ��");
			//�ж��ǲ��ǵ�һ�β���
			
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * �鼮�������ͳ��
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
			AssertUtil.notBlank(userId, "�û�ID����Ϊ��");
			AssertUtil.notBlank(bookId, "�鼮��Ų���Ϊ��");
			
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * �鼮����ʱ��ͳ�ƽ���
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
			AssertUtil.notBlank(userId, "�û�ID����Ϊ��");
			AssertUtil.notBlank(bookId, "�鼮��Ų���Ϊ��");
			AssertUtil.notBlank(interfaces, "���治��Ϊ��");
			
			//��ѯ���һ�����ؼ�¼
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * �鼮�������ʱ��ͳ��ƽ�������ٶ�
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
			AssertUtil.notBlank(userId, "�û�ID����Ϊ��");
			AssertUtil.notBlank(bookId, "�鼮��Ų���Ϊ��");
			AssertUtil.notBlank(speed, "ƽ�������ٶȲ���Ϊ��");
			
			//��ѯ���һ�����ؼ�¼
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * �鼮����ʧ��
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
			AssertUtil.notBlank(userId, "�û�ID����Ϊ��");
			AssertUtil.notBlank(bookId, "�鼮��Ų���Ϊ��");
			
			//��ѯ���һ�����ؼ�¼
			DownloadLogDO downloadLog = downloadLogDAO.queryByUserIdAndBookId(userId, bookId);
			if(downloadLog != null){
				downloadLogDAO.updateSpeedByUserId(downloadLog.getDownloadSpeed(), "1", downloadLog.getId());
				return result;
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * ���ܵ�ʹ��ͳ��
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "functionsPoint.do")
	@ResponseBody
	public Object functionsPoint(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String modelId = request.getParameter("modelId");//ģ��ID
		String interfaces = request.getParameter("interfaces");//����
		String type = request.getParameter("type");//0 ����ɨ�����ʱ, 1 �Ŵ����ʱ, 2 ��С����ʱ, 3 ��ת����ʱ, 4 ���ÿ����ťʱ, 5 �㿪��λ����ʱ
		String position = request.getParameter("position");
		try {
			AssertUtil.notBlank(userId, "�û�ID����Ϊ��");
			AssertUtil.notBlank(modelId, "ģ��ID����Ϊ��");
			//�ж��ǲ��ǵ�һ�β���
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * ģ��ʹ����ͳ��
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "modelUse.do")
	@ResponseBody
	public Object modelUse(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId= request.getParameter("userId");
		String modelId = request.getParameter("modelId");
		String type = request.getParameter("type");//0 ������ƣ�1 ɨ��ɹ��� 2 �����ֶ�ѡ�� 3 �ֶ�ѡ��ɹ�
		try {
			AssertUtil.notBlank(userId, "�û�ID����Ϊ��");
			AssertUtil.notBlank(modelId, "�鼮��Ų���Ϊ��");
			AssertUtil.notBlank(type, "���Ͳ���Ϊ��");
			
		
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * ��֤����Ч��ͳ��
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
			AssertUtil.notBlank(userId, "�û�ID����Ϊ��");
			AssertUtil.notBlank(imei, "�ֻ�ʶ���벻��Ϊ��");
			AssertUtil.notBlank(interfaces, "���治��Ϊ��");
			
		
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
}
