package com.onway.model.conver;

import com.onway.fyapp.common.dal.dataobject.OrderDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.fyapp.common.dal.dataobject.returnObj.OrderInfo;
import com.onway.fyapp.common.dal.dataobject.returnObj.UserInfo;
import com.onway.utils.DateUtil;

/**
 * 
 * @author Yz
 *
 */
public class OrderConver {

	public static OrderInfo buildOrderInfo( OrderDO orderDO) {
		if (null == orderDO)
			return null;

		OrderInfo info = new OrderInfo();
		info.setAmount(orderDO.getPrice().toString());
		info.setBookId(orderDO.getBookId());
		info.setOrderId(orderDO.getOrderId());
		info.setUserId(orderDO.getUserId());
		return info;
	}
}
