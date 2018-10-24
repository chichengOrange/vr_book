package com.onway.web.controller.count;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.web.controller.AbstractController;

@Controller
public class CountController<E> extends AbstractController {
    /**
     * 协议查看率
     * 
     * @return
     */
    @RequestMapping("/selectprotocol.do")
    @ResponseBody
    public JSONObject selectprotocol() {
        JSONObject jo = new JSONObject();
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> data = UserProtocolDAO.selectProtocol();
        for (Map<String, Object> map : data) {
            if ("0".equals(map.get("name"))) {
                map.put("name", "未查看");
            } else {
                map.put("name", "已查看");
            }
            list.add(map);
        }
        jo.put("data", list);
        return jo;
    }

    /**
     * 关键词统计
     * 
     * @return
     */
    @RequestMapping("/selectallkeyword.do")
    @ResponseBody
    public JSONObject selectallkeyword(HttpServletRequest request, Integer wordCount,
                                       Integer offset, Integer limit) {
        JSONObject jo = new JSONObject();
        List<Map<String, Object>> selectAllSearch = SearchDAO.selectAllSearch(wordCount, offset,
            Integer.valueOf("2147483647"));
        if (-1 == limit) {
            jo.put("rows",
                SearchDAO.selectAllSearch(wordCount, offset, Integer.valueOf("2147483647")));

        } else {
            jo.put("rows", SearchDAO.selectAllSearch(wordCount, offset, limit));
        }
        jo.put("total", selectAllSearch.size());

        return jo;
    }

    /**
     * 手机类型统计
     * 
     * @return
     */
    @RequestMapping("/selectphone.do")
    @ResponseBody
    public JSONObject selectphone() {
        JSONObject jo = new JSONObject();
        List<Map<String, Object>> data = PhoneDAO.selectPhone();
        for (Map<String, Object> map : data) {
            if (null == map.get("name")) {
                map.put("name", "其他");
            }
        }
        jo.put("data", data);
        return jo;
    }

    /**
     * 手机系统统计
     * 
     * @return
     */
    @RequestMapping("/selectOs.do")
    @ResponseBody
    public JSONObject selectOs() {
        JSONObject jo = new JSONObject();
        List<Map<String, Object>> data = PhoneDAO.selectOs();
        for (Map<String, Object> map : data) {
            if (null == map.get("name")) {
                map.put("name", "其他");
            }
        }
        jo.put("data", data);
        return jo;
    }

    /**
     * 登录方式统计
     * 
     * @return
     */
    @RequestMapping("/selectLoginType.do")
    @ResponseBody
    public JSONObject selectLoginType() {
        JSONObject jo = new JSONObject();
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> data = UserLoginLogDAO.selectLoginType();
        for (Map<String, Object> map : data) {
            if ("0".equals(map.get("name"))) {
                map.put("name", "注册后登陆");
            } else if ("1".equals(map.get("name"))) {
                map.put("name", "记忆状态登陆");
            } else if ("2".equals(map.get("name"))) {
                map.put("name", "账号密码登陆");
            } else if ("3".equals(map.get("name"))) {
                map.put("name", "手机号密码登陆");
            } else if ("4".equals(map.get("name"))) {
                map.put("name", "第三方登录");
            }
            list.add(map);
        }
        jo.put("data", list);
        return jo;
    }

    /**
     * 退出界面统计
     * 
     * @return
     */
    @RequestMapping("/selectOutInterface.do")
    @ResponseBody
    public JSONObject selectOutInterface() {
        JSONObject jo = new JSONObject();
        List<Map<String, Object>> data = OntimeDAO.selectOutInterface();
        for (Map<String, Object> map : data) {
            if (null == map.get("name")) {
                map.put("name", "其他");
            }
        }
        jo.put("data", data);
        return jo;
    }

    /**
     * 使用时间统计
     * 
     * @return
     */
    @RequestMapping("/selectOntime.do")
    @ResponseBody
    public JSONObject selectOntime() {
        JSONObject jo = new JSONObject();
        //		int count1=0;
        //		int count2=0;
        //		int count3=0;
        //		int count4=0;
        List<Map<String, Object>> data = OntimeDAO.selectOntime();
        /*for (Map<String, Object> map : data) {
        	if (null == map.get("name")) {
        		map.put("name", "其他");
        	} else if (0<=Integer.valueOf(map.get("name").toString())&&Integer.valueOf(map.get("name").toString())<10) {
        		map.put("name", "0-10分钟");
        		int object = Integer.valueOf(map.get("value").toString());
        		count1+=object;
        		map.put("value", count1);
        	}else if (10<=Integer.valueOf(map.get("name").toString())&&Integer.valueOf(map.get("name").toString())<30) {
        		map.put("name", "10-30分钟");
        		int object = Integer.valueOf(map.get("value").toString());
        		count2+=object;
        		map.put("value", count2);
        	}else if (60<=Integer.valueOf(map.get("name").toString())&&Integer.valueOf(map.get("name").toString())<1200) {
        		map.put("name", "60-120分钟");
        		int object = Integer.valueOf(map.get("value").toString());
        		count3+=object;
        		map.put("value", count3);
        	}else {
        		map.put("name", "120分钟以上");
        		int object = Integer.valueOf(map.get("value").toString());
        		count4+=object;
        		map.put("value", count4);
        	}
        }*/
        jo.put("data", data);
        return jo;
    }

    /**
     * 地区数据统计
     * 
     * @return
     */
    @RequestMapping("/selectaddress.do")
    @ResponseBody
    public JSONObject selectaddress() {
        JSONObject jo = new JSONObject();
        ArrayList<Object> name = new ArrayList<Object>();
        ArrayList<Object> value = new ArrayList<Object>();
        List<Map<String, Object>> data = PhoneDAO.selectAddress();
        for (Map<String, Object> map : data) {
            if (null == map.get("name")) {
                map.put("name", "其他");
            }
            name.add(map.get("name"));
            value.add(map.get("value"));
        }
        jo.put("name", name);
        jo.put("value", value);
        return jo;
    }

    /**
     * app版本统计
     * 
     * @return
     */
    @RequestMapping("/selectVersion.do")
    @ResponseBody
    public JSONObject selectVersion() {
        JSONObject jo = new JSONObject();
        List<Map<String, Object>> data = UserVesionDAO.selectVersion();
        jo.put("data", data);
        return jo;
    }

    /**
     * 书籍使用统计
     * 
     * @return
     */
    @RequestMapping("/selectHandle.do")
    @ResponseBody
    public JSONObject selectHandle() {
        JSONObject jo = new JSONObject();
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> data = BookHandleDAO.selectHandle();
        for (Map<String, Object> map : data) {
            if ("1".equals(map.get("name"))) {
                map.put("name", "进入扫描界面");
            } else if ("2".equals(map.get("name"))) {
                map.put("name", "扫描界面返回主界面");
            } else if ("3".equals(map.get("name"))) {
                map.put("name", "删除书籍");
            }
            list.add(map);
        }
        jo.put("data", list);
        return jo;
    }

    /**
     * 各功能使用统计
     * 
     * @return
     */
    @RequestMapping("/selectFunctions.do")
    @ResponseBody
    public JSONObject selectFunctions() {
        JSONObject jo = new JSONObject();
        List<Map<String, Object>> data = FunctionsLogDAO.selectFunctions();
        jo.put("data", data);
        return jo;
    }

    /**
     * 验证码使用统计
     * 
     * @return
     */
    @RequestMapping("/selectSms.do")
    @ResponseBody
    public JSONObject selectSms() {
        JSONObject jo = new JSONObject();
        List<Map<String, Object>> data = SmsLogDAO.selectSms();
        jo.put("data", data);
        return jo;
    }

    /**
     * 搜索关键词统计
     * 
     * @return
     */
    @RequestMapping("/selectSearch.do")
    @ResponseBody
    public JSONObject selectSearch() {
        JSONObject jo = new JSONObject();
        ArrayList<Object> name = new ArrayList<Object>();
        ArrayList<Object> value = new ArrayList<Object>();
        List<Map<String, Object>> data = SearchDAO.selectSearch();
        for (Map<String, Object> map : data) {
            name.add(map.get("name"));
            value.add(map.get("value"));
        }
        jo.put("name", name);
        jo.put("value", value);
        return jo;
    }

    /**
     * 搜索类型统计
     * 
     * @return
     */
    @RequestMapping("/selectSearchType.do")
    @ResponseBody
    public JSONObject selectSearchType() {
        JSONObject jo = new JSONObject();

        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> data = SearchDAO.selectSearchType();
        for (Map<String, Object> map : data) {
            if ("0".equals(map.get("name"))) {
                map.put("name", "出版社");
            } else if ("1".equals(map.get("name"))) {
                map.put("name", "书籍");
            }
            list.add(map);
        }
        jo.put("data", list);
        return jo;
    }
    
    @RequestMapping("/selectAllBookHandle.do")
    @ResponseBody
    public JSONObject selectAllBookHandle(String userName, String bookName,
             Integer offset, Integer limit) {
        JSONObject jo = new JSONObject();
        if(-1==limit){
            jo.put("rows", BookHandleDAO.selectByQuery(userName, bookName,
                offset, Integer.valueOf("2147483647")));
            
        }else{
            jo.put("rows", BookHandleDAO.selectByQuery(userName, bookName,
                offset, limit));
        }
        
        
        jo.put("total",
            BookHandleDAO.selectCountByQuery(userName, bookName));
        return jo;
    }
}
