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
 * 2018年7月12日 下午2:56:00
 */
@Controller
public class HomeController extends AbstractController{

	@Resource
	private VersionDAO versionDAO;
	
	@Resource
	private SysConfigDAO sysConfigDAO;
	
	/**
	 * 根据key获取系统配置
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getSomeInfo.do")
	public Object getConfig(HttpServletRequest request){
		String key = request.getParameter("key");
		JsonQueryResult<String> result = new JsonQueryResult<String>(true);
		try {
			AssertUtil.notBlank(key, "参数不能为空");
			SysConfigDO sysConfigDO = sysConfigDAO.selectByKey(key);
			if(sysConfigDO != null){
				result.setObj(sysConfigDO.getSysValue());
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			return new JsonResult(false, "", MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, "", MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	
	/**
	 * 关于我们
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
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("查看失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查看异常：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	
	/**
	 * 插页app更新说明
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateExplain.do")
	@ResponseBody
	public Object updateExplain(HttpServletRequest request){
		JsonQueryResult<String> result = new JsonQueryResult<String>(true);
		String type = request.getParameter("type");
		try {
			AssertUtil.notBlank(type, "系统不能为空");
			VersionDO versionDO = versionDAO.queryNewVersion(type, StatusEnum.ENABLED.getCode());
			if(versionDO != null){
				result.setObj(versionDO.getMemo());
			}else{
				result.setObj("暂无更新");
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("获取更新说明失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("获取更新说明异常：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	/**
	 * 检查更新接口
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
			AssertUtil.notBlank(version, "版本号不能为空");
			AssertUtil.notBlank(type, "系统不能为空");
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
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("检查更新失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("检查更新异常：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
}
