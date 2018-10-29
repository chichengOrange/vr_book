package com.onway.web.controller.user;

import java.text.MessageFormat;

import io.netty.handler.codec.http.HttpServerCodec;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.fyapp.common.dal.daointerface.BookUserDAO;
import com.onway.fyapp.common.dal.dataobject.BookUserDO;
import com.onway.model.enums.DelEnum;
import com.onway.model.enums.ErrorCode;
import com.onway.platform.common.enums.BaseResultCodeEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;

/**
 * 
 * @author lishuaikai
 *
 * 2018��7��23�� ����9:16:20
 */
@Controller
public class UserBookController extends AbstractController{

	
	@Resource
	private BookUserDAO bookUserDAO;
	
	@RequestMapping(value = "download.do")
	@ResponseBody
	public Object download(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId = request.getParameter("userId");
		String bookId =  request.getParameter("bookId");
		//0  ���أ���1 ɾ��
		String type = request.getParameter("type");
		try {
			AssertUtil.notBlank(userId, "�û�ID����Ϊ��");
			AssertUtil.notBlank(bookId, "�鼮��Ų���Ϊ��");
			AssertUtil.notBlank(type, "���Ͳ���Ϊ��");
			//�ж����ػ���ɾ��
			if("0".equals(type)){//����
				
				BookUserDO userBook = bookUserDAO.queryByUserIdAndBookIdLocalFlg(userId, DelEnum.NOTDELETE.getCode(), bookId);
				if(userBook != null){
					return new JsonResult(true, "", "");
				}
				//���Ƿ�ɾ���������ص�
				BookUserDO userBookDO = bookUserDAO.queryByUserIdAndBookIdLocalFlg(userId, DelEnum.DELETE.getCode(), bookId);
				if(userBookDO != null){
					//����״̬
					bookUserDAO.updateBookLocalFlg(DelEnum.NOTDELETE.getCode(), userId, bookId);
				}else{
					//��������
					BookUserDO bookUser = new BookUserDO();
					bookUser.setBookId(bookId);
					bookUser.setDeleteFlg(DelEnum.NOTDELETE.getCode());
					bookUser.setLocalFlg(DelEnum.NOTDELETE.getCode());
					bookUser.setUserId(userId);
					bookUser.setVersion("0");
					bookUserDAO.insert(bookUser);
				}
			}else{
				//ɾ��
				bookUserDAO.updateBookLocalFlg(DelEnum.DELETE.getCode(), userId, bookId);
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "����Ϊ��");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("����ʧ�ܣ�{0}", new Object[]{e.getMessage()}));
		}catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("�����쳣��{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
}