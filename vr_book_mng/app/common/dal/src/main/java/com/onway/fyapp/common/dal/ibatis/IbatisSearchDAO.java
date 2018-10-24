/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.SearchDAO;

import com.onway.fyapp.common.dal.dataobject.SearchDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.HashMap;

import java.util.Map;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.SearchDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_search.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisSearchDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisSearchDAO extends SqlMapClientDaoSupport implements SearchDAO {
	/**
	 *  Query DB table <tt>vr_search</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from vr_search where (1 = 1)</tt>
	 *
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   selectSearch() throws DataAccessException {


        return getSqlMapClientTemplate().queryForList("MS-SEARCH-SELECT-SEARCH", null);

    }

	/**
	 *  Query DB table <tt>vr_search</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from vr_search where (1 = 1)</tt>
	 *
	 *	@param wordCount
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   selectAllSearch(Integer wordCount, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("wordCount", wordCount);
        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-SEARCH-SELECT-ALL-SEARCH", param);

    }

	/**
	 *  Query DB table <tt>vr_search</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from vr_search where (1 = 1)</tt>
	 *
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   selectSearchType() throws DataAccessException {


        return getSqlMapClientTemplate().queryForList("MS-SEARCH-SELECT-SEARCH-TYPE", null);

    }

}