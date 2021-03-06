/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.SmDAO;

import com.onway.fyapp.common.dal.dataobject.SmDO;
import org.springframework.dao.DataAccessException;
import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.SmDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_sms.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisSmDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisSmDAO extends SqlMapClientDaoSupport implements SmDAO {
	/**
	 *  Insert one <tt>SmDO</tt> object to DB table <tt>vr_sms</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into vr_sms(CELL,VERIFY_CODE,SMS_STATUS,GMT_CREATE,MEMO) values (?, ?, ?, CURRENT_TIMESTAMP, ?)</tt>
	 *
	 *	@param sm
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int insert(SmDO sm) throws DataAccessException {
    	if (sm == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-SM-INSERT", sm);

        return sm.getId();
    }

	/**
	 *  Query DB table <tt>vr_sms</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from vr_sms</tt>
	 *
	 *	@param cell
	 *	@return SmDO
	 *	@throws DataAccessException
	 */	 
    public SmDO selectSmsBycell(String cell) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("cell", cell);

        return (SmDO) getSqlMapClientTemplate().queryForObject("MS-SM-SELECT-SMS-BYCELL", param);

    }

}