package com.onway.web.controller.book;

import java.text.MessageFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.fyapp.common.dal.daointerface.AdDAO;
import com.onway.fyapp.common.dal.dataobject.AdDO;
import com.onway.fyapp.common.dal.dataobject.returnObj.AdInfo;
import com.onway.model.conver.AdConver;
import com.onway.model.enums.ErrorCode;
import com.onway.model.enums.StatusEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonQueryResult;
import com.onway.web.controller.result.JsonResult;

/**
 * ��������
 * @author lishuaikai
 *
 * 2018��7��17�� ����9:19:48
 */
@Controller
public class AdController extends AbstractController{

	@Resource
	private AdDAO adDAO;
	
	/**
	 * ��ȡ���
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryAd.do")
	@ResponseBody
	public Object queryAd(HttpServletRequest request){
		JsonQueryResult<AdInfo> result = new JsonQueryResult<AdInfo>(true);
		try {
			AdDO adDO = adDAO.queryAd(StatusEnum.ENABLED.getCode());
			AdInfo info = AdConver.buildAdInfo(adDO);
			result.setObj(info);
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.GET_AD_FAIL.getCode(), MessageFormat.format("��ȡ���ʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("��ȡ����쳣��{0}", new Object[]{e.getMessage()}));
		}
		return result;
		
	}
	
}