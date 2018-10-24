/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.SysRoleDAO;

import com.onway.fyapp.common.dal.dataobject.SysRoleDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.List;

import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.SysRoleDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/sys_role.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisSysRoleDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisSysRoleDAO extends SqlMapClientDaoSupport implements SysRoleDAO {
	/**
	 *  Query DB table <tt>sys_role</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from sys_role</tt>
	 *
	 *	@param name
	 *	@param offset
	 *	@param limit
	 *	@return List<SysRoleDO>
	 *	@throws DataAccessException
	 */	 
    public  List<SysRoleDO>   selectallrole(String name, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("name", name);
        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-SYS-ROLE-SELECTALLROLE", param);

    }

	/**
	 *  Query DB table <tt>sys_role</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from sys_role</tt>
	 *
	 *	@param name
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectallrolecount(String name) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("name", name);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-SYS-ROLE-SELECTALLROLECOUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Insert one <tt>SysRoleDO</tt> object to DB table <tt>sys_role</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into sys_role(ROLE_ID,NAME,CREATER,GMT_CREATE) values (?, ?, ?, ?)</tt>
	 *
	 *	@param sysRole
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insertrole(SysRoleDO sysRole) throws DataAccessException {
    	if (sysRole == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-SYS-ROLE-INSERTROLE", sysRole);

        return sysRole.getId();
    }

	/**
	 *  Update DB table <tt>sys_role</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update sys_role set NAME=?, MODIFIER=?, GMT_MODIFIED=? where (id = ?)</tt>
	 *
	 *	@param name
	 *	@param modifier
	 *	@param gmtModified
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updaterole(String name, String modifier, Date gmtModified, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("name", name);
        param.put("modifier", modifier);
        param.put("gmtModified", gmtModified);
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-SYS-ROLE-UPDATEROLE", param);
    }

	/**
	 *  Update DB table <tt>sys_role</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update sys_role set JURISDICTION=? where (id = ?)</tt>
	 *
	 *	@param jurisdiction
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updatejurisidiction(String jurisdiction, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("jurisdiction", jurisdiction);
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-SYS-ROLE-UPDATEJURISIDICTION", param);
    }

	/**
	 *  Query DB table <tt>sys_role</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select JURISDICTION from sys_role where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return String
	 *	@throws DataAccessException
	 */	 
    public  String   selectjurisidiction(int id) throws DataAccessException {

        Integer param = new Integer(id);

	        return (String) getSqlMapClientTemplate().queryForObject("MS-SYS-ROLE-SELECTJURISIDICTION", param);
		
    }

	/**
	 *  Query DB table <tt>sys_role</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from sys_role</tt>
	 *
	 *	@return List< Map<String,Object> >
	 *	@throws DataAccessException
	 */	 
    public  List< Map<String,Object> >   selectallroletouser() throws DataAccessException {


	return  getSqlMapClientTemplate().queryForList("MS-SYS-ROLE-SELECTALLROLETOUSER", null);
		
    }

	/**
	 *  Query DB table <tt>sys_role</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select JURISDICTION from sys_role where (ROLE_ID = ?)</tt>
	 *
	 *	@param roleId
	 *	@return String
	 *	@throws DataAccessException
	 */	 
    public  String   selectByRoleId(String roleId) throws DataAccessException {

 
	        return (String) getSqlMapClientTemplate().queryForObject("MS-SYS-ROLE-SELECT-BY-ROLE-ID", roleId);
		
    }

	/**
	 *  Delete records from DB table <tt>sys_role</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from sys_role where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteRole(int id) throws DataAccessException {
        Integer param = new Integer(id);

        return getSqlMapClientTemplate().delete("MS-SYS-ROLE-DELETE-ROLE", param);
    }

}