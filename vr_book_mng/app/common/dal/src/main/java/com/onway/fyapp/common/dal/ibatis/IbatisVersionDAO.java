/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.VersionDAO;

import com.onway.fyapp.common.dal.dataobject.VersionDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.HashMap;

import java.util.Map;

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
	 *  Query DB table <tt>vr_version</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from vr_version</tt>
	 *
	 *	@param versionNum
	 *	@param versionType
	 *	@param status
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   selectVersion(String versionNum, String versionType, String status, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("versionNum", versionNum);
        param.put("versionType", versionType);
        param.put("status", status);
        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-VERSION-SELECT-VERSION", param);

    }

	/**
	 *  Query DB table <tt>vr_version</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from vr_version</tt>
	 *
	 *	@param versionNum
	 *	@param versionType
	 *	@param status
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectVersionCount(String versionNum, String versionType, String status) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("versionNum", versionNum);
        param.put("versionType", versionType);
        param.put("status", status);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-VERSION-SELECT-VERSION-COUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Insert one <tt>VersionDO</tt> object to DB table <tt>vr_version</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into vr_version(VERSION_NUM,VERSION_TYPE,STATUS,DOWNLOAD_URL,MEMO,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param version
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insert(VersionDO version) throws DataAccessException {
    	if (version == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-VERSION-INSERT", version);

        return version.getId();
    }

	/**
	 *  Delete records from DB table <tt>vr_version</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from vr_version where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   delete(int id) throws DataAccessException {
        Integer param = new Integer(id);

        return getSqlMapClientTemplate().delete("MS-VERSION-DELETE", param);
    }

	/**
	 *  Update DB table <tt>vr_version</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update vr_version set VERSION_NUM=?, VERSION_TYPE=?, STATUS=?, DOWNLOAD_URL=?, MEMO=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ID = ?)</tt>
	 *
	 *	@param versionNum
	 *	@param versionType
	 *	@param status
	 *	@param downloadUrl
	 *	@param memo
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updatVersion(String versionNum, String versionType, String status, String downloadUrl, String memo, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("versionNum", versionNum);
        param.put("versionType", versionType);
        param.put("status", status);
        param.put("downloadUrl", downloadUrl);
        param.put("memo", memo);
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-VERSION-UPDAT-VERSION", param);
    }

}