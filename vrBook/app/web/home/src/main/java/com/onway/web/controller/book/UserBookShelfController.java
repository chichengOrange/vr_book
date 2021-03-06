package com.onway.web.controller.book;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.fyapp.common.dal.daointerface.BookDAO;
import com.onway.fyapp.common.dal.daointerface.BookUserDAO;
import com.onway.fyapp.common.dal.daointerface.BookVersionDAO;
import com.onway.fyapp.common.dal.daointerface.OrderDAO;
import com.onway.fyapp.common.dal.dataobject.BookDO;
import com.onway.fyapp.common.dal.dataobject.BookUserDO;
import com.onway.fyapp.common.dal.dataobject.BookVersionDO;
import com.onway.fyapp.common.dal.dataobject.returnObj.BookInfo;
import com.onway.fyapp.common.dal.dataobject.returnObj.BookVersionInfo;
import com.onway.model.conver.BookConver;
import com.onway.model.enums.DelEnum;
import com.onway.model.enums.EnabledEnum;
import com.onway.model.enums.ErrorCode;
import com.onway.model.enums.OrderStatusEnum;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.enums.BaseResultCodeEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.template.ServiceCallback;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.utils.DateUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonListResult;
import com.onway.web.controller.result.JsonQueryResult;
import com.onway.web.controller.result.JsonResult;
/**
 * 用户书架
 * @author lishuaikai
 *
 * 2018年7月12日 下午1:46:37
 */
@Controller
public class UserBookShelfController extends AbstractController{

	@Resource
	private BookDAO bookDAO;
	
	@Resource
	private BookUserDAO bookUserDAO;
	
	@Resource
	private BookVersionDAO bookVersionDAO;
	
	@Resource
	private OrderDAO orderDAO;

	
	/**
	 * 最近阅读 
	 */
	@RequestMapping(value = "recentReading.do")
	@ResponseBody
	public Object recentReading(HttpServletRequest request){
		JsonListResult<BookInfo> result = new JsonListResult<BookInfo>(true);
		String userId = request.getParameter("userId");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			List<String> bookIds = bookUserDAO.queryShelfByUserId(userId, DelEnum.NOTDELETE.getCode());
			if(bookIds.size() >= 5){
				bookIds = bookIds.subList(0, 5);
			}
			List<BookInfo> list = new ArrayList<BookInfo>();
			for (String bookId : bookIds) {
				BookDO bookDO = bookDAO.queryBookById(bookId);
				BookInfo info = BookConver.buildBookInfo(bookDO);
				BookUserDO bookUserDO = bookUserDAO.queryShelfByUserIdAndBookId(userId, DelEnum.NOTDELETE.getCode(), bookId);
				if(bookUserDO != null){
					if(bookDO != null){
						info.setTime(DateUtil.getTimeStr(bookUserDO.getGmtModified()));	
					}
				}
				list.add(info);
			}
			result.setListObject(list);
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.GET_LATELY_FAIL.getCode(), MessageFormat.format("获取最近阅读书籍失败：{0}", new Object[]{e.getMessage()}));
		}catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("操作异常：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	
	/**
	 * 我的书架 
	 */
	@RequestMapping(value = "bookShelf.do")
	@ResponseBody
	public Object bookShelf(HttpServletRequest request){
		JsonListResult<BookInfo> result = new JsonListResult<BookInfo>(true);
		String userId = request.getParameter("userId");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			List<String> bookIds = bookUserDAO.queryShelfByUserId(userId, DelEnum.NOTDELETE.getCode());
			List<BookInfo> list = new ArrayList<BookInfo>();
			for (String bookId : bookIds) {
				BookDO bookDO = bookDAO.queryBookById(bookId);
				BookInfo info = BookConver.buildBookInfo(bookDO);
				list.add(info);
			}
			result.setListObject(list);
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.GET_BOOKSHELF_FAIL.getCode(), MessageFormat.format("获取我的书架失败：{0}", new Object[]{e.getMessage()}));
		}catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("操作异常：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	/**
	 * 从我的书架  删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delBookShelf.do")
	@ResponseBody
	public Object delBookShelf(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId = request.getParameter("userId");
		String bookIds = request.getParameter("bookIds");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(bookIds, "书籍编号不能为空");
			String bookId[] = bookIds.split(",");
			int del = 0;
			if(bookId.length > 0){
				List<String> book = new ArrayList<String>();
				for (String string : bookId) {
					book.add(string);
				}
				del = bookUserDAO.updateStatus(userId, book);
			}
			result.setBizSucc(del > 0 ? true : false);

		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.DEL_BOOK_FAIL.getCode(), MessageFormat.format("删除书籍失败：{0}", new Object[]{e.getMessage()}));
		}catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("操作异常：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	
	/**
	 * 添加到我的书架
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addBookShelf.do")
	@ResponseBody
	public Object addBookShelf(HttpServletRequest request){
		
		JsonResult result = new JsonResult(true);
		String userId = request.getParameter("userId");
		String bookId = request.getParameter("bookId");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(bookId, "书籍编号不能为空");
			//判断是否已经在书架
			if(bookUserDAO.queryShelfByUserIdAndBookId(userId, DelEnum.NOTDELETE.getCode(), bookId) != null){
				result.setErrMsg("书籍已在书架，请勿重复添加");
				result.setBizSucc(false);
				return result;
			}
			//判断之前是否添加过
			BookUserDO bookUserDO  = bookUserDAO.queryShelfByUserIdAndBookId(userId, DelEnum.DELETE.getCode(), bookId);
			if(bookUserDO != null){
				int update = bookUserDAO.updateStatusByBookIdAndUserId(DelEnum.NOTDELETE.getCode(), userId, bookId);
				result.setBizSucc(update > 0 ? true : false);
				return result;
			}
			BookUserDO bookUser = new BookUserDO();
			bookUser.setBookId(bookId);
			bookUser.setUserId(userId);
			bookUser.setDeleteFlg(DelEnum.NOTDELETE.getCode());
			bookUser.setLocalFlg(DelEnum.DELETE.getCode());
			BookVersionDO bookVersionDO = bookVersionDAO.queryByBookId(bookId, EnabledEnum.ENABLED.getCode());
			if(bookVersionDO == null){
				bookUser.setVersion("0");
			}else{
				bookUser.setVersion(bookVersionDO.getReviseNum());
			}
			bookUserDAO.insert(bookUser);
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.ADD_BOOK_FAIL.getCode(), MessageFormat.format("添加书籍失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("添加异常：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	/**
	 * 更改最近阅读顺序
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateSort.do")
	@ResponseBody
	public Object updateSort(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String bookId = request.getParameter("bookId");
		String userId = request.getParameter("userId");
		try {
			AssertUtil.notBlank(bookId, "书籍编号不能为空");
			AssertUtil.notBlank(userId, "用户id不能为空");
			int update = bookUserDAO.updateSort(userId, bookId);
			result.setBizSucc(update > 0 ? true : false);
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, "", MessageFormat.format("修改失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("修改异常：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	/**
	 * 书架书籍检查更新
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bookUpdate.do")
	@ResponseBody
	public Object checkBookUpdate(HttpServletRequest request){
		JsonQueryResult<BookVersionInfo> result = new JsonQueryResult<BookVersionInfo>(true);
		String bookId = request.getParameter("bookId");
		String userId = request.getParameter("userId");
		try {
			AssertUtil.notBlank(bookId, "书籍ID不能为空");
			AssertUtil.notBlank(userId, "用户ID不能为空");
			BookUserDO userBook = bookUserDAO.queryShelfByUserIdAndBookId(userId, DelEnum.NOTDELETE.getCode(), bookId);
			if(userBook == null){
				return new JsonResult(false, "", "书架书籍不存在");
			}
			BookVersionDO version = bookVersionDAO.queryByBookId(bookId, EnabledEnum.ENABLED.getCode());
			if(version == null){
				return new JsonQueryResult<BookVersionInfo>(true, "", "暂无更新");
			}
			int old = Integer.parseInt(userBook.getVersion());
			int now = Integer.parseInt(version.getReviseNum());
			if(now > old){
				BookVersionInfo info = new BookVersionInfo();
				info.setDownloadUrl(bookDAO.queryBookById(bookId).getDownloadUrl());
				info.setBookId(bookId);
				info.setChangeLog(version.getChangeLog());
				info.setReviseNum(version.getReviseNum());
				info.setMemo(version.getMemo());
				info.setCreateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(version.getGmtCreate()));
				result.setObj(info);
			}else{
				return new JsonQueryResult<BookVersionInfo>(true, "", "暂无更新");
			}
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.UPDATE_BOOK_FAIL.getCode(), MessageFormat.format("更新书籍失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("修改失败：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	/**
	 * 更新用户书架书籍版本
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateUserBook.do")
	@ResponseBody
	public Object updateUserBookVersion(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId = request.getParameter("userId");
		String bookId  =request.getParameter("bookId");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(bookId, "用户ID不能为空");
			
			BookVersionDO version = bookVersionDAO.queryByBookId(bookId, EnabledEnum.ENABLED.getCode());
			if(version != null){
				bookUserDAO.updateBookVersion(version.getReviseNum(), userId, bookId);
			}
			
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("更新书籍失败：{0}", new Object[]{e.getMessage()}));

		}catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("修改失败：{0}", new Object[]{e.getMessage()}));

		}
		return result;
	}
	
	/**
	 * 已有书籍
	 * @return
	 */
	@RequestMapping(value = "userHaveBook.do", method = RequestMethod.POST)
	@ResponseBody
	public Object userHaveBook(HttpServletRequest request){
		JsonListResult<BookInfo> result = new JsonListResult<BookInfo>(true);
		
		try {
			String userId = request.getParameter("userId");
			List<BookUserDO> bookUserDOs = bookUserDAO.queryByUserIdAndLocalFlg(userId, DelEnum.NOTDELETE.getCode());
			List<BookInfo> infoList = new ArrayList<BookInfo>();
			for (BookUserDO bookUserDO : bookUserDOs) {
				BookDO bookDO = bookDAO.queryBookById(bookUserDO.getBookId());
				BookInfo info = BookConver.buildBookInfo(bookDO);
				if(info != null){
					infoList.add(info);
				}

			}
			result.setListObject(infoList);
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
}
