/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.SysConfigDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>vr_sys_config</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_sys_config.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: SysConfigDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface SysConfigDAO {
	/**
	 *  Query DB table <tt>vr_sys_config</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id, sys_key, sys_value, status, memo, gmt_create, gmt_modified from vr_sys_config</tt>
	 *
	 *	@return List<SysConfigDO>
	 *	@throws DataAccessException
	 */	 
    public List<SysConfigDO> loadAll() throws DataAccessException;

	/**
	 *  Query DB table <tt>vr_sys_config</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, sys_key, sys_value, status, memo, gmt_create, gmt_modified from vr_sys_config where (sys_key = ?)</tt>
	 *
	 *	@param sysKey
	 *	@return SysConfigDO
	 *	@throws DataAccessException
	 */	 
    public SysConfigDO selectByKey(String sysKey) throws DataAccessException;

	/**
	 *  Update DB table <tt>vr_sys_config</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update vr_sys_config set sys_value=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (sys_key = ?)</tt>
	 *
	 *	@param sysValue
	 *	@param sysKey
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateSYSValue(String sysValue, String sysKey) throws DataAccessException;

}