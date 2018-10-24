package com.onway.web.controller.book;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.onway.fyapp.common.dal.dataobject.AdDO;
import com.onway.platform.common.configration.ConfigrationFactory;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;

@Controller
public class AdController extends AbstractController {

	private static final String IMAGE_FILE = ConfigrationFactory
			.getConfigration().getPropertyValue("user_img_upload_realpath");

	private static final String IMAGE_PATH = ConfigrationFactory
			.getConfigration().getPropertyValue("user_img_path");

	@RequestMapping("/selectAllAd.do")
	@ResponseBody
	public JSONObject selectallsysuser(String adTitleFind, String adTypeFind,
			String statusFind, Integer offset, Integer limit) {
		JSONObject jo = new JSONObject();
		if(-1==limit){
		    jo.put("rows", adDAO.selectByQuery(adTitleFind, adTypeFind, statusFind,
                offset, Integer.valueOf("2147483647")));
            
        }else{
            jo.put("rows", adDAO.selectByQuery(adTitleFind, adTypeFind, statusFind,
                offset, limit));
        }
		
		jo.put("total",
				adDAO.selectCountByQuery(adTitleFind, adTypeFind, statusFind));
		return jo;
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @param themefile
	 * @param bookfile
	 * @return
	 */
	@RequestMapping("/addAdInfo.do")
	@ResponseBody
	public Object addAdInfo(final HttpServletRequest request,
			final MultipartFile posterFile) {
		final JsonResult result = new JsonResult(true);
		final String adTitle = request.getParameter("adTitle");
		final String adDesc = request.getParameter("adDesc");
		final String html = request.getParameter("html");
		// final String adContent = request.getParameter("adContent");
		final String status = request.getParameter("status");
		final String adType = request.getParameter("adType");
		final String adDay = request.getParameter("adDay");
		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {

				// 如果没有这个路径,则创建
				String imgName = getRandomCode(3) + ".jpg";
				String path = IMAGE_FILE + imgName;
				String url = IMAGE_PATH + imgName;
				try {
					uploadFile(posterFile, path);
				} catch (Exception e) {
					e.printStackTrace();
				}
				AdDO adDO = new AdDO();
				adDO.setAdPoster(url);
				adDO.setAdTitle(adTitle);
				adDO.setAdDesc(adDesc);
				adDO.setAdType(adType);
				adDO.setStatus(status);
				adDO.setAdContent(html);
				adDO.setAdDay(Integer.valueOf(adDay));
				int insertID = adDAO.insert(adDO);
				result.setSuccess(insertID > 0 ? true : false);
				result.setMessage(insertID > 0 ? "添加成功" : "添加失败");
			}

			@Override
			public void check() {
				AssertUtil.notNull(posterFile, "图片不能为空");
				AssertUtil.notBlank(adDay, "天数不能为空");
			}
		});
		return result;
	}

	/**
	 * 添加书籍
	 * 
	 * @param request
	 * @param themefile
	 * @param bookfile
	 * @return
	 */
	@RequestMapping("/updateAdInfo.do")
	@ResponseBody
	public Object updateAdInfo(final HttpServletRequest request,
			final MultipartFile posterFile) {
		final JsonResult result = new JsonResult(true);
		final String idStr = request.getParameter("id");
		final String adTitle = request.getParameter("adTitle");
		final String adDesc = request.getParameter("adDesc");
		final String html = request.getParameter("html");
		// final String adContent = request.getParameter("adContent");
		final String status = request.getParameter("status");
		final String adType = request.getParameter("adType");
		final String adDay = request.getParameter("adDay");
		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {

				AdDO adDO = adDAO.selectById(Integer.valueOf(idStr));
				if (null == adDO) {
					throw new RuntimeException("记录不存在");
				}

				// 如果没有这个路径,则创建
				if (null != posterFile) {
					String imgName = getRandomCode(3) + ".jpg";
					String path = IMAGE_FILE + imgName;
					String url = IMAGE_PATH + imgName;
					try {
						uploadFile(posterFile, path);
					} catch (Exception e) {
						throw new RuntimeException("图片上传异常");
					}
					adDO.setAdPoster(url);
				}

				adDO.setAdTitle(adTitle);
				adDO.setAdDesc(adDesc);
				adDO.setAdType(adType);
				adDO.setStatus(status);
				adDO.setAdContent(html);
				adDO.setAdDay(Integer.valueOf(adDay));
				int insertID = adDAO.update(adDO.getAdTitle(),
						adDO.getAdDesc(), adDO.getAdPoster(),
						adDO.getAdContent(), adDO.getAdDay(), adDO.getAdType(),
						adDO.getStatus(), Integer.valueOf(idStr));
				result.setSuccess(insertID > 0 ? true : false);
				result.setMessage(insertID > 0 ? "修改成功" : "修改失败");
			}

			@Override
			public void check() {
				AssertUtil.notBlank(idStr, "id不能为空");
				AssertUtil.notBlank(adDay, "id不能为空");
			}
		});
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteAd.do")
	@ResponseBody
	public Object deleteBook(final HttpServletRequest request) {
		final JsonResult result = new JsonResult(true);
		final String idStr = request.getParameter("id");
		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {
				result.setSuccess(adDAO.deleteById(Integer.valueOf(idStr)) > 0);
				if (result.isSuccess()) {
					result.setMessage("删除成功");
				} else {
					result.setMessage("删除失败");
				}
			}

			@Override
			public void check() {
				AssertUtil.notBlank(idStr, "编号不能为空");

			}
		});
		return result;
	}

	/**
	 * 图片上传接口
	 * 
	 * @param photo
	 * @return
	 */
	@RequestMapping("/fileUploadForEditor.do")
	@ResponseBody
	public String fileUploadForEditor(@RequestParam MultipartFile photo) {

		String ImgName = getRandomCode(3) + ".jpg";
		String path = IMAGE_FILE + ImgName;

		String url = "";
		if (photo != null) {
			url = IMAGE_PATH + ImgName;
			try {
				uploadFile(photo, path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e, e);
			}
		}

		// try {
		// ImageUploadUtil.doUploadFile(photo, realpathPex + ImgName);
		// } catch (IOException e) {
		// e.printStackTrace();
		// //result.setBizSucc(false);
		// //result.setErrMsg("上传文件失败");
		// return null;
		// } // 上传文件
		// {"errno":0,"data":[
		//
		// "/可读取你图片的路径.jpg"
		//
		// ]
		//
		// }
		String data = "{\"errno\":0,\"data\":[\"" + url + "\"]}";
		return data;
	}

	// 生成随机数的,防止图片重复
	public String getRandomCode(int length) {
		if (length < 1 || length > 10) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= length; i++) {
			int rand = new Random().nextInt(10);
			sb.append(rand);
		}
		return String.valueOf(System.currentTimeMillis()) + sb.toString();
	}

}
