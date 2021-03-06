package com.onway.web.controller.mng;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.onway.web.frameset.smvc.session.SessionConfig;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	/** logger */
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory
			.getLogger(AuthInterceptor.class);

	/**
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String user = (String) request.getSession().getAttribute("user_id");
		String thisURL = request.getRequestURL().toString();

		if (user == null) {
			// String param = request.getRequestURI().toString();
			if (thisURL.contains("index")) {
				return true;
			} else if (thisURL.contains("LoginMng.do")) {
				return true;
			} else if (thisURL.contains("updatePassword.html")) {
				return true;
			} else if (thisURL.contains("updatepassword.do")) {
				return true;
			} else if (thisURL.contains("registerenterprise.html")) {
				return true;
			} else if (thisURL.contains("registerenterprise.do")) {
				return true;
			} else if (thisURL.contains("captcha.do")) {
				return true;
			} else if (thisURL.contains("login.do")) {
				return true;
			}
			request.getSession().setAttribute(SessionConfig.TARG_URL, thisURL);
			response.sendRedirect("index");
			return false;
		} else {
			// 判断用户是否有该权限
			// String menu = roleDAO.selectjurisidiction(Integer.valueOf(user));
			// if(StringUtils.isNotBlank(menu)){
			// if(!menu.contains(thisURL)){
			// return false;
			// }
			// }
		}
		return true;
	}

	/**
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}
}