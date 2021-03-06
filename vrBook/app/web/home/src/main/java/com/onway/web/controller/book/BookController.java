package com.onway.web.controller.book;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.fyapp.common.dal.daointerface.BookChapterDAO;
import com.onway.fyapp.common.dal.daointerface.BookDAO;
import com.onway.fyapp.common.dal.daointerface.BookSectionDAO;
import com.onway.fyapp.common.dal.daointerface.BookUserDAO;
import com.onway.fyapp.common.dal.dataobject.BookChapterDO;
import com.onway.fyapp.common.dal.dataobject.BookDO;
import com.onway.fyapp.common.dal.dataobject.BookSectionDO;
import com.onway.fyapp.common.dal.dataobject.BookUserDO;
import com.onway.fyapp.common.dal.dataobject.returnObj.BookChapterInfo;
import com.onway.fyapp.common.dal.dataobject.returnObj.BookInfo;
import com.onway.fyapp.common.dal.dataobject.returnObj.ChapterInfo;
import com.onway.fyapp.common.dal.dataobject.returnObj.SectionsInfo;
import com.onway.model.conver.BookConver;
import com.onway.model.enums.DelEnum;
import com.onway.model.enums.ErrorCode;
import com.onway.platform.common.enums.BaseResultCodeEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonListResult;
import com.onway.web.controller.result.JsonPageResult;
import com.onway.web.controller.result.JsonQueryResult;
import com.onway.web.controller.result.JsonResult;

/**
 * 
 * @author lishuaikai
 *	书控制类
 * 2018年7月11日 下午1:40:12
 */
@Controller
public class BookController extends AbstractController{

	@Resource
	private BookDAO bookDAO;
	
	@Resource
	private BookUserDAO bookUserDAO;
	
	@Resource
	private BookChapterDAO bookChapterDAO;
	
	@Resource
	private BookSectionDAO bookSectionDAO;
	/**
	 * 搜索书籍
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryBook.do")
	@ResponseBody
	public Object queryBook(HttpServletRequest request){
		JsonListResult<BookInfo> result = new JsonListResult<BookInfo>(true);
		String searchValue = request.getParameter("searchValue");
		String type = request.getParameter("type");
		
		try {
			AssertUtil.notBlank(searchValue, "搜索内容不能为空");
			AssertUtil.notBlank(type, "搜索类型不能为空");
			List<BookDO> books = bookDAO.queryBook(searchValue,type);
			List<BookInfo> bookList = new ArrayList<BookInfo>();
			for (BookDO bookDO : books) {
				bookList.add(BookConver.buildBookInfo(bookDO));
			}
			result.setListObject(bookList);
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.SEARCH_BOOK_FAIL.getCode(), MessageFormat.format("搜索书籍失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查找异常：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	/**
	 * 书籍详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bookInfo.do")
	@ResponseBody
	public Object bookInfo(HttpServletRequest request){
		String bookId = request.getParameter("bookId");
		JsonQueryResult<BookInfo> result = new JsonQueryResult<BookInfo>(true);
		try {
			AssertUtil.notBlank(bookId, "书籍编号不能为空");
			BookDO bookDO = bookDAO.queryBookById(bookId);
			BookInfo info = BookConver.buildBookInfo(bookDO);
			if(info == null){
				return new JsonResult(false, "", "书籍不存在");
			}
			result.setObj(info);
			
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.GET_BOOK_INFO_FAIL.getCode(), MessageFormat.format("获取书籍详情失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查看异常：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	/**
	 * 下载后，更改书籍下载量,添加到书架
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "download2.do")
	@ResponseBody
	public Object updateDownCount(HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		String userId = request.getParameter("userId");
		String bookId = request.getParameter("bookId");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank("书籍编号不能为空", bookId);
			if(bookDAO.queryBookById(bookId) == null){
				return new JsonResult(false, "", "书籍不存在");
			}
			BookUserDO bookUserDO = new BookUserDO();
			bookUserDO.setBookId(bookId);
			bookUserDO.setUserId(userId);
			bookUserDO.setDeleteFlg(DelEnum.NOTDELETE.getCode());
			int insert = bookUserDAO.insert(bookUserDO);
			int update = bookDAO.updateDownCount(bookId);
			result.setBizSucc(update > 0 && insert > 0 ? true : false );
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("操作失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("修改异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
	
	
	/**
	 * 推荐书籍
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "recommendBook.do")
	@ResponseBody
	public Object recommendBook(HttpServletRequest request){
		JsonPageResult<BookInfo> result = new JsonPageResult<BookInfo>(true);
		int pageNum = adjustPageNo(request);
		int pageSize = adjustPageSize(request);
		try {
			int count = bookDAO.bookCount();
			List<BookDO> books = bookDAO.recommendBook((pageNum - 1) * pageSize, pageSize);
			List<BookInfo> bookList = new ArrayList<BookInfo>();
			for (BookDO bookDO : books) {
				BookInfo info = BookConver.buildBookInfo(bookDO);
				bookList.add(info);
			}
			result.setListObject(bookList);
			result.setNext(pageNum * pageSize < count ? true :  false);
			result.setPageNum(pageNum);
			result.setTotalPages((count % pageSize) == 0 ? (count / pageSize) : (count / pageSize + 1));
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.GET_BOOK_MALL_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
		}
		return result;
	}
	
	/**
	 * 获取书的章节
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bookChapter.do")
	@ResponseBody
	public Object bookChapter(HttpServletRequest request){
		JsonQueryResult<BookChapterInfo> result = new JsonQueryResult<BookChapterInfo>(true);
		String bookId = request.getParameter("bookId");
		try {
			AssertUtil.notBlank(bookId, "书籍编号不能为空");
			BookDO bookDO  = bookDAO.queryBookById(bookId);
			
			BookChapterInfo bookChapterInfo = new BookChapterInfo();
				List<ChapterInfo> chapters = new ArrayList<ChapterInfo>();
				//章
				List<BookChapterDO> bookChapterDOs = bookChapterDAO.queryChapterByBookId(bookId);
				for (BookChapterDO bookChapterDO : bookChapterDOs) {
					ChapterInfo info = new ChapterInfo();
					info.setChapterId(bookChapterDO.getChapterId());
					info.setChapterName(bookChapterDO.getChapterName());
					
					//节
					List<BookSectionDO> bookSectionDOs = bookSectionDAO.querySectionByChapterId(bookChapterDO.getChapterId());
					List<SectionsInfo> sections = new ArrayList<SectionsInfo>();
					for (BookSectionDO bookSectionDO : bookSectionDOs) {
						SectionsInfo sectionsInfo = new SectionsInfo();
						sectionsInfo.setSectionId(bookSectionDO.getSectionId());
						sectionsInfo.setSectionName(bookSectionDO.getSectionName());
						sectionsInfo.setModelId(bookSectionDO.getModelId());
						sections.add(sectionsInfo);
						
					}
					info.setSectionList(sections);
					chapters.add(info);
					
				}
				bookChapterInfo.setChapterList(chapters);
				bookChapterInfo.setBookName(bookDO.getBookName());
				bookChapterInfo.setBookId(bookId);
				result.setObj(bookChapterInfo);
				
			} catch (BaseRuntimeException e) {
				if(e.getCode().equals("NULL_ARGUMENT")){
					return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
				}
				return new JsonResult(false, ErrorCode.GET_BOOK_MALL_FAIL.getCode(), MessageFormat.format("查询失败：{0}", new Object[]{e.getMessage()}));
			} catch (Exception e) {
				// TODO: handle exception
				return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("查询异常：{0}", new Object[]{e.getMessage()}));
			}
		return result;
	}

}