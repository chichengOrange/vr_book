/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.VersionDAO;

import com.onway.fyapp.common.dal.dataobject.VersionDO;
import org.springframework.dao.DataAccessException;
import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.VersionDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_version.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisVersionDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisVersionDAO extends SqlMapClientDaoSupport implements VersionDAO {
	/**
	 *  Insert one <tt>VersionDO</tt> object to DB table <tt>vr_version</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into vr_version(ID,VERSION_NUM,VERSION_TYPE,STATUS,MEMO,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param version
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int insert(VersionDO version) throws DataAccessException {
    	if (version == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-VERSION-INSERT", version);

        return version.getId();
    }

	/**
	 *  Query DB table <tt>vr_version</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, VERSION_NUM, VERSION_TYPE, STATUS, MEMO, GMT_CREATE, GMT_MODIFIED from vr_version where ((VERSION_NUM = ?) AND (VERSION_TYPE = ?) AND (STATUS = ?))</tt>
	 *
	 *	@param versionNum
	 *	@param versionType
	 *	@param status
	 *	@return VersionDO
	 *	@throws DataAccessException
	 */	 
    public VersionDO queryVersion(String versionNum, String versionType, String status) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("versionNum", versionNum);
        param.put("versionType", versionType);
        param.put("status", status);

        return (VersionDO) getSqlMapClientTemplate().queryForObject("MS-VERSION-QUERY-VERSION", param);

    }

	/**
	 *  Query DB table <tt>vr_version</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from vr_version</tt>
	 *
	 *	@param type
	 *	@param status
	 *	@return VersionDO
	 *	@throws DataAccessException
	 */	 
    public VersionDO queryNewVersion(String type, String status) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("type", type);
        param.put("status", status);

        return (VersionDO) getSqlMapClientTemplate().queryForObject("MS-VERSION-QUERY-NEW-VERSION", param);

    }

}