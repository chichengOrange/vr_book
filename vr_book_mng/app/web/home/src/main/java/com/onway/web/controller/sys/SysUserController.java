package com.onway.web.controller.sys;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.code.CodeGenerateComponent;
import com.onway.fyapp.common.dal.dataobject.SysRoleUserDO;
import com.onway.platform.common.enums.PlatformCodeEnum;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.Md5Encrypt;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerTemplateImpl;
/**
 * 系统人员管理
 * @author ASUS
 *
 */
@Controller
public class SysUserController extends AbstractController{
	
	/**
	 * 跳转到页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/tosysuser.html")
	public String tosysuser(ModelMap map,HttpSession session){
		String menu = (String)session.getAttribute("role");
    	if(StringUtils.isNotBlank(menu)){
			if(!menu.contains("tosysuser.html")){
				return "html/index";
	    	}
    	}
		map.put("role", sysRoleDAO.selectallroletouser());
		return "html/sys/sysuser";
	}
	/**
	 * 查看人员信息
	 * @param username
	 * @param offset
	 * @param limit
	 * @return
	 */
	@RequestMapping("/selectallsysuser.do")
	@ResponseBody
	public JSONObject selectallsysuser(String name,Integer offset,Integer limit){
		JSONObject jo = new JSONObject();
		jo.put("rows", sysRoleUserDAO.selectalladminuser(name, offset, limit));
		jo.put("total", sysRoleUserDAO.selectalladminusercount(name));
		return jo;
	}
	/**
	 * 修改信息
	 * @param username
	 * @param roleId
	 * @param re
	 * @param id
	 * @return
	 */
	@RequestMapping("/updatesysuser.do")
	@ResponseBody
	public Object updatesysuser(final String username,final String roleId,final HttpServletRequest re,final Integer id){
		final JsonResult jr = new JsonResult(true);
		controllerTemplate.execute(jr, new ServiceCheckCallback() {
			
			@Override
			public void executeService() {
				// TODO Auto-generated method stub
				jr.setMessage(sysRoleUserDAO.updateadminuser(username, roleId, re.getSession().getAttribute("username")+"", new Date(), id)>0 ? "修改成功":"修改失败");
				
			}
			
			@Override
			public void check() {
				// TODO Auto-generated method stub
				AssertUtil.notBlank(username, "用户帐号不能为空！");
				AssertUtil.notBlank(roleId, "请选择角色信息！");
			}
		});
		return jr;
	}
	
	@RequestMapping("/deleteUserById.do")
	@ResponseBody
	public Object deleteUserById(final HttpServletRequest re,final Integer id){
		final JsonResult jr = new JsonResult(true);
		controllerTemplate.execute(jr, new ServiceCheckCallback() {
			
			@Override
			public void executeService() {
				int deleteSysUserById = sysRoleUserDAO.deleteSysUserById("1", re.getSession().getAttribute("username")+"", new Date(), id);
				jr.setSuccess(deleteSysUserById > 0 ? true
						: false);
				jr.setMessage(deleteSysUserById > 0 ? "删除成功"
						: "删除失败");
			}
			
			@Override
			public void check() {
				AssertUtil.notNull(id, "参数错误！");
			}
		});
		return jr;
	}
	/**
	 * 添加信息
	 * @param adminUser
	 * @param re
	 * @return
	 */
	@RequestMapping("/addsysuser.do")
	@ResponseBody
	public Object addsysuser(final SysRoleUserDO sysRoleUserDO,final HttpServletRequest re){
		final JsonResult jr = new JsonResult(true);
		controllerTemplate.execute(jr, new ServiceCheckCallback() {
			
			@Override
			public void executeService() {
				// TODO Auto-generated method stub
				sysRoleUserDO.setUserId(codeGenerateComponent.nextCodeByType(PlatformCodeEnum.WEBSITE_PLATFORM));
				sysRoleUserDO.setPassword(Md5Encrypt.toMD5High(sysRoleUserDO.getPassword()));
				sysRoleUserDO.setCreater(re.getSession().getAttribute("username")+"");
				sysRoleUserDO.setGmtCreate(new Date());
				jr.setMessage(sysRoleUserDAO.addadminuser(sysRoleUserDO)>0 ? "添加成功":"添加失败");
				
			}
			
			@Override
			public void check() {
				// TODO Auto-generated method stub
				AssertUtil.notBlank(sysRoleUserDO.getUsername(), "用户账号不能为空！");
				AssertUtil.notBlank(sysRoleUserDO.getPassword(), "密码不能为空！");
				AssertUtil.notBlank(sysRoleUserDO.getRoleId(), "请选择角色信息！");
			}
		});
		return jr;
	}
	
	
	
	@RequestMapping("/resetpassword.do")
	@ResponseBody
	public Object resetPassword(final String password ,final String userId,final HttpServletRequest re){
		final JsonResult jr = new JsonResult(true);
		controllerTemplate.execute(jr, new ServiceCheckCallback() {
			
			@Override
			public void executeService() {
				int resetpassword = sysRoleUserDAO.resetpassword(Md5Encrypt.toMD5High(password), userId);
				jr.setSuccess(resetpassword>0 ? true:false);
				jr.setMessage(resetpassword>0 ? "重置成功,新密码为：888888":"重置失败");
				
			}
			
			@Override
			public void check() {
				
			}
		});
		return jr;
	}
}
