package com.onway.web.controller.sms;

import java.text.MessageFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.core.service.SmsComponent;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.model.enums.ErrorCode;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.utils.LogUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonQueryResult;
import com.onway.web.controller.result.JsonResult;


/**
 * 短信服务
 * 
 * <pre>
 *  <li>1：用户注册时需要使用短信服务获取验证码</li>
 *  <li>2：忘记密码时需要使用短信服务获取验证码</li>
 * </pre>
 * 
 * @author dou.guo
 * @version $Id: SmsController.java, v 0.1 2016年10月19日 下午2:25:31 dou.guo Exp $
 */
@Controller
public class SmsController extends AbstractController {

    /** logger */
    private static final Logger logger = Logger.getLogger(SmsController.class);
    
    @Resource
    private SmsComponent smsComponent;
    
    @Resource
    private UserDAO userDAO;

    /**
     * 注册发送验证码
     * 
     * @param request
     * @param modelMap
     * @return 
     * 
     * method=RequestMethod.POST,
     */
    @RequestMapping(method = RequestMethod.POST, value = "sendVerifyCode.do")
    @ResponseBody
    public Object sendVerifyCode(HttpServletRequest request, ModelMap modelMap) {
        String cell = request.getParameter("cell");
        try {
            // 【1】验证手机号的合法性
            cellVerify(cell);

            //判断手机号是否被注册
            UserDO userDO = userDAO.selectUserByCell(cell);
            if(userDO == null){
            	return new JsonResult(false, "", "手机号还未注册");
            }
            // 【2】发送验证码
            JsonQueryResult<String> res =  smsComponent.sendVerifyCode(cell);
            boolean isSendCode = res.isBizSucc();

            if (!isSendCode)
                return new JsonResult(false, "", "发送验证码失败");

            return new JsonResult(true, "", "");
        } catch (BaseRuntimeException e) {
            LogUtil.error(
                logger,
                MessageFormat.format("发送验证码失败，cell:{0},message:{1}",
                    new Object[] { cell, e.getMessage() }));
            return new JsonResult(false, e.getCode(), e.getMessage());
        } catch (Exception e) {
            LogUtil.error(
                logger,
                MessageFormat.format("发送验证码异常！cell:{0},message:{1}",
                    new Object[] { cell, e.getCause() }));
            return new JsonResult(false, "",
                "发送验证码异常！");
        }
    }

    
    /**
     * 修改密码发送验证码
     * 
     * @param request
     * @param modelMap
     * @return 
     * 
     * method=RequestMethod.POST,
     */
    @RequestMapping(method = RequestMethod.POST, value = "sendVerifyCodeUpdatePsw.do")
    @ResponseBody
    public Object sendVerifyCodeUpdatePsw(HttpServletRequest request, ModelMap modelMap) {
        String cell = request.getParameter("cell");
        try {
            // 【1】验证手机号的合法性
            cellVerify(cell);

            //判断手机号是否被注册
            UserDO userDO = userDAO.selectUserByCell(cell);
            if(userDO == null){
            	return new JsonResult(false, "", "手机号未被注册");
            }
            
            // 【2】发送验证码
            JsonQueryResult<String> res =  smsComponent.sendVerifyCode(cell);
            boolean isSendCode = res.isBizSucc();

            if (!isSendCode)
                return new JsonResult(false, "", "发送验证码失败");

            return new JsonResult(true, "", "");
        } catch (BaseRuntimeException e) {
            LogUtil.error(
                logger,
                MessageFormat.format("发送验证码失败，cell:{0},message:{1}",
                    new Object[] { cell, e.getMessage() }));
            return new JsonResult(false, e.getCode(), e.getMessage());
        } catch (Exception e) {
            LogUtil.error(
                logger,
                MessageFormat.format("发送验证码异常！cell:{0},message:{1}",
                    new Object[] { cell, e.getCause() }));
            return new JsonResult(false, ErrorCode.SYSTEM_ERROR.getCode(),
                ErrorCode.SYSTEM_ERROR.getDesc());
        }
    }
    
    /**
     * 校验验证码	
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "verifyCode.do")
    @ResponseBody
    public Object verifyCode(HttpServletRequest request, ModelMap modelMap) {
        String cell = request.getParameter("cell");
        String checkCode = request.getParameter("checkCode");
        try {
            // 【1】验证手机号的合法性
            cellVerify(cell);

            // 【2】校验验证码
            JsonResult res = smsComponent.verifyCode(cell, checkCode);
            boolean isSendCode = res.isBizSucc();

            if (!isSendCode)
                return new JsonResult(false, "", res.getErrMsg());

            return new JsonResult(true, "", "");
        } catch (BaseRuntimeException e) {
            LogUtil.error(
                logger,
                MessageFormat.format("校验验证码失败，cell:{0},message:{1}",
                    new Object[] { cell, e.getMessage() }));
            return new JsonResult(false, e.getCode(), e.getMessage());
        } catch (Exception e) {
            LogUtil.error(
                logger,
                MessageFormat.format("校验验证码异常！cell:{0},message:{1}",
                    new Object[] { cell, e.getCause() }));
            return new JsonResult(false, ErrorCode.SYSTEM_ERROR.getCode(),
                ErrorCode.SYSTEM_ERROR.getDesc());
        }
    }
    
    
}