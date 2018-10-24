/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.OrderDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Date;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>vr_order</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_order.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: OrderDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface OrderDAO {
	/**
	 *  Query DB table <tt>vr_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, ORDER_ID, USER_ID, PAY_STATUS, BOOK_ID, PRICE, GMT_CREATE, GMT_MODIFIED from vr_order</tt>
	 *
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public  List<OrderDO>   loadAllOrder() throws DataAccessException;

	/**
	 *  Query DB table <tt>vr_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from vr_order</tt>
	 *
	 *	@param orderId
	 *	@param userId
	 *	@param bookId
	 *	@param payStatus
	 *	@param offset
	 *	@param limit
	 *	@return List< Map<String,Object> >
	 *	@throws DataAccessException
	 */	 
    public  List< Map<String,Object> >   selectAllOrder(String orderId, String userId, String bookId, String payStatus, Integer offset, Integer limit) throws DataAccessException;

	/**
	 *  Query DB table <tt>vr_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from vr_order</tt>
	 *
	 *	@param orderId
	 *	@param userId
	 *	@param bookId
	 *	@param payStatus
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectAllOrderCount(String orderId, String userId, String bookId, String payStatus) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>vr_order</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from vr_order where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deletOrderbyid(int id) throws DataAccessException;

	/**
	 *  Update DB table <tt>vr_order</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update vr_order set USER_ID=?, BOOK_ID=?, PAY_STATUS=?, PRICE=?, GMT_MODIFIED=? where (ID = ?)</tt>
	 *
	 *	@param userId
	 *	@param bookId
	 *	@param payStatus
	 *	@param price
	 *	@param gmtModified
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateOrder(String userId, String bookId, String payStatus, long price, Date gmtModified, int id) throws DataAccessException;

}