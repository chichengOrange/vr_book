package com.onway.web.controller.book;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.web.controller.AbstractController;

@Controller
public class BookUserController extends AbstractController {

	@RequestMapping("/selectAllBookUser.do")
	@ResponseBody
	public JSONObject selectAllBookUser(String userName, String bookName,
			 Integer offset, Integer limit) {
		JSONObject jo = new JSONObject();
		if(-1==limit){
		    jo.put("rows", bookUserDAO.selectByQuery(userName, bookName,
                offset, Integer.valueOf("2147483647")));
            
        }else{
            jo.put("rows", bookUserDAO.selectByQuery(userName, bookName,
                offset, limit));
        }
        
		
		jo.put("total",
				bookUserDAO.selectCountByQuery(userName, bookName));
		return jo;
	}

}
