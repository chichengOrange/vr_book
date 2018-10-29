package com.onway.web.controller.user;

import java.text.MessageFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.core.service.code.CodeGenerateComponent;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.fyapp.common.dal.dataobject.returnObj.UserInfo;
import com.onway.model.conver.UserConver;
import com.onway.model.enums.BizTypeEnum;
import com.onway.model.enums.ErrorCode;
import com.onway.platform.common.enums.BaseResultCodeEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.utils.md5.Md5Encrypt;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonQueryResult;
import com.onway.web.controller.result.JsonResult;

/**
 * 
 * @author lishuaikai
 * @Description �û�������	
 * @data 2018��7��09�� ����2:30:17
 */
@Controller
public class UserController extends AbstractController{

	@Resource
	private UserDAO userDAO;
	
	@Resource
	private CodeGenerateComponent codeGenerateComponent;
	
	/**
	 * �û���¼
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "userLogin.do", method = RequestMethod.POST)
	@ResponseBody
	public Object login(HttpServletRequest request){
		String account = request.getParameter("account");
		String psw = request.getParameter("psw");
		JsonQueryResult<UserInfo> result = new JsonQueryResult<UserInfo>(true);
		try {
			AssertUtil.notBlank(account, "�û�������Ϊ��");
			AssertUtil.notBlank(psw, "���벻��Ϊ��");
			UserDO userDO = userDAO.userLogin(account, Md5Encrypt.toMD5High(psw));
			if (userDO == null) {
				result.setBizSucc(false);
				result.setErrCode(ErrorCode.ERROR_LOGIN_PWD.getCode());
				result.setErrMsg("�û������������");
				return result;
			}else{
				UserInfo info = UserConver.buildUserInfo(userDO);
				result.setObj(info);
			}
		} catch (BaseRuntimeException e)  {
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.ERROR_LOGIN_PWD.getCode(), MessageFormat.format("��¼ʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		}
		catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��¼�쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}

	
	/**
	 * �û�ע��
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "register.do", method = RequestMethod.POST)
	@ResponseBody
	public Object registerUser(HttpServletRequest request){
		String userName = request.getParameter("userName");
		String psw = request.getParameter("psw");
		String cell = request.getParameter("cell");
		JsonQueryResult<UserInfo> result = new JsonQueryResult<UserInfo>(true);
		try {
			AssertUtil.notBlank(userName, "�û�������Ϊ��");
			AssertUtil.notBlank(psw, "���벻��Ϊ��");
			AssertUtil.state(rexCheckPassword(userName,"8"), "�û�������8~18λ�ַ��Ҳ��ܰ������Ļ������ַ�");
            AssertUtil.state(rexCheckPassword(psw,"6"), "���볤��6~18λ�ַ��Ҳ��ܰ������Ļ������ַ�");
            //�ж��ֻ��Ƿ��Ѿ���ע��
            UserDO u = userDAO.selectUserByCell(cell);
            if(u != null){
            	return new JsonResult(false, ErrorCode.CELL_BE_REGISTER.getCode(), "�ֻ����ѱ�ע��");
            }
            UserDO userDO = new UserDO();
            String userId = "";
			userId = codeGenerateComponent.nextCodeByType(BizTypeEnum.USER_ID);
            userDO.setUserId(userId);
            userDO.setCell(cell);
            userDO.setUserName(userName);
            userDO.setPsw(Md5Encrypt.toMD5High(psw));
            int insert = userDAO.insertUser(userDO);
            if(insert > 0){
            	UserDO user = userDAO.selectUserByUserId(userId);
            	UserInfo info = UserConver.buildUserInfo(user);
            	result.setObj(info);
            }
			result.setBizSucc(insert > 0 ? true : false);
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.REGISTER_FAIL.getCode(), MessageFormat.format("ע��ʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("ע���쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * ΢�ŵ�¼�����
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "weChatLogin.do", method = RequestMethod.POST)
	@ResponseBody
	public Object weChatLogin(HttpServletRequest request){
		String openId = request.getParameter("openId");
		String userName = request.getParameter("userName");
		String headUrl = request.getParameter("headUrl");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String area = request.getParameter("area");
		JsonQueryResult<UserInfo> result = new JsonQueryResult<UserInfo>(true);
		try {
			AssertUtil.notBlank(userName, "�û�������Ϊ��");
			AssertUtil.notBlank(openId, "openId����Ϊ��");
			AssertUtil.notBlank(headUrl, "ͷ����Ϊ��");
			
			UserDO wxUser = userDAO.selectUserByOpenId(openId);
			if(wxUser == null){
				String userId = "";
				userId = codeGenerateComponent.nextCodeByType(BizTypeEnum.USER_ID);
				UserDO userDO = new UserDO();
	            userDO.setUserId(userId);
	            userDO.setOpenId(openId);
	            userDO.setHeadUrl(headUrl);
	            userDO.setUserName(userName);
	            userDO.setProvince(province);
	            userDO.setCity(city);
	            userDO.setArea(area);
	            int insert = userDAO.insertUser(userDO);
	            if(insert > 0){
	            	UserDO user = userDAO.selectUserByUserId(userId);
	            	UserInfo info = UserConver.buildUserInfo(user);
	            	result.setObj(info);
	            }
				result.setBizSucc(insert > 0 ? true : false);
				
			}else{
            	UserInfo info = UserConver.buildUserInfo(wxUser);
            	result.setObj(info);
			}
			
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.REGISTER_FAIL.getCode(), MessageFormat.format("ע��ʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("ע���쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**
	 * �޸�����
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updatePsw.do", method = RequestMethod.POST)
	@ResponseBody
	public Object updatePsw(HttpServletRequest request){
		String cell = request.getParameter("cell");
		String psw = request.getParameter("psw");
		JsonResult result = new JsonResult(true);
		try {
            AssertUtil.state(rexCheckPassword(psw,"6"), "���볤��6~18λ�ַ��Ҳ��ܰ������Ļ������ַ�");
            int update = userDAO.updatePswByCell(Md5Encrypt.toMD5High(psw), cell);
			result.setBizSucc(update > 0 ? true : false);
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.UPDATE_PSW_FAIL.getCode(), MessageFormat.format("�޸�����ʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("�޸��쳣��{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	/**  * ������ʽ��֤����  * @param input  * @return  */
	public boolean rexCheckPassword(String input,String length) { 
		// 6-20 λ����ĸ�����֡��ַ�  //
//		String reg = "^([A-Z]|[a-z]|[0-9]|[`-=[];,./~!@#$%^*()_+}{:?]){6,30}$";
		String regStr = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~��@#��%����&*��������+|{}��������������'��������]){"+length+",18}$";
		return input.matches(regStr);  
   } 
	
}
