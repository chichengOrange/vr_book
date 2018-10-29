package com.onway.web.controller.order;

import java.text.MessageFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.fyapp.common.dal.daointerface.BookDAO;
import com.onway.fyapp.common.dal.daointerface.OrderDAO;
import com.onway.fyapp.common.dal.dataobject.BookDO;
import com.onway.fyapp.common.dal.dataobject.OrderDO;
import com.onway.fyapp.common.dal.dataobject.returnObj.OrderInfo;
import com.onway.model.conver.OrderConver;
import com.onway.model.enums.BizTypeEnum;
import com.onway.model.enums.ErrorCode;
import com.onway.model.enums.OrderStatusEnum;
import com.onway.platform.common.enums.BaseResultCodeEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonQueryResult;
import com.onway.web.controller.result.JsonResult;
/**
 * 
 * @author lishuaikai
 *
 * 2018年7月31日 上午11:48:11
 */
@Controller
public class OrderController extends AbstractController{

	@Resource
	private OrderDAO orderDAO;
	
	@Resource
	private BookDAO bookDAO;
	
	/**
	 * 创建订单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "initOrder.do", method = RequestMethod.POST)
	@ResponseBody
	public Object initOrder(HttpServletRequest request){
		JsonQueryResult<OrderInfo> result = new JsonQueryResult<OrderInfo>(true);
		String userId = request.getParameter("userId");
		String bookId = request.getParameter("bookId");
		try {
			AssertUtil.notBlank(userId, "用户ID不能为空");
			AssertUtil.notBlank(bookId, "书籍ID不能为空");

			String orderId = userId = codeGenerateComponent.nextCodeByType(BizTypeEnum.ORDER_NO);
			BookDO bookDO = bookDAO.queryBookById(bookId);
			OrderDO orderDO = new OrderDO();
			orderDO.setBookId(bookId);
			orderDO.setUserId(userId);
			orderDO.setOrderId(orderId);
			orderDO.setPayStatus(OrderStatusEnum.INIT.getCode());
			orderDO.setPrice(bookDO.getPrice());
			int insert = orderDAO.insert(orderDO);
			result.setBizSucc(insert > 0 ? true :false);
			result.setObj(OrderConver.buildOrderInfo(orderDO));
		} catch (BaseRuntimeException e) {
			// TODO: handle exception
			if(e.getCode().equals("NULL_ARGUMENT")){
				return new JsonResult(false, BaseResultCodeEnum.NULL_ARGUMENT.getCode(), "参数为空");
			}
			return new JsonResult(false, ErrorCode.OPERATION_FAIL.getCode(), MessageFormat.format("创建订单失败：{0}", new Object[]{e.getMessage()}));
		} catch (Exception e) {
			// TODO: handle exception
			return new JsonResult(false, ErrorCode.SYSTEM_FAILURE.getCode(), MessageFormat.format("创建订单异常：{0}", new Object[]{e.getMessage()}));
		}
		
		return result;
	}
}
