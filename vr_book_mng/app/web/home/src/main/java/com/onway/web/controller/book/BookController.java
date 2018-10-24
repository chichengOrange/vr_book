package com.onway.web.controller.book;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.Money;
import com.onway.fyapp.common.dal.dataobject.BookChapterDO;
import com.onway.fyapp.common.dal.dataobject.BookDO;
import com.onway.fyapp.common.dal.dataobject.BookSectionDO;
import com.onway.fyapp.common.dal.dataobject.BookVersionDO;
import com.onway.model.enums.BizTypeEnum;
import com.onway.platform.common.helper.SystemHelper;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;

/**
 * 书籍管理
 * 
 * @author ASUS
 *
 */
@Controller
public class BookController extends AbstractController {

	/**
	 * 添加书籍
	 * 
	 * @param request
	 * @param themefile
	 * @param bookfile
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/addbook.do")
	@ResponseBody
	public Object addmenu(final HttpServletRequest request, MultipartFile themefile, MultipartFile bookfile,
			final MultipartFile iosfile, final String PRICE, final Integer canDownloadValue, final String BOOK_ID,
			final String BOOK_NAME, final String SORT, final String downloadCount, final String BOOK_PUBLISH,
			final String BOOK_AUTHOR) {
		final JsonResult result = new JsonResult(true);
		if (null == themefile || null == bookfile) {
			result.setSuccess(false);
			result.setMessage("书籍图片和附件均不能为空!");
			return result;
		}

		final String booksize = bookfile.getSize() + "";
		String img = themefile.getOriginalFilename();
		// 得到后缀名并生成uuid将其合起来得到最终名称
		String imgname = img.substring(img.lastIndexOf("."));
		String book = bookfile.getOriginalFilename();

		if (book.indexOf(".") > 0) {
			;
		} else {
			result.setSuccess(false);
			result.setMessage("附件需要后缀名!");
			return result;
		}

		/*
		 * 客户需求,文件存入数据库名称为文件的MD5值
		 */
		InputStream inputStream;
		String md5 = null;

		try {
			inputStream = bookfile.getInputStream();
			md5 = DigestUtils.md5Hex(IOUtils.toByteArray(inputStream));
			IOUtils.closeQuietly(inputStream);
		} catch (IOException e1) {
			logger.error("获取文件MD5失败", e1);
		}

		String bookname = book.substring(book.lastIndexOf("."));
		String uuid = getUUID();
		final String imgfinalname = uuid + imgname;
		final String bookfinalname = md5 + bookname;

		// 上传路径和返回到前台路径
		String bookImgPath = SystemHelper.getSystemConfig("book_img_upload_realpath");
		final String bookImgUrl = SystemHelper.getSystemConfig("book_img_path");

		String bookPath = SystemHelper.getSystemConfig("book_upload_realpath");
		final String bookUrl = SystemHelper.getSystemConfig("book_path");

		try {
			uploadFile(themefile, bookImgPath + imgfinalname);
			uploadFile(bookfile, bookPath + bookfinalname);
		} catch (Exception e) {
			throw new RuntimeException("上传异常");
		}
		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {
				BookDO book = new BookDO();

				book.setBookId(codeGenerateComponent.nextCodeByType(BizTypeEnum.BOOK_ID));
				book.setBookName(BOOK_NAME);
				book.setBookAuthor(BOOK_AUTHOR);
				book.setBookPublish(BOOK_PUBLISH);
				book.setBookImg(bookImgUrl + imgfinalname);
				book.setDownloadUrl(bookUrl + bookfinalname);
				book.setBookSize(booksize);
				book.setBookHeat(0);
				book.setDownloadCount(Integer.valueOf(downloadCount));
				book.setSort(Integer.valueOf(SORT));
				book.setCanDownload(canDownloadValue);
				book.setPrice(new Money(PRICE));
				book.setDistributor(request.getSession().getAttribute("username").toString());

				uploadIosAffix(iosfile, book);

				int insertbook = BookDAO.insertbook(book);
				// request.getSession().getAttribute("user_id");
				// int addmenu = sysMenuDAO.addmenu(menu);
				result.setSuccess(insertbook > 0 ? true : false);
				result.setMessage(insertbook > 0 ? "添加成功" : "添加失败");
			}

			@Override
			public void check() {
				// AssertUtil.notBlank(name, "名称不能为空！");
				// AssertUtil.notNull(id, "异常！");
				// AssertUtil.notNull(pid, "异常！");
			}
		});
		return result;
	}

	/**
	 * 更新书籍
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/updatebook.do")
	@ResponseBody
	public Object updateBook(final HttpServletRequest request, final MultipartFile themefile,
			final MultipartFile bookfile, MultipartFile iosfile, final String PRICE, final Integer canDownloadValue,
			final String SORT, final String downloadCount, final String BOOK_NAME, final String BOOK_PUBLISH,
			final String BOOK_AUTHOR, final Integer id) throws IOException {
		final JsonResult result = new JsonResult(true);
		if (null == id) {
			result.setSuccess(false);
			result.setMessage("参数错误!");
			return result;
		}
		if (null != bookfile) {

		}

		// 图片及书籍上传路径和返回路径
		final String bookImgPath = SystemHelper.getSystemConfig("book_img_upload_realpath");
		final String bookImgUrl = SystemHelper.getSystemConfig("book_img_path");

		final String bookPath = SystemHelper.getSystemConfig("book_upload_realpath");
		final String bookUrl = SystemHelper.getSystemConfig("book_path");

		
		//新添加更新ios附件
		if (iosfile != null) {
			BookDO book = new BookDO();
			uploadIosAffix(iosfile, book);
			String iosSize = book.getBookSizeIos();
			String iosUrl = book.getDownloadUrlIos();
			if (iosfile != null && iosUrl != null) {
				int updateIos = BookDAO.updatBookIos(iosSize, iosUrl, id);
				if (updateIos == 0) {
					result.setSuccess(false);
					result.setMessage("更新失败");
					return result;
				}
			}

		}

		// 傻逼
		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {
				if (null == themefile && null == bookfile) {
					// 如果两个文件都没有传,则不更新数据库中的数据
					int updatBookNoTwo = BookDAO.updatBookNoTwo(BOOK_NAME, BOOK_AUTHOR, BOOK_PUBLISH, canDownloadValue,
							new Money(PRICE), Integer.valueOf(SORT), Integer.valueOf(downloadCount), id);
					result.setSuccess(updatBookNoTwo > 0 ? true : false);
					result.setMessage(updatBookNoTwo > 0 ? "更新成功" : "更新失败");
				} else if (null != themefile && null == bookfile) {
					String img = themefile.getOriginalFilename();
					String imgname = img.substring(img.lastIndexOf("."));
					String uuid = getUUID();
					final String imgfinalname = uuid + imgname;
					try {
						uploadFile(themefile, bookImgPath + imgfinalname);
					} catch (Exception e) {
						throw new RuntimeException("上传异常");
					}
					int updatBookNofile = BookDAO.updatBookNofile(BOOK_NAME, BOOK_AUTHOR, BOOK_PUBLISH,
							bookImgUrl + imgfinalname, canDownloadValue, new Money(PRICE), Integer.valueOf(SORT),
							Integer.valueOf(downloadCount), id);
					result.setSuccess(updatBookNofile > 0 ? true : false);
					result.setMessage(updatBookNofile > 0 ? "更新成功" : "更新失败");
				} else if (null == themefile && null != bookfile) {
					// 如果传了图书则更新图书路径和大小
					/*
					 * 客户需求,文件存入数据库名称为文件的MD5值
					 */
					InputStream inputStream;
					String MD5 = null;
					try {
						inputStream = bookfile.getInputStream();
						MD5 = DigestUtils.md5Hex(IOUtils.toByteArray(inputStream));
						IOUtils.closeQuietly(inputStream);
					} catch (IOException e1) {
						logger.error("获取文件MD5失败", e1);
					}
					System.err.println("MD5:" + MD5);
					String book = bookfile.getOriginalFilename();
					if (book.indexOf(".") > 0) {
						;
					} else {
						result.setSuccess(false);
						result.setMessage("附件需要后缀名!");
						return;
					}
					String bookname = book.substring(book.lastIndexOf("."));

					final String bookfinalname = MD5 + bookname;
					final String booksize = bookfile.getSize() + "";
					try {
						uploadFile(bookfile, bookPath + bookfinalname);
					} catch (Exception e) {
						throw new RuntimeException("上传异常");
					}
					int updatBookNoImg = BookDAO.updatBookNoImg(BOOK_NAME, BOOK_AUTHOR, BOOK_PUBLISH, booksize,
							bookUrl + bookfinalname, canDownloadValue, new Money(PRICE), Integer.valueOf(SORT),
							Integer.valueOf(downloadCount), id);
					result.setSuccess(updatBookNoImg > 0 ? true : false);
					result.setMessage(updatBookNoImg > 0 ? "更新成功" : "更新失败");
				} else if (null != themefile && null != bookfile) {
					// 两个都传了就都更新
					/*
					 * 客户需求,文件存入数据库名称为文件的MD5值
					 */
					InputStream inputStream;
					String MD5 = null;
					try {
						inputStream = bookfile.getInputStream();
						MD5 = DigestUtils.md5Hex(IOUtils.toByteArray(inputStream));
						IOUtils.closeQuietly(inputStream);
					} catch (IOException e1) {
						logger.error("获取文件MD5失败", e1);
					}
					System.err.println("MD5:" + MD5);
					final String booksize = bookfile.getSize() + "";
					String img = themefile.getOriginalFilename();
					String imgname = img.substring(img.lastIndexOf("."));
					String book = bookfile.getOriginalFilename();
					if (book.indexOf(".") > 0) {
						;
					} else {
						result.setSuccess(false);
						result.setMessage("附件需要后缀名!");
						return;
					}
					String bookname = book.substring(book.lastIndexOf("."));
					String uuid = getUUID();
					final String imgfinalname = uuid + imgname;
					final String bookfinalname = MD5 + bookname;
					try {
						uploadFile(themefile, bookImgPath + imgfinalname);
						uploadFile(bookfile, bookPath + bookfinalname);
					} catch (Exception e) {
						throw new RuntimeException("图片上传异常");
					}

					int updatBook = BookDAO.updatBook(BOOK_NAME, BOOK_AUTHOR, BOOK_PUBLISH, bookImgUrl + imgfinalname,
							booksize, bookUrl + bookfinalname, canDownloadValue, new Money(PRICE),
							Integer.valueOf(SORT), Integer.valueOf(downloadCount), id);
					result.setSuccess(updatBook > 0 ? true : false);
					result.setMessage(updatBook > 0 ? "更新成功" : "更新失败");
				}
			}

			@Override
			public void check() {

			}
		});
		return result;
	}

	/**
	 * 删除书籍
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteBook.do")
	@ResponseBody
	public Object deleteBook(final Integer id) {
		final JsonResult result = new JsonResult(true);
		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {
				if (id != null) {
					int deletbookbyid = BookDAO.deletbookbyid(id);
					result.setSuccess(deletbookbyid > 0 ? true : false);
					result.setMessage(deletbookbyid > 0 ? "删除成功" : "删除失败");
				} else {
					result.setSuccess(false);
					result.setMessage("数据异常!");
				}
			}

			@Override
			public void check() {

			}
		});
		return result;
	}

	/**
	 * 查询所有书籍
	 * 
	 * @param BOOK_ID
	 * @param BOOK_NAME
	 * @param BOOK_PUBLISH
	 * @param BOOK_AUTHOR
	 * @param offset
	 * @param limit
	 * @return
	 */
	@RequestMapping("/selectallbook.do")
	@ResponseBody
	public JSONObject selectallsysuser(final HttpServletRequest request, String _BOOK_ID, String _BOOK_NAME,
			String _BOOK_PUBLISH, String _BOOK_AUTHOR, Integer offset, Integer limit) {
		String username = request.getSession().getAttribute("username").toString();
		JSONObject jo = new JSONObject();
		// 如果是admin就显示所有图书,否则按用户名查询
		if ("admin".equals(username)) {
			username = "";
		}
		if (-1 == limit) {
			jo.put("rows", BookDAO.selectAllBook(_BOOK_ID, _BOOK_NAME, _BOOK_PUBLISH, _BOOK_AUTHOR, username, offset,
					Integer.valueOf("2147483647")));

		} else {
			jo.put("rows",
					BookDAO.selectAllBook(_BOOK_ID, _BOOK_NAME, _BOOK_PUBLISH, _BOOK_AUTHOR, username, offset, limit));
		}
		jo.put("total", BookDAO.selectallbookcount(_BOOK_ID, _BOOK_NAME, _BOOK_PUBLISH, _BOOK_AUTHOR, username));
		return jo;
	}

	/**
	 * 文件下载功能
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/downloadBook.do")
	public void down(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		// 前台传过来的中文乱码
		name = new String(name.getBytes("ISO-8859-1"), "utf-8");
		String val = request.getParameter("val");
		String houzhui = val.substring(val.lastIndexOf("."));
		String bookPath = SystemHelper.getSystemConfig("book_upload_realpath");
		String bookname = StringUtils.substringAfterLast(val, "/book/");
		// 获取输入流
		InputStream bis = new BufferedInputStream(new FileInputStream(new File(bookPath + bookname)));
		// 假如以中文名下载的话
		// 转码，免得文件名中文乱码
		String filename = URLEncoder.encode(name + houzhui, "UTF-8");
		// 设置文件下载头
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		int len = 0;
		byte[] buf = new byte[10240];
		while ((len = bis.read(buf)) != -1) {
			out.write(buf, 0, len);
			;
			out.flush();
		}
		out.close();
	}

	/**
	 * 查询修订书籍
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/selectrevise.do")
	@ResponseBody
	public Object selectrevise(String bookId) {
		JSONObject result = new JSONObject();
		BookVersionDO BookVersion = BookVersionDAO.selectBookVersionByBookId(bookId);
		result.put("data", BookVersion);
		result.put("bookId", bookId);
		result.put("success", true);
		return result;
	}

	/**
	 * 修订书籍
	 * 
	 * @param request
	 * @param bookID
	 * @param versionId
	 * @param REVISE_NUM
	 * @param EXPLAIN
	 * @param MEMO
	 * @param revisefile
	 * @return
	 */
	@RequestMapping("/revise.do")
	@ResponseBody
	public Object revise(HttpServletRequest request, final String bookID, final Integer versionId,
			final String REVISE_NUM, final String EXPLAIN, final String MEMO, final MultipartFile revisefile) {
		final JsonResult result = new JsonResult(true);
		if (null == revisefile) {
			result.setSuccess(false);
			result.setMessage("请选择书籍附件!");
			return result;
		}
		/*
		 * 客户需求,文件存入数据库名称为文件的MD5值
		 */
		InputStream inputStream;
		String MD5 = null;
		try {
			inputStream = revisefile.getInputStream();
			MD5 = DigestUtils.md5Hex(IOUtils.toByteArray(inputStream));
			IOUtils.closeQuietly(inputStream);
		} catch (IOException e1) {
			logger.error("获取文件MD5失败", e1);
		}
		System.err.println("MD5:" + MD5);
		final String booksize = revisefile.getSize() + "";
		String book = revisefile.getOriginalFilename();
		if (book.indexOf(".") > 0) {
			;
		} else {
			result.setSuccess(false);
			result.setMessage("附件需要后缀名!");
			return result;
		}
		String bookname = book.substring(book.lastIndexOf("."));
		final String bookfinalname = MD5 + bookname;
		String bookPath = SystemHelper.getSystemConfig("book_upload_realpath");
		final String bookUrl = SystemHelper.getSystemConfig("book_path");
		try {
			uploadFile(revisefile, bookPath + bookfinalname);
		} catch (Exception e) {
			throw new RuntimeException("上传异常");
		}

		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {

				BookDAO.updatBookUrl(booksize, bookUrl + bookfinalname, bookID);
				if (versionId < 0) {
					BookVersionDO bookVersionDO = new BookVersionDO();
					bookVersionDO.setBookId(bookID);
					bookVersionDO.setBookVersionId(codeGenerateComponent.nextCodeByType(BizTypeEnum.BOOK_VERSION_ID));
					bookVersionDO.setChangeLog(EXPLAIN);
					bookVersionDO.setGmtCreate(new Date());
					bookVersionDO.setReviseNum(REVISE_NUM);
					bookVersionDO.setMemo(MEMO);
					bookVersionDO.setStatus("ENABLED");
					int insertBookVersion = BookVersionDAO.insertBookVersion(bookVersionDO);
					result.setSuccess(insertBookVersion > 0 ? true : false);
					result.setMessage(insertBookVersion > 0 ? "修订成功" : "修订失败");

				} else {
					int updateBookVersionByBookId = BookVersionDAO.updateBookVersionByBookId(REVISE_NUM, EXPLAIN,
							"ENABLED", MEMO, new Date(), versionId);
					result.setSuccess(updateBookVersionByBookId > 0 ? true : false);
					result.setMessage(updateBookVersionByBookId > 0 ? "修订成功" : "修订失败");
				}
			}

			@Override
			public void check() {
			}
		});
		return result;

	}

	/**
	 * 通过书籍id查询所有的章
	 * 
	 * @param request
	 * @param offset
	 * @param limit
	 * @return
	 */
	@RequestMapping("/selectallchapter.do")
	@ResponseBody
	public JSONObject selectallchapter(HttpServletRequest request, Integer offset, Integer limit) {
		String bookIdForChapter = request.getParameter("bookIdForChapter");
		JSONObject jo = new JSONObject();

		jo.put("rows", BookChapterDAO.selectChapterByBookId(bookIdForChapter, offset, limit));
		jo.put("total", BookChapterDAO.selectCountChapterByBookId(bookIdForChapter));
		return jo;
	}

	/**
	 * 通过章id查询所有的节
	 * 
	 * @param request
	 * @param offset
	 * @param limit
	 * @return
	 */
	@RequestMapping("/selectallsections.do")
	@ResponseBody
	public JSONObject selectallsections(HttpServletRequest request, Integer offset, Integer limit) {
		String chapterIdfor = request.getParameter("chapterIdfor");
		JSONObject jo = new JSONObject();

		jo.put("rows", BookSectionDAO.selectSectionsByChapterId(chapterIdfor, offset, limit));
		jo.put("total", BookSectionDAO.selectCountsectionsByBookId(chapterIdfor));
		return jo;
	}

	/**
	 * 添加章
	 * 
	 * @param request
	 * @param chapterId
	 * @param bookIdForChapter
	 * @return
	 */
	@RequestMapping("/addchapter.do")
	@ResponseBody
	public Object addchapter(HttpServletRequest request, final String chapterId, final String bookIdForChapter) {
		final JsonResult result = new JsonResult(true);

		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {
				BookChapterDO bookChapterDO = new BookChapterDO();
				bookChapterDO.setBookId(bookIdForChapter);
				bookChapterDO.setChapterId(codeGenerateComponent.nextCodeByType(BizTypeEnum.CHAPTER_ID));
				bookChapterDO.setChapterName(chapterId);
				bookChapterDO.setGmtCreate(new Date());
				int insertChapter = BookChapterDAO.insertChapter(bookChapterDO);
				result.setSuccess(insertChapter > 0 ? true : false);
				result.setMessage(insertChapter > 0 ? "添加成功" : "添加失败");
			}

			@Override
			public void check() {
				AssertUtil.notBlank(bookIdForChapter, "参数错误！");
				AssertUtil.notBlank(chapterId, "名称不能为空！");
			}
		});

		return result;
	}

	/**
	 * 添加节
	 * 
	 * @param request
	 * @param sectionsName
	 * @param chapterIdfor
	 * @return
	 */
	@RequestMapping("/addSection.do")
	@ResponseBody
	public Object addSection(HttpServletRequest request, final String sectionsName, final String chapterIdfor,
			final String modelId) {
		final JsonResult result = new JsonResult(true);

		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {
				BookSectionDO bookSectionDO = new BookSectionDO();
				bookSectionDO.setChapterId(chapterIdfor);
				bookSectionDO.setModelId(modelId);
				bookSectionDO.setSectionId(codeGenerateComponent.nextCodeByType(BizTypeEnum.SECTION_ID));
				bookSectionDO.setSectionName(sectionsName);
				bookSectionDO.setGmtCreate(new Date());
				int insertSections = BookSectionDAO.insertSections(bookSectionDO);
				result.setSuccess(insertSections > 0 ? true : false);
				result.setMessage(insertSections > 0 ? "添加成功" : "添加失败");
			}

			@Override
			public void check() {
				AssertUtil.notBlank(chapterIdfor, "参数错误！");
				AssertUtil.notBlank(sectionsName, "节名称不能为空！");
				AssertUtil.notBlank(modelId, "模型ID不能为空！");
			}
		});

		return result;
	}

	/**
	 * 删除章
	 * 
	 * @param request
	 * @param id
	 * @return
	 */

	@RequestMapping("/deleteChapter.do")
	@ResponseBody
	public Object deleteChapter(HttpServletRequest request, final Integer id, final String ChapterId) {
		final JsonResult result = new JsonResult(true);

		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {
				// 删除章时把其对应的节也删掉
				int deleteByChapterId = BookSectionDAO.deleteByChapterId(ChapterId);
				int deleteById = BookChapterDAO.deleteById(id);
				result.setSuccess(deleteById > 0 ? true : false);
				result.setMessage(deleteById > 0 ? "删除成功" : "删除失败");
			}

			@Override
			public void check() {

			}
		});

		return result;
	}

	/**
	 * 删除节
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSection.do")
	@ResponseBody
	public Object deleteSection(HttpServletRequest request, final Integer id) {
		final JsonResult result = new JsonResult(true);

		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {

				int deleteById = BookSectionDAO.deleteById(id);
				result.setSuccess(deleteById > 0 ? true : false);
				result.setMessage(deleteById > 0 ? "删除成功" : "删除失败");
			}

			@Override
			public void check() {

			}
		});

		return result;
	}

	/**
	 * 更新章
	 * 
	 * @param request
	 * @param updateChapterId
	 * @param updateChapterName
	 * @return
	 */
	@RequestMapping("/updateChapter.do")
	@ResponseBody
	public Object updateChapter(HttpServletRequest request, final Integer updateChapterId,
			final String updateChapterName) {
		final JsonResult result = new JsonResult(true);

		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {

				int updateChapterById = BookChapterDAO.updateChapterById(updateChapterName, new Date(),
						updateChapterId);
				result.setSuccess(updateChapterById > 0 ? true : false);
				result.setMessage(updateChapterById > 0 ? "修改成功" : "修改失败");
			}

			@Override
			public void check() {
				AssertUtil.notBlank(updateChapterName, "名称不能为空！");
			}
		});

		return result;
	}

	/**
	 * 更新节
	 * 
	 * @param request
	 * @param updateSectionId
	 * @param updateSectionName
	 * @return
	 */
	@RequestMapping("/updateSections.do")
	@ResponseBody
	public Object updateSection(HttpServletRequest request, final Integer updateSectionId,
			final String updateSectionName, final String updateModelId) {
		final JsonResult result = new JsonResult(true);

		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {

				int updateSectionsById = BookSectionDAO.updateSectionsById(updateSectionName, updateModelId, new Date(),
						updateSectionId);
				result.setSuccess(updateSectionsById > 0 ? true : false);
				result.setMessage(updateSectionsById > 0 ? "修改成功" : "修改失败");
			}

			@Override
			public void check() {
				AssertUtil.notBlank(updateSectionName, "名称不能为空！");
				AssertUtil.notBlank(updateModelId, "模型ID不能为空！");
			}
		});

		return result;
	}

	/**
	 * ios附件可选上传
	 */
	private void uploadIosAffix(MultipartFile iosfile, BookDO book) {
		if (iosfile != null) {
			String iosBook = iosfile.getOriginalFilename();
			final String iosBookPath = SystemHelper.getSystemConfig("book_upload_realpath") + "ios/";
			final String iosBookUrl = SystemHelper.getSystemConfig("book_path") + "ios/";
			if (iosBook.indexOf(".") > 0) {
				try {
					InputStream inputStreamIos = iosfile.getInputStream();
					String md5Ios = DigestUtils.md5Hex(IOUtils.toByteArray(inputStreamIos));
					IOUtils.closeQuietly(inputStreamIos);
					// 上传
					String iosBookfinalname = md5Ios + iosBook.substring(iosBook.lastIndexOf("."));
					uploadFile(iosfile, iosBookPath + iosBookfinalname);
					// 实体类设置属性
					book.setDownloadUrlIos(iosBookUrl + iosBookfinalname);
					book.setBookSizeIos(iosfile.getSize() + "");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
}