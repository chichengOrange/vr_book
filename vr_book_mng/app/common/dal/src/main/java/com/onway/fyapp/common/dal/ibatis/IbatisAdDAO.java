/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.AdDAO;

import com.onway.fyapp.common.dal.dataobject.AdDO;
import java.util.List;
import org.springframework.dao.DataAccessException;

import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.AdDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_ad.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisAdDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisAdDAO extends SqlMapClientDaoSupport implements AdDAO {
	/**
	 *  Query DB table <tt>vr_ad</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from vr_ad</tt>
	 *
	 *	@param adTitle
	 *	@param adType
	 *	@param status
	 *	@param offset
	 *	@param limit
	 *	@return List<AdDO>
	 *	@throws DataAccessException
	 */	 
    public  List<AdDO>   selectByQuery(String adTitle, String adType, String status, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("adTitle", adTitle);
        param.put("adType", adType);
        param.put("status", status);
        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-AD-SELECT-BY-QUERY", param);

    }

	/**
	 *  Query DB table <tt>vr_ad</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from vr_ad</tt>
	 *
	 *	@param adTitle
	 *	@param adType
	 *	@param status
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectCountByQuery(String adTitle, String adType, String status) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("adTitle", adTitle);
        param.put("adType", adType);
        param.put("status", status);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-AD-SELECT-COUNT-BY-QUERY", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Insert one <tt>AdDO</tt> object to DB table <tt>vr_ad</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into vr_ad(AD_TITLE,AD_DESC,AD_POSTER,AD_CONTENT,AD_DAY,AD_TYPE,STATUS,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param ad
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insert(AdDO ad) throws DataAccessException {
    	if (ad == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-AD-INSERT", ad);

        return ad.getId();
    }

	/**
	 *  Query DB table <tt>vr_ad</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, AD_TITLE, AD_DESC, AD_POSTER, AD_CONTENT, AD_DAY, AD_TYPE, STATUS, GMT_CREATE, GMT_MODIFIED from vr_ad where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return AdDO
	 *	@throws DataAccessException
	 */	 
    public  AdDO   selectById(int id) throws DataAccessException {

        Integer param = new Integer(id);

	        return (AdDO) getSqlMapClientTemplate().queryForObject("MS-AD-SELECT-BY-ID", param);
		
    }

	/**
	 *  Update DB table <tt>vr_ad</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update vr_ad set AD_TITLE=?, AD_DESC=?, AD_POSTER=?, AD_CONTENT=?, AD_DAY=?, AD_TYPE=?, STATUS=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ID = ?)</tt>
	 *
	 *	@param adTitle
	 *	@param adDesc
	 *	@param adPoster
	 *	@param adContent
	 *	@param adDay
	 *	@param adType
	 *	@param status
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   update(String adTitle, String adDesc, String adPoster, String adContent, int adDay, String adType, String status, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("adTitle", adTitle);
        param.put("adDesc", adDesc);
        param.put("adPoster", adPoster);
        param.put("adContent", adContent);
        param.put("adDay", new Integer(adDay));
        param.put("adType", adType);
        param.put("status", status);
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-AD-UPDATE", param);
    }

	/**
	 *  Delete records from DB table <tt>vr_ad</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from vr_ad where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteById(int id) throws DataAccessException {
        Integer param = new Integer(id);

        return getSqlMapClientTemplate().delete("MS-AD-DELETE-BY-ID", param);
    }

}