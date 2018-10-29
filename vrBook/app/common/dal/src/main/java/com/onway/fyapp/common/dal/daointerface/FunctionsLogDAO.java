/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.FunctionsLogDO;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>vr_functions_log</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_functions_log.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: FunctionsLogDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface FunctionsLogDAO {
	/**
	 *  Insert one <tt>FunctionsLogDO</tt> object to DB table <tt>vr_functions_log</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into vr_functions_log(ID,USER_ID,FUNCTIONS,CLICK_COUNT,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param functionsLog
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int insert(FunctionsLogDO functionsLog) throws DataAccessException;

	/**
	 *  Update DB table <tt>vr_functions_log</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update vr_functions_log set GMT_MODIFIED=CURRENT_TIMESTAMP, CLICK_COUNT=(CLICK_COUNT + 1) where ((USER_ID = ?) AND (FUNCTIONS = ?))</tt>
	 *
	 *	@param userId
	 *	@param functions
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateByUserId(String userId, String functions) throws DataAccessException;

	/**
	 *  Query DB table <tt>vr_functions_log</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, USER_ID, FUNCTIONS, CLICK_COUNT, GMT_CREATE, GMT_MODIFIED from vr_functions_log where ((USER_ID = ?) AND (FUNCTIONS = ?))</tt>
	 *
	 *	@param userId
	 *	@param functions
	 *	@return FunctionsLogDO
	 *	@throws DataAccessException
	 */	 
    public FunctionsLogDO queryByUserIdAndFunction(String userId, String functions) throws DataAccessException;

}