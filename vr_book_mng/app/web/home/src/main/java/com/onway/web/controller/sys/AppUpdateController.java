package com.onway.web.controller.sys;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.daointerface.SysConfigDAO;
import com.onway.fyapp.common.dal.dataobject.VersionDO;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;

@Controller
public class AppUpdateController extends AbstractController {
	@Autowired
	private SysConfigDAO sysConfigDAO;

	@RequestMapping("/selectAppVersion.do")
	@ResponseBody
	public JSONObject selectAppVersion(HttpServletRequest request,
			String VERSION_NUM, String VERSION_TYPE, String STATUS,
			Integer offset, Integer limit) {

		JSONObject jo = new JSONObject();
		if (-1 == limit) {
			jo.put("rows", versionDAO.selectVersion(VERSION_NUM, VERSION_TYPE,
					STATUS, offset, Integer.valueOf("2147483647")));

		} else {
			jo.put("rows", versionDAO.selectVersion(VERSION_NUM, VERSION_TYPE,
					STATUS, offset, limit));
		}
		jo.put("total", versionDAO.selectVersionCount(VERSION_NUM,
				VERSION_TYPE, STATUS));
		return jo;
	}

	@RequestMapping("/addVersion.do")
	@ResponseBody
	public Object addVersion(final HttpServletRequest request,
			final VersionDO versionDO) {
		final JsonResult result = new JsonResult(true);
		if (StringUtils.isBlank(versionDO.getVersionNum())) {
			result.setSuccess(false);
			result.setMessage("版本号不能为空！");
			return result; 
		}
		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {
				sysConfigDAO.updateByKey("IOS".equals(versionDO
						.getVersionType()) ? "VERSION_IOS" : "VERSION_ANDROID",
						versionDO.getVersionNum());
				sysConfigDAO.updateByKey("IOS".equals(versionDO
						.getVersionType()) ? "IOS_DOWNLOAD_URL" : "ANDROID_DOWNLOAD_URL",
						versionDO.getDownloadUrl());
				int insert = versionDAO.insert(versionDO);
				result.setSuccess(insert > 0 ? true : false);
				result.setMessage(insert > 0 ? "添加成功" : "添加失败");
			}

			@Override
			public void check() {

			}
		});
		return result;
	}

	/**
	 * 更新书籍
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/updateVersion.do")
	@ResponseBody
	public Object updateVersion(final HttpServletRequest request,
			final VersionDO versionDO, final Integer id) {
		final JsonResult result = new JsonResult(true);
		if (null == id) {
			result.setSuccess(false);
			result.setMessage("参数错误!");
			return result;
		}

		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {
				int updatVersion = versionDAO.updatVersion(
						versionDO.getVersionNum(), versionDO.getVersionType(),
						versionDO.getStatus(),versionDO.getDownloadUrl(), versionDO.getMemo(), id);
				result.setSuccess(updatVersion > 0 ? true : false);
				result.setMessage(updatVersion > 0 ? "更新成功" : "更新失败");
			}

			@Override
			public void check() {

			}
		});
		return result;
	}

	@RequestMapping("/deleteVersion.do")
	@ResponseBody
	public Object deleteVersion(final Integer id) {
		final JsonResult result = new JsonResult(true);
		controllerTemplate.execute(result, new ServiceCheckCallback() {
 
			@Override
			public void executeService() {
				if (id != null) {
					int delete = versionDAO.delete(id);
					result.setSuccess(delete > 0 ? true : false);
					result.setMessage(delete > 0 ? "删除成功" : "删除失败");
				} else {
					result.setSuccess(false);
					result.setMessage("数据异常!");
				}
			}

			@Override
			public void check() {

			}
		});
		return result;
	}
}
