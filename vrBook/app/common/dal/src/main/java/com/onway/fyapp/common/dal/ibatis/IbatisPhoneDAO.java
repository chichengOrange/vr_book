/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.PhoneDAO;

import com.onway.fyapp.common.dal.dataobject.PhoneDO;
import org.springframework.dao.DataAccessException;
import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.PhoneDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/vr_phone.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisPhoneDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisPhoneDAO extends SqlMapClientDaoSupport implements PhoneDAO {
	/**
	 *  Insert one <tt>PhoneDO</tt> object to DB table <tt>vr_phone</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into vr_phone(ID,USER_ID,PHONE_TYPE,IMEI,SYS_VERSION,PROVINCE,CITY,AREA,GMT_CREATE) values (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param phone
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int insert(PhoneDO phone) throws DataAccessException {
    	if (phone == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-PHONE-INSERT", phone);

        return phone.getId();
    }

}