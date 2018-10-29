/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.ButtonLogDAO;

import com.onway.fyapp.common.dal.dataobject.ButtonLogDO;
import org.springframework.dao.DataAccessException;
import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.ButtonLogDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_button_log.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisButtonLogDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisButtonLogDAO extends SqlMapClientDaoSupport implements ButtonLogDAO {
	/**
	 *  Insert one <tt>ButtonLogDO</tt> object to DB table <tt>vr_button_log</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into vr_button_log(ID,USER_ID,BUTTON,CLICK_COUNT,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param buttonLog
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int insert(ButtonLogDO buttonLog) throws DataAccessException {
    	if (buttonLog == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-BUTTON-LOG-INSERT", buttonLog);

        return buttonLog.getId();
    }

	/**
	 *  Update DB table <tt>vr_button_log</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update vr_button_log set GMT_MODIFIED=CURRENT_TIMESTAMP, CLICK_COUNT=(CLICK_COUNT + 1) where ((USER_ID = ?) AND (BUTTON = ?))</tt>
	 *
	 *	@param userId
	 *	@param button
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateByUserId(String userId, String button) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("userId", userId);
        param.put("button", button);

        return getSqlMapClientTemplate().update("MS-BUTTON-LOG-UPDATE-BY-USER-ID", param);
    }

	/**
	 *  Query DB table <tt>vr_button_log</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, USER_ID, BUTTON, CLICK_COUNT, GMT_CREATE, GMT_MODIFIED from vr_button_log where ((USER_ID = ?) AND (BUTTON = ?))</tt>
	 *
	 *	@param userId
	 *	@param button
	 *	@return ButtonLogDO
	 *	@throws DataAccessException
	 */	 
    public ButtonLogDO queryByUserIdAndButton(String userId, String button) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("userId", userId);
        param.put("button", button);

        return (ButtonLogDO) getSqlMapClientTemplate().queryForObject("MS-BUTTON-LOG-QUERY-BY-USER-ID-AND-BUTTON", param);

    }

}