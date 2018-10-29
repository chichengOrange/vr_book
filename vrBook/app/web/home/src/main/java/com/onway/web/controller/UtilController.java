/**
 * onway.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.onway.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.web.controller.result.JsonResult;


@Controller
public class UtilController extends AbstractController{
	
	/**
	 * 刷新缓存
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/reloadSys.do")
    @ResponseBody
    public Object reloadSys(HttpServletRequest request, ModelMap modelMap) throws Exception {
    	
    	sysConfigCacheManager.reload();
    	
        return new JsonResult(true, "SUCCESS", "操作成功");
    }
}
