/**
 * onway.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.onway.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.base.QueryResult;
import com.onway.utils.UserInfo;
import com.onway.web.controller.result.JsonResult;

/**
 * 
 * @author guangdong.li
 * @version $Id: IndexController.java, v 0.1 2013-9-22 下午5:30:36 WJL Exp $
 */
@Controller
public class IndexController extends AbstractController{
	


    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public void index(HttpServletRequest request, ModelMap modelMap) {
    }

    @RequestMapping("/test.do")
    @ResponseBody
    public Object testDo(HttpServletRequest request, ModelMap modelMap) throws Exception {
        return new JsonResult(true, "SUCCESS", "操作成功");
    }
    
    
}
