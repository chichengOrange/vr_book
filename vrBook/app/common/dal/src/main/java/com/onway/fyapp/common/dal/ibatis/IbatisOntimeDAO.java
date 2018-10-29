/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.OntimeDAO;

import com.onway.fyapp.common.dal.dataobject.OntimeDO;
import org.springframework.dao.DataAccessException;
import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.OntimeDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_ontime.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisOntimeDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisOntimeDAO extends SqlMapClientDaoSupport implements OntimeDAO {
	/**
	 *  Insert one <tt>OntimeDO</tt> object to DB table <tt>vr_ontime</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into vr_ontime(ID,USER_ID,START_TIME,END_TIME,IMEI,QUIT_INTERFACE,GMT_CREATE,GMT_MODIFIED) values (?, ?, CURRENT_TIMESTAMP, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param ontime
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int insert(OntimeDO ontime) throws DataAccessException {
    	if (ontime == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-ONTIME-INSERT", ontime);

        return ontime.getId();
    }

	/**
	 *  Query DB table <tt>vr_ontime</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from vr_ontime</tt>
	 *
	 *	@param userId
	 *	@param imei
	 *	@return OntimeDO
	 *	@throws DataAccessException
	 */	 
    public OntimeDO queryByUserId(String userId, String imei) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("userId", userId);
        param.put("imei", imei);

        return (OntimeDO) getSqlMapClientTemplate().queryForObject("MS-ONTIME-QUERY-BY-USER-ID", param);

    }

	/**
	 *  Update DB table <tt>vr_ontime</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update vr_ontime set GMT_MODIFIED=CURRENT_TIMESTAMP, END_TIME=CURRENT_TIMESTAMP, QUIT_INTERFACE=? where (ID = ?)</tt>
	 *
	 *	@param quitInterface
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateByUserId(String quitInterface, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("quitInterface", quitInterface);
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-ONTIME-UPDATE-BY-USER-ID", param);
    }

}