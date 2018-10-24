package com.onway.web.controller.mng;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.web.controller.AbstractController;


@Controller
public class BannerController extends AbstractController{
	
//显示banner页面跳转
	@RequestMapping("/selectallbaner.html")
	public String selectBanner(){
		return "html/banner/selectallbanner";
	}
	
	
	
//添加banner
	@RequestMapping("/banner.do")
	@ResponseBody
	public Object addbanner(HttpServletRequest request, String bannerType, String bannerImg, String bannerContent, 
			String bannerPosition, int rank, String status ){
		final BaseResult baseResult = new BaseResult(true);
		serviceTemplate.execute(baseResult, new ServiceCheckCallback() {
			
			@Override
			public void executeService() {
				
			}
			
			@Override
			public void check() {
				// TODO Auto-generated method stub
				
			}
		});
		return null;
	}

}


