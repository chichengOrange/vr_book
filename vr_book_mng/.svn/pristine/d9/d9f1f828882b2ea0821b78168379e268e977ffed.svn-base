//package com.onway.web.controller;
//
//import java.util.Date;
//import java.util.Random;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.alibaba.fastjson.JSONObject;
//import com.onway.common.lang.StringUtils;
//import com.onway.dxsmng.common.dal.daointerface.SysImageDAO;
//import com.onway.dxsmng.common.dal.dataobject.SysImageDO;
//import com.onway.platform.common.configration.ConfigrationFactory;
//
///**
// * banner����
// * @author ASUS
// *
// */
//@Controller
//public class BannerController extends AbstractController{
//	private static Logger logger = Logger.getLogger(BannerController.class);
//	@Resource
//	private SysImageDAO SysImageDAO;
//	private static final String IMAGE_FILE           = ConfigrationFactory.getConfigration()
//            .getPropertyValue(
//                "user_img_upload_realpath");
//
//    private static final String IMAGE_PATH           = ConfigrationFactory.getConfigration()
//            .getPropertyValue("user_img_path");
//	/**
//	 * banner�鿴
//	 * @return
//	 */
//	@RequestMapping("/selectallbanner.do")
//	@ResponseBody
//	public JSONObject selectallbanner(Integer offset ,Integer limit ,String subtype){
//		JSONObject jo = new JSONObject();
//		jo.put("rows", SysImageDAO.selectallbanner("BANNER", subtype, offset, limit));
//		jo.put("total", SysImageDAO.selectallbannercount("BANNER", subtype));
//		return jo;
//	}
//	/**
//	 * ���banner
//	 * @param sysImage
//	 * @return
//	 */
//	@RequestMapping("/addbanner.do")
//	@ResponseBody
//	public JSONObject addbanner(SysImageDO sysImage,HttpServletRequest re,@RequestParam("bannerfile") MultipartFile bannerfile){
//		JSONObject jo = new JSONObject();
//		
//		long da = System.currentTimeMillis();
//		String path  =IMAGE_FILE+da+".jpg";
//		String url = IMAGE_PATH+da+".jpg";
//		if(bannerfile.getSize()>0){
//			try {
//				uploadFile(bannerfile,path);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				logger.error(e,e);
//			}//�ϴ�ͼƬ
//		}
//		sysImage.setImgUrl(url);
//		sysImage.setCreater(re.getSession().getAttribute("username").toString());
//		sysImage.setGmtCreate(new Date());
//		sysImage.setGmtModified(new Date());
//		sysImage.setType("BANNER");
//		sysImage.setModifier(re.getSession().getAttribute("username").toString());
//		int i = SysImageDAO.addbanner(sysImage);
//		jo.put("flag", i>0 ? true:false);
//		jo.put("data", i>0 ? "添加成功":"添加失败");
//		return jo;
//	}
//	/**
//	 * �޸�banner
//	 * @param id
//	 * @param bannerfile
//	 * @param subtupe
//	 * @param rank
//	 * @param memo
//	 * @param re
//	 * @return
//	 */
//	@RequestMapping("/updatebanner.do")
//	@ResponseBody
//	public JSONObject updatebanner(String id,@RequestParam(required = false) MultipartFile bannerfile,String subType,
//			String rank,String memo,HttpServletRequest re,String oldurl,String linkUrl){
//		JSONObject jo = new JSONObject();
//		long da = System.currentTimeMillis();
//		String path  =IMAGE_FILE+da+".jpg";
//		String url ="";
//		
//		if(bannerfile!=null){
//			url=IMAGE_PATH+da+".jpg";
//			try {
//				uploadFile(bannerfile,path);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				logger.error(e,e);
//			}
//		}
//		int i = SysImageDAO.updatebanner(id, "", subType, url, rank, re.getSession().getAttribute("username").toString(), new Date(), memo,linkUrl);
//		jo.put("flag", i>0 ? true:false);
//		jo.put("data", i>0 ? "修改成功":"修改失败");
//		return jo;
//	}
//	/**
//	 * ɾ��banner
//	 * @param id
//	 * @param photourl
//	 * @return
//	 */
//	@RequestMapping("/deletebanner.do")
//	@ResponseBody
//	public int deletebanner(Integer id,String photourl){
//		//删除原图片
//		deletefile(IMAGE_FILE+StringUtils.substringAfter(photourl,IMAGE_PATH));
//		
//		return SysImageDAO.deletebanner(id);
//	}
//	
//	/**
//	 * 图片上传接口
//	 * @param photo
//	 * @return
//	 */
//	@RequestMapping("/fileUploadForEditor.do")
//	@ResponseBody
//	public String fileUploadForEditor(@RequestParam MultipartFile photo) {
//		
//		String ImgName = getRandomCode(3) + ".jpg";
//		String path  =IMAGE_FILE + ImgName;
//		
//		String url ="";
//		if(photo!=null){
//			url=IMAGE_PATH + ImgName;
//			try {
//				uploadFile(photo,path);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				logger.error(e,e);
//			}
//		}
//		
////		try {
////			ImageUploadUtil.doUploadFile(photo, realpathPex + ImgName);
////		} catch (IOException e) {
////			e.printStackTrace();
////			//result.setBizSucc(false);
////			//result.setErrMsg("上传文件失败");
////			return null;
////		} // 上传文件
////		{"errno":0,"data":[
////
////			"/可读取你图片的路径.jpg"
////
////			  ]
////
////			}
//		String data = "{\"errno\":0,\"data\":[\""+url+"\"]}";
//		return data;
//	}
//	
//	// 生成随机数的,防止图片重复
//		public String getRandomCode(int length) {
//			if (length < 1 || length > 10) {
//				return "";
//			}
//			StringBuffer sb = new StringBuffer();
//			for (int i = 1; i <= length; i++) {
//				int rand = new Random().nextInt(10);
//				sb.append(rand);
//			}
//			return String.valueOf(System.currentTimeMillis()) + sb.toString();
//		}
//}
