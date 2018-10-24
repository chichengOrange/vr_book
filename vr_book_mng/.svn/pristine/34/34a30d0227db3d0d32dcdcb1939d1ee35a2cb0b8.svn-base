package com.onway.web.controller.order;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
@Controller
public class OrderController extends AbstractController{
	/**
	 * 查询所有订单
	 * @param selectuserId
	 * @param selectBookId
	 * @param selectType
	 * @param offset
	 * @param limit
	 * @return
	 */
	@RequestMapping("/selectallorder.do")
	@ResponseBody
	public JSONObject selectallaccount(String selectOrderId, String selectUserId, String selectBookId,
			String selectType,  Integer offset,
			Integer limit) {
		JSONObject jo = new JSONObject();
		if(-1==limit){
		    jo.put("rows", OrderDAO.selectAllOrder(selectOrderId,selectUserId, selectBookId, selectType, offset, Integer.valueOf("2147483647")));
        }else{
            jo.put("rows", OrderDAO.selectAllOrder(selectOrderId,selectUserId, selectBookId, selectType, offset, limit));
        }
		
		jo.put("total",OrderDAO.selectAllOrderCount(selectOrderId,selectUserId, selectBookId, selectType));
		return jo;
	}
	
	/**
	 * 删除书籍
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteOrder.do")
	@ResponseBody
	public Object deleteBook(final Integer id) {
		final JsonResult result = new JsonResult(true);
		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {
				if (id != null) {
					int deletOrderbyid = OrderDAO.deletOrderbyid(id);
					result.setSuccess(deletOrderbyid > 0 ? true : false);
					result.setMessage(deletOrderbyid > 0 ? "删除成功" : "删除失败");
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
	
	@RequestMapping("/updateOrder.do")
	@ResponseBody
	public Object updateOrder(final HttpServletRequest request,final Integer ID,final String USER_ID,final String BOOK_ID,final String PAY,final Long PRICE ) {
		final JsonResult result = new JsonResult(true);
		controllerTemplate.execute(result, new ServiceCheckCallback() {

			@Override
			public void executeService() {
				if (ID != null) {
					int updateOrder = OrderDAO.updateOrder( USER_ID, BOOK_ID, PAY, PRICE, new Date(), ID);
					result.setSuccess(updateOrder > 0 ? true : false);
					result.setMessage(updateOrder > 0 ? "修改成功" : "修改失败");
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
}
