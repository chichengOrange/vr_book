package com.onway.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.platform.common.helper.SystemHelper;
import com.onway.result.JsonResult;

@Controller
public class UserController extends AbstractController{
	
	private static final String IMAGE_FILE = "D:\\image\\";
	
	@Autowired
	UserDAO userdao;
	

	//添加用户
	@RequestMapping("/adduser.do")
	@ResponseBody
	public JsonResult addUser(HttpServletRequest request,UserDO user, MultipartFile wxfile){
		JsonResult result = new JsonResult();
		
		String upload_realpath = SystemHelper.getSystemConfig("upload_realpath");
		String visit_url = SystemHelper.getSystemConfig("visit_url");
		long da = System.currentTimeMillis();
		String path1 =upload_realpath + da + ".jpg";//下载到磁盘上的路径
		String path2 =visit_url + da + ".jpg";//需要从数据库读取的路劲，放到页面上去显示。例如在localhost：8093/image下面
		if(wxfile.getSize()>0){
			try {
				uploadFile(wxfile,path1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e,e);
			}
		}
		
		user.setUserName(request.getParameter("userName"));
		user.setPsw(request.getParameter("pwd"));
		user.setUserId(request.getParameter("userId"));
		user.setOpenId(request.getParameter("wxId"));
		user.setHeadUrl(path2);
		user.setCell(request.getParameter("tel"));
		
		int i = userdao.adduser(user);
		
		if(i > 0){
			result.setSuccess(true);
			result.setMessage("添加成功");
		}else{
			result.setSuccess(false);
			result.setMessage("添加失败");
		}
		return result;
	}
	
	//查询用户
	@RequestMapping("/selectalluser.do")
	@ResponseBody
	public JSONObject selectAllUser(HttpServletRequest request,Integer offset,Integer limit){
	    JSONObject jo = new JSONObject();
		String userName = request.getParameter("userName");
		String cell = request.getParameter("cell");
		String status = request.getParameter("status");
		if(-1==limit){
            jo.put("rows",userdao.selectuser(userName, cell, status, offset, Integer.valueOf("2147483647")));
            
        }else{
            jo.put("rows", userdao.selectuser(userName, cell, status, offset, limit));
        }
		jo.put("total", userdao.queryListCount(userName, cell, status));
		return jo;
	}
	
	
	//根据id删除用户
	@RequestMapping("deleteUserbyid.do")
	@ResponseBody
	public JsonResult deleteUserById(HttpServletRequest request){
		
		JsonResult result = new JsonResult();
		int i = userdao.deletUserbyid(Integer.parseInt(request.getParameter("id")));
		if(i>0){
			result.setSuccess(true);
			result.setMessage("删除成功");
		}
		else{
			result.setSuccess(false);
			result.setMessage("删除失败");
		}
		return result;
	}

}
