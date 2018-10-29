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
 * ���ŷ���
 * 
 * <pre>
 *  <li>1���û�ע��ʱ��Ҫʹ�ö��ŷ����ȡ��֤��</li>
 *  <li>2����������ʱ��Ҫʹ�ö��ŷ����ȡ��֤��</li>
 * </pre>
 * 
 * @author dou.guo
 * @version $Id: SmsController.java, v 0.1 2016��10��19�� ����2:25:31 dou.guo Exp $
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
     * ע�ᷢ����֤��
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
            // ��1����֤�ֻ��ŵĺϷ���
            cellVerify(cell);

            //�ж��ֻ����Ƿ�ע��
            UserDO userDO = userDAO.selectUserByCell(cell);
            if(userDO == null){
            	return new JsonResult(false, "", "�ֻ��Ż�δע��");
            }
            // ��2��������֤��
            JsonQueryResult<String> res =  smsComponent.sendVerifyCode(cell);
            boolean isSendCode = res.isBizSucc();

            if (!isSendCode)
                return new JsonResult(false, "", "������֤��ʧ��");

            return new JsonResult(true, "", "");
        } catch (BaseRuntimeException e) {
            LogUtil.error(
                logger,
                MessageFormat.format("������֤��ʧ�ܣ�cell:{0},message:{1}",
                    new Object[] { cell, e.getMessage() }));
            return new JsonResult(false, e.getCode(), e.getMessage());
        } catch (Exception e) {
            LogUtil.error(
                logger,
                MessageFormat.format("������֤���쳣��cell:{0},message:{1}",
                    new Object[] { cell, e.getCause() }));
            return new JsonResult(false, "",
                "������֤���쳣��");
        }
    }

    
    /**
     * �޸����뷢����֤��
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
            // ��1����֤�ֻ��ŵĺϷ���
            cellVerify(cell);

            //�ж��ֻ����Ƿ�ע��
            UserDO userDO = userDAO.selectUserByCell(cell);
            if(userDO == null){
            	return new JsonResult(false, "", "�ֻ���δ��ע��");
            }
            
            // ��2��������֤��
            JsonQueryResult<String> res =  smsComponent.sendVerifyCode(cell);
            boolean isSendCode = res.isBizSucc();

            if (!isSendCode)
                return new JsonResult(false, "", "������֤��ʧ��");

            return new JsonResult(true, "", "");
        } catch (BaseRuntimeException e) {
            LogUtil.error(
                logger,
                MessageFormat.format("������֤��ʧ�ܣ�cell:{0},message:{1}",
                    new Object[] { cell, e.getMessage() }));
            return new JsonResult(false, e.getCode(), e.getMessage());
        } catch (Exception e) {
            LogUtil.error(
                logger,
                MessageFormat.format("������֤���쳣��cell:{0},message:{1}",
                    new Object[] { cell, e.getCause() }));
            return new JsonResult(false, ErrorCode.SYSTEM_ERROR.getCode(),
                ErrorCode.SYSTEM_ERROR.getDesc());
        }
    }
    
    /**
     * У����֤��	
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
            // ��1����֤�ֻ��ŵĺϷ���
            cellVerify(cell);

            // ��2��У����֤��
            JsonResult res = smsComponent.verifyCode(cell, checkCode);
            boolean isSendCode = res.isBizSucc();

            if (!isSendCode)
                return new JsonResult(false, "", res.getErrMsg());

            return new JsonResult(true, "", "");
        } catch (BaseRuntimeException e) {
            LogUtil.error(
                logger,
                MessageFormat.format("У����֤��ʧ�ܣ�cell:{0},message:{1}",
                    new Object[] { cell, e.getMessage() }));
            return new JsonResult(false, e.getCode(), e.getMessage());
        } catch (Exception e) {
            LogUtil.error(
                logger,
                MessageFormat.format("У����֤���쳣��cell:{0},message:{1}",
                    new Object[] { cell, e.getCause() }));
            return new JsonResult(false, ErrorCode.SYSTEM_ERROR.getCode(),
                ErrorCode.SYSTEM_ERROR.getDesc());
        }
    }
    
    
}