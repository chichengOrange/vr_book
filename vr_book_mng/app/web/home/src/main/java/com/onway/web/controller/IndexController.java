/**
 * onway.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.onway.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.Md5Encrypt;

@Controller
public class IndexController extends AbstractController {

	@RequestMapping("/test")
	@ResponseBody
	public Object test(HttpServletRequest request) {
		return new JsonResult(true, "code", request.getParameter("name"));
	}

	/**
	 * 跳转到登陆页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap modelMap) {
		return "html/login";
	}

	@RequestMapping(value = "/updateUsernameOrPassword.action")
	public String updateUsernameOrPassword(Integer type,
			HttpServletRequest request) {

		return "/html/updateUsernameOrPassword.html";
	}
	@RequestMapping(value = "/updatepassword.do")
	public String updatePassword(Integer type,
			HttpServletRequest request) {
		
		return "html/updatepassword";
	}

	/**
	 * 更改密码
	 * 
	 * @param password
	 * @param newpassword
	 * @param newpasswords
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatemypassword.do")
	@ResponseBody
	public Object updatemypassword(final String password,
			final String newpassword, final String newpasswords,
			final HttpServletRequest request) {
		final JsonResult result = new JsonResult(true);
		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {
				// TODO Auto-generated method stub

				result.setMessage(sysRoleUserDAO.updatepassword(
						Md5Encrypt.toMD5High(newpassword), request.getSession()
								.getAttribute("user_id") + "") > 0 ? "操作成功,系统即将退出!"
						: "操作失败");
			}

			@Override
			public void check() {
				// TODO Auto-generated method stub
				AssertUtil.notBlank(password, "密码不能为空");
				AssertUtil.notBlank(newpassword, "新密码不能为空");
				AssertUtil.notBlank(newpasswords, "新密码不能为空");
				AssertUtil.state(newpassword.equals(newpasswords), "两次密码不一致");
				AssertUtil.state(
						Md5Encrypt.toMD5High(password).equals(
								sysRoleUserDAO.selectpassword(request
										.getSession().getAttribute("user_id")
										+ "")), "原密码输入有误");
			}
		});
		return result;
	}

	/**
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/home.html")
	public String indexhtml(HttpSession session, HttpServletRequest request) {
		request.setAttribute("USER_NAME", session.getAttribute("username"));
		request.setAttribute("userid", session.getAttribute("user_id"));
		request.setAttribute("firstmenu",
				JSONArray.parseArray(session.getAttribute("role") + ""));
		return "html/index";
	}

	@RequestMapping("/welcome.html")
	public String welcome(ModelMap map) {

		return "html/welcome";
	}

	/**
	 * 
	 * @param re
	 * @return
	 */
	@RequestMapping("/removesession.do")
	@ResponseBody
	public String removesession(HttpServletRequest request) {
		request.getSession().removeAttribute("username");
		request.getSession().removeAttribute("user_id");
		return "html/login";
	}

	@RequestMapping("/login.do")
	@ResponseBody
	public JSONObject login(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		JSONObject result = new JSONObject();
		result.put("flag", false);

		Map<String, Object> user = sysRoleUserDAO.login(userName,
				Md5Encrypt.toMD5High(password));
		if (null == user) {
			result.put("data", "用户名或密码有误");
			return result;
		} else {
			request.getSession().setAttribute("user_id", user.get("user_id"));
			request.getSession().setAttribute("username", user.get("username"));
			request.getSession().setAttribute("role", user.get("JURISDICTION"));
			result.put("data", "登陆成功");
			result.put("flag", true);
		}
		return result;
	}

	/**
	 * 跳转界面
	 * @param url 
	 * @return
	 */
	@RequestMapping("/tourl.do")
	public String tourl(String url,String userId,HttpSession session,HttpServletRequest re){
		String menu = (String)session.getAttribute("role");
    	if(StringUtils.isNotBlank(menu)){
			if(!menu.contains(url)&&!url.contains("updatepassword")){
				re.getSession().removeAttribute("username");
		    	re.getSession().removeAttribute("user_id");
				return "html/login";
	    	}
    	}
		if(StringUtils.isNotBlank(userId))
			return url+"?userId="+userId;
		return url;
	}
	/**
	 * Home页面今日信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/selecthometotoday.do")
	@ResponseBody
	public JSONObject selectHomeToToday(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		//查询今日新增用户
		long selectTodayUser = UserDAO.selectTodayUser();
		//查询今日新增用户
		long selectMonthUser = UserDAO.selectMonthUser();
		//查询今日新增用户
		long selectQuarterUser = UserDAO.selectQuarterUser();
		result.put("todayUser",selectTodayUser );
		result.put("monthUser",selectMonthUser );
		result.put("quarterUser",selectQuarterUser );
		//查询今日充值金额
		return result;
	}

}
