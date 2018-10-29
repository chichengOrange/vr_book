/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.SearchDO;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>vr_search</tt>.
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
 * @version $Id: SearchDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface SearchDAO {
	/**
	 *  Insert one <tt>SearchDO</tt> object to DB table <tt>vr_search</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into vr_search(ID,USER_ID,KEY_WORDS,TYPE,GMT_CREATE) values (?, ?, ?, ?, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param search
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int insert(SearchDO search) throws DataAccessException;

}