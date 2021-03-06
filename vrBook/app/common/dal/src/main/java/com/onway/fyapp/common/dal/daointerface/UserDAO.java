/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.UserDO;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>vr_user</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_user.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: UserDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface UserDAO {
	/**
	 *  Insert one <tt>UserDO</tt> object to DB table <tt>vr_user</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into vr_user(ID,USER_ID,USER_NAME,OPEN_ID,HEAD_URL,CELL,PSW,PROVINCE,CITY,AREA,STATUS,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param user
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int insertUser(UserDO user) throws DataAccessException;

	/**
	 *  Query DB table <tt>vr_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from vr_user</tt>
	 *
	 *	@param account
	 *	@param passWord
	 *	@return UserDO
	 *	@throws DataAccessException
	 */	 
    public UserDO userLogin(String account, String passWord) throws DataAccessException;

	/**
	 *  Query DB table <tt>vr_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, USER_ID, USER_NAME, OPEN_ID, HEAD_URL, CELL, PSW, PROVINCE, CITY, AREA, STATUS, GMT_CREATE, GMT_MODIFIED from vr_user where ((1 = 1) AND (CELL = ?))</tt>
	 *
	 *	@param cell
	 *	@return UserDO
	 *	@throws DataAccessException
	 */	 
    public UserDO selectUserByCell(String cell) throws DataAccessException;

	/**
	 *  Query DB table <tt>vr_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, USER_ID, USER_NAME, OPEN_ID, HEAD_URL, CELL, PSW, PROVINCE, CITY, AREA, STATUS, GMT_CREATE, GMT_MODIFIED from vr_user where ((1 = 1) AND (USER_ID = ?))</tt>
	 *
	 *	@param userId
	 *	@return UserDO
	 *	@throws DataAccessException
	 */	 
    public UserDO selectUserByUserId(String userId) throws DataAccessException;

	/**
	 *  Update DB table <tt>vr_user</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update vr_user set PSW=? where (CELL = ?)</tt>
	 *
	 *	@param psw
	 *	@param cell
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updatePswByCell(String psw, String cell) throws DataAccessException;

	/**
	 *  Query DB table <tt>vr_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, USER_ID, USER_NAME, OPEN_ID, HEAD_URL, CELL, PSW, PROVINCE, CITY, AREA, STATUS, GMT_CREATE, GMT_MODIFIED from vr_user where ((1 = 1) AND (OPEN_ID = ?))</tt>
	 *
	 *	@param openId
	 *	@return UserDO
	 *	@throws DataAccessException
	 */	 
    public UserDO selectUserByOpenId(String openId) throws DataAccessException;

}