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
 * 2018年7月23日 上午9:16:20
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
		//0  下载，，1 删除
		String type = request.getParameter("type");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(bookId, "书籍编号不能为空");
			AssertUtil.notBlank(type, "类型不能为空");
			//判断下载还是删除
			if("0".equals(type)){//下载
				
				BookUserDO userBook = bookUserDAO.queryByUserIdAndBookIdLocalFlg(userId, DelEnum.NOTDELETE.getCode(), bookId);
				if(userBook != null){
					return new JsonResult(true, "", "");
				}
				//看是否删除后又下载的
				BookUserDO userBookDO = bookUserDAO.queryByUserIdAndBookIdLocalFlg(userId, DelEnum.DELETE.getCode(), bookId);
				if(userBookDO != null){
					//更改状态
					bookUserDAO.updateBookLocalFlg(DelEnum.NOTDELETE.getCode(), userId, bookId);
				}else{
					//插入数据
					BookUserDO bookUser = new BookUserDO();
					bookUser.setBookId(bookId);
					bookUser.setDeleteFlg(DelEnum.NOTDELETE.getCode());
					bookUser.setLocalFlg(DelEnum.NOTDELETE.getCode());
					bookUser.setUserId(userId);
					bookUser.setVersion("0");
					bookUserDAO.insert(bookUser);
				}
			}else{
				//删除
				bookUserDAO.updateBookLocalFlg(DelEnum.DELETE.getCode(), userId, bookId);
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("操作失败：{0}", new Object[]{e.getMessage()}));
		}catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("操作异常：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
}
