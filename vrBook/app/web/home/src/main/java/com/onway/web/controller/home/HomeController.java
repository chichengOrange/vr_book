package com.onway.web.controller.home;

import java.text.MessageFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.fyapp.common.dal.daointerface.SysConfigDAO;
import com.onway.fyapp.common.dal.daointerface.VersionDAO;
import com.onway.fyapp.common.dal.dataobject.SysConfigDO;
import com.onway.fyapp.common.dal.dataobject.VersionDO;
import com.onway.fyapp.common.dal.dataobject.returnObj.VersionInfo;
import com.onway.model.enums.ErrorCode;
import com.onway.model.enums.StatusEnum;
import com.onway.platform.common.enums.BaseResultCodeEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.utils.DateUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonQueryResult;
import com.onway.web.controller.result.JsonResult;

/**
 * 
 * @author lishuaikai
 *
 * 2018��7��12�� ����2:56:00
 */
@Controller
public class HomeController extends AbstractController{

	@Resource
	private VersionDAO versionDAO;
	
	@Resource
	private SysConfigDAO sysConfigDAO;
	
	/**
	 * ����key��ȡϵͳ����
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getSomeInfo.do")
	public Object getConfig(HttpServletRequest request){
		String key = request.getParameter("key");
		JsonQueryResult<String> result = new JsonQueryResult<String>(true);
		try {
			AssertUtil.notBlank(key, "��������Ϊ��");
			SysConfigDO sysConfigDO = sysConfigDAO.selectByKey(key);
			if(sysConfigDO != null){
				result.setObj(sysConfigDO.getSysValue());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			return new JsonResult(false, "", MessageFormat.format("��ѯʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, "", MessageFormat.format("��ѯ�쳣��{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	
	/**
	 * ��������
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "aboutUs.do")
	@ResponseBody
	public Object aboutUs(HttpServletRequest request){
		JsonQueryResult<String> result = new JsonQueryResult<String>(true);
		try {
			result.setObj(sysConfigCacheManager.getConfigValue("ABOUT_US"));
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("�鿴ʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("�鿴�쳣��{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	
	/**
	 * ��ҳapp����˵��
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateExplain.do")
	@ResponseBody
	public Object updateExplain(HttpServletRequest request){
		JsonQueryResult<String> result = new JsonQueryResult<String>(true);
		String type = request.getParameter("type");
		try {
			AssertUtil.notBlank(type, "ϵͳ����Ϊ��");
			VersionDO versionDO = versionDAO.queryNewVersion(type, StatusEnum.ENABLED.getCode());
			if(versionDO != null){
				result.setObj(versionDO.getMemo());
			}else{
				result.setObj("���޸���");
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("��ȡ����˵��ʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ȡ����˵���쳣��{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	/**
	 * �����½ӿ�
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateVersion.do")
	@ResponseBody
	public Object updateVersion(HttpServletRequest request){
		JsonQueryResult<VersionInfo> result = new JsonQueryResult<VersionInfo>(true);
		String version = request.getParameter("version");
		String type = request.getParameter("type");
		try {
			AssertUtil.notBlank(version, "�汾�Ų���Ϊ��");
			AssertUtil.notBlank(type, "ϵͳ����Ϊ��");
			sysConfigCacheManager.reload();
			String newest = sysConfigCacheManager.getConfigValue("VERSION_"+type);
			int i = compareVersion2(version, newest);
			VersionInfo info = new VersionInfo();
			info.setVersion_type(type);
			if(i == -1){
				info.setNeedUpdate(true);
				info.setVersionNum(newest);
				info.setDownloadUrl(sysConfigCacheManager.getConfigValue(type + "_DOWNLOAD_URL"));
				VersionDO versionDO = versionDAO.queryVersion(newest, type, StatusEnum.ENABLED.getCode());
				if(versionDO != null){
					info.setDescribe(versionDO.getMemo());
					info.setCreateTime(DateUtil.dateToString(versionDO.getGmtCreate(), "yyyy-MM-dd HH:mm"));
				}
			}else{
				info.setNeedUpdate(false);
				info.setVersionNum(newest);
			}
			result.setObj(info);
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("������ʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("�������쳣��{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
}